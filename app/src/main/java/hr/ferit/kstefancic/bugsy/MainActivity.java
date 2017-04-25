package hr.ferit.kstefancic.bugsy;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private RssFeed rssFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssFeed = new RssFeed(this);
        rssFeed.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.rssFeed !=null){
            this.rssFeed.detach();
        }
    }


}
