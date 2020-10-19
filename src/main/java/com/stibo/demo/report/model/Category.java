package com.stibo.demo.report.model;

import java.util.List;

public class Category   {
  private String id;
  private String name;
  private String description;
  private String parentId;
  private List<AttributeLink> attributeLinks;

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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public List<AttributeLink> getAttributeLinks() {
    return attributeLinks;
  }

  public void setAttributeLinks(List<AttributeLink> attributeLinks) {
    this.attributeLinks = attributeLinks;
  }

  @Override
  public int hashCode() {
    return this.getId().hashCode();
    //return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    Category category = (Category) obj;
    if (this.id.equals(category.getId())){
      return true;
    }else{
      return false;
    }


    //return super.equals(obj);
  }


}

