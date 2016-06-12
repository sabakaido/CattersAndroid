package net.catdroid.catters.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.catdroid.catters.R;
import net.catdroid.catters.models.Cat;
import net.catdroid.catters.ui.views.adapter.CatGridAdapter;
import net.catdroid.catters.ui.views.manager.CatGridLayoutManager;
import net.catdroid.catters.utils.CatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/06/12.
 */
public class CatFavoriteListFragment extends Fragment {

    private List<Cat> mCatList = new ArrayList<>();

    private CatGridAdapter mCatGridAdapter;

    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayoutEmpty;

    public static CatFavoriteListFragment newInstance() {
        CatFavoriteListFragment fragment = new CatFavoriteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatGridAdapter = new CatGridAdapter(getActivity(), mCatList, CatUtils.getDisplayWidth(getContext().getApplicationContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFavorite);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new CatGridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mCatGridAdapter);
        mLinearLayoutEmpty = (LinearLayout) view.findViewById(R.id.linearLayoutFavoriteEmpty);

        refreshFavorites();

        return view;
    }

    public void refreshFavorites() {
        mCatList = CatUtils.getFavoriteCatList(getContext().getApplicationContext());
        if (mCatList == null) {
            mLinearLayoutEmpty.setVisibility(View.VISIBLE);
            return;
        }

        if (mCatList.isEmpty()) {
            mCatGridAdapter.setCatList(mCatList);
            mCatGridAdapter.notifyDataSetChanged();
            mLinearLayoutEmpty.setVisibility(View.VISIBLE);
            return;
        }

        mCatGridAdapter.setCatList(mCatList);
        mCatGridAdapter.notifyDataSetChanged();
        mLinearLayoutEmpty.setVisibility(View.GONE);
    }
}
