package android.support.v17.leanback.streamingapp.presenter.card;

import android.content.Context;
import android.graphics.Color;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.widget.ImageCardView;
import android.view.ContextThemeWrapper;

import com.squareup.picasso.Picasso;

public class ImageCardPresenter extends AbstractCardPresenter<ImageCardView> {
    public ImageCardPresenter(Context context, int cardThemeResId) {
        super(new ContextThemeWrapper(context, cardThemeResId));
    }

//    public ImageCardPresenter(Context context) {
//        this(context, R.style.DefaultCardTheme);
//    }


    // TODO DELETE THIS
    @Override
    protected ImageCardView onCreateView() {
        ImageCardView imageCardView = new ImageCardView(getContext());
//        imageCardView.setOnClickListener(new View.OnClickListenr() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Clicked on ImageCardView", Toast.LENGTH_SHORT).show();
//            }
//        });
        return imageCardView;
    }

    @Override
    public void onBindViewHolder(Card card, final ImageCardView cardView) {
        cardView.setTag(card);
        cardView.setTitleText(card.getTitle());
        cardView.setContentText(card.getSubtitle());

        if(card.getFooterColor() == -1) {
            cardView.setInfoAreaBackgroundColor(Color.parseColor(getContext().getResources().getString(0 + R.color.default_card_footer_background_color)));
        } else {
            cardView.setInfoAreaBackgroundColor(card.getFooterColor());
        }

        if (card.getImageUrl() != null) {
            Picasso.with(getContext()).load(card.getImageUrl()).into(cardView.getMainImageView());

        } else if (card.getLocalImageResource() != null) {
            int resourceId = getContext().getResources()
                    .getIdentifier(card.getLocalImageResource(),
                            "drawable", getContext().getPackageName());
            Picasso.with(getContext()).load(resourceId).into(cardView.getMainImageView());
        }
    }
}
