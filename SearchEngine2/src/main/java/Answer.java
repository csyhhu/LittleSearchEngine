/**
 * Created by csy on 22/03/17.
 * Class to store its answer and corresponding question
 */
public class Answer {

    private String Id;
    private String ParentId;
    private String Body;

    private String QuestionTitle;
    private String QuestionTitleBoby;

    private String code;

    public Answer(String Id, String ParentId, String Body, String code_segment){
        this.Id = Id;
        this.ParentId = ParentId;
        this.Body = Body;
        this.code = code_segment;
        this.QuestionTitle = "Currently No Question Title.";
        this.QuestionTitleBoby = " ";
    }

    public String getParentId(){
        return this.ParentId;
    }

    public String getBody(){ return Body; }

    public String getQuestionTitle() {return this.QuestionTitle; }

    public String getQuestionBody() {return this.QuestionTitleBoby; }

    public String getCode() {return this.code; }

    public void addQuestionTitle(String QuestionTitle){
        this.QuestionTitle = QuestionTitle;
    }

    public void addQuestionTitleBody(String QuestionTitleBody) { this.QuestionTitleBoby = QuestionTitleBoby; }

}
