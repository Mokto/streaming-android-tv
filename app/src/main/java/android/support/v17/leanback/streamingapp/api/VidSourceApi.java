package android.support.v17.leanback.streamingapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mokto on 04/03/2017.
 */

public class VidSourceApi {
    public void get(String imdbId, Context context, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.vidsourceapi.com/WebService.asmx/GetStreamEmbedUrlByIMDBID?apikey=caksDrL1kpHjNvos&imdbid="+imdbId+"&redirecton=false";//0816692

        Log.d("Mokto", url);

        StringRequest getRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        // Add the request to the RequestQueue.
        queue.add(getRequest);
    }
}
