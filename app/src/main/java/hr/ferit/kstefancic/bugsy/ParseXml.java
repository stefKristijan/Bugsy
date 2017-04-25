package hr.ferit.kstefancic.bugsy;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristijan on 25.4.2017..
 */

public class ParseXml {

    private List<News> mNews;
    private News aNews=null;
    private String mText;

   public ParseXml(){
       mNews = new ArrayList<News>();
   }

    public List<News> getNews() {
        return mNews;
    }

   public List<News> parse(InputStream inputStream){
       XmlPullParserFactory factory = null;
       XmlPullParser parser = null;

       try {
           factory=XmlPullParserFactory.newInstance();
           factory.setNamespaceAware(true);

           parser = factory.newPullParser();
           parser.setInput(inputStream,null);

           int eventType = parser.getEventType();

           while(eventType != XmlPullParser.END_DOCUMENT){

               String tagName = parser.getName();
               switch (eventType){
                   case XmlPullParser.START_TAG:
                       if(tagName.equals("item")){
                           aNews = new News();
                       }
                       break;

                   case XmlPullParser.TEXT:
                       mText = parser.getText();
                       break;

                   case XmlPullParser.END_TAG:
                       if(tagName.equals("item") && aNews!=null){
                           mNews.add(aNews);
                       }
                       else if(tagName.equals("title") && aNews!=null){
                           aNews.setmTitle(mText);
                       }
                       else if(tagName.equals("link") && aNews!=null){
                           aNews.setmLink(mText);
                       }
                       else if(tagName.equals("description") && aNews!=null){
                           aNews.setmDescription(mText);
                       }
                       else if(tagName.equals("pubDate") && aNews!=null){
                           aNews.setmPubDate(mText);
                       }
                       else if(tagName.equals("enclosure") && aNews!=null){
                           String url = parser.getAttributeValue(null,"url");
                           aNews.setmNewsImage(url);
                       }
                       break;
                   default:
                       break;
               }
               eventType=parser.next();
           }

       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
       return mNews;
   }
}
