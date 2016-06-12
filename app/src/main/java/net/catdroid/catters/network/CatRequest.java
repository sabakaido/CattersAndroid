package net.catdroid.catters.network;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;

import net.catdroid.catters.models.Cat;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatRequest implements LoaderCallbacks<Pair<Integer, List<Cat>>> {

    private Activity mActivity;
    private LoaderManager mLoaderManager;
    private int mLoaderId = -1;
    private String mMaxCatId = "";

    // イベントリスナ
    private CatRequestStatusListener mCatRequestStatusListener;

    public CatRequest(Activity activity, LoaderManager loaderManager, String maxCatId, int loaderId) {
        mActivity = activity;
        mLoaderManager = loaderManager;
        mLoaderId = loaderId;
        mMaxCatId = maxCatId;
    }

    public void start() {
        try {
            mLoaderManager.destroyLoader(mLoaderId);
            mLoaderManager.restartLoader(mLoaderId, null, this);
        } catch (RejectedExecutionException e) {
        }
    }

    @Override
    public Loader<Pair<Integer, List<Cat>>> onCreateLoader(int id, Bundle args) {
        CatAsyncTaskLoader loader = new CatAsyncTaskLoader(mActivity, mMaxCatId);

        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Pair<Integer, List<Cat>>> loader, Pair<Integer, List<Cat>> pair) {
        final int statusCode = pair.first;
        final List<Cat> data = pair.second;

        if (statusCode == 0) {
            mCatRequestStatusListener.OnRequestTimeout(data);
        } else if (statusCode < 400) {
            mCatRequestStatusListener.OnRequestSuccess(data);
        } else {
            mCatRequestStatusListener.OnRequestError(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Pair<Integer, List<Cat>>> loader) {

    }

    // StatusListenerを外から注入
    public void setOnRequestStatusListener(CatRequestStatusListener l) {
        mCatRequestStatusListener = l;
    }
}
