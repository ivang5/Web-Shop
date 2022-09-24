package com.ivang.webshop.lucene.model.shop;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "products")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class ProductEs {

    @Id
    private Long associatedId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text, ignoreAbove = 6000)
    private String detailedDescription;

    @Field(type = FieldType.Double)
    private double price;
    
    @Field(type = FieldType.Keyword)
    private String keywords;
    
    private String filename;
}
