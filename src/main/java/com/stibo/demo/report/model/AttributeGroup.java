package com.stibo.demo.report.model;

public class AttributeGroup {
  private String id;
  private String name;
  private String description;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    return this.getId().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    AttributeGroup attributeGroup = (AttributeGroup) obj;
    if (this.id.equals(attributeGroup.getId())){
      return true;
    }else{
      return false;
    }
  }
}

