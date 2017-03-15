package android.support.v17.leanback.streamingapp.app;

import android.content.Context;
import android.support.v17.leanback.streamingapp.R;
import android.support.v17.leanback.widget.SearchOrbView;
import android.support.v17.leanback.widget.TitleViewAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Custom title view to be used in {@link android.support.v17.leanback.app.BrowseFragment}.
 */
public class BrowseTitleView extends RelativeLayout implements TitleViewAdapter.Provider {
    private final TextView mTitleView;
    private final View mAnalogClockView;
    private final SearchOrbView mSearchOrbView;

    private final TitleViewAdapter mTitleViewAdapter = new TitleViewAdapter() {
        @Override
        public View getSearchAffordanceView() {
            return mSearchOrbView;
        }

        @Override
        public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
            mSearchOrbView.setOrbColors(colors);
        }

        @Override
        public void setTitle(CharSequence titleText) {
            BrowseTitleView.this.setTitle(titleText);
        }

        @Override
        public void setOnSearchClickedListener(OnClickListener listener) {
            mSearchOrbView.setOnClickListener(listener);
        }
//
//        @Override
//        public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
//            Log.d("Mokto", "Set colors");
////            mTitleViewAdapter.setSearchAffordanceColors(colors);
////            mSearchOrbView.setBackgroundColor(colors.color);
//        }

        @Override
        public void updateComponentsVisibility(int flags) {
            int visibility = (flags & SEARCH_VIEW_VISIBLE) == SEARCH_VIEW_VISIBLE
                    ? View.VISIBLE : View.INVISIBLE;
            mSearchOrbView.setVisibility(visibility);
        }
    };

    public BrowseTitleView(Context context) {
        this(context, null);
    }

    public BrowseTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrowseTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View root  = LayoutInflater.from(context).inflate(R.layout.custom_titleview, this);
        mTitleView = (TextView) root.findViewById(R.id.title_tv);
        mAnalogClockView = root.findViewById(R.id.clock);
        Log.d("Mokto", "PLouf");
        mSearchOrbView = (SearchOrbView) root.findViewById(R.id.search_orb);
    }

    public void setTitle(CharSequence title) {
        if (title != null) {
            mTitleView.setText(title);
            mTitleView.setVisibility(View.VISIBLE);
            mAnalogClockView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public TitleViewAdapter getTitleViewAdapter() {
        return mTitleViewAdapter;
    }



//    public void setBadgeDrawable(Drawable drawable) {
//        if (drawable != null) {
//            mTitleView.setVisibility(View.GONE);
//            mAnalogClockView.setVisibility(View.VISIBLE);
//        }
//    }

//
//        @Override
//        public void setBadgeDrawable(Drawable drawable) {
//            //CustomTitleView.this.setBadgeDrawable(drawable);
//        }
}
