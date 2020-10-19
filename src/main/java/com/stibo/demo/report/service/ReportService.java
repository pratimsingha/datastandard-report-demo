package com.stibo.demo.report.service;

import com.stibo.demo.report.model.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService {
    public Stream<Stream<String>> report(Datastandard datastandard, String categoryId) {
        // TODO: implement

        List<List<String>> outerList = new ArrayList<>();
        Category category = new Category();
        AttributeLink attributeLink = new AttributeLink();
        Attribute attribute = new Attribute();
        Boolean optionalValue = Boolean.TRUE;
        String attributeLinkId = null;
        category.setId(categoryId);
        if (datastandard.getCategories().contains(category)) {
            //category = datastandard.getCategories().get(category);
            List<Category> result = datastandard.getCategories().stream()
                    .filter(cat -> categoryId.equals(cat.getId()))
                    .collect(Collectors.toList());

            List<String> headerList = new ArrayList<>();
            headerList.add("Category Name");
            headerList.add("Attribute Name");
            headerList.add("Description");
            headerList.add("Type");
            headerList.add("Groups");
            outerList.add(headerList);
            for (int j = 0; j < result.size(); j++) {
                List<String> innerList = new ArrayList<>();
                category = datastandard.getCategories().get(datastandard.getCategories().indexOf(category));
                innerList.add(category.getName());
                if (category.getAttributeLinks().size() > 0) {
                    StringBuilder attributeBuilder = new StringBuilder();
                    for (int i = 0; i < category.getAttributeLinks().size(); i++) {
                        attributeLinkId = category.getAttributeLinks().get(i).getId();
                        optionalValue = category.getAttributeLinks().get(i).getOptional();
                        if (attributeLinkId != null) {
                            attribute.setId(attributeLinkId);
                            if (datastandard.getAttributes().contains(attribute)) {
                                attribute = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attribute));
                                if (optionalValue.equals(Boolean.FALSE)) {
                                    attributeBuilder.append(attributeBuilder)
                                            .append(attribute.getName())
                                            .append("*");
                                    innerList.add(attributeBuilder.toString());
                                } else {
                                    innerList.add(attribute.getName());
                                }
                                if (attribute.getDescription() != null) {
                                    innerList.add(attribute.getDescription());
                                } else {

                                    innerList.add("");
                                }
                            }
                        }
                    }
                    innerList.add(type(datastandard, attribute));
                }
                innerList.add(groups(datastandard,attribute));
                outerList.add(innerList);
            }
        }
//            category = datastandard.getCategories().get(datastandard.getCategories().indexOf(category));
//            innerList.add(category.getName());
//            if (category.getAttributeLinks().size() > 0) {
//                StringBuilder attributeBuilder = new StringBuilder();
//                for (int i = 0; i < category.getAttributeLinks().size(); i++) {
//                    attributeLinkId = category.getAttributeLinks().get(i).getId();
//                    optionalValue = category.getAttributeLinks().get(i).getOptional();
//                    if (attributeLinkId != null) {
//                        attribute.setId(attributeLinkId);
//                        if (datastandard.getAttributes().contains(attribute)) {
//                            attribute = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attribute));
//                            if (optionalValue == false) {
//                                //StringBuilder stringBuilder = new StringBuilder();
//                                attributeBuilder.append(attributeBuilder)
//                                        .append(attribute.getName())
//                                        .append("*");
//                                innerList.add(attributeBuilder.toString());
//                            } else {
//                                innerList.add(attribute.getName());
//                            }
//                            if (attribute.getDescription() != null) {
//                                innerList.add(attribute.getDescription());
//                            } else {
//
//                                innerList.add("");
//                            }
//                        }
//                    }
//                }
//                StringBuilder attributeLinksBuilder = new StringBuilder();




                //Type
