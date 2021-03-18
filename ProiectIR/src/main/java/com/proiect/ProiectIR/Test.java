package com.proiect.ProiectIR;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Test {
	
	public static void main(String[] args) throws Exception{
		
		
		 String doc_path = args[0];
		 String index_path = args[1];
		 App app = new App(doc_path, index_path);


		 System.out.println();
		 System.out.println("Choose one of the below options.\n");

		 while(true) {
		        Scanner scanner = new Scanner(new InputStreamReader(System.in));

		        System.out.println("Enter 0 to exit.");
		        System.out.println("Enter 1 for indexing.");
		        System.out.println("Enter 2 for searching.");
		        try {
			        int number = scanner.nextInt();
			        
			        if(number == 1) 
			        	app.indexeaza();
			        
			        else if(number == 2) {	        	
			        	app.getQueryResult(args[2]);
			        }
			        else if(number == 0)
			        	break;
			        else
			        	System.out.println("Please enter a valid option.\n");

		        }
		        catch(IOException ex) {
		        	System.out.println("The indexer could not be found!");
		        }
		        catch(InputMismatchException ex) {
		        	System.out.println("Enter a valid integer!");
		        }
		        


		 }
		 

		 
		 

		 //String res = proiect.stemContent("fetelor");
		 //proiect.citesteDocumente();
		 //String[] content = proiect.getContent();
		//System.out.println(content);
		 //System.out.println(res);
	}

}
