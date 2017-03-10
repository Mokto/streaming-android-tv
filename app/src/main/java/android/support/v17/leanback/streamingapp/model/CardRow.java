package android.support.v17.leanback.streamingapp.model;

import com.google.common.collect.ImmutableBiMap;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardRow {

    @SerializedName("shadow") private boolean shadow = true;
    @SerializedName("id") private String id;
    @SerializedName("title") private String title;
    @SerializedName("cards") private List<Card> cards;

    static public ImmutableBiMap<Long, String> ids = new ImmutableBiMap.Builder<Long, String>()
            .put(0L, "trakt-lists")
            .put(1L, "trakt-genres")
            .put(2L, "recommendations")
            .put(3L, "watchlist")
            .build();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean useShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
