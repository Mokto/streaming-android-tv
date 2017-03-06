package android.support.v17.leanback.supportleanbackshowcase.app.page;

import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.supportleanbackshowcase.api.Api;
import android.support.v17.leanback.supportleanbackshowcase.api.VidSourceApi;
import android.support.v17.leanback.supportleanbackshowcase.old.oldapp.olddetails.ShadowRowPresenterSelector;
import android.support.v17.leanback.supportleanbackshowcase.old.oldcards.presenters.CardPresenterSelector;
import android.support.v17.leanback.supportleanbackshowcase.old.oldmodels.Card;
import android.support.v17.leanback.supportleanbackshowcase.old.oldmodels.CardRow;
import android.support.v17.leanback.supportleanbackshowcase.utils.CardListRow;
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

public class StreamingTVFragment extends RowsFragment {
    private final ArrayObjectAdapter mRowsAdapter;
    private JSONArray response;


    public StreamingTVFragment() {
        mRowsAdapter = new ArrayObjectAdapter(new ShadowRowPresenterSelector());

        setAdapter(mRowsAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {


                getEmbeddableLink(((Card) item).getImdbId());

                Toast.makeText(getActivity(), "Implement click handler", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void getEmbeddableLink(String imdbId) {
        VidSourceApi api = new VidSourceApi();
        api.get(imdbId, this.getActivity().getBaseContext(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String r) {

                        //"https://openload.co/embed/zQqgABpbm1s"

                        Log.d("Mokto", r);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Mokto", error.getMessage() + " / " + error.networkResponse.statusCode);
                    }
                });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getData();
    }

    public void getData() {
        Api api = new Api();
        api.getMovies(this.getActivity().getBaseContext(),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray r) {
                        response = r;
                        onDataResponse();
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

    public void onDataResponse() {
        createRows();
        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }

    private void createRows() {

        CardRow[] rows = new Gson().fromJson(response.toString(), CardRow[].class);
        for (CardRow row : rows) {
            if (row.getType() == CardRow.TYPE_DEFAULT) {
                Row cardRow2 = createCardRow(row);
                cardRow2.setHeaderItem(null);
//                    if (row.getTitle() == "") {
//                        cardRow2.setHeaderItem(null);
//                    }
                mRowsAdapter.add(cardRow2);
            }
        }
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
