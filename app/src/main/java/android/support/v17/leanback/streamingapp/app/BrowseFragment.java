package android.support.v17.leanback.streamingapp.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.app.page.GridTestFragment;
import android.support.v17.leanback.streamingapp.app.page.MoviesFragment;
import android.support.v17.leanback.streamingapp.app.page.SettingsFragment;
import android.support.v17.leanback.streamingapp.app.page.CardsExampleFragment;
import android.support.v17.leanback.streamingapp.app.search.SearchActivity;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.SearchOrbView;
import android.view.View;


public class BrowseFragment extends android.support.v17.leanback.app.BrowseFragment {
    private static final long HEADER_ID_1 = 1;
    private static final String HEADER_NAME_1 = "Movies";
    private static final long HEADER_ID_2 = 2;
    private static final String HEADER_NAME_2 = "Settings";
    private static final long HEADER_ID_4 = 4;
    private static final String HEADER_NAME_4 = "Cards Example";

    private BackgroundManager mBackgroundManager;

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        loadData();
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        getMainFragmentRegistry().registerFragment(PageRow.class,
                new PageRowFragmentFactory(mBackgroundManager));
    }

    private void setupUi() {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.primary_dark));
        setSearchAffordanceColors(new SearchOrbView.Colors(getResources().getColor(R.color.accent)));

        setTitle("Coloc 5815 Parc");

        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            }
        });

        prepareEntranceTransition();
    }

    private void loadData() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createRows();
                startEntranceTransition();
            }
        }, 2000);
    }

    private void createRows() {

        HeaderItem headerItem1 = new HeaderItem(HEADER_ID_1, HEADER_NAME_1);
        PageRow pageRow1 = new PageRow(headerItem1);
        mRowsAdapter.add(pageRow1);

        HeaderItem headerItem2 = new HeaderItem(HEADER_ID_2, HEADER_NAME_2);
        PageRow pageRow2 = new PageRow(headerItem2);
        mRowsAdapter.add(pageRow2);

        HeaderItem headerItem4 = new HeaderItem(HEADER_ID_4, HEADER_NAME_4);
        PageRow pageRow4 = new PageRow(headerItem4);
        mRowsAdapter.add(pageRow4);
    }

    private static class PageRowFragmentFactory extends android.support.v17.leanback.app.BrowseFragment.FragmentFactory {
        private final BackgroundManager mBackgroundManager;

        PageRowFragmentFactory(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }

        @Override
        public Fragment createFragment(Object rowObj) {
            Row row = (Row)rowObj;
            mBackgroundManager.setDrawable(null);

            if (row.getHeaderItem().getId() == HEADER_ID_1) {
                return new MoviesFragment();

            } else if (row.getHeaderItem().getId() == HEADER_ID_2) {
                return new SettingsFragment();

            } else if (row.getHeaderItem().getId() == HEADER_ID_4) {
                return new CardsExampleFragment();
            }

            throw new IllegalArgumentException(String.format("Invalid row %s", rowObj));
        }
    }

}
