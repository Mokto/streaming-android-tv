package android.support.v17.leanback.streamingapp.presenter.card;

import android.content.Context;
import android.graphics.Color;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.widget.ImageCardView;

public class SingleLineCardPresenter extends ImageCardViewPresenter {
    public SingleLineCardPresenter(Context context, int styleId) {
        super(context, styleId);
    }

    @Override public void onBindViewHolder(Card card, ImageCardView cardView) {
        super.onBindViewHolder(card, cardView);

        if(card.getFooterColor() == -1) {
            cardView.setInfoAreaBackgroundColor(Color.parseColor(getContext().getResources().getString(0 + R.color.default_card_footer_background_color)));
        } else {
            cardView.setInfoAreaBackgroundColor(card.getFooterColor());
        }
    }
}
