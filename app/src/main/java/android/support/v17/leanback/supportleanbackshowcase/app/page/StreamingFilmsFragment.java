package android.support.v17.leanback.supportleanbackshowcase.app.page;

import android.os.Bundle;
import android.support.v17.leanback.supportleanbackshowcase.api.Api;
import android.support.v17.leanback.supportleanbackshowcase.app.oldpage.GridFragment;
import android.support.v17.leanback.supportleanbackshowcase.oldcards.presenters.CardPresenterSelector;
import android.support.v17.leanback.supportleanbackshowcase.oldmodels.Card;
import android.support.v17.leanback.supportleanbackshowcase.oldmodels.CardRow;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StreamingFilmsFragment extends GridFragment {
    private static final int COLUMNS = 7;
    private final int ZOOM_FACTOR = FocusHighlight.ZOOM_FACTOR_LARGE;
    private ArrayObjectAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();
        loadData();
        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }


    private void setupAdapter() {
        VerticalGridPresenter presenter = new VerticalGridPresenter(ZOOM_FACTOR);
        presenter.setNumberOfColumns(COLUMNS);
        setGridPresenter(presenter);

        CardPresenterSelector cardPresenter = new CardPresenterSelector(getActivity());
        mAdapter = new ArrayObjectAdapter(cardPresenter);
        setAdapter(mAdapter);

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {
                Card card = (Card)item;
                Toast.makeText(getActivity(),
                        "Clicked on "+card.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        Api api = new Api();
        api.getTrending(this.getActivity().getBaseContext(),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray r) {
                        JSONObject row = new JSONObject();
                        try {
                            row.put("cards", r);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CardRow cardRow = new Gson().fromJson(row.toString(), CardRow.class);
                        mAdapter.addAll(0, cardRow.getCards());

                        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Mokto", "SUCCESS" + error.toString());
                    }
                });

    }

}
