package de.gwdg.metadataqa.api.util;

import de.gwdg.metadataqa.api.calculator.SkippedEntryChecker;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.model.pathcache.JsonPathCache;
import de.gwdg.metadataqa.api.model.pathcache.PathCache;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.schema.edm.EdmFullBeanSchema;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SkippedEntitySelectorTest {

  @Test
  public void testIsCollectionSkippable() throws URISyntaxException, IOException {
    String jsonString = FileUtils.readFirstLineFromResource("issue-examples/issue48.json");
    Schema schema = new EdmFullBeanSchema();
    JsonPathCache cache = new JsonPathCache(jsonString);

    SkippedEntryChecker checker = new SkippedEntryCheckerImpl();
    SkippedEntitySelector selector = new SkippedEntitySelector();

    List<String> skippableIds = Arrays.asList("/proxy/provider/2064125/Museu_ProvidedCHO_museum_digital_96__technical_number_");
    var i = 0;
    DataElement providerProxyBranch = schema.getCollectionPaths().get(1);
    Object rawJsonFragment = cache.getFragment(providerProxyBranch.getPath());
    List<Object> jsonFragments = Converter.jsonObjectToList(rawJsonFragment, schema);
    Object jsonFragment = jsonFragments.get(i);
    assertTrue(selector.isCollectionSkippable(skippableIds, providerProxyBranch, i, cache, jsonFragment));
  }

  @Test
  public void testSetSkippedEntryChecker() {
    SkippedEntitySelector selector = new SkippedEntitySelector();
    assertNull(selector.getSkippedEntryChecker());
    selector.setSkippedEntryChecker(new SkippedEntryCheckerImpl());
    assertNotNull(selector.getSkippedEntryChecker());
  }

  @Test
  public void constructor_withCHecker() {
    SkippedEntitySelector selector = new SkippedEntitySelector(new SkippedEntryCheckerImpl());
    assertNotNull(selector.getSkippedEntryChecker());
  }

  class SkippedEntryCheckerImpl implements SkippedEntryChecker {

    @Override
    public List<String> getSkippableCollectionIds(PathCache cache) {
      return null;
    }

    @Override
    public <T extends XmlFieldInstance> String extractId(T value) {
      return null;
    }
  }
}