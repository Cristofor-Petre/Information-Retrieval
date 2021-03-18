package com.proiect.ProiectIR;


import java.io.IOException;

import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.CharArraySet;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;

import org.apache.tika.sax.BodyContentHandler;
import org.tartarus.snowball.ext.RomanianStemmer;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{
   	 private String docPath;
   	 private String indexPath;
   	 private IndexerIR idxIR;
   	 private IndexSearcher searcher ;
   	 Preprocesare p = new Preprocesare();
   	 

   	 public App(String docPath, String indexPath) {
   		 this.docPath = docPath;
   		 this.indexPath = indexPath;
   		 
   	 }
   	 public void indexeaza() throws Exception{
   		idxIR = new IndexerIR(this.docPath, this.indexPath);
   		idxIR.indexDocuments();
   	 }
   	 
   	 
   	 public void getSearcher() throws Exception {
   		 this.searcher = new SearcherIR().getSearcher(this.indexPath);
   	 }
   	 

	public List<String> getQueryContent(String path){
		BufferedReader reader;
		List<String> ls = new ArrayList<>();
		
		
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while(line != null) {
				ls.add(line);
				
				line = reader.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	public void getQueryResult(String path) throws Exception
	{
		
		 if (!new File(this.indexPath).exists()) {
			 System.out.println("Nu s-a gasit nici un indexer la locatia specificata!");
			 return;
	    }
		List<String> ls = getQueryContent(path);
		this.getSearcher();
		QueryParser parser = new QueryParser("content", IndexerIR.roAnalyzer);
	

	   	 
		
		 for(String querySearch : ls) {
			 
			 String queryPrep = p.prepDoc(querySearch);
		   	 Query query = parser.parse(queryPrep);
		   	 TopDocs results = searcher.search(query, 100);
		   	 System.out.println("Number of hits for " + querySearch +  ": " + results.totalHits);
		   	 System.out.println("Documents retrieved: ");
		   	 for (ScoreDoc score : results.scoreDocs)
		   	 {
		   		 Document d = searcher.doc(score.doc);
		   		 System.out.println(d.get("name"));
		   		 
		   	 }
		   	 System.out.println("\n");
		 }

	   	 
	}
	

}
