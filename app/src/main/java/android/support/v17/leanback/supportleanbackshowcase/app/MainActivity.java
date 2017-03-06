package android.support.v17.leanback.supportleanbackshowcase.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.supportleanbackshowcase.R;
import android.support.v17.leanback.supportleanbackshowcase.app.search.SearchActivity;

/**
 * Created by theo on 17-03-05.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);
    }
//
//    @Override
//    public boolean onSearchRequested() {
//        startActivity(new Intent(this, SearchActivity.class));
//        return true;
//    }
}

