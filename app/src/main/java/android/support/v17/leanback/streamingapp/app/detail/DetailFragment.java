package android.support.v17.leanback.streamingapp.app.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.api.IO;
import android.support.v17.leanback.streamingapp.app.loading.SpinnerFragment;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.streamingapp.model.DetailedCard;
import android.support.v17.leanback.streamingapp.presenter.CardPresenterSelector;
import android.support.v17.leanback.streamingapp.utils.OldCardListRow;
import android.support.v17.leanback.streamingapp.utils.PicassoBackgroundManager;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import io.socket.client.Ack;

//import android.support.v17.leanback.streamingapp.old.oldcards.presenters.OldCardPresenterSelector;

public class DetailFragment extends DetailsFragment implements OnItemViewClickedListener, OnItemViewSelectedListener {

    public static final String TRANSITION_NAME = "t_for_transition";
    public static final String EXTRA_CARD = "card";

    private ArrayObjectAdapter mRowsAdapter;
    private DetailedCard detailedCard;
    private DetailsOverviewRow detailsOverview;
    private Bitmap background = null;
    private static PicassoBackgroundManager picassoBackgroundManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        setupEventListeners();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
    }

    private void setupUi() {
        // Load the card we want to display from a JSON resource. This JSON data could come from
        // anywhere in a real world app, e.g. a server.

        // Setup fragment

        FullWidthDetailsOverviewRowPresenter rowPresenter = new FullWidthDetailsOverviewRowPresenter(
                new DetailDescriptionPresenter(getActivity())) {

            @Override
            protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
                RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);

                View actionsView = viewHolder.view.
                        findViewById(R.id.details_overview_actions_background);
                actionsView.setBackgroundColor(getActivity().getResources().
                        getColor(R.color.primary_dark));


                View detailsView = viewHolder.view.findViewById(R.id.details_frame);
                detailsView.setBackgroundColor(
                        getResources().getColor(R.color.primary_alpha));

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

        this.getData();
        // Do some background process here.
        // It just waits 5 sec in this Tutorial
    }

    private void getData() {
        final SpinnerFragment mSpinnerFragment = new SpinnerFragment();
        getFragmentManager().beginTransaction().add(R.id.detailsFragment, mSpinnerFragment).commit();

        IO.emit("movie", "moana-2016", new Ack() {
            @Override
            public void call(final Object... args) {

                detailedCard = new Gson().fromJson(((JSONObject) args[0]).toString(), DetailedCard.class);
                detailsOverview = new DetailsOverviewRow(detailedCard);
                if(detailedCard.getPoster() != null) {
                    try {
                        Bitmap poster = Picasso.with(getActivity()).load(detailedCard.getPoster()).get();
                        detailsOverview.setImageBitmap(getActivity(), poster);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(detailedCard.getBackground() != null) {
                    picassoBackgroundManager.updateBackgroundWithDelay(detailedCard.getBackground());
                }



                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
                        loadData();
                    }
                });
            }
        });
    }

    private void loadData() {// @drawable/background_canyon

        // Setup action and detail row.


//        detailsOverview.


//        int imageResId = data.getLocalImageResourceId(getActivity());
//
//        Bundle extras = getActivity().getIntent().getExtras();
//        if (extras != null && extras.containsKey(EXTRA_CARD)) {
//            imageResId = extras.getInt(EXTRA_CARD, imageResId);
//        }
//        detailsOverview.setImageDrawable(getResources().getDrawable(imageResId, null));

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
        actionAdapter.add(new Action(1, getString(R.string.action_buy) + detailedCard.getPrice()));
        actionAdapter.add(new Action(2, getString(R.string.action_wishlist)));
        actionAdapter.add(new Action(3, getString(R.string.action_related)));
        detailsOverview.setActionsAdapter(actionAdapter);
        mRowsAdapter.add(detailsOverview);

        HeaderItem header;
        ArrayObjectAdapter listRowAdapter;

        // Setup related row.
//        listRowAdapter = new ArrayObjectAdapter(
//                new CardPresenterSelector(getActivity()));
//        for (Card characterOldCard : data.getCharacters()) listRowAdapter.add(characterOldCard);
//        header = new HeaderItem(0, getString(R.string.header_related));
//        mRowsAdapter.add(new OldCardListRow(header, listRowAdapter, null));

        // Setup recommended row.
        listRowAdapter = new ArrayObjectAdapter(new CardPresenterSelector(getActivity()));
        for (Card oldCard : detailedCard.getRecommended()) listRowAdapter.add(oldCard);
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

