package net.catdroid.catters.catters.home.staticview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.catdroid.catters.catters.R;
import net.catdroid.catters.catters.home.staticview.model.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/03/06.
 */
public class StaticListViewFragment extends Fragment {
    private View view;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_listview, null);
        List<ListItem> list = new ArrayList<ListItem>();

        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem();
            item.setImageId(R.drawable.cat);
            list.add(item);
        }

        ListviewArrayAdapter adapter = new ListviewArrayAdapter(getActivity(), R.layout.cat_list_item, list);

        lv = (ListView) view.findViewById(R.id.cat);
        lv.setAdapter(adapter);

        return view;
    }
}
