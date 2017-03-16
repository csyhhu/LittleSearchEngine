import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by root on 16/03/17.
 */
public class SaxHandler extends DefaultHandler {

    private int count; // Current posts processed.
    private int terminateNum; // The number of posts used for testing.
    LuceneHandler mLuceneHandler;

    public SaxHandler(LuceneHandler mLuceneHandler){
        this.mLuceneHandler = mLuceneHandler;
        count = 0;
        terminateNum = 10;
        System.out.println("SaxHandler Init.");
    }
    /* 此方法有三个参数
       arg0是传回来的字符数组，其包含元素内容
       arg1和arg2分别是数组的开始位置和结束位置 */
    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String content = new String(arg0, arg1, arg2);
        System.out.println(content);
        super.characters(arg0, arg1, arg2);

        count ++;
        System.out.println("Currently process to Element: " + count + "\n");
        if (count > terminateNum){
            /*
            try {
                mLuceneHandler.endIndex();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            System.exit(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        //System.out.println("\n End Analyzing Document");
        super.endDocument();
    }

    /* arg0是名称空间
       arg1是包含名称空间的标签，如果没有名称空间，则为空
       arg2是不包含名称空间的标签 */
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

    /*arg0是名称空间
      arg1是包含名称空间的标签，如果没有名称空间，则为空
      arg2是不包含名称空间的标签
      arg3很明显是属性的集合 */
    @Override
    public void startElement(String arg0, String arg1, String arg2,
                             Attributes arg3) throws SAXException {
        //System.out.println("Start Analyzing This Post.");

        String Body = null, Title = null, code_segment = null;

        if (arg3 != null && arg3.getLength()>0) {
            //System.out.println(arg3);
            //System.out.println("Length of arg3: " + arg3.getLength() + "\n");
            for (int i = 0; i < arg3.getLength(); i++) {
                // getQName()是获取属性名称，
                // System.out.print(arg3.getQName(i) + "=\"" + arg3.getValue(i) + "\"");
                if (arg3.getQName(i).equals("Body")) {
                    Body = arg3.getValue(i);
                    // Parse the Boby
                    Document doc = Jsoup.parse(Body);
                    Elements code_content = doc.getElementsByTag("code");
                    code_segment = code_content.text();
                    // System.out.print("************************Body************************ \n");
                    // System.out.println("code seg num:" + code_content.size());
                    // Separate the code segment from the content.
                    /*
                    for(int code_index = 0; code_index < code_content.size(); code_index ++) {
                        //System.out.println(code_content.get(code_index).text());
                        Body = Body.replaceAll(code_content.get(code_index).text(), " ");
                    }*/
                    //System.out.print(Body);
                    //System.out.println("************************Code************************ \n");
                    //System.out.println(code_content.text());

                } else if (arg3.getQName(i).equals("Title")) {
                    Title = arg3.getValue(i);
                    //System.out.print("************************Title************************\n");
                    //System.out.print(Title);
                }
            }
            /*
            try {
                //System.out.print("\n************************Adding index from Sax Handler************************\n");
                if (Body == null){
                    Body = "No Content.";
                }
                if (Title == null){ // Can not use .equals(null), dont know why
                    Title = "No Title.";
                }
                mLuceneHandler.createIndex(Title, Body, Code);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        // System.out.print(arg2 + ":");
        super.startElement(arg0, arg1, arg2, arg3);
    }
}
