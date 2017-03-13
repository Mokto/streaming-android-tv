package android.support.v17.leanback.streamingapp.model;

import android.content.Context;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;

import com.google.gson.annotations.SerializedName;

/**
 * Created by theo on 17-03-13.
 */

public class DetailedCard {
    @SerializedName("title") private String mTitle = "";
    @SerializedName("description") private String mDescription = "";
    @SerializedName("text") private String mText = "";
    @SerializedName("localImageResource") private String mLocalImageResource = null;
    @SerializedName("price") private String mPrice = null;
    @SerializedName("characters") private OldCard[] mCharacters = null;
    @SerializedName("recommended") private OldCard[] mRecommended = null;
    @SerializedName("year") private int mYear = 0;


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

    public OldCard[] getCharacters() {
        return mCharacters;
    }

    public OldCard[] getRecommended() {
        return mRecommended;
    }

    public int getLocalImageResourceId(Context context) {
        return context.getResources()
                .getIdentifier(getLocalImageResource(), "drawable", context.getPackageName());
    }
}
