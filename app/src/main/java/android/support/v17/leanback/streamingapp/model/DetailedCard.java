package android.support.v17.leanback.streamingapp.model;

import android.content.Context;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;

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
}
