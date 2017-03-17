/**
 * Created by csy on 17/03/17.
 */
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import spark.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static spark.Spark.*;

public class Main {


    public static void main(String[] args) throws IOException, ParseException {
        /*
        QueryResult results = mLuceneHandler.search("title", "java");
        for (int i=0; i<results.getTitleLength(); i++){
            System.out.println("*************************************");
            System.out.println(results.getTitle(i));
        }
        */

        staticFiles.location("/public"); // Static files

        get("/", (request, response) ->{
            // Redirect to the helloworld page
            response.redirect("/helloworld.html");
            return null;
        });

        post("/query/:content", (request, response) -> {
            LuceneHandler mLuceneHandler = new LuceneHandler();
            System.out.println("Get Connection, data received: " + request.params("content"));
            ArrayList result = mLuceneHandler.search("title", request.params("content"));
            JSONArray result_json = JSONArray.fromObject(result);
            // System.out.println(result_json);
            return result_json;
        });

        post("/query/:field/:content", (request, response) -> {
            //System.out.println(response);
            //QueryResult results = mLuceneHandler.search(request.params("field"), request.params("content"));

            return "Query Result:"+request.params("field");
        });

    }
}