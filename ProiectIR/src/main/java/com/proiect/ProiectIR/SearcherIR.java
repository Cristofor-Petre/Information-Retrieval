package com.proiect.ProiectIR;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class SearcherIR {

	
	public IndexSearcher getSearcher(String indexPath) throws Exception {

		 
		FSDirectory directory = FSDirectory.open(Paths.get(indexPath));	 

	   	IndexReader reader = DirectoryReader.open(directory);
	   	

 	   	

	   	 
	   	return new IndexSearcher (reader);

	}
}
