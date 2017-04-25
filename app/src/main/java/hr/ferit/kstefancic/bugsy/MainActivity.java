package hr.ferit.kstefancic.bugsy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity {

    private RssFeed rssFeed;

    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssFeed = new RssFeed(this);
        rssFeed.execute();

    }

    public void setUI(List<News> newses) {

        Context context = getApplicationContext();
        this.mRecyclerView= (RecyclerView) this.findViewById(R.id.rvNews);
        this.mNewsAdapter = new NewsAdapter(newses ,context);
        this.mLayoutManager = new LinearLayoutManager(context);
        this.mItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);

        this.mRecyclerView.addItemDecoration(this.mItemDecoration);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mNewsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.rssFeed !=null){
            this.rssFeed.detach();
        }
    }


}
