package de.gwdg.metadataqa.api.model;

import de.gwdg.metadataqa.api.json.DataElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The functional categories.
 */
public enum Category {

  MANDATORY("mandatory"),
  DESCRIPTIVENESS("descriptiveness"),
  SEARCHABILITY("searchability"),
  CONTEXTUALIZATION("contextualization"),
  IDENTIFICATION("identification"),
  BROWSING("browsing"),
  VIEWING("viewing"),
  REUSABILITY("re-usability"),
  MULTILINGUALITY("multilinguality");

  private final String name;

  Category(String name) {
    this.name = name;
  }

  public static List<String> extractCategories(Collection<DataElement> paths) {
    return extractCategories(paths, false);
  }

  public static List<String> extractCategories(Collection<DataElement> paths,
                                               boolean reorder) {
    List<String> existingCategories = extractExistingCategories(paths);

    if (reorder)
      existingCategories = reorder(existingCategories);

    return existingCategories;
  }

  private static List<String> extractExistingCategories(Collection<DataElement> dataElements) {
    List<String> existingCategories = new ArrayList<>();
    for (DataElement dataElement : dataElements)
      if (dataElement.isActive())
        for (String category : dataElement.getCategories())
          if (!existingCategories.contains(category))
            existingCategories.add(category);

    return existingCategories;
  }

  private static List<String> reorder(List<String> existingCategories) {
    List<String> goodOrder = new ArrayList<>();
    for (Category category : values())
      if (existingCategories.contains(category.toString())) {
        goodOrder.add(category.toString());
        existingCategories.remove(category.toString());
      }

    goodOrder.addAll(existingCategories);

    return goodOrder;
  }
}
