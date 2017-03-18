/**
 * Created by csy on 17/03/17.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public class LuceneHandler {

    private Analyzer analyzer;
    private IndexWriterConfig indexWriterConfig;
    private Directory directory;
    //private IndexWriter indexWrite;

    LuceneHandler(){
        analyzer = new StandardAnalyzer();
        //indexwriter 配置信息
        //indexWriterConfig = new IndexWriterConfig(analyzer);
        //indexWriterConfig.setOpenMode(OpenMode.APPEND);

        try {
            //directory = FSDirectory.open(Paths.get("/media/csy/New Volume/Courses/Infomation Retrieval/WorkSpace/wholeIndex"));
            directory = FSDirectory.open(Paths.get("../100000000Index"));
            //indexWrite = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Luence Indexer Init.");
    }

    public ArrayList<Map> search(String searchField, String searchContent) throws IOException, ParseException {
        //System.out.println("Now Searching.");
        // Now search the index:
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser(searchField, analyzer);

        Query query = parser.parse(searchContent);
        // if you parse("the")，then hits.length=0,because "the" is
        // stopWord,others like "to" "be",also stopWord
        ScoreDoc[] hits = searcher.search(query, 2).scoreDocs;
        System.out.println("Query Found: " + hits.length);

        // QueryResult QR = new QueryResult();

        // Iterate through the results:
        ArrayList result = new ArrayList<Map>();
        for (int i = 0; i < hits.length; i++) {
            Map eachRes = new HashMap();
            Document hitDoc = searcher.doc(hits[i].doc);
            //System.out.println(hitDoc.get("title"));
            eachRes.put("title", hitDoc.get("title"));
            //System.out.println(hitDoc.get("content"));
            eachRes.put("content", hitDoc.get("content"));
            result.add(eachRes);
        }

        reader.close();
        directory.close();

        return result;
    }
}
