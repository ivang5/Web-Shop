package com.ivang.webshop.lucene.indexing.handlers;

import java.io.File;

import com.ivang.webshop.lucene.model.shop.ProductEs;

public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract ProductEs getIndexUnit(File file);
	public abstract String getText(File file);

}
