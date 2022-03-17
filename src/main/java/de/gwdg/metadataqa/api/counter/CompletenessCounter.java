package de.gwdg.metadataqa.api.counter;

import de.gwdg.metadataqa.api.model.Category;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.json.FieldGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Completeness counter
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class CompletenessCounter implements Serializable {

  public static final String TOTAL = "TOTAL";
  private static final long serialVersionUID = -3954161093449269927L;
  private Map<String, BasicCounter> basicCounters;
  private Schema schema;

  public CompletenessCounter(Schema schema) {
    this.schema = schema;
    initialize();
  }

  public BasicCounter get(String key) {
    return basicCounters.get(key);
  }

  public FieldCounter<Double> getFieldCounter() {
    FieldCounter<Double> fieldCounter = new FieldCounter<>();
    for (Map.Entry<String, BasicCounter> counter : basicCounters.entrySet()) {
      counter.getValue().calculate();
      fieldCounter.put(counter.getKey(), counter.getValue().getResult());
    }
    return fieldCounter;
  }

  public void calculateResults() {
    for (BasicCounter counter : basicCounters.values()) {
      counter.calculate();
    }
  }

  public void increaseInstance(List<String> categories) {
    basicCounters.get(TOTAL).increaseInstance();
    for (String category : categories)
      basicCounters.get(category).increaseInstance();
  }

  public void increaseInstance(String category, boolean increase) {
    basicCounters.get(category).increaseTotal();
    if (increase) {
      basicCounters.get(category).increaseInstance();
    }
  }

  public void increaseTotal(List<String> categories) {
    basicCounters.get(TOTAL).increaseTotal();
    for (String category : categories)
      if (category != null)
        basicCounters.get(category).increaseTotal();
  }

  private void initialize() {
    basicCounters = new LinkedHashMap<>();
    for (String name : getHeaders(schema))
      basicCounters.put(name, new BasicCounter());
  }

  public static List<String> getHeaders(Schema schema) {
    List<String> headers = new ArrayList<>();
    headers.add(TOTAL);

    for (String category : schema.getCategories())
      headers.add(category);

    for (FieldGroup group: schema.getFieldGroups())
      if (!headers.contains(group.getCategory()))
        headers.add(group.getCategory());

    return headers;
  }

  public BasicCounter getStatComponent(Category category) {
    return basicCounters.get(category.name());
  }
}
