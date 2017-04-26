package hr.ferit.kstefancic.bugsy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static hr.ferit.kstefancic.bugsy.R.id.spinnerCategories;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ALL = "All categories";
    private static final String SQLDATABASE = "Saved news";
    private RssFeed rssFeed;

    SwipeRefreshLayout swipeRecyclerView;
    RecyclerView mRecyclerView;
    NewsAdapter mNewsAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration mItemDecoration;
    Spinner mSpinnerCategories;
    List<News> mNews;
    FloatingActionButton fab, fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.swipeRecyclerView= (SwipeRefreshLayout) findViewById(R.id.swipeRecyclerView);
        swipeRecyclerView.setOnRefreshListener(this);

        this.mRecyclerView= (RecyclerView) this.findViewById(R.id.rvNews);

        executeGetRssFeed();
    }

    private void executeGetRssFeed() {
        rssFeed = new RssFeed(this);
        rssFeed.execute();
    }

    public void setUI(final List<News> newses) {

        mNews=newses;
        this.mSpinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        setUpSpinner(mNews);

        setUpRecyclerView(mNews);

        mSpinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                List<News> selectedNews = new ArrayList<News>();

                if(selectedCategory==ALL) {
                    setUpRecyclerView(mNews);
                }
                else if(selectedCategory==SQLDATABASE){
                    selectedNews = NewsDBHelper.getInstance(getApplicationContext()).getAllNews();
                    setUpRecyclerView(selectedNews);
                }
                else {
                    for (int i = 0; i < mNews.size(); i++) {
                        News aNews = mNews.get(i);
                        if (aNews.getCategory() == selectedCategory) {
                            selectedNews.add(aNews);
                        }
                    }
                    setUpRecyclerView(selectedNews);
                }
                mSpinnerCategories.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setUpFloatingButtons();

        if(swipeRecyclerView.isRefreshing()){
            swipeRecyclerView.setRefreshing(false);
        }


    }

    private void setUpFloatingButtons() {
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSpinnerCategories.getVisibility()==View.GONE) {
                    mRecyclerView.smoothScrollToPosition(0);
                    mSpinnerCategories.setVisibility(View.VISIBLE);
                }
                else mSpinnerCategories.setVisibility(View.GONE);
            }
        });

        this.fabSave= (FloatingActionButton) findViewById(R.id.fabSave);
        this.fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<mNews.size();i++){
                    News aNews=mNews.get(i);
                    if(aNews.ismIsSelected()){
                        NewsDBHelper.getInstance(getApplicationContext()).insertNews(aNews);
                        mNews.get(i).setmIsSelected(false);
                    }
                }
            }
        });
    }

    private void setUpRecyclerView(List<News> newses) {

        Context context = getApplicationContext();
        this.mNewsAdapter = new NewsAdapter(newses ,context);
        this.mLayoutManager = new LinearLayoutManager(context);
        this.mItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);

        this.mRecyclerView.addItemDecoration(this.mItemDecoration);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mNewsAdapter);
    }

    private void setUpSpinner(List<News> newses) {
        ArrayList<String> categories = getCategories(newses);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategories.setAdapter(spinnerAdapter);
    }

    private ArrayList<String> getCategories(List<News> newses) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add(ALL);
        categories.add(SQLDATABASE);
        for(int i=0;i<newses.size();i++){
            String category = newses.get(i).getCategory();
            if(i==0){
                categories.add(category);
            }
            else {
                for(int j=0;j<categories.size();j++){
                    if(category==categories.get(j))
                        break;
                    else if(j==categories.size()-1 && category!=categories.get(j)){
                        categories.add(category);
                    }
                }
            }
        }
        return categories;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.rssFeed !=null){
            this.rssFeed.detach();
        }
    }


    @Override
    public void onRefresh() {
        executeGetRssFeed();
    }
}
