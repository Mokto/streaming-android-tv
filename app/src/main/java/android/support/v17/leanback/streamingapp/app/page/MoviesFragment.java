package android.support.v17.leanback.streamingapp.app.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.streamingapp.api.IO;
import android.support.v17.leanback.streamingapp.app.infinitegrid.InfiniteGridActivity;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.streamingapp.model.CardRow;
import android.support.v17.leanback.streamingapp.presenter.CardPresenterSelector;
import android.support.v17.leanback.streamingapp.presenter.ShadowRowPresenterSelector;
import android.support.v17.leanback.streamingapp.utils.CardListRow;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import io.socket.client.Ack;

public class MoviesFragment extends RowsFragment {
    private final ArrayObjectAdapter mRowsAdapter;

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
                Card card = (Card) item;

                Intent intent = new Intent(getActivity().getBaseContext(), InfiniteGridActivity.class);
                intent.putExtra("id", card.getId());
                intent.putExtra("params", card.getParams());
                intent.putExtra("title", card.getTitle());

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                        .toBundle();
                startActivity(intent, bundle);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getMenu();

    }

    private void getMenu() {
        IO.emit("movies-menu", new Ack() {
            @Override
            public void call(Object... args) {
                final JSONArray menu = (JSONArray) args[0];

                if(getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        createRows(menu.toString());
                        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
                    }
                });
            }
        });
    }

    private void createRows(String json) {

        CardRow[] rows = new Gson().fromJson(json, CardRow[].class);
        for (CardRow row : rows) {
            mRowsAdapter.add(createCardRow(row));
        }

    }

    private Row createCardRow(CardRow CardRow) {
        PresenterSelector presenterSelector = new CardPresenterSelector(getActivity());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenterSelector);

        for (Card card : CardRow.getCards()) {
            adapter.add(card);
        }

        HeaderItem headerItem = new HeaderItem(CardRow.ids.inverse().get(CardRow.getId()), CardRow.getTitle());
        return new CardListRow(headerItem, adapter, CardRow);
    }
}
