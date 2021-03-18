# Information-Retrieval
## Text retrieval (querying indexed documents specified by a user) using Apache Lucene (Java implementation)


HOW TO RUN

- Open cmd and change the current directory to the root of the project where the pom.xml file lies.

- To run the project you need to run the executable jar in the \target directory
and pass three arguments: the path to the documents, the path where the indexer will be
saved and the path to the text documents containing queries. After that hit Enter.

For example:
java -jar target\ProiectIR-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
path_to_documents path_to_indexer path_to_queries

Note: You need to specify the full path in quotation marks in each case; the path for the
query text file must differ from the path for the documents to index (otherwise the query
text file will also be indexed).

- When you first run the program you need to specify, for the path to the indexer
(the second argument), a folder which is not created yet. In the above example the indexer folder 
DOESN'T exist when running the command. Afterwards, when you repeatedly index the documents, the
new indexer will override the existing indexer in that respective folder, so you can safely
specify the same path.

- The indexer can only index files with txt, pdf and docx extension.

- The text file for the queries contains queries separated by a new line. You may write as many
queries as you wish as long as they are separated by a new line.

After succesfully running the command, a menu will apear which will either index documents (type
1 and then hit enter), search queries (type 2 and enter) or exit the program (type 0 and enter).
For the second option (searching queries), the program prints the number of hits (documents
retrieved) and the name of the documents retrieved.
The words with diacritics may not be displaying correctly in the cmd due to the encoding type
the cmd uses.


ABOUT THE PROJECT

I've created separate classes for the Indexer and Searcher with an additional class of
Preprocesare (responsable for removing diacritics and stemming the words), App (which binds
together the Indexer and Searcher) and a Test class where I've wrote the main menu.
For this task I've used the RomanianAnalyzer class which was initialized with the default
stop set (to which I've added the same stop words set but without diacritics). For
the stemming part I've used RomanianStemmer from the lucene package. The reading of the
documents was done using Apache Tika, so you don't have to specify any encoding for the 
documents or query search text document.
The indexer and searcher may be run separately, however there needs to be an indexer saved
on disk when running the searcher. The query search is preprocessed in the same manner
as each document (remove diacritics and then stemm).


JAVA VERSION: 1.8.0_171
JDK VERSION: 1.8.0_171
JRE VERSION: 1.8.0_171
