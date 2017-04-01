/**
 * Created by csy on 17/03/17.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;

import javax.print.Doc;

public class LuceneHandler {

    private Analyzer analyzer;
    // private IndexWriterConfig indexWriterConfig;
    private Directory directory;
    //private IndexWriter indexWrite;

    LuceneHandler(){
        analyzer = new StandardAnalyzer();
        //indexwriter Configuration
        //indexWriterConfig = new IndexWriterConfig(analyzer);
        //indexWriterConfig.setOpenMode(OpenMode.APPEND);
        try {
            //directory = FSDirectory.open(Paths.get("/media/csy/New Volume/Courses/Infomation Retrieval/WorkSpace/wholeIndex"));
            directory = FSDirectory.open(Paths.get("/media/csy/New Volume/Courses/Infomation Retrieval/WorkSpace/SearchEngine2/200000Index"));
            //indexWrite = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Luence Indexer Init.");
    }

    public ArrayList<Map> multisearch(String ques_cont, String ans_cont, String code_cont, String topN) throws IOException, ParseException {
        System.out.println("Now Searching: " + ques_cont + " in Question, " + ans_cont + " in Answer, " + code_cont + " in Code");

        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Query ques_query = new TermQuery(new Term("Question", ques_cont));
        BooleanClause ques_bc = new BooleanClause(ques_query, BooleanClause.Occur.MUST);

        Query ans_query = new TermQuery(new Term("Answer", ans_cont));
        BooleanClause ans_bc = new BooleanClause(ans_query, BooleanClause.Occur.MUST);

        Query code_query = new TermQuery(new Term("Code", code_cont));
        BooleanClause code_bc = new BooleanClause(code_query, BooleanClause.Occur.MUST);

        // final query
        BooleanQuery finalQuery = new BooleanQuery.Builder().add(ques_bc).add(ans_bc).add(code_bc).build();

        ScoreDoc[] hits = searcher.search(finalQuery, Integer.parseInt(topN)).scoreDocs;
        System.out.println("Query Found: " + hits.length);

        // Iterate through the results:
        ArrayList result = new ArrayList<Map>();
        for (int i = 0; i < hits.length; i++) {
            Map eachRes = new HashMap();
            Document hitDoc = searcher.doc(hits[i].doc);
            eachRes.put("title", hitDoc.get("Question"));
            eachRes.put("content", hitDoc.get("Answer"));
            result.add(eachRes);
        }

        reader.close();
        directory.close();

        return result;

    }

    public ArrayList<Map> search(String searchField, String searchContent, String TopN) throws IOException, ParseException {
        System.out.println("Now Searching: " + searchContent + " in " + searchField);
        // Now search the index:
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser(searchField, analyzer);

        Query query = parser.parse(searchContent);
        // if you parse("the")，then hits.length=0,because "the" is
        // stopWord,others like "to" "be",also stopWord
        ScoreDoc[] hits = searcher.search(query, Integer.parseInt(TopN)).scoreDocs;
        System.out.println("Query Found: " + hits.length);

        // QueryResult QR = new QueryResult();

        // Iterate through the results:
        ArrayList result = new ArrayList<Map>();
        for (int i = 0; i < hits.length; i++) {
            Map eachRes = new HashMap();
            Document hitDoc = searcher.doc(hits[i].doc);
            //System.out.println(hitDoc.get("title"));
            eachRes.put("title", hitDoc.get("Question"));
            //System.out.println(hitDoc.get("content"));
            eachRes.put("content", hitDoc.get("Answer"));
            eachRes.put("score", hits[i].score);
            eachRes.put("DocID", hits[i].doc);
            result.add(eachRes);
        }

        reader.close();
        directory.close();

        return result;
    }

    public ArrayList<Map> SimilarDocSearch(String DocID) throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        MoreLikeThis mlt = new MoreLikeThis(reader);

        mlt.setAnalyzer(analyzer);
        mlt.setFieldNames(new String[] {"Answer"});
        mlt.setMinTermFreq(1);
        mlt.setMinDocFreq(1);

        Query query = mlt.like(Integer.parseInt(DocID));
        //TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] hits = searcher.search(query, 10).scoreDocs;
        System.out.println("Similar Doc Query Found: " + hits.length);

        // QueryResult QR = new QueryResult();

        // Iterate through the results:
        ArrayList result = new ArrayList<Map>();
        for (int i = 1; i < hits.length; i++) {
            Map eachRes = new HashMap();
            Document hitDoc = searcher.doc(hits[i].doc);
            System.out.println(hits[i].doc);
            //System.out.println(hitDoc.get("title"));
            eachRes.put("title", hitDoc.get("Question"));
            //System.out.println(hitDoc.get("content"));
            eachRes.put("content", hitDoc.get("Answer"));
            eachRes.put("score", hits[i].score);
            eachRes.put("DocID", hits[i].doc);
            result.add(eachRes);
        }
        reader.close();
        directory.close();

        return result;
    }

}
