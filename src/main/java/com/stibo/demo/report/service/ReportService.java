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

        List<List<String>> outerList = new ArrayList<>();
        Category category = new Category();
        //AttributeLink attributeLink = new AttributeLink();
        Attribute attribute = new Attribute();
        Boolean optionalValue = Boolean.TRUE;
        String attributeLinkId = null;
        category.setId(categoryId);
        if (datastandard.getCategories().contains(category)) {
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
                category = datastandard.getCategories().get(j);
                innerList.add(category.getName());
                Attribute attributeN = new Attribute();
                if (category.getAttributeLinks().size() > 0) {
                    StringBuilder attributeBuilder = new StringBuilder();
                    for (int i = 0; i < category.getAttributeLinks().size(); i++) {
                        attributeLinkId = category.getAttributeLinks().get(i).getId();
                        optionalValue = category.getAttributeLinks().get(i).getOptional();
                        if (attributeLinkId != null) {

                            attributeN.setId(attributeLinkId);
                            if (datastandard.getAttributes().contains(attributeN)) {
                                attributeN = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attributeN));
                                if (optionalValue.equals(Boolean.FALSE)) {
                                    attributeBuilder.append(attributeBuilder)
                                            .append(attributeN.getName())
                                            .append("*");
                                    innerList.add(attributeBuilder.toString());
                                } else {
                                    innerList.add(attributeN.getName());
                                }
                                if (attribute.getDescription() != null) {
                                    innerList.add(attributeN.getDescription());
                                } else {

                                    innerList.add("");
                                }
                            }
                        }
                    }
                    innerList.add(type(datastandard, attributeN));
                }
                innerList.add(groups(datastandard,attributeN));
                outerList.add(innerList);
            }
        }
        return outerList.stream().map(List::stream);
    }

    private String type(Datastandard datastandard, Attribute attribute){
        StringBuilder stringBuilder = new StringBuilder();
        Boolean multiValue = attribute.getType().getMultiValue();
        stringBuilder.append(attribute.getType().getId());
        List<AttributeLink> attributeLinks = attribute.getAttributeLinks();
        if (attributeLinks != null && attributeLinks.size() > 0) {
            stringBuilder.append("{\n");
            for (AttributeLink a : attributeLinks) {
                String tempAttLink = a.getId();
                Attribute attribute1 = new Attribute();
                attribute1.setId(tempAttLink);
                if (datastandard.getAttributes().contains(attribute1)) {
                    attribute1 = datastandard.getAttributes().get(datastandard.getAttributes().indexOf(attribute1));
                    if (a.getOptional().equals(Boolean.FALSE)) {
                        stringBuilder.append(attribute1.getName())
                                .append("*:  ")
                                .append(attribute1.getType().getId());
                    }else{
                        stringBuilder.append(attribute1.getType().getId())
                                .append("{\n");
                        stringBuilder.append(attribute1.getName())
                                .append(":  ")
                                .append(attribute1.getType().getId());
                    }

                }
                stringBuilder.append("\n");
            }
            stringBuilder.append("}");
        }

        if (multiValue != null && multiValue.equals(Boolean.TRUE)) {
            stringBuilder.append("[]");
        }

        return stringBuilder.toString();

    }

    private String groups(Datastandard datastandard, Attribute attribute){
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
