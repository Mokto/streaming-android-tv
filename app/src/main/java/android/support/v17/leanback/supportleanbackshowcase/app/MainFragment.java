package android.support.v17.leanback.supportleanbackshowcase.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.supportleanbackshowcase.R;
import android.support.v17.leanback.supportleanbackshowcase.app.page.SettingsFragment;
import android.support.v17.leanback.supportleanbackshowcase.app.page.StreamingFilmsFragment;
import android.support.v17.leanback.supportleanbackshowcase.app.page.StreamingTVFragment;
import android.support.v17.leanback.supportleanbackshowcase.app.search.SearchActivity;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.SearchOrbView;
import android.util.Log;
import android.view.View;


public class MainFragment extends BrowseFragment {
    private static final long HEADER_ID_1 = 1;
    private static final String HEADER_NAME_1 = "Streaming Films";
    private static final long HEADER_ID_2 = 2;
    private static final String HEADER_NAME_2 = "Streaming TV";
    private static final long HEADER_ID_3 = 3;
    private static final String HEADER_NAME_3 = "Settings";
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
//        setSearchAffordanceColor(getResources().getColor(R.color.accent));

//        mBrowseFragment.setSearchAffordanceColor(getResources().getColor(R.color.primary_dark));
//        SearchOrbView.Colors colors = new SearchOrbView.Colors(10, 10, 10);
//        setSearchAffordanceColors(colors);

        setTitle("Welcome to Coloc 5815");

        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            Log.d("Mokto", "Click search");
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

        HeaderItem headerItem3 = new HeaderItem(HEADER_ID_3, HEADER_NAME_3);
        PageRow pageRow3 = new PageRow(headerItem3);
        mRowsAdapter.add(pageRow3);
    }

    private static class PageRowFragmentFactory extends BrowseFragment.FragmentFactory {
        private final BackgroundManager mBackgroundManager;

        PageRowFragmentFactory(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }

        @Override
        public Fragment createFragment(Object rowObj) {
            Row row = (Row)rowObj;
            mBackgroundManager.setDrawable(null);

            if (row.getHeaderItem().getId() == HEADER_ID_1) {
                return new StreamingFilmsFragment();

            } else if (row.getHeaderItem().getId() == HEADER_ID_2) {
                return new StreamingTVFragment();

            } else if (row.getHeaderItem().getId() == HEADER_ID_3) {
                return new SettingsFragment();
            }

            throw new IllegalArgumentException(String.format("Invalid row %s", rowObj));
        }
    }

}
