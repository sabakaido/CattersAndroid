package net.catdroid.catters.catters.home.staticview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import net.catdroid.catters.catters.R;
import net.catdroid.catters.catters.home.staticview.model.ListItem;

import java.util.List;

/**
 * Created by anikaido on 2016/03/05.
 */
public class ListviewArrayAdapter extends ArrayAdapter<ListItem> {

    private int resourceId;
    private List<ListItem> listItems;
    private LayoutInflater inflater;

    public ListviewArrayAdapter(Context context, int resourceId, List<ListItem> listItems) {
        super(context, resourceId, listItems);

        this.resourceId = resourceId;
        this.listItems = listItems;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = this.inflater.inflate(this.resourceId, null);
        }

        ListItem listItem = this.listItems.get(position);

        ImageView imageView = (ImageView)view.findViewById(R.id.cat_image);
        imageView.setImageResource(listItem.getImageId());

        return view;
    }
}
