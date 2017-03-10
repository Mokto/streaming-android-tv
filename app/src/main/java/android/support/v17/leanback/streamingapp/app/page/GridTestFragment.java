package android.support.v17.leanback.streamingapp.app.page;

import android.os.Bundle;
import android.support.v17.leanback.streamingapp.api.Api;
import android.support.v17.leanback.streamingapp.old.oldcards.presenters.OldCardPresenterSelector;
import android.support.v17.leanback.streamingapp.old.oldmodels.OldCard;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class GridTestFragment extends android.support.v17.leanback.streamingapp.old.oldapp.oldpage.GridFragment {
    private static final int COLUMNS = 6;
    private final int ZOOM_FACTOR = FocusHighlight.ZOOM_FACTOR_LARGE;
    private ArrayObjectAdapter mAdapter;
//    private List<OldCard> cards = new ArrayList<>();
    private Boolean isLoading = false;
    private Integer page = 1;
    private static Integer PER_PAGE = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();
        loadData();
//        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }


    private void setupAdapter() {
        VerticalGridPresenter presenter = new VerticalGridPresenter(ZOOM_FACTOR, false);
        presenter.setNumberOfColumns(COLUMNS);
        setGridPresenter(presenter);

        OldCardPresenterSelector cardPresenter = new OldCardPresenterSelector(getActivity());
        mAdapter = new ArrayObjectAdapter(cardPresenter);
        setAdapter(mAdapter);

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {
                OldCard oldCard = (OldCard)item;
                Toast.makeText(getActivity(),
                        "Clicked on "+ oldCard.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {

                Integer indexSelected = mAdapter.indexOf(item);
                if (isLoading == false && (mAdapter.size() - indexSelected < PER_PAGE)) {
                    loadData();
                }
            }

        });
    }

    private void loadData() {
        Api api = new Api();

        this.isLoading = true;
//        api.getTrending(page, PER_PAGE, this.getActivity().getBaseContext(),
//                new Response.Listener<JSONArray>()
//                {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        isLoading = false;
//                        page++;
//
//                        List<OldCard> oldCards = new Gson().fromJson(response.toString(), new TypeToken<List<OldCard>>(){}.getType());
//
//                        mAdapter.addAll(mAdapter.size(), oldCards);
//
//                        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        isLoading = false;
//                        Log.e("Mokto", error.toString());
//                    }
//                });

    }

}
