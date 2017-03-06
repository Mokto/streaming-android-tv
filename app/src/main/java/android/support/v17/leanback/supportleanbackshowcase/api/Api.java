package android.support.v17.leanback.supportleanbackshowcase.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by theo on 17-03-04.
 */

public class Api {

    private String baseUrl = "http://192.168.1.103:3000";

    public void getMovies(Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {

        this.get("/movies", context, responseListener, errorListener);

    }

    public void getTrending(Integer page, Integer limit, Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {

        this.get("/movies/trending/" + page + "/" + limit, context, responseListener, errorListener);

    }




    private void get(String path, Context context , Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, this.baseUrl + path, null, responseListener, errorListener);

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }
}
