package android.support.v17.leanback.streamingapp.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by theo on 17-03-04.
 */

public class Api {

    private String baseUrl = "http://192.168.1.103:3000";
//
//    public void getMovies(Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
//
//        this.get("/movies", context, responseListener, errorListener);
//
//    }
//
//    public void getTrending(Integer page, Integer limit, Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
//
//        this.get("/movies/trending/" + page + "/" + limit, context, responseListener, errorListener);
//
//    }




    public void get(String path, Map urlsParams, Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Log.d("Mokto", this.getUrl(path, urlsParams));

        JSONArray params = new JSONArray();
        JSONObject page = new JSONObject();

        try {
            page.put("page", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put(page);

        Log.w("", params.toString());

        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET,
                this.getUrl(path, urlsParams),
                params,
                responseListener,
                errorListener
        );

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    private String getUrl(String path, Map urlParams) {
        Uri.Builder builder = Uri.parse(path).buildUpon();
        this.setUrlParams(builder, urlParams);

        String url = builder.build().toString();

        return this.baseUrl + url;
    }

    private Uri.Builder setUrlParams(Uri.Builder builder, Map urlParams) {
        Iterator it = urlParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            builder.appendQueryParameter((String) pair.getKey(), (String) pair.getValue());

            it.remove();
        }

        return builder;
    }

}
