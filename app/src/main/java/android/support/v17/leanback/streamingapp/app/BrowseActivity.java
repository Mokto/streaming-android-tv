package android.support.v17.leanback.streamingapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.streamingapp.R;

/**
 * Created by theo on 17-03-05.
 */

public class BrowseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_fragment);
    }
//
//    @Override
//    public boolean onSearchRequested() {
//        startActivity(new Intent(this, SearchActivity.class));
//        return true;
//    }
}

