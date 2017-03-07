package android.support.v17.leanback.streamingapp.app.page;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.streamingapp.api.Api;
import android.support.v17.leanback.streamingapp.api.VidSourceApi;
import android.support.v17.leanback.streamingapp.old.oldapp.olddetails.ShadowRowPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldcards.presenters.CardPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldmodels.Card;
import android.support.v17.leanback.streamingapp.old.oldmodels.CardRow;
import android.support.v17.leanback.streamingapp.utils.CardListRow;
import android.support.v17.leanback.streamingapp.utils.Utils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;

public class MoviesFragment extends RowsFragment {
    private final ArrayObjectAdapter mRowsAdapter;
    private JSONArray response;


    public MoviesFragment() {
        mRowsAdapter = new ArrayObjectAdapter(new ShadowRowPresenterSelector());
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
                .inputStreamToString(getResources().openRawResource(R.raw.movies_menu));

        CardRow[] rows = new Gson().fromJson(json, CardRow[].class);
        for (CardRow row : rows) {
            mRowsAdapter.add(createCardRow(row));
        }

//        mRowsAdapter.add(new CardListRow(new HeaderItem("Test"), null, (CardRow) new GridFragment());
    }

    private Row createCardRow(CardRow cardRow) {
        PresenterSelector presenterSelector = new CardPresenterSelector(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);
        for (Card card : cardRow.getCards()) {
            adapter.add(card);
        }

        HeaderItem headerItem = new HeaderItem(cardRow.getTitle());
        return new CardListRow(headerItem, adapter, cardRow);
    }
}
