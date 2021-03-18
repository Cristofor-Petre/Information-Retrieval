package com.proiect.ProiectIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class IndexerIR {
	
	 public static Analyzer roAnalyzer =  new RomanianAnalyzer(Preprocesare.getAllStopWords());
	 private FSDirectory directory;
  	 private Document document;
  	 private String docPath;
  	 private String indexPath;
  	 private Set<String> ext;
  	 private AutoDetectParser parser;
  	 private BodyContentHandler handler;
  	 private Metadata metadata;
  	 private Preprocesare p;
	
	public IndexerIR(String docPath, String indexPath) throws IOException{
		p = new Preprocesare();
	    parser = new AutoDetectParser();
  		this.docPath = docPath;
  		this.indexPath = indexPath;
		this.ext = new HashSet<String>()
  		 {{
  			add("docx"); 
  			add("txt");
  			add("pdf");
  		 }};

  	 }
	
	
	public IndexWriter indexDocuments() throws IOException, ParseException, SAXException, TikaException{
		 System.out.println();
		 System.out.println("Indexing documents...");
		 directory = FSDirectory.open(Paths.get(indexPath));

		 //directory = new SimpleFSDirectory(Paths.get(indexPath));
	   	 IndexWriterConfig config = new IndexWriterConfig(roAnalyzer); 
	   	 IndexWriter writer = new IndexWriter(directory, config);
	   	 writer.deleteAll();
	   	 Map<String, String> contentMap = citesteDocumente();
	   	 
  		 for(Map.Entry<String, String> m : contentMap.entrySet()) 
  		   	 writer.addDocument(getDoc(m.getValue(), m.getKey()));
  		 
  		 
  		 writer.commit();

	   	 writer.close();
	   	 
	   	 System.out.println("Documents succesfully indexed!\n\n");
	   	 
	   	 return writer;
  	 
	}
	
  	 public Map<String,String> citesteDocumente() throws IOException, SAXException, TikaException {
   		 File[] files = new File(docPath).listFiles();

   		 Map<String, String> contentMap = new LinkedHashMap<>();
   		 
   		 for(File file : files) {

   			 if(file.isFile() && 
   					 ext.contains(FileNameUtils.getExtension(file.getName())) ) {
   				 String content = p.prepDoc(parseContent(file.getPath()));
   				 

   				contentMap.put(file.getName(),content);
   			 }
   		 }
   		 


   		 return contentMap;

   	 }
	
	public String parseContent(String filePath) throws IOException, SAXException, TikaException{

	    handler = new BodyContentHandler();
	    metadata = new Metadata();


        FileInputStream inputstream = new FileInputStream(new File(filePath));
        parser.parse(inputstream, handler, metadata);
        return handler.toString();
	}
	public  Document getDoc(String content, String name) throws IOException {
   		 document = new Document();

	   	 document.add(new TextField("content", content, Field.Store.YES));
	   	 document.add(new StringField("name", name, Field.Store.YES));
	   	 //docId++;
	   	 
	   	 return document;

	}



	

}
