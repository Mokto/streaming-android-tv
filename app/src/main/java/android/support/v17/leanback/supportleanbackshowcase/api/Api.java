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
    public void get(Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.1.103:3000/movies";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, responseListener, errorListener);

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    public void getTrending(Context context, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.1.103:3000/movies/trending/1/28";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, responseListener, errorListener);

        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }
}
