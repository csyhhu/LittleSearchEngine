/**
 * Created by root on 16/03/17.
 */

import java.io.IOException;
import java.nio.file.Paths;

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
    private IndexWriter indexWrite;

    LuceneHandler(){

        //指定索引分词技术，这里使用的是标准分词
        analyzer = new StandardAnalyzer();
        //indexwriter 配置信息
        indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE);
        //索引的打开方式，没有索引文件就新建，有就打开
        //indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        //indexWriterConfig.setInfoStream(System.out);

        try {
            //指定索引硬盘存储路径
            directory = FSDirectory.open(Paths.get("testIndex"));
            //如果索引处于锁定状态，则解锁
            /*
            if (IndexWriter.isLocked(directory)){
                IndexWriter.unlock(directory);
            }
            */
            //指定所以操作对象indexWrite
            indexWrite = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Luence Indexer Init.");
    }

    public void createIndex(String title, String content, String code) throws IOException {
        //System.out.print("---------------Adding a doc---------------: \n" + "---------------title---------------: \n" + title + "\n" + "---------------content---------------: \n" + content + "\n");
        Document doc = new Document();
        doc.add(new Field("title", title, TextField.TYPE_STORED));
        doc.add(new Field("content", content, TextField.TYPE_STORED));
        doc.add(new Field("code", code, TextField.TYPE_STORED));
        indexWrite.addDocument(doc);
        // System.out.println("---------------doc added---------------\n");
    }

    public void endIndex() throws IOException {
        //提交写入
        System.out.println("Commit Start.");
        indexWrite.commit();
        /* Close writer */
        indexWrite.close();
        System.out.println("Commit Finish.");
    }

    public void search() throws IOException, ParseException {
        //System.out.println("Now Searching.");
        // Now search the index:
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser parser = new QueryParser("title", analyzer);

        Query query = parser.parse("setting");
        // if you parse("the")，then hits.length=0,because "the" is
        // stopWord,others like "to" "be",also stopWord
        ScoreDoc[] hits = searcher.search(query, 5).scoreDocs;
        System.out.println(hits.length);

        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = searcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("title"));
            System.out.println(hitDoc.get("content"));
        }
        reader.close();
        directory.close();
    }
}
