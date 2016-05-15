package net.catdroid.catters.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.catdroid.catters.R;
import net.catdroid.catters.views.HomeActivity;

/**
 * Created by anikaido on 2016/05/14.
 */
public class ExpandedImageFragment extends Fragment {
    public static final String TAG = "EXPAND";

    private View view;
    private HomeActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.expanded_imageview, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.expanded_image);
        imageView.setTransitionName("cat");

        String url = getArguments().getString("url");

        Picasso.with(getContext())
                .load(url)
                .into(imageView);

        WindowManager wm = activity.getWindowManager();
        final Display disp = wm.getDefaultDisplay();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        params.width = disp.getWidth();
        imageView.setLayoutParams(params);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof HomeActivity) {
            this.activity = (HomeActivity) context;
        }
    }
}
