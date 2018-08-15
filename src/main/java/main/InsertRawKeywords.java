package main;

import extractKeywords.ExtractKeywords;

public class InsertRawKeywords {
	public static void main(String[] args) throws Exception {
		ExtractKeywords ek = new ExtractKeywords();
		ek.setUp();
		ek.nlpExtractKeywords();
		ek.tearDown();
	}
}
