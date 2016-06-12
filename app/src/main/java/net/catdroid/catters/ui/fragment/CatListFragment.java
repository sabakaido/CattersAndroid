package net.catdroid.catters.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import net.catdroid.catters.R;
import net.catdroid.catters.models.Cat;
import net.catdroid.catters.network.CatRequest;
import net.catdroid.catters.network.CatRequestStatusListener;
import net.catdroid.catters.ui.views.adapter.CatGridAdapter;
import net.catdroid.catters.ui.views.manager.CatGridLayoutManager;
import net.catdroid.catters.utils.CatUtils;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by anikaido on 2016/06/11.
 *
 * CatListのfragment
 */
public class CatListFragment extends Fragment implements View.OnClickListener {

    private List<Cat> mCatList = new ArrayList<>();
    private boolean isRequestingCat = false;
    private boolean isInit = true;

    private CatGridAdapter mCatGridAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CircularProgressBar mCircularProgressBar;
    private Button mButtonRequestAgain;
    private LinearLayout mLinearLayoutError;

    public static CatListFragment newInstance() {
        CatListFragment fragment = new CatListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatGridAdapter = new CatGridAdapter(getActivity(), mCatList, CatUtils.getDisplayWidth(getContext().getApplicationContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cat_list, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMain);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new CatGridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mCatGridAdapter);
        mRecyclerView.addOnScrollListener(getCatRecyclerViewScrollListener());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutMain);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(getRefreshListener());

        mLinearLayoutError = (LinearLayout) view.findViewById(R.id.linearLayoutCatError);
        mCircularProgressBar = (CircularProgressBar) view.findViewById(R.id.progressBarMain);
        mButtonRequestAgain = (Button) view.findViewById(R.id.buttonCatRequestAgain);
        mButtonRequestAgain.setOnClickListener(this);

        if (isInit) {
            isInit = false;
            requestCat("", true);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.buttonCatRequestAgain) {
            requestCat("", true);
            return;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void requestCat(final String maxId, final boolean isClear) {
        if (isRequestingCat) {
            return;
        }

        isRequestingCat = true;
        if (TextUtils.isEmpty(maxId)) {
            mCircularProgressBar.setVisibility(View.VISIBLE);
            mCatList.clear();
            mCatGridAdapter.setCatList(mCatList);
        }

        CatRequest catRequest = new CatRequest(getActivity(), getLoaderManager(), maxId, 0);
        catRequest.setOnRequestStatusListener(new CatRequestStatusListener() {
            @Override
            public void OnRequestSuccess(List<Cat> data) {

                ArrayList<Cat> favoriteList = CatUtils.getFavoriteCatList(getContext().getApplicationContext());
                final int favoriteSize = favoriteList != null ? favoriteList.size() : 0;
                if (favoriteSize > 0) {
                    final int allSize = data.size();
                    for (int i = 0; i < allSize; i++) {
                        String catId = data.get(i).getCatId();
                        for (int j = 0; j < favoriteSize; j++) {
                            String favoriteId = favoriteList.get(j).getCatId();
                            if (catId.equals(favoriteId)) {
                                data.get(i).setFavorite(true);
                            }
                        }
                    }
                }

                if (isClear) {
                    mCatList = data;
                } else {
                    mCatList.addAll(data);
                }

                mCatGridAdapter.setCatList(mCatList);
                mCatGridAdapter.notifyDataSetChanged();
                isRequestingCat = false;

                mCircularProgressBar.setVisibility(View.GONE);
                mLinearLayoutError.setVisibility(View.GONE);
            }

            @Override
            public void OnRequestTimeout(List<Cat> data) {
                isRequestingCat = false;
                mCircularProgressBar.setVisibility(View.GONE);
                mLinearLayoutError.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnRequestError(List<Cat> data) {
                isRequestingCat = false;
                mCircularProgressBar.setVisibility(View.GONE);
                mLinearLayoutError.setVisibility(View.VISIBLE);
            }
        });
        catRequest.start();
    }

    private RecyclerView.OnScrollListener getCatRecyclerViewScrollListener() {
        final String readingMesage = getResources().getString(R.string.additional_read_cat);
        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CatGridLayoutManager layoutManager = (CatGridLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount && mCatList != null) {
                    final int size = mCatList.size();
                    if (size > 0) {
                        Cat cat = mCatList.get(size -1);
                        if (cat != null) {
                            Snackbar.make(mRecyclerView, readingMesage, Snackbar.LENGTH_SHORT).show();
                            final String maxCatId = cat.getCatId();
                            requestCat(maxCatId, false);
                        }
                    }
                }
            }
        };
        return listener;
    }

    private SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestCat(null, true);
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        };
        return listener;
    }
}
