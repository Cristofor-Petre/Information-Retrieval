package com.proiect.ProiectIR;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.tartarus.snowball.ext.RomanianStemmer;

public class Preprocesare {
	
	

	
	public String prepDoc(String content) {
		return this.stemContent(this.removeDiacritics(content));
		
	}
	
	public static CharArraySet getAllStopWords() {
		
    	CharArraySet stopWords = RomanianAnalyzer.getDefaultStopSet();
    	CharArraySet stopWordsNoDiacritics = RomanianAnalyzer.getDefaultStopSet();
    	
    	Iterator<Object> iterator = stopWords.iterator();
    	while(iterator.hasNext()) {
    		char[] stopW = (char[])iterator.next();
    		char[] output = new char[stopW.length];
    		ASCIIFoldingFilter.foldToASCII(stopW,0,output,0, stopW.length);
    		stopWordsNoDiacritics.add(String.valueOf(output));
    	
    	}
    	
    	
    	return stopWordsNoDiacritics;
	}
	

	public String removeDiacritics(String content) {
		
   		char[] output = new char[content.toCharArray().length];
   		ASCIIFoldingFilter.foldToASCII(content.toCharArray(),0,output,0, content.toCharArray().length);
			
   		return String.valueOf(output);
	}
	
	public String stemContent(String content) {
		
		
		RomanianStemmer roStemmer = new RomanianStemmer();
		StringTokenizer st = new StringTokenizer(content.replace("\n", "")," ");
		StringBuilder sb = new StringBuilder();
		
		while(st.hasMoreTokens()){
			String curr = st.nextToken();
			
			roStemmer.setCurrent(curr);
			roStemmer.stem();
			// System.out.println(roStemmer.getCurrent() + " " + roStemmer.getCurrent().length());
		    sb.append(roStemmer.getCurrent() + " ");
		}
		
		return sb.toString();
		
		
	}

	

}
