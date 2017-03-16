/**
 * Created by root on 14/03/17.
 */
import org.xml.sax.SAXException;

import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/*
public class SearchEngine{

    LuceneHandler mLuceneHandler = null;
    SAXParserFactory factory = null;
    SAXParser parser = null;
    File posts = null;
    SaxHandler dh = null;

    SearchEngine() throws ParserConfigurationException, SAXException {
        // Initalize a Lucene
        mLuceneHandler = new LuceneHandler();

        // 1.实例化SAXParserFactory对象
        factory = SAXParserFactory.newInstance();
        // 2.创建解析器
        parser = factory.newSAXParser();
        // 3.获取需要解析的文档，生成解析器,最后解析文档
        posts = new File("/media/csy/New Volume1/Courses/Infomation Retrieval/WorkSpace/dataset/Posts.xml");
        dh = new SaxHandler(mLuceneHandler);
    }

    public   static   void  main(String[] args)  throws Exception {
        // Init the SearchEngine class
        SearchEngine SE =  new SearchEngine();

        // Use SAX to read in and process the xml data, meanwhile create index because the posts is too big.
        // SE.parser.parse(SE.posts, SE.dh);

        SE.mLuceneHandler.search();
    }
}
*/

public class SearchEngine{

    static LuceneHandler mLuceneHandler = null;
    static SAXParserFactory factory = null;
    static SAXParser parser = null;
    static File posts = null;
    static SaxHandler dh = null;

    public   static   void  main(String[] args)  throws Exception {

        // Initalize a Lucene
        mLuceneHandler = new LuceneHandler();
        /*
        // 1.实例化SAXParserFactory对象
        factory = SAXParserFactory.newInstance();
        // 2.创建解析器
        parser = factory.newSAXParser();
        // 3.获取需要解析的文档，生成解析器,最后解析文档
        posts = new File("/media/csy/New Volume1/Courses/Infomation Retrieval/WorkSpace/dataset/Posts.xml");
        dh = new SaxHandler(mLuceneHandler);
        */
        /* Use SAX to read in and process the xml data, meanwhile create index because the posts is too big.
           If finish indexing, comment the  line.
        */
        // parser.parse(posts, dh);
        // Search the content
        // mLuceneHandler.search();
    }
}