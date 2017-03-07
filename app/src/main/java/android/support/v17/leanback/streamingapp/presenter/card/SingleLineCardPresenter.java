package android.support.v17.leanback.streamingapp.presenter.card;

import android.content.Context;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.widget.ImageCardView;

public class SingleLineCardPresenter extends ImageCardViewPresenter {
    public SingleLineCardPresenter(Context context, int styleId) {
        super(context, styleId);
    }

    @Override public void onBindViewHolder(Card card, ImageCardView cardView) {
        super.onBindViewHolder(card, cardView);
        cardView.setInfoAreaBackgroundColor(card.getFooterColor());
    }
}
