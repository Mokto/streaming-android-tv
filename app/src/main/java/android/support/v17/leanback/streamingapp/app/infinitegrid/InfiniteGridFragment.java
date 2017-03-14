package android.support.v17.leanback.streamingapp.app.infinitegrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.streamingapp.api.IO;
import android.support.v17.leanback.streamingapp.app.search.SearchActivity;
import android.support.v17.leanback.streamingapp.model.Card;
import android.support.v17.leanback.streamingapp.old.oldapp.olddetails.OldDetailViewExampleActivity;
import android.support.v17.leanback.streamingapp.presenter.CardPresenterSelector;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.Ack;

public class InfiniteGridFragment extends VerticalGridFragment {

    private static final int COLUMNS = 6;
    private final int ZOOM_FACTOR = FocusHighlight.ZOOM_FACTOR_LARGE;
    private ArrayObjectAdapter mAdapter;

//    private Api api = new Api();
    private String id;
    private JSONObject params;
    private Integer page = 1;
    private static Integer LIMIT = 30;
    private Boolean isLoading = false;
    private Boolean isDone = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        Bundle bundle = getActivity().getIntent().getExtras();

        setTitle(bundle.getString("title"));

        id = bundle.getString("id");
        try {
            params = new JSONObject(bundle.getString("params"));
            Log.w("socket", params.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setupAdapter();
        loadData();
    }


    private void setupAdapter() {
        VerticalGridPresenter presenter = new VerticalGridPresenter(ZOOM_FACTOR, false);
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
                Card oldCard = (Card) item;


                Intent intent = new Intent(getActivity().getBaseContext(), OldDetailViewExampleActivity.class);
//                intent.putExtra("id", card.getId());

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
                        .toBundle();
                startActivity(intent, bundle);
//                Toast.makeText(getActivity(),
//                        "Clicked on "+ oldCard.getTitle(),
//                        Toast.LENGTH_SHORT).show();
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
                if (isLoading == false && (mAdapter.size() - indexSelected < LIMIT)) {
                    loadData();
                }
            }

        });
    }

    private void loadData() {

        if(isDone) {
            return;
        }

        Log.i("socket", params.toString());
        try {
            params.put("limit", LIMIT);
            params.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.isLoading = true;

        IO.emit("movies", params, new Ack() {
            @Override
            public void call(Object... args) {
                final JSONArray response = (JSONArray) args[0];

                if(getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        isLoading = false;
                        page++;

                        List<Card> cards = new Gson().fromJson(response.toString(), new TypeToken<List<Card>>(){}.getType());

                        if(cards.size() == 0) {
                            isDone = true;
                        }

                        mAdapter.addAll(mAdapter.size(), cards);

                        startEntranceTransition();
                    }
                });
            }
        });

//        this.isLoading = true;
//        api.get(url, urlParams, this.getActivity().getBaseContext(),
//                new Response.Listener<JSONArray>()
//                {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        isLoading = false;
//                        page++;
//
//                        List<Card> cards = new Gson().fromJson(response.toString(), new TypeToken<List<Card>>(){}.getType());
//
//                        mAdapter.addAll(mAdapter.size(), cards);
//
//                        startEntranceTransition();
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
