package de.gwdg.metadataqa.api.schema.edm;

import de.gwdg.metadataqa.api.json.FieldGroup;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.model.Category;
import de.gwdg.metadataqa.api.schema.Format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Europeana Data Model (EDM) representation of the metadata schema interface.
 * This class represents what fields will be analyzed in different measurements.
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class EdmOaiPmLimitedJsonSchema extends EdmSchema implements Serializable {

  private static final long serialVersionUID = -9205604492275740771L;

  public EdmOaiPmLimitedJsonSchema() {
    initialize();
  }

  private void initialize() {
    longSubjectPath = "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:subject']";
    titlePath = "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:title']";
    descriptionPath = "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:description']";

    addPath(new DataElement("edm:ProvidedCHO/@about",
      "$.['edm:ProvidedCHO'][0]['@about']")
      .setCategories(Category.MANDATORY));
    addPath(new DataElement("Proxy/dc:title",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:title']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
        Category.IDENTIFICATION, Category.MULTILINGUALITY)
      .setIndexField("dc_title_txt")
    );
    addPath(new DataElement("Proxy/dcterms:alternative",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:alternative']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
        Category.IDENTIFICATION, Category.MULTILINGUALITY)
      .setIndexField("dcterms_alternative_txt")
    );
    addPath(new DataElement("Proxy/dc:description",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:description']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
        Category.CONTEXTUALIZATION, Category.IDENTIFICATION,
        Category.MULTILINGUALITY)
      .setIndexField("dc_description_txt")
    );
    addPath(new DataElement("Proxy/dc:creator",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:creator']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
        Category.CONTEXTUALIZATION, Category.BROWSING));
    addPath(new DataElement("Proxy/dc:publisher",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:publisher']")
      .setCategories(Category.SEARCHABILITY, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dc:contributor",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:contributor']")
      .setCategories(Category.SEARCHABILITY));
    addPath(new DataElement("Proxy/dc:type",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:type']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
        Category.IDENTIFICATION, Category.BROWSING));
    addPath(new DataElement("Proxy/dc:identifier",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:identifier']")
      .setCategories(Category.IDENTIFICATION));
    addPath(new DataElement("Proxy/dc:language",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:language']")
      .setCategories(Category.DESCRIPTIVENESS, Category.MULTILINGUALITY));
    addPath(new DataElement("Proxy/dc:coverage",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:coverage']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
        Category.BROWSING));
    addPath(new DataElement("Proxy/dcterms:temporal",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:temporal']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/dcterms:spatial",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:spatial']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/dc:subject",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:subject']")
      .setCategories(Category.DESCRIPTIVENESS, Category.SEARCHABILITY,
      Category.CONTEXTUALIZATION, Category.MULTILINGUALITY));
    addPath(new DataElement("Proxy/dc:date",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:date']")
      .setCategories(Category.IDENTIFICATION, Category.BROWSING,
      Category.REUSABILITY));
    addPath(new DataElement("Proxy/dcterms:created",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:created']")
      .setCategories(Category.IDENTIFICATION, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dcterms:issued",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:issued']")
      .setCategories(Category.IDENTIFICATION, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dcterms:extent",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:extent']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dcterms:medium",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:medium']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dcterms:provenance",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:provenance']")
      .setCategories(Category.DESCRIPTIVENESS));
    addPath(new DataElement("Proxy/dcterms:hasPart",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:hasPart']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/dcterms:isPartOf",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dcterms:isPartOf']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/dc:format",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:format']")
      .setCategories(Category.DESCRIPTIVENESS, Category.REUSABILITY));
    addPath(new DataElement("Proxy/dc:source",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:source']")
      .setCategories(Category.DESCRIPTIVENESS));
    addPath(new DataElement("Proxy/dc:rights",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:rights']")
      .setCategories(Category.REUSABILITY));
    addPath(new DataElement("Proxy/dc:relation",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:relation']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/edm:isNextInSequence",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['edm:isNextInSequence']")
      .setCategories(Category.SEARCHABILITY, Category.CONTEXTUALIZATION,
      Category.BROWSING));
    addPath(new DataElement("Proxy/edm:type",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['edm:type']")
      .setCategories(Category.SEARCHABILITY, Category.BROWSING));
    /*
    addPath(new DataELement("Proxy/edm:rights",
      "$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['edm:rights']",
      Category.MANDATORY, Category.REUSABILITY));
    */
    addPath(new DataElement("Aggregation/edm:rights",
      "$.['ore:Aggregation'][0]['edm:rights']")
      .setCategories(Category.MANDATORY, Category.REUSABILITY));
    addPath(new DataElement("Aggregation/edm:provider",
      "$.['ore:Aggregation'][0]['edm:provider']")
      .setCategories(Category.MANDATORY, Category.SEARCHABILITY, Category.IDENTIFICATION));
    addPath(new DataElement("Aggregation/edm:dataProvider",
      "$.['ore:Aggregation'][0]['edm:dataProvider']")
      .setCategories(Category.MANDATORY, Category.SEARCHABILITY,
      Category.IDENTIFICATION));
    addPath(new DataElement("Aggregation/edm:isShownAt",
      "$.['ore:Aggregation'][0]['edm:isShownAt']")
      .setCategories(Category.BROWSING, Category.VIEWING));
    addPath(new DataElement("Aggregation/edm:isShownBy",
      "$.['ore:Aggregation'][0]['edm:isShownBy']")
      .setCategories(Category.BROWSING, Category.VIEWING,
      Category.REUSABILITY));
    addPath(new DataElement("Aggregation/edm:object",
      "$.['ore:Aggregation'][0]['edm:object']")
      .setCategories(Category.VIEWING, Category.REUSABILITY));
    addPath(new DataElement("Aggregation/edm:hasView",
      "$.['ore:Aggregation'][0]['edm:hasView']")
      .setCategories(Category.BROWSING, Category.VIEWING));

    fieldGroups.add(
      new FieldGroup(
        Category.MANDATORY,
        "Proxy/dc:title", "Proxy/dc:description"));
    fieldGroups.add(
      new FieldGroup(
        Category.MANDATORY,
        "Proxy/dc:type", "Proxy/dc:subject", "Proxy/dc:coverage",
        "Proxy/dcterms:temporal", "Proxy/dcterms:spatial"));
    fieldGroups.add(
      new FieldGroup(
        Category.MANDATORY,
        "Aggregation/edm:isShownAt", "Aggregation/edm:isShownBy"));

    noLanguageFields.addAll(Arrays.asList(
      "edm:ProvidedCHO/@about", "Proxy/edm:isNextInSequence",
      "Proxy/edm:type", "Aggregation/edm:isShownAt",
      "Aggregation/edm:isShownBy", "Aggregation/edm:object",
      "Aggregation/edm:hasView"));

    // solrFields.put("dc:title", "dc_title_txt");
    // solrFields.put("dcterms:alternative", "dcterms_alternative_txt");
    // solrFields.put("dc:description", "dc_description_txt");

    extractableFields.put("recordId", "$.identifier");
    extractableFields.put("dataset", "$.sets[0]");
    extractableFields.put("dataProvider", "$.['ore:Aggregation'][0]['edm:dataProvider'][0]");

    emptyStrings.add("$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:title']");
    emptyStrings.add("$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:description']");
    emptyStrings.add("$.['ore:Proxy'][?(@['edm:europeanaProxy'][0] == 'false')]['dc:subject']");

  }

  @Override
  public Format getFormat() {
    return Format.JSON;
  }

  @Override
  public List<DataElement> getCollectionPaths() {
    return new ArrayList<>();
  }

  @Override
  public List<DataElement> getRootChildrenPaths() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public DataElement getPathByLabel(String label) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