//
//
//                            //Type
//                            Boolean multiValue = attribute.getType().getMultiValue();
//                            List<AttributeLink> attributeLinks = attribute.getAttributeLinks();
//                            if (attributeLinks.size() > 0) {
//                                StringBuilder stringBuilder = new StringBuilder();
//                                for (AttributeLink a : attributeLinks) {
//                                    String tempAttLink = a.getId();
//                                    Attribute attribute1 = new Attribute();
//                                    attribute1.setId(tempAttLink);
//                                    if (datastandard.getAttributes().contains(attribute1)) {
//                                        attribute1 = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attribute1));
//                                        if (a.getOptional().equals(Boolean.FALSE)) {
//                                            stringBuilder.append(attribute1.getType().getId())
//                                                    .append("{\n")
//                                                    .append(attribute1.getName())
//                                                    .append("*:  ")
//                                                    .append(attribute1.getType().getId());
//                                        }
//
//                                    }
//                                }
//                            }
//                            if (multiValue != null && multiValue.equals(Boolean.TRUE)) {
//                                StringBuilder stringBuilder = new StringBuilder();
//                                stringBuilder.append(attribute.getType().getId())
//                                        .append("[]");
//                                innerList.add(stringBuilder.toString());
//                            } else {
//                                innerList.add(attribute.getType().getId());
//                            }
//
//
//                            //Group
//                            List<String> groups = attribute.getGroupIds();
//                            StringBuilder stringBuilder = new StringBuilder();
//                            for (String group : groups) {
//                                AttributeGroup attributeGroup = new AttributeGroup();
//                                attributeGroup.setId(group);
//                                if (datastandard.getAttributeGroups().contains(attributeGroup)) {
//                                    attributeGroup = datastandard.getAttributeGroups().get(datastandard.getAttributeGroups().indexOf(attributeGroup));
//                                    stringBuilder.append(attributeGroup.getName())
//                                            .append(System.lineSeparator());
//                                }
//                            }
//                            innerList.add(stringBuilder.toString().trim());
//
//                        }
//                    }
//                }

//            }



        return outerList.stream().map(List::stream);
    }

    public String categoryName(Datastandard datastandard, Category category) {
        category = datastandard.getCategories().get(datastandard.getCategories().indexOf(category));
        return category.getName();
    }

    public String type(Datastandard datastandard, Attribute attribute){
        StringBuilder stringBuilder = new StringBuilder();
        Boolean multiValue = attribute.getType().getMultiValue();
        List<AttributeLink> attributeLinks = attribute.getAttributeLinks();
        Boolean optionalValue = Boolean.TRUE;
        if (attributeLinks.size() > 0) {
            for (AttributeLink a : attributeLinks) {
                String tempAttLink = a.getId();
                Attribute attribute1 = new Attribute();
                attribute1.setId(tempAttLink);
                if (datastandard.getAttributes().contains(attribute1)) {
                    attribute1 = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attribute1));
                    optionalValue = a.getOptional();
                    if (a.getOptional().equals(Boolean.FALSE)) {
                        stringBuilder.append(attribute.getType().getId())
                                .append("{\n")
                                .append(attribute1.getName())
                                .append("*:  ")
                                .append(attribute1.getType().getId())
                                .append("\n}");
                    }else{
                        stringBuilder.append(attribute1.getType().getId())
                                .append("{\n")
                                .append(attribute1.getName())
                                .append(":  ")
                                .append(attribute1.getType().getId())
                                .append("\n}");
                    }

                }
            }

        }
        if (multiValue != null && multiValue.equals(Boolean.TRUE)) {

            stringBuilder
                    .append(attribute.getType().getId())
                    .append("[]");
            //innerList.add(stringBuilder.toString());
        } else {
            stringBuilder
                    .append(attribute.getType().getId());
            //innerList.add(attribute.getType().getId());
        }

        return stringBuilder.toString();

    }

    public String groups(Datastandard datastandard, Attribute attribute){
        //Group
        List<String> groups = attribute.getGroupIds();
        StringBuilder stringBuilder = new StringBuilder();
        for (String group : groups) {
            AttributeGroup attributeGroup = new AttributeGroup();
            attributeGroup.setId(group);
            if (datastandard.getAttributeGroups().contains(attributeGroup)) {
                attributeGroup = datastandard.getAttributeGroups().get(datastandard.getAttributeGroups().indexOf(attributeGroup));
                stringBuilder.append(attributeGroup.getName())
                        .append(System.lineSeparator());
            }
        }
        return stringBuilder.toString().trim();
    }
}
