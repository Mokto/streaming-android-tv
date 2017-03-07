package android.support.v17.leanback.streamingapp.app.search;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.support.v17.leanback.streamingapp.utils.OldUtils;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider {

    private static final int REQUEST_SPEECH = 0x00000010;
    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        Log.d("Mokto", "start Activity");
        setSearchResultProvider(this);

        if (!OldUtils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
            // SpeechRecognitionCallback is not required and if not provided recognition will be handled
            // using internal speech recognizer, in which case you must have RECORD_AUDIO permission
            setSpeechRecognitionCallback(new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    Log.d("Mokto", "recognizeSpeech");
                    try {
                        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
                    } catch (ActivityNotFoundException e) {
                        Log.e("Mokto", "Cannot find activity for speech recognizer", e);
                    }
                }
            });
        }
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        Log.d("Mokto", "getResultsAdapter");
        Log.d("Mokto", mRowsAdapter.toString());

        // It should return search result here,
        // but static OldMovie Item list will be returned here now for practice.
//        ArrayList<OldMovie> mItems = MovieProvider.getMovieItems();
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        listRowAdapter.addAll(0, mItems);
//        HeaderItem header = new HeaderItem("Search results");
//        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.i("Mokto", String.format("Search Query Text Change %s", newQuery));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("Mokto", String.format("Search Query Text Submit %s", query));
        return true;
    }
}