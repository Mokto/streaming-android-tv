package android.support.v17.leanback.streamingapp.app.page;

import android.app.Activity;
import android.os.Handler;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.old.oldapp.oldpage.SettingsIconPresenterOldOld;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCardRow;
import android.support.v17.leanback.streamingapp.utils.OldCardListRow;
import android.support.v17.leanback.streamingapp.utils.Utils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.google.gson.Gson;

public class SettingsFragment extends RowsFragment {

    private final ArrayObjectAdapter mRowsAdapter;

    public SettingsFragment() {
        ListRowPresenter selector = new ListRowPresenter();
        selector.setNumRows(2);
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
            OldCardRow oldCardRow = new Gson().fromJson(json, OldCardRow.class);
            mRowsAdapter.add(createCardRow(oldCardRow));
            mRowsAdapter.add(createCardRow(oldCardRow));
            getMainFragmentAdapter().getFragmentHost().notifyDataReady(
                    getMainFragmentAdapter());
        }
    }

    private ListRow createCardRow(OldCardRow oldCardRow) {
        SettingsIconPresenterOldOld iconCardPresenter = new SettingsIconPresenterOldOld(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(iconCardPresenter);
        for(OldCard oldCard : oldCardRow.getCards()) {
            adapter.add(oldCard);
        }

        HeaderItem headerItem = new HeaderItem(oldCardRow.getTitle());
        return new OldCardListRow(headerItem, adapter, oldCardRow);
    }
}
