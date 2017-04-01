import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by csy on 17/03/17.
 */
public class QueryResult {

    private ArrayList<String> title;
    private ArrayList<String> content;


    QueryResult(){
        title = new ArrayList<String>();
        content = new ArrayList<String>();
    }

    int getTitleLength(){
        return title.size();
    }

    int getContentLength(){
        return content.size();
    }

    String getTitle(int index){
        return title.get(index);
    }

    String getContent(int index){
        return content.get(index);
    }

    void addTitle(String newTitle){
        title.add(newTitle);
    }

    void addContent(String newContent){
        content.add(newContent);
    }

    void PrintAll(){
        for(int index = 0; index < title.size(); index++){
            System.out.println(title.get(index));
            System.out.println(content.get(index));
        }
    }

    Map ReturnResult(){
        Map map = new LinkedHashMap();
        map.put("title", title);
        map.put("content", content);

        return map;
    }
}
