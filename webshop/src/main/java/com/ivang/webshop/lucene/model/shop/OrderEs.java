package com.ivang.webshop.lucene.model.shop;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "products")
public class OrderEs {
    
    @Id
    private Long associatedId;

    @Field(type = FieldType.Date)
    private LocalDate time;

    @Field(type = FieldType.Boolean)
    private boolean delivered = false;

    @Field(type = FieldType.Integer)
    private Integer rate;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Boolean)
    private boolean anonymousComment = false;
    
    @Field(type = FieldType.Boolean)
    private boolean archivedComment = false;

    @Field(type = FieldType.Double)
    private double price;
}
