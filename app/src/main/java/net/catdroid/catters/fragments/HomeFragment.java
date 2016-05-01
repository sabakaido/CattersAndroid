package net.catdroid.catters.fragments;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import net.catdroid.catters.catters.R;
import net.catdroid.catters.managers.CatImageArrayAdapter;
import net.catdroid.catters.managers.CattersAsyncTaskLoader;
import net.catdroid.catters.models.home.CatImage;
import net.catdroid.catters.models.home.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/04/30.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CatImage>> {
    private String requestUrl;
    private ProgressDialog progressDialog;
    private GridView lv;
    private View view;
    private static final int LOADER_ID = 0;

    public HomeFragment(String requestUrl) {
        this.requestUrl = requestUrl;
    }

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
        progressDialog.setMessage("ねこ取得中");
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
        CatImageArrayAdapter adapter = new CatImageArrayAdapter(getActivity(), R.layout.cat_list_item, catImageList);

        lv = (GridView) view.findViewById(R.id.cat);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), catImageList.get(position).getImageUrl(), Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<CatImage>> loader) {

    }
}
