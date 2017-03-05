package android.support.v17.leanback.supportleanbackshowcase.app.page;

import android.app.Activity;
import android.os.Handler;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.supportleanbackshowcase.R;
import android.support.v17.leanback.supportleanbackshowcase.app.oldpage.SettingsIconPresenter;
import android.support.v17.leanback.supportleanbackshowcase.oldmodels.Card;
import android.support.v17.leanback.supportleanbackshowcase.oldmodels.CardRow;
import android.support.v17.leanback.supportleanbackshowcase.utils.CardListRow;
import android.support.v17.leanback.supportleanbackshowcase.utils.Utils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.google.gson.Gson;

public class SettingsFragment extends RowsFragment {

    private final ArrayObjectAdapter mRowsAdapter;

    public SettingsFragment() {
        ListRowPresenter selector = new ListRowPresenter();
        selector.setNumRows(1);
        mRowsAdapter = new ArrayObjectAdapter(selector);
        setAdapter(mRowsAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 200);
    }

    private void loadData() {
        if (isAdded()) {
            String json = Utils.inputStreamToString(getResources().openRawResource(
                    R.raw.icon_example));
            CardRow cardRow = new Gson().fromJson(json, CardRow.class);
            mRowsAdapter.add(createCardRow(cardRow));
            getMainFragmentAdapter().getFragmentHost().notifyDataReady(
                    getMainFragmentAdapter());
        }
    }

    private ListRow createCardRow(CardRow cardRow) {
        SettingsIconPresenter iconCardPresenter = new SettingsIconPresenter(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(iconCardPresenter);
        for(Card card : cardRow.getCards()) {
            adapter.add(card);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }
}
