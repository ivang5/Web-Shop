package com.ivang.webshop.lucene.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SimpleQuery {
	
	private String field;
	private String value;	
}
