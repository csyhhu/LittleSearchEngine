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

        // This is a test url for bootstrap
        get("/home", ((request, response) -> {
            response.redirect("/template1/index.html");
            return null;
        }));

        /*
        post("/query/:content", (request, response) -> {
            LuceneHandler mLuceneHandler = new LuceneHandler();
            System.out.println("Get Connection, data received: " + request.params("content"));
            ArrayList result = mLuceneHandler.search("QuesTitle", request.params("content"));
            JSONArray result_json = JSONArray.fromObject(result);
            // System.out.println(result_json);
            return result_json;
        });
        */

        post("/query/:field/:content/:TopN", (request, response) -> {
            //System.out.println(response);
            LuceneHandler mLuceneHandler = new LuceneHandler();
            // System.out.println(request.body());
            // System.out.println("Get Connection, data received: " + request.params("content"));
            ArrayList result = mLuceneHandler.search(request.params("field"), request.params("content"), request.params("TopN"));
            JSONArray result_json = JSONArray.fromObject(result);

            return result_json;
        });

        post("/multiquery/Question/:ques_cont/Answer/:ans_cont/Code/:code_cont/TopN/:TopN", (request, response) -> {
            System.out.println(request.params());
            LuceneHandler mLuceneHandler = new LuceneHandler();
            ArrayList result = mLuceneHandler.multisearch(request.params("ques_cont"), request.params("ans_cont"), request.params("code_cont"), request.params("TopN"));
            JSONArray result_json = JSONArray.fromObject(result);
            return result_json;
        });

        post("/query/:content", (request, response) -> {
            LuceneHandler mLuceneHandler = new LuceneHandler();
            ArrayList result = mLuceneHandler.SimilarDocSearch(request.params("content"));
            JSONArray result_json = JSONArray.fromObject(result);
            return result_json;
        });

    }
}