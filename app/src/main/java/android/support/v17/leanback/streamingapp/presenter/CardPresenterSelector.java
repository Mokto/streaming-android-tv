package android.support.v17.leanback.streamingapp.presenter;

import android.content.Context;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.streamingapp.presenter.card.SingleLineCardPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.util.Log;

import java.util.HashMap;

public class CardPresenterSelector extends PresenterSelector {

    private final Context mContext;
    private final HashMap<Card.Type, Presenter> presenters = new HashMap<Card.Type, Presenter>();

    public CardPresenterSelector(Context context) {
        mContext = context;
    }

    @Override
    public Presenter getPresenter(Object item) {
        if (!(item instanceof Card)) throw new RuntimeException(
                String.format("The PresenterSelector only supports data items of type '%s'",
                        Card.class.getName()));
        Card card = (Card) item;
        Presenter presenter = presenters.get(card.getType());
        if (presenter == null) {
            Log.w("Mokto", card.toString());
            switch (card.getType()) {
                case SINGLE_LINE:
                    presenter = new SingleLineCardPresenter(mContext);
                    break;
//                case VIDEO_GRID:
//                    presenter = new OldVideoCardViewPresenterOldOld(mContext, R.style.VideoGridCardTheme);
//                    break;
//                case MOVIE:
//                case MOVIE_BASE:
//                case MOVIE_COMPLETE:
//                case SQUARE_BIG:
//                case GRID_SQUARE:
//                case GAME: {
//                    int themeResId = R.style.MovieCardSimpleTheme;
//                    if (oldCard.getType() == OldCard.Type.MOVIE_BASE) {
//                        themeResId = R.style.MovieCardBasicTheme;
//                    } else if (oldCard.getType() == OldCard.Type.MOVIE_COMPLETE) {
//                        themeResId = R.style.MovieCardCompleteTheme;
//                    } else if (oldCard.getType() == OldCard.Type.SQUARE_BIG) {
//                        themeResId = R.style.SquareBigCardTheme;
//                    } else if (oldCard.getType() == OldCard.Type.GRID_SQUARE) {
//                        themeResId = R.style.GridCardTheme;
//                    } else if (oldCard.getType() == OldCard.Type.GAME) {
//                        themeResId = R.style.GameCardTheme;
//                    }
//                    presenter = new OldImageCardViewPresenterOld(mContext, themeResId);
//                    break;
//                }
//                case SIDE_INFO:
//                    presenter = new OldSideInfoCardPresenterOld(mContext);
//                    break;
//                case TEXT:
//                    presenter = new OldTextCardPresenterOld(mContext);
//                    break;
//                case ICON:
//                    presenter = new OldIconCardPresenterOldOld(mContext);
//                    break;
//                case CHARACTER:
//                    presenter = new OldCharacterCardPresenterOld(mContext);
//                    break;
//                default:
//                    presenter = new OldImageCardViewPresenterOld(mContext);
//                    break;
            }
        }
        presenters.put(card.getType(), presenter);
        return presenter;
    }
}
