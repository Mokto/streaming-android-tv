/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package android.support.v17.leanback.streamingapp.old.oldcards.presenters;

import android.content.Context;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import java.util.HashMap;

/**
 * This PresenterSelector will decide what Presenter to use depending on a given card's type.
 */
public class OldCardPresenterSelector extends PresenterSelector {

    private final Context mContext;
    private final HashMap<OldCard.Type, Presenter> presenters = new HashMap<OldCard.Type, Presenter>();

    public OldCardPresenterSelector(Context context) {
        mContext = context;
    }

    @Override
    public Presenter getPresenter(Object item) {
        if (!(item instanceof OldCard)) throw new RuntimeException(
                String.format("The PresenterSelector only supports data items of type '%s'",
                        OldCard.class.getName()));
        OldCard oldCard = (OldCard) item;
        Presenter presenter = presenters.get(oldCard.getType());
        if (presenter == null) {
            switch (oldCard.getType()) {
                case SINGLE_LINE:
                    presenter = new OldSingleLineCardPresenterOldOld(mContext);
                    break;
                case VIDEO_GRID:
                    presenter = new OldVideoCardViewPresenterOldOld(mContext, R.style.VideoGridCardTheme);
                    break;
                case MOVIE:
                case MOVIE_BASE:
                case MOVIE_COMPLETE:
                case SQUARE_BIG:
                case GRID_SQUARE:
                case GAME: {
                    int themeResId = R.style.MovieCardSimpleTheme;
                    if (oldCard.getType() == OldCard.Type.MOVIE_BASE) {
                        themeResId = R.style.MovieCardBasicTheme;
                    } else if (oldCard.getType() == OldCard.Type.MOVIE_COMPLETE) {
                        themeResId = R.style.MovieCardCompleteTheme;
                    } else if (oldCard.getType() == OldCard.Type.SQUARE_BIG) {
                        themeResId = R.style.SquareBigCardTheme;
                    } else if (oldCard.getType() == OldCard.Type.GRID_SQUARE) {
                        themeResId = R.style.GridCardTheme;
                    } else if (oldCard.getType() == OldCard.Type.GAME) {
                        themeResId = R.style.GameCardTheme;
                    }
                    presenter = new OldImageCardViewPresenterOld(mContext, themeResId);
                    break;
                }
                case SIDE_INFO:
                    presenter = new OldSideInfoCardPresenterOld(mContext);
                    break;
                case TEXT:
                    presenter = new OldTextCardPresenterOld(mContext);
                    break;
                case ICON:
                    presenter = new OldIconCardPresenterOldOld(mContext);
                    break;
                case CHARACTER:
                    presenter = new OldCharacterCardPresenterOld(mContext);
                    break;
                default:
                    presenter = new OldImageCardViewPresenterOld(mContext);
                    break;
            }
        }
        presenters.put(oldCard.getType(), presenter);
        return presenter;
    }

}
