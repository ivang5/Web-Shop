package com.ivang.webshop.lucene.indexing.handlers;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import com.ivang.webshop.lucene.model.shop.ProductEs;

public class PDFHandler extends DocumentHandler {

	@Override
	public ProductEs getIndexUnit(File file) {
		ProductEs retVal = new ProductEs();
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getText(parser);
			retVal.setDetailedDescription(text);

			// metadata extraction
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();

			String keywords = ""+info.getKeywords();
			retVal.setKeywords(keywords);
			
			retVal.setFilename(file.getCanonicalPath());
			
			pdf.close();
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}

		return retVal;
	}

	@Override
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}
	
	public String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}

}
