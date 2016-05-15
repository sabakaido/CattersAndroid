package net.catdroid.catters.managers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.catdroid.catters.R;
import net.catdroid.catters.models.home.CatImage;

import java.util.List;

/**
 * Created by anikaido on 2016/03/06.
 */
public class CatImageArrayAdapter extends ArrayAdapter<CatImage> {
    private int resource;
    private List<CatImage> listItems;
    private LayoutInflater inflater;
    private Context context;

    public CatImageArrayAdapter(Context context, int resource, List<CatImage> listItems) {
        super(context, resource, listItems);

        this.resource = resource;
        this.listItems = listItems;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.resource, parent, false).findViewById(R.id.cat_image);
        }

        String url = listItems.get(position).getImageUrl();

        Picasso.with(context)
                .load(url)
                .into((ImageView) convertView);

        return convertView;
    }
}
