package android.support.v17.leanback.streamingapp.model;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;


public class Card {
    @SerializedName("id") private String id;
    @SerializedName("type") private Card.Type type;
    @SerializedName("title") private String title = "";
    @SerializedName("subtitle") private String subtitle = "";
    @SerializedName("localImageResource") private String localImageResource = null;
    @SerializedName("image") private String imageUrl = null;
    @SerializedName("footerColor") private String footerColor = null;
    @SerializedName("params") private String params = null;

    public enum Type {
        SQUARE_SINGLE_LINE,
        SQUARE_SINGLE_LINE_CENTERED,
        MOVIE,
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card.Type getType() {
        return type;
    }

    public void setType(Card.Type type) {
        this.type = type;
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

    public int getFooterColor() {
        if (this.footerColor == null) return -1;
        return Color.parseColor(this.footerColor);
    }

    public void setFooterColor(String footerColor) {
        this.footerColor = footerColor;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
