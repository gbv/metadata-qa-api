package de.gwdg.metadataqa.api.calculator;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.schema.edm.EdmOaiPmhJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import net.minidev.json.JSONArray;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class NodeEnabledCalculatorTest {

  Schema schema = new EdmOaiPmhJsonSchema();

  @Test
  public void test() throws URISyntaxException, IOException {
    String jsonString = FileUtils.readFirstLineFromResource("general/test.json");

    Object jsonDocument = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
    for (DataElement collectionBranch : schema.getCollectionPaths()) {
      Object rawCollection = null;
      try {
        rawCollection = JsonPath.read(jsonDocument, collectionBranch.getPath());
      } catch (PathNotFoundException e) {}

      if (rawCollection != null) {
        if (rawCollection instanceof JSONArray) {
          JSONArray collection = (JSONArray)rawCollection;
          collection.forEach(
            node -> processNode(node, collectionBranch.getChildren())
          );
        } else {
          processNode(rawCollection, collectionBranch.getChildren());
        }
      }
    }
  }

  private void processNode(Object node, List<DataElement> fields) {
    for (DataElement fieldBranch : fields) {
      try {
        Object val = JsonPath.read(node, fieldBranch.getPath());
        if (val != null) {
          if ("ProvidedCHO/rdf:about".equals(fieldBranch.getLabel()))
            assertTrue(val instanceof String);
          if ("Proxy/dc:title".equals(fieldBranch.getLabel()))
            assertTrue(val instanceof JSONArray);
        }
      } catch (PathNotFoundException e) {}
    }
  }
}
