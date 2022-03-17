package de.gwdg.metadataqa.api.abbreviation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>The AbbreviationManager takes care about abbreviations: parse source file,
 * lookup and save. Abbreviations are used in output, where you want a short
 * (numeric) entry instead of a long text. So for example if the source record
 * contain the value "Bavarian State Library", and we map number 5 to it
 * the project will output 5 instead of the longer string.</p>
 *
 * <p>It can read two types of files. For plain files the abbreviatable entry
 * will be the full line, and the abbreviated value will be the line number.
 * For comma separated files the first field will be the abbreviated value,
 * and the rest of the line will be the key to abbreviate.</p>
 *
 * Example for plain file
 * <pre>
 * National Library of France
 * Österreichische Nationalbibliothek - Austrian National Library
 * National Library of the Netherlands
 * </pre>
 *
 * Example for comma separated file:
 * <pre>
 * 1;National Library of France
 * 2;Österreichische Nationalbibliothek - Austrian National Library
 * 3;National Library of the Netherlands
 * </pre>
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class AbbreviationManager implements Serializable {

  private static final Logger LOGGER = Logger.getLogger(AbbreviationManager.class.getCanonicalName());
  private static final long serialVersionUID = 8680703008286808252L;
  private Map<String, Integer> data;
  private static FileSystem fs;
  private String fileName;

  public AbbreviationManager() {
    data = new LinkedHashMap<>();
  }

  protected void initialize(String pFileName) {
    initialize(pFileName, false);
  }

  /**
  * Initialize abbreviations. It reads a file and fulfill the abbreviation map.
  * @param pFileName The name of input file
  * @param parse Whether parse the file to extract the abbreviation or use line number as the abbreviated value
  */
  protected void initialize(String pFileName, boolean parse) {
    this.fileName = pFileName;
    Path path = null;
    try {
      path = getPath(pFileName);
      List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
      var i = 1;
      for (String line : lines) {
        processLine(line, i, parse);
      }
    } catch (URISyntaxException | IOException | FileSystemNotFoundException ex) {
      LOGGER.severe(String.format("Error with file: %s, path: %s.", pFileName, path));
      LOGGER.severe(ex.getLocalizedMessage());
    }
  }

  public void processLine(String line, int i, boolean parse) throws NumberFormatException {
    if (parse && line.contains(";")) {
      String[] parts = line.split(";", 2);
      data.put(parts[1].replace("\\n", "\n"), Integer.parseInt(parts[0]));
    } else {
      data.put(line, i++);
    }
  }

  /**
   * Looking for the abbreviated value of a text.
   *
   * @param entry A key to abbreviate
   * @return The abbreviated value
   */
  public Integer lookup(String entry) {
    if (!data.containsKey(entry)) {
      var oldsize = data.size();
      data.put(entry, data.size() + 1);
      var msg = String.format(
        "new entry: %s (size: %d -> %d)",
        entry, oldsize, data.size()
      );
      if (fileName != null) {
        msg += " " + fileName;
      }
      LOGGER.info(msg);
    }
    return data.get(entry);
  }

  /**
   * Save the abbreviations into a file.
   *
   * @param fileName The file name.
   *
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   */
  public void save(String fileName)
      throws FileNotFoundException, UnsupportedEncodingException {
    try (var writer = new PrintWriter(fileName, "UTF-8")) {
      for (Map.Entry<String, Integer> entry : data.entrySet())
        writer.println(String.format("%d;%s", entry.getValue(), entry.getKey()));
      writer.flush();
    }
  }

  public String searchById(Integer id) {
    if (data.containsValue(id))
      for (Map.Entry<String, Integer> entry : data.entrySet())
        if (entry.getValue().equals(id))
          return entry.getKey();

    return null;
  }

  /**
   * A get a java.nio.file.Path object from a file name.
   * @param fileName The file name
   * @return The Path object
   * @throws IOException
   * @throws URISyntaxException
   */
  private static Path getPath(String fileName)
      throws IOException, URISyntaxException {
    Path path;
    var url = AbbreviationManager.class.getClassLoader().getResource(fileName);
    if (url == null)
      throw new IOException(String.format("File %s is not existing", fileName));

    var uri = url.toURI();
    Map<String, String> env = new HashMap<>();
    if (uri.toString().contains("!")) {
      String[] parts = uri.toString().split("!", 2);
      if (fs == null)
        fs = FileSystems.newFileSystem(URI.create(parts[0]), env);
      path = fs.getPath(parts[1]);
    } else {
      path = Paths.get(uri);
    }
    return path;
  }

  public Map<String, Integer> getData() {
    return data;
  }

  /**
   * Do not create new abbreviation, simply returns an existing or a default number
   * @param key
   * @param defaultValue
   * @return
   */
  public Integer getOrDefault(String key, Integer defaultValue) {
    return data.getOrDefault(key, defaultValue);
  }
}
