package hr.ferit.kstefancic.bugsy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kristijan on 24.4.2017..
 */

public class NewsDBHelper extends SQLiteOpenHelper{

    static final String CREATE_TABLE_NEWS = "CREATE TABLE " + Schema.TABLE_NEWS +
            " (" + Schema.TITLE + " VARCHAR(100),"+ Schema.LINK+ " VARCHAR(100)," + Schema.DESCRIPTION+ " TEXT," + Schema.DATE + " DATETIME," + Schema.CATEGORY + " VARCHAR(30)," + Schema.IMAGE+" IMAGE );";
    static final String DROP_TABLE_NEWS = "DROP TABLE IF EXISTS " + Schema.TABLE_NEWS;
    static final String SELECT_ALL_NEWS = "SELECT * FROM "+ Schema.TABLE_NEWS;
    static final String SELECT_CATEGORIES = "SELECT "+ Schema.CATEGORY+" FROM "+ Schema.TABLE_NEWS;


    private static NewsDBHelper mNewsHelper = null;
    Context context;

    private NewsDBHelper(Context context){

        super(context.getApplicationContext(), Schema.DATABASE_NAME,null, Schema.SCHEMA_VER);
        this.context=context;
    }

    public static synchronized NewsDBHelper getInstance(Context context){
        if(mNewsHelper==null){
            mNewsHelper = new NewsDBHelper(context);
        }
        return mNewsHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_NEWS);
        this.onCreate(db);
    }

    public ArrayList<String> getCategories(){
        ArrayList<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_CATEGORIES,null);
        if(cursor.moveToFirst()){
            do{
                String category = cursor.getString(0);
                if(!categories.contains(category)) {
                    categories.add(category);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void insertNews(News news){
        ContentValues contVal = new ContentValues();
        contVal.put(Schema.TITLE, news.getTitle());
        contVal.put(Schema.LINK, news.getLink());
        contVal.put(Schema.DESCRIPTION, news.getDescription());
        contVal.put(Schema.DATE, news.getPubDate());
        contVal.put(Schema.CATEGORY,news.getCategory());
        contVal.put(Schema.IMAGE, news.getNewsImage());
        SQLiteDatabase newsDB = this.getWritableDatabase();
        newsDB.insert(Schema.TABLE_NEWS, Schema.TITLE, contVal);
        newsDB.close();
    }

    public void deleteNews(News news){
        SQLiteDatabase db =  this.getWritableDatabase();
        db.delete(Schema.TABLE_NEWS, Schema.TITLE+"='"+news.getTitle()+"' AND "+ Schema.DATE+"='"+news.getPubDate()+"'",null);
        db.close();
    }

    public ArrayList<News> getAllNews(){
        SQLiteDatabase newsDB = this.getWritableDatabase();
        Cursor cursor = newsDB.rawQuery(SELECT_ALL_NEWS,null);
        ArrayList<News> news = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(0);
                String link = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);
                String category = cursor.getString(4);
                String image = cursor.getString(5);
                news.add(new News(title,link, date, image, description, category));
            }while(cursor.moveToNext());
        }
        cursor.close();
        newsDB.close();
        return news;
    }

    public static class Schema {
        private static final int SCHEMA_VER = 1;
        private static final String DATABASE_NAME = "news.db";

        static final String TABLE_NEWS = "news";
        static final String TITLE = "title";
        static final String LINK = "link";
        static final String DESCRIPTION = "description";
        static final String CATEGORY = "category";
        static final String IMAGE = "image";
        static final String DATE = "pubDate";
    }
}

