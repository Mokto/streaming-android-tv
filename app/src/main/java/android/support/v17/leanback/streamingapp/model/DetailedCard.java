package android.support.v17.leanback.streamingapp.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by theo on 17-03-13.
 */

public class DetailedCard {
    @SerializedName("title") private String mTitle = "";
    @SerializedName("description") private String mDescription = "";
    @SerializedName("text") private String mText = "";
    @SerializedName("localImageResource") private String mLocalImageResource = null;
    @SerializedName("price") private String mPrice = null;
    @SerializedName("characters") private Card[] mCharacters = null;
    @SerializedName("recommended") private Card[] mRecommended = null;
    @SerializedName("year") private int mYear = 0;
    @SerializedName("poster") private String poster = null;
    @SerializedName("background") private String background = null;
    @SerializedName("imdbId") private String imdbId = "";


    public String getPrice() {
        return mPrice;
    }

    public int getYear() {
        return mYear;
    }

    public String getLocalImageResource() {
        return mLocalImageResource;
    }

    public String getText() {
        return mText;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public Card[] getCharacters() {
        return mCharacters;
    }

    public Card[] getRecommended() {
        return mRecommended;
    }

    public int getLocalImageResourceId(Context context) {
        return context.getResources()
                .getIdentifier(getLocalImageResource(), "drawable", context.getPackageName());
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "DetailedCard{" +
                "mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mText='" + mText + '\'' +
                ", mLocalImageResource='" + mLocalImageResource + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mCharacters=" + Arrays.toString(mCharacters) +
                ", mRecommended=" + Arrays.toString(mRecommended) +
                ", mYear=" + mYear +
                ", poster='" + poster + '\'' +
                ", background='" + background + '\'' +
                '}';
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmLocalImageResource() {
        return mLocalImageResource;
    }

    public void setmLocalImageResource(String mLocalImageResource) {
        this.mLocalImageResource = mLocalImageResource;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public Card[] getmCharacters() {
        return mCharacters;
    }

    public void setmCharacters(Card[] mCharacters) {
        this.mCharacters = mCharacters;
    }

    public Card[] getmRecommended() {
        return mRecommended;
    }

    public void setmRecommended(Card[] mRecommended) {
        this.mRecommended = mRecommended;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }
}
