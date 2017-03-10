package android.support.v17.leanback.streamingapp.app.detail;

import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.old.oldapp.olddetails.OldDetailsDescriptionPresenter;
import android.support.v17.leanback.streamingapp.old.oldcards.presenters.OldCardPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldDetailedCard;
import android.support.v17.leanback.streamingapp.utils.OldCardListRow;
import android.support.v17.leanback.streamingapp.utils.Utils;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

public class DetailFragment extends DetailsFragment implements OnItemViewClickedListener, OnItemViewSelectedListener {

    public static final String TRANSITION_NAME = "t_for_transition";
    public static final String EXTRA_CARD = "card";

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        setupEventListeners();
    }

    private void setupUi() {
        // Load the card we want to display from a JSON resource. This JSON data could come from
        // anywhere in a real world app, e.g. a server.
        String json = Utils
                .inputStreamToString(getResources().openRawResource(R.raw.detail_example));
        OldDetailedCard data = new Gson().fromJson(json, OldDetailedCard.class);

        // Setup fragment
        setTitle(getString(R.string.detail_view_title));

        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new OldDetailsDescriptionPresenter(getActivity())) {

            @Override
            protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
                RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);

                View actionsView = viewHolder.view.
                        findViewById(R.id.details_overview_actions_background);
                actionsView.setBackgroundColor(getActivity().getResources().
                        getColor(R.color.detail_view_actionbar_background));

                View detailsView = viewHolder.view.findViewById(R.id.details_frame);
                detailsView.setBackgroundColor(
                        getResources().getColor(R.color.detail_view_background));
                return viewHolder;
            }
        };

        FullWidthDetailsOverviewSharedElementHelper mHelper = new FullWidthDetailsOverviewSharedElementHelper();
        mHelper.setSharedElementEnterTransition(getActivity(), TRANSITION_NAME);
        rowPresenter.setListener(mHelper);
        rowPresenter.setParticipatingEntranceTransition(false);
        prepareEntranceTransition();

        ListRowPresenter shadowDisabledRowPresenter = new ListRowPresenter();
        shadowDisabledRowPresenter.setShadowEnabled(false);

        // Setup PresenterSelector to distinguish between the different rows.
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        rowPresenterSelector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
        rowPresenterSelector.addClassPresenter(OldCardListRow.class, shadowDisabledRowPresenter);
        rowPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(rowPresenterSelector);

        // Setup action and detail row.
        DetailsOverviewRow detailsOverview = new DetailsOverviewRow(data);
        int imageResId = data.getLocalImageResourceId(getActivity());

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_CARD)) {
            imageResId = extras.getInt(EXTRA_CARD, imageResId);
        }
        detailsOverview.setImageDrawable(getResources().getDrawable(imageResId, null));
        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
        actionAdapter.add(new Action(1, getString(R.string.action_buy) + data.getPrice()));
        actionAdapter.add(new Action(2, getString(R.string.action_wishlist)));
        actionAdapter.add(new Action(3, getString(R.string.action_related)));
        detailsOverview.setActionsAdapter(actionAdapter);
        mRowsAdapter.add(detailsOverview);

        // Setup related row.
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(
                new OldCardPresenterSelector(getActivity()));
        for (OldCard characterOldCard : data.getCharacters()) listRowAdapter.add(characterOldCard);
        HeaderItem header = new HeaderItem(0, getString(R.string.header_related));
        mRowsAdapter.add(new OldCardListRow(header, listRowAdapter, null));

        // Setup recommended row.
        listRowAdapter = new ArrayObjectAdapter(new OldCardPresenterSelector(getActivity()));
        for (OldCard oldCard : data.getRecommended()) listRowAdapter.add(oldCard);
        header = new HeaderItem(1, getString(R.string.header_recommended));
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        setAdapter(mRowsAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEntranceTransition();
            }
        }, 500);
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(this);
        setOnItemViewClickedListener(this);
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                              RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (!(item instanceof Action)) return;
        Action action = (Action) item;
        if (action.getId() == 3) {
            setSelectedPosition(1);
        } else {
            Toast.makeText(getActivity(), getString(R.string.action_cicked), Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                               RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (mRowsAdapter.indexOf(row) > 0) {
            int backgroundColor = getResources().getColor(R.color.detail_view_related_background);
            getView().setBackgroundColor(backgroundColor);
        } else {
            getView().setBackground(null);
        }
    }
}

