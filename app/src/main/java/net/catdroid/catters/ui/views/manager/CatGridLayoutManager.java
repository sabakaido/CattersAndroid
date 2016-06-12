package net.catdroid.catters.ui.views.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by anikaido on 2016/06/12.
 */
public class CatGridLayoutManager extends GridLayoutManager {

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    public CatGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CatGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
