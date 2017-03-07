package android.support.v17.leanback.streamingapp.model;

import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;

import com.google.gson.annotations.SerializedName;


public class Card {
    @SerializedName("id") private int id;
    @SerializedName("title") private String title = "";
    @SerializedName("subtitle") private String subtitle = "";
    @SerializedName("localImageResource") private String localImageResource = null;
    @SerializedName("image") private String imageUrl = null;
    @SerializedName("type") private OldCard.Type type;

    public enum Type {
        SINGLE_LINE,
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLocalImageResource() {
        return localImageResource;
    }

    public void setLocalImageResource(String localImageResource) {
        this.localImageResource = localImageResource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OldCard.Type getType() {
        return type;
    }

    public void setType(OldCard.Type type) {
        this.type = type;
    }
}
