import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by root on 16/03/17.
 */
public class SaxHandler extends DefaultHandler {

    private long count; // Current posts processed.
    //private int terminateNum; // The number of posts used for testing.
    private int displayFreq; // The freq to show
    //LuceneHandler mLuceneHandler;
    //private Date currData;
    private Map<String, Answer> answer_map;
    private Map<String, Question> question_map;
    //private String SaxType;

    /*
    SaxHandler(LuceneHandler mLuceneHandler){
        this.mLuceneHandler = mLuceneHandler;
        count = 0;
        terminateNum = 10000;
        displayFreq = 100000000;
        currData = new Date();
        System.out.println("SaxHandler Init.");
    }
    */

    SaxHandler(Map question_map, Map answer_map){
        //this.SaxType = ans;
        this.count = 0;
        this.question_map = question_map;
        this.answer_map = answer_map;
        //this.terminateNum = 100;
        this.displayFreq = 10000;
    }

    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String content = new String(arg0, arg1, arg2);
        // System.out.println(content);
        super.characters(arg0, arg1, arg2);
    }

    @Override
    public void endDocument() throws SAXException {
        //System.out.println("\n End Analyzing Document");
        // mLuceneHandler.endIndex();
        super.endDocument();
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        //System.out.println("End Analyzing This Post.");
        super.endElement(arg0, arg1, arg2);
    }

    @Override
    public void startDocument() throws SAXException {
        //System.out.println("Start Analyzing Document.\n");
        super.startDocument();
    }

    @Override
    public void startElement(String arg0, String arg1, String arg2,
                             Attributes post) throws SAXException {
        //System.out.println("Start Analyzing This Post.");
        if(post.getLength() > 0 ){
            String PostTypeId = post.getValue("PostTypeId");
            String Id = post.getValue("Id");

            // Parse the Boby to find code
            String Body = post.getValue("Body");
            Document doc = Jsoup.parse(Body);
            Elements code_content = doc.getElementsByTag("code");
            String code_segment = code_content.text();

            // Deal with question
            if(PostTypeId.equals("1")){ // Here we must use .equal
                String AcceptedAnswerId = post.getValue("AcceptedAnswerId");
                String QuesTitle = post.getValue("Title");
                String QuesBody = post.getValue("Body");
                Question new_question = new Question(Id, AcceptedAnswerId, QuesTitle, QuesBody, code_segment);
                this.question_map.put(Id, new_question);
            }

            // Deal with answer
            else{
                String ParentId = post.getValue("ParentId");
                // String Body = post.getValue("Body");
                Answer new_answer = new Answer(Id, ParentId, Body, code_segment);
                this.answer_map.put(Id, new_answer);
                // Display how many posts have been processed.
                if (count % displayFreq == 0) {
                    //System.out.print("Currently process to Element: " + count + "\n With time consuming: "+ (new Date().getTime() - currData.getTime()) + "\n");
                    //currData = new Date();
                    System.out.println("Count now is : "  + count);
                }
            }
        }
        count += 1;
        // System.out.print(arg2 + ":");
        super.startElement(arg0, arg1, arg2, post);

    }
}
