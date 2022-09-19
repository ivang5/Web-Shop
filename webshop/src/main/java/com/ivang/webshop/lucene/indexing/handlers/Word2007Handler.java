package com.ivang.webshop.lucene.indexing.handlers;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.ivang.webshop.lucene.model.shop.ProductEs;

public class Word2007Handler extends DocumentHandler {

	public ProductEs getIndexUnit(File file) {
		ProductEs retVal = new ProductEs();

		try {
			XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(file));
			XWPFWordExtractor we = new XWPFWordExtractor(wordDoc);

			String text = we.getText();
			retVal.setDetailedDescription(text);

			POIXMLProperties props = wordDoc.getProperties();

			String keywords = props.getCoreProperties()
					.getUnderlyingProperties().getKeywordsProperty().getValue();
			retVal.setKeywords(keywords);

			retVal.setFilename(file.getCanonicalPath());
			
			we.close();

		} catch (Exception e) {
			System.out.println("Problem pri parsiranju docx fajla");
		}

		return retVal;
	}

	@Override
	public String getText(File file) {
		String text = null;
		try {
			XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(file));
			XWPFWordExtractor we = new XWPFWordExtractor(wordDoc);
			text = we.getText();
			we.close();
		}catch (Exception e) {
			System.out.println("Problem pri parsiranju docx fajla");
		}
		return text;
	}

}
