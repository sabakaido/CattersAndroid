package net.catdroid.catters.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import net.catdroid.catters.R;
import net.catdroid.catters.managers.CatImageArrayAdapter;
import net.catdroid.catters.managers.CattersAsyncTaskLoader;
import net.catdroid.catters.models.home.CatImage;
import net.catdroid.catters.models.home.ListItem;
import net.catdroid.catters.views.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/04/30.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CatImage>> {
    public static final String TAG = "HOME";

    private ProgressDialog progressDialog;
    private GridView lv;
    private View view;
    private HomeActivity activity;
    private static final int LOADER_ID = 0;

    private static final String requestUrl = "http://catroid.net:3000/get/CatApartment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_listview, null);
        List<ListItem> list = new ArrayList<ListItem>();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("読み込み中...");
        progressDialog.show();

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<List<CatImage>> onCreateLoader(int id, Bundle args) {
        CattersAsyncTaskLoader cattersAsyncTaskLoader = new CattersAsyncTaskLoader(getActivity(), requestUrl);

        cattersAsyncTaskLoader.forceLoad();
        return cattersAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<CatImage>> loader, final List<CatImage> catImageList) {
        progressDialog.hide();
        final CatImageArrayAdapter adapter = new CatImageArrayAdapter(getActivity(), R.layout.list_imageview, catImageList);

        lv = (GridView) view.findViewById(R.id.home_cat);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catImageList.get(position).getImageUrl();
                activity.flagment(view, catImageList.get(position).getImageUrl());
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<CatImage>> loader) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof HomeActivity) {
            this.activity = (HomeActivity) context;
        }
    }
}
