package com.ivang.webshop.lucene.indexing.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ivang.webshop.lucene.model.shop.ProductEs;

public class TextDocHandler extends DocumentHandler {

	@Override
	public ProductEs getIndexUnit(File file) {
		ProductEs retVal = new ProductEs();
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(
					fis, "UTF8"));

			String secondLine = reader.readLine();
			retVal.setKeywords(secondLine);

			String fullText = "";
			while (true) {
				secondLine = reader.readLine();
				if (secondLine == null) {
					break;
				}
				fullText += " " + secondLine;
			}
			retVal.setDetailedDescription(fullText);
			
			retVal.setFilename(file.getCanonicalPath());

			return retVal;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Datoteka ne postoji");
		} catch (IOException e) {
			throw new IllegalArgumentException("Greska: Datoteka nije u redu");
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
	}

	@Override
	public String getText(File file) {
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(
					fis, "UTF8"));
			String secondLine;
			String fullText = "";
			while (true) {
				secondLine = reader.readLine();
				if (secondLine == null) {
					break;
				}
				fullText += " " + secondLine;
			}
			return fullText;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Datoteka ne postoji");
		} catch (IOException e) {
			throw new IllegalArgumentException("Greska: Datoteka nije u redu");
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
	}

}
