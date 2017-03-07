package android.support.v17.leanback.streamingapp.app.page;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.old.oldapp.olddetails.OldShadowRowPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldcards.presenters.OldCardPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCardRow;
import android.support.v17.leanback.streamingapp.utils.OldCardListRow;
import android.support.v17.leanback.streamingapp.utils.Utils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.google.gson.Gson;

import org.json.JSONArray;

public class CardsExampleFragment extends RowsFragment {
    private final ArrayObjectAdapter mRowsAdapter;
    private JSONArray response;


    public CardsExampleFragment() {
        mRowsAdapter = new ArrayObjectAdapter(new OldShadowRowPresenterSelector());
        setAdapter(mRowsAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {

            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createRows();
        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }

    private void createRows() {
        String json = Utils
                .inputStreamToString(getResources().openRawResource(R.raw.cards_example));

        OldCardRow[] rows = new Gson().fromJson(json, OldCardRow[].class);
        for (OldCardRow row : rows) {
            mRowsAdapter.add(createCardRow(row));
        }

//        mRowsAdapter.add(new OldCardListRow(new HeaderItem("Test"), null, (OldCardRow) new GridFragment());
    }

    private Row createCardRow(OldCardRow oldCardRow) {
        PresenterSelector presenterSelector = new OldCardPresenterSelector(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);
        for (OldCard oldCard : oldCardRow.getCards()) {
            adapter.add(oldCard);
        }

        HeaderItem headerItem = new HeaderItem(oldCardRow.getTitle());
        return new OldCardListRow(headerItem, adapter, oldCardRow);
    }
}
