import org.apache.lucene.queryparser.classic.ParseException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by csy on 21/03/17.
 */
public class Main {

    static LuceneHandler mLuceneHandler = null;
    static SAXParserFactory factory = null;
    static SAXParser parser = null;
    static File posts = null;
    static SaxHandler dh = null;

    public   static   void  main(String[] args)  throws Exception {
        /*
        // First step: Create map for every answer
        // 1. Instantiate a SAXParserFactory object
        factory = SAXParserFactory.newInstance();
        // This line ensures that this code can process very large file
        // factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);

        // 2. Create Parser
        parser = factory.newSAXParser();
        // 3. Fetch the required file and generate parsers to parse this file
        posts = new File("../dataset/Post_sample.xml");

        // question_map: key - the id of the question
        // answer_map: key - the id of the question
        Map<String, Question> question_map = new HashMap<String, Question>();
        Map<String, Answer> answer_map = new HashMap<String, Answer>();
        // String ANSWER = "2";
        dh = new SaxHandler(question_map, answer_map);

        //Use SAX to read in and process the xml data, meanwhile create index because the posts is too big.
        //  If finish indexing, comment the  line.
        parser.parse(posts, dh);

        System.out.println("Parse Finish.");

        // Make up all the information
        // Find the acc ans for every question
        for (Map.Entry<String, Question> ques : question_map.entrySet()){
            String AccAnsId = ques.getValue().getAccAnsId();
            //System.out.println(AccAnsId);
            // Find that answer in the answer map
            if (answer_map.containsKey(AccAnsId)){
                //System.out.println("This Question got a accepted answer.");
                Answer ans = answer_map.get(AccAnsId);
                ques.getValue().addAccAns(ans.getBody());
            }
        }
        System.out.println("Question Fill Finish.");
        // Find the Parent question for every answer
        for (Map.Entry<String, Answer> ans : answer_map.entrySet()){
            String ParentId = ans.getValue().getParentId();
            // Find the parent question
            if (question_map.containsKey(ParentId)){
                // System.out.println("This answer got a question");
                // Get the question
                Question ques = question_map.get(ParentId);
                // Add the question title
                ans.getValue().addQuestionTitle(ques.getTitle());
                // Add the question title body
                ans.getValue().addQuestionTitleBody(ques.getTitleBody());

                // If this is not the accepted answer, we should add the answer info into the question
                // if (! ques.getAccAnsId().equals(ans.getKey())){
                //    ques.addOtherAns(ans.getValue().getBody());
                //}

            }
        }
        System.out.println("Answer Fill Finish.");

        //int count = 0;
        //for (Map.Entry<String, Answer> ans : answer_map.entrySet()){
        //    System.out.println(ans.getValue().getQuestionTitle());
        //    count ++;
        //
        //   if (count == 5){
        //        break;
        //    }
        //}

        // Perform Lucene index creation
        mLuceneHandler = new LuceneHandler();

        for (Map.Entry<String, Question> ques : question_map.entrySet()){
            mLuceneHandler.createIndex(ques.getValue());
        }

        for (Map.Entry<String, Answer> ans : answer_map.entrySet()){
            mLuceneHandler.createIndex(ans.getValue());
        }
        System.out.println("Lucene make index finish.");
        mLuceneHandler.endIndex();
        */
        searchTest();
    }

    public static void searchTest() throws IOException, ParseException {
        mLuceneHandler = new LuceneHandler();
        //mLuceneHandler.search();
        mLuceneHandler.SimilarDocSearch();
    }

    public void test(){
        //read from file
        try{
            File toRead=new File("answer_map_file");
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            HashMap<String,String> mapInFile = (HashMap<String,String>)ois.readObject();

            ois.close();
            fis.close();
            //print All data in MAP
            int count = 0;
            System.out.print(mapInFile.size());

        }
        catch(Exception e){}
    }

    public void save(){
        /*
        // Save the answer map
        File answer_map_file = new File("answer_map_file");
        FileOutputStream fos = new FileOutputStream(answer_map_file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(answer_map);
        oos.flush();
        oos.close();
        fos.close();

        // Save the question map
        File question_map_file = new File("question_map_file");
        fos = new FileOutputStream(question_map_file);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(answer_map);
        oos.flush();
        oos.close();
        fos.close();
        */
    }
}
