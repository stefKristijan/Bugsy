package hr.ferit.kstefancic.bugsy;

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

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public void setmPubDate(String mPubDate) {
        this.mPubDate = mPubDate;
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

    @Override
    public String toString() {
        return mTitle+" "+mDescription+" "+mPubDate;
    }
}
