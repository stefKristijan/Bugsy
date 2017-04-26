package hr.ferit.kstefancic.bugsy;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kristijan on 24.4.2017..
 */


public class News {

    private String mTitle;
    private String mLink;
    private String mPubDate;
    private String mNewsImage;
    private String mDescription;
    private String mCategory;
    private boolean mIsSelected;

    public News(){}

    public News(String mTitle, String mLink, String mPubDate, String mNewsImage, String mDescription, String mCategory) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPubDate = mPubDate;
        this.mNewsImage = mNewsImage;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
    }
    public void setmIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }

    public boolean ismIsSelected() {

        return mIsSelected;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public void setmPubDate(String mPubDate) {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss z",Locale.ENGLISH);
        Date pubDate = null;
        try {
            pubDate = dateFormat.parse(mPubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("dd. MMMM, yyyy. - HH:mm");
        this.mPubDate = timeFormat.format(pubDate);
    }

    public void setmNewsImage(String mNewsImage) {
        this.mNewsImage = mNewsImage;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public String getNewsImage() {
        return mNewsImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

}
