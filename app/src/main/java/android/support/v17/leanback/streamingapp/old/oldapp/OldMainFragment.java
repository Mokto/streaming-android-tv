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
 */

package android.support.v17.leanback.streamingapp.old.oldapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.app.BrowseActivity;
import android.support.v17.leanback.streamingapp.old.oldapp.olddetails.OldDetailViewExampleActivity;
import android.support.v17.leanback.streamingapp.old.oldapp.olddialog.DialogExampleActivity;
import android.support.v17.leanback.streamingapp.old.oldapp.oldmedia.VideoExampleActivity;
import android.support.v17.leanback.streamingapp.old.oldapp.oldsettings.SettingsExampleActivity;
import android.support.v17.leanback.streamingapp.old.oldapp.oldwizard.WizardExampleActivity;
import android.support.v17.leanback.streamingapp.old.oldcards.presenters.OldCardPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCardRow;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldMovie;
import android.support.v17.leanback.streamingapp.utils.OldUtils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;

import com.google.gson.Gson;


public class OldMainFragment extends BrowseFragment {

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupUIElements();
        setupRowAdapter();
        setupEventListeners();

        this.loadMainPage();
    }

    private void loadMainPage() {
        Intent intent = new Intent(getActivity().getBaseContext(),
                // CardExampleActivity.class);
                BrowseActivity.class);
        if (intent != null) {
            getActivity().startActivityForResult(intent, 1);
        }
    }

    private void setupRowAdapter() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        createRows();
        setAdapter(mRowsAdapter);
    }

    private void createRows() {
        String json = OldUtils
                .inputStreamToString(getResources().openRawResource(R.raw.launcher_cards));
        OldCardRow[] rows = new Gson().fromJson(json, OldCardRow[].class);
        for (OldCardRow row : rows) {
            mRowsAdapter.add(createCardRow(row));
        }
    }

    private ListRow createCardRow(OldCardRow oldCardRow) {
        PresenterSelector presenterSelector = new OldCardPresenterSelector(getActivity());
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenterSelector);
        for (OldCard oldCard : oldCardRow.getCards()) {
            listRowAdapter.add(oldCard);
        }
        return new ListRow(listRowAdapter);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));
        setBadgeDrawable(getResources().getDrawable(R.drawable.title_android_tv, null));
        setHeadersState(HEADERS_DISABLED);
        setHeadersTransitionOnBackEnabled(false);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Intent intent = null;
            OldCard oldCard = (OldCard) item;
            int id = oldCard.getId();
            switch (id) {
                case 4: {
                    intent = new Intent(getActivity().getBaseContext(),
                            OldDetailViewExampleActivity.class);
                    break;
                }
                case 5: {
                    intent = new Intent(getActivity().getBaseContext(),
                            VideoExampleActivity.class);
                    break;
                }
                case 7: {
                    // Let's create a new Wizard for a given OldMovie. The oldMovie can come from any sort
                    // of data source. To simplify this example we decode it from a JSON source
                    // which might be loaded from a server in a real world example.
                    intent = new Intent(getActivity().getBaseContext(),
                            WizardExampleActivity.class);

                    // Prepare extras which contains the OldMovie and will be passed to the Activity
                    // which is started through the Intent/.
                    Bundle extras = new Bundle();
                    String json = OldUtils.inputStreamToString(
                            getResources().openRawResource(R.raw.wizard_example));
                    OldMovie oldMovie = new Gson().fromJson(json, OldMovie.class);
                    extras.putSerializable("oldMovie", oldMovie);
                    intent.putExtras(extras);

                    // Finally, start the wizard Activity.
                    break;
                }
                case 8: {
                    intent = new Intent(getActivity().getBaseContext(),
                            SettingsExampleActivity.class);
                    startActivity(intent);
                    return;
                }
                case 9: {
                    intent = new Intent(getActivity().getBaseContext(),
                            DialogExampleActivity.class);
                    break;
                }

                case 10:
                    intent = new Intent(getActivity().getBaseContext(),
                            BrowseActivity.class);
                    break;
                default:
                    break;
            }
            if (intent != null) {
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                        .toBundle();
                startActivity(intent, bundle);
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
        }
    }
}
