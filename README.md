This is a little search engine for Stackoverflow data for Information Retrieval project.

Mar.16th upload:
1) src/SearchEngine.java
The main function of the program
2) src/LuceneHandler.java
Implement the index creation, search function using Lucene
3) src/SaxHandler.java
Implement reading xml and processing meanwhile

Currently it can read in and build the index for the Post.xml file data and perform search.
I have set 3 field for indexing: 1.title 2.content 3.code

***********************************************************************************************

Mar.17th upload:
1) Finish the basic implementation of search engine on the background using Java Spark Web Framework
2) Write a very ugly and simple html for the search
3) Upload a 100000000 posts index made by Lucence for experiment
4) The SearchBackground project is a maven project

***********************************************************************************************

Mar.18th upload:
1) Finish the basic background design and simple front end webpage
2) Now can search according to "title", "content" and "code"

***********************************************************************************************

Apr.1st upload:
1) Upload the second version of search engine. In this version, question and answer are combined into one document.
2) Refine background and frontend web. User can perform multi-field search and single-field search.
3) User can find similar answer after perform single-field search.

***********************************************************************************************

Apr.3rd upload:
1) Final modification of the source code for submitting the course project.

Required Java External Libraries:
1) lucence (version 6.4.2)
2) jsoup (version 1.10.2) 
3) Check the POM.xml, I find maven extremely useful in building project.(Mar.17th)