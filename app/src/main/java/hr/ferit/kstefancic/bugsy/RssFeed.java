package hr.ferit.kstefancic.bugsy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristijan on 25.4.2017..
 */

public class RssFeed extends AsyncTask<Void,Void,List<News>>{

    private static final String FEED_URL = "http://www.bug.hr/rss/vijesti";
    Context mContext;
    ProgressDialog mProgressDialog;
    URL url;

    public RssFeed(Context context){
        this.mContext=context;
        this.mProgressDialog=new ProgressDialog(context);
        mProgressDialog.setMessage("Loading bug.hr news...");
    }

    @Override
    protected void onPostExecute(List<News> newses) {
        mProgressDialog.dismiss();
        Log.d("Tag",newses.get(0).getTitle());
        super.onPostExecute(newses);

    }

    @Override
    protected List<News> doInBackground(Void... params) {
        return getData();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    private List<News> getData() { //fetching XML document without processing (parsing) data
        try {
            url=new URL(FEED_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            ParseXml xmlParser = new ParseXml();
            return xmlParser.parse(inputStream);

        } catch (Exception e) {
            Toast.makeText(mContext,"Something went wrong. Please try again!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }
}
