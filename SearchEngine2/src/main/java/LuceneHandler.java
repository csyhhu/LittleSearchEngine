/**
 * Created by root on 16/03/17.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.util.Version;

public class LuceneHandler {

    private Analyzer analyzer;
    private IndexWriterConfig indexWriterConfig;
    private Directory directory;
    private IndexWriter indexWrite;

    LuceneHandler(){

        CharArraySet stopSet = CharArraySet.copy(StandardAnalyzer.STOP_WORDS_SET);
        stopSet.add("<p>");
        stopSet.add("</p>");
        stopSet.add("<a>");
        stopSet.add("</a>");
        stopSet.add("<href>");
        stopSet.add("</href>");
        // Assign index participle, here use standard participle
        analyzer = new StandardAnalyzer(stopSet);
        //indexwriter configuration
        indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        // The way to open the index
        //indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        //indexWriterConfig.setInfoStream(System.out);

        try {
            //Assign the location where index store
            directory = FSDirectory.open(Paths.get("200000Index"));
            //If the index is locked, then unlock
            /*
            if (IndexWriter.isLocked(directory)){
                IndexWriter.unlock(directory);
            }
            */
            //Init a indexWrite
            indexWrite = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Luence Indexer Init.");
    }
    /*
    public void createIndex(String QuesTitle, String QuestContent, String String code) throws IOException {
        //System.out.print("---------------Adding a doc---------------: \n" + "---------------title---------------: \n" + title + "\n" + "---------------content---------------: \n" + content + "\n");
        Document doc = new Document();
        doc.add(new Field("title", title, TextField.TYPE_STORED));
        doc.add(new Field("content", content, TextField.TYPE_STORED));
        doc.add(new Field("code", code, TextField.TYPE_STORED));
        indexWrite.addDocument(doc);
        // System.out.println("---------------doc added---------------\n");
    }
    */

    public void createIndex(Question ques) throws IOException {

        String QuesTitle = ques.getTitle();
        String QuesBody = ques.getTitleBody();
        String Question = QuesTitle + "<br>" +QuesBody;
        String AccAns = ques.getAccAns();
        AccAns += "<br> Accepted Answer.";
        String code = ques.getCode();
        // List<String> otherAns = ques.getOtherAnswer();
        // String all_ans = AccAns + "<br>";
        /*
        for (String ans : otherAns){
            all_ans += (ans + "<br>");
        }
        */
        Document doc = new Document();
        //doc.add(new Field("QuesTitle", QuesTitle, TextField.TYPE_STORED));
        //doc.add(new Field("QuesBody", QuesBody, TextField.TYPE_STORED));
        // doc.add(new Field("Answer", all_ans, TextField.TYPE_STORED));
        doc.add(new Field("Question", Question, TextField.TYPE_STORED));
        doc.add(new Field("Answer", AccAns, TextField.TYPE_STORED));
        doc.add(new Field("Code", code, TextField.TYPE_STORED));

        indexWrite.addDocument(doc);
    }

    public void createIndex(Answer ans) throws IOException {

        String QuesTitle = ans.getQuestionTitle();
        String QuesBody = ans.getQuestionBody();
        String Question = QuesTitle + "<br>" + QuesBody;
        String answer = ans.getBody();
        String code = ans.getCode();

        Document doc = new Document();
        doc.add(new Field("Question", Question, TextField.TYPE_STORED));
        doc.add(new Field("Answer", answer, TextField.TYPE_STORED));
        doc.add(new Field("Code", code, TextField.TYPE_STORED));

        indexWrite.addDocument(doc);
    }

    public void endIndex() throws IOException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = new Date();
        System.out.println("Begin Lucene Submit: " + df.format(start));
        //Lucene commit
        System.out.println("Commit Start.");
        indexWrite.commit();
        /* Close writer */
        indexWrite.close();
        System.out.println("Commit Finish.");

        Date end = new Date();
        System.out.println("End Lucene Submit: " + df.format(end));
        System.out.println("Time Consume in Submitting: " + (end.getTime() - start.getTime()));
    }

    public ScoreDoc[] search() throws IOException, ParseException {
        //System.out.println("Now Searching.");
        // Now search the index:
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("Question", analyzer);

        Query query = parser.parse("java");
        // if you parse("the")，then hits.length=0,because "the" is
        // stopWord,others like "to" "be",also stopWord
        ScoreDoc[] hits = searcher.search(query, 5).scoreDocs;
        System.out.println("Query Found: " + hits.length);

        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = searcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("Question"));
            System.out.println(hitDoc.get("Answer"));
        }
        reader.close();
        directory.close();

        return hits;
    }

    public void SimilarDocSearch() throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        MoreLikeThis mlt = new MoreLikeThis(reader);

        mlt.setAnalyzer(analyzer);
        mlt.setFieldNames(new String[] { "Question"});
        mlt.setMinTermFreq(1);
        mlt.setMinDocFreq(1);

        Query query = mlt.like(131860);
        TopDocs topDocs = searcher.search(query, 10);
        for (ScoreDoc doc : topDocs.scoreDocs) {
            System.out.println(doc.doc);
            Document dlike = reader.document(doc.doc);
            System.out.println(dlike.getField("Question").stringValue());
        }
    }
}
