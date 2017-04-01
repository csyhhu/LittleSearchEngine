/**
 * Created by csy on 22/03/17.
 * Class to store question and its all answers
 */
public class Question {

    private String Id;
    private String AcceptedAnswerId;
    private String QuesTitle;
    private String QuesBody;
    private String AccAns;
    private String code;
    //private List<String> otherAns;
    // private String tag;

    public Question(String Id, String AcceptedAnswerId, String QuesTitle, String QuesBody, String code){
        this.Id = Id;
        this.QuesTitle = QuesTitle;
        this.QuesBody = QuesBody;
        this.AcceptedAnswerId = AcceptedAnswerId;
        this.code = code;
        this.AccAns = "No answer now";
    }

    public String getAccAnsId(){
        return this.AcceptedAnswerId;
    }

    public String getTitle(){
        return this.QuesTitle;
    }

    public String getTitleBody(){
        return this.QuesBody;
    }

    public String getCode(){ return this.code; }

    //public List<String> getOtherAnswer(){ return otherAns; }

    public String getAccAns(){ return AccAns; }

    public void addAccAns(String AccAns){
        this.AccAns = AccAns;
    }

    // public void addOtherAns(String ans){ this.otherAns.add(ans); }

}
