package com.ivang.webshop.lucene.indexing.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.ivang.webshop.lucene.model.shop.ProductEs;

public class WordHandler extends DocumentHandler {

	public ProductEs getIndexUnit(File file) {
		ProductEs retVal = new ProductEs();
		InputStream is;

		try {
			is = new FileInputStream(file);
			// pomocu WordExtractor objekta izvuci tekst
			WordExtractor we = new WordExtractor(is);
			String text = we.getText();
			retVal.setDetailedDescription(text);
			
			// pomocu SummaryInformation objekta izvuci ostale metapodatke
			SummaryInformation si = we.getSummaryInformation();

			String keywords = si.getKeywords();
			retVal.setKeywords(keywords);
			
			retVal.setFilename(file.getCanonicalPath());
			
			we.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Dokument ne postoji");
		} catch (Exception e) {
			System.out.println("Problem pri parsiranju doc fajla");
		}

		return retVal;
	}

	@Override
	public String getText(File file) {
		String text = null;
		try {
			WordExtractor we = new WordExtractor(new FileInputStream(file));
			text = we.getText();
			we.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Dokument ne postoji");
		} catch (Exception e) {
			System.out.println("Problem pri parsiranju doc fajla");
		}
		return text;
	}

}
