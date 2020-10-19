package com.stibo.demo.report.model;

public class AttributeLink   {
  private String id;
  private Boolean optional;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getOptional() {
    return optional;
  }

  public void setOptional(Boolean optional) {
    this.optional = optional;
  }

  @Override
  public int hashCode() {
    return this.getId().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    AttributeLink attributeLink = (AttributeLink) obj;
    if (this.id.equals(attributeLink.getId())){
      return true;
    }else{
      return false;
    }
  }
}

