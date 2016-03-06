package net.catdroid.catters.catters.home.dynamicview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.catdroid.catters.catters.R;
import net.catdroid.catters.catters.home.dynamicview.model.CatImage;

import java.util.List;

/**
 * Created by anikaido on 2016/03/06.
 */
public class CatImageArrayAdapter extends ArrayAdapter<CatImage> {
    private int resource;
    private List<CatImage> listItems;
    private LayoutInflater inflater;

    public CatImageArrayAdapter(Context context, int resource, List<CatImage> listItems) {
        super(context, resource, listItems);

        this.resource = resource;
        this.listItems = listItems;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = this.inflater.inflate(this.resource, null);
        }

        CatImage catImage = this.listItems.get(position);

        ImageView imageView = (ImageView)view.findViewById(R.id.cat_image);

        Picasso.with(getContext())
                .load(catImage.getImageUrl())
                .into(imageView);

        return view;
    }
}
