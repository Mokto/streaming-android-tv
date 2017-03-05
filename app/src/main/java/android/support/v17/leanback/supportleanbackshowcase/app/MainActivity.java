package android.support.v17.leanback.supportleanbackshowcase.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.supportleanbackshowcase.R;

/**
 * Created by theo on 17-03-05.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);
    }
}

