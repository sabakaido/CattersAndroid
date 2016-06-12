package net.catdroid.catters.ui.views.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.catdroid.catters.CatApplication;
import net.catdroid.catters.R;
import net.catdroid.catters.models.Cat;
import net.catdroid.catters.ui.activity.CatDetailActivity;
import net.catdroid.catters.utils.CatUtils;

import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by anikaido on 2016/06/11.
 *
 * GridViewのadapter
 */
public class CatGridAdapter extends RecyclerView.Adapter<CatGridAdapter.ViewHolder> {

    private final String mHeart;
    private final String mHeartFull;
    private final int mImageSize;
    private final LayoutInflater mLayoutInflater;
    private List<Cat> mCatList;
    private Activity mActivity;
    private Animation mFavoriteAnim;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mRelativeLayout;
        public ImageView imageViewCat;
        public TextView textViewCatId;
        public CircularProgressBar circularProgressBar;
        public TextView textViewFavorite;

        public ViewHolder(View itemView, int size) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutCatContainer);
            imageViewCat = (ImageView) itemView.findViewById(R.id.imageViewCat);
            textViewCatId = (TextView) itemView.findViewById(R.id.textViewCat);
            circularProgressBar = (CircularProgressBar) itemView.findViewById(R.id.progressBarCat);
            textViewFavorite = (TextView) itemView.findViewById(R.id.textViewCatFavorite);

            final int height = size / itemView.getContext().getResources().getInteger(R.integer.cat_grid_columns);
            mRelativeLayout.getLayoutParams().height = height;
            mRelativeLayout.getLayoutParams().width = height;
            imageViewCat.getLayoutParams().height = height;
            imageViewCat.getLayoutParams().width = height;
        }
    }

    public CatGridAdapter(Activity activity, List<Cat> catList, int width) {
        mActivity = activity;
        mCatList = catList;
        mImageSize = width;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFavoriteAnim = AnimationUtils.loadAnimation(mActivity, R.anim.favorite_anim);
        mHeart = "♡";
        mHeartFull = "♥";
    }

    public void setCatList(List<Cat> catList) { mCatList = catList; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.content_cat, parent, false);
        return new ViewHolder(view, mImageSize);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Cat cat = mCatList.get(position);
        if (cat != null) {

            final String catId = cat.getCatId();
            final String catImageUrl = cat.getCatImageUrl();

            setCatIdView(holder, catId);

            setCatImageView(holder, catImageUrl);

            setCatFavoriteView(holder, cat);

            holder.mRelativeLayout.setOnClickListener(getCatClickListener(holder, cat));
        }
    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    private void setCatIdView(@NonNull final ViewHolder holder, @NonNull final String catId) {
        holder.textViewCatId.setText(catId);
    }

    private void setCatImageView(@NonNull final ViewHolder holder, @NonNull final String catImageUrl) {
        holder.circularProgressBar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(catImageUrl)) {
            holder.circularProgressBar.setVisibility(View.GONE);
            return;
        }

        Picasso.with(mActivity.getApplicationContext()).load(catImageUrl).into(holder.imageViewCat, new Callback() {
            @Override
            public void onSuccess() {
                holder.circularProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.circularProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setCatFavoriteView(@NonNull final ViewHolder holder, @NonNull final Cat cat) {
        final boolean isFavorite = cat.isFavorite();
        final ColorStateList oldColors = holder.textViewCatId.getTextColors();
        holder.textViewFavorite.setText(isFavorite ? mHeartFull : mHeart);
        if (isFavorite) {
            holder.textViewFavorite.setTextColor(Color.RED);
        } else {
            holder.textViewFavorite.setTextColor(oldColors);
        }

        holder.textViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat.setFavorite(!cat.isFavorite());
                holder.textViewFavorite.setText((cat.isFavorite() ? mHeartFull : mHeart));

                if (cat.isFavorite()) {
                    holder.textViewFavorite.setTextColor(Color.RED);

                    holder.textViewFavorite.startAnimation(mFavoriteAnim);

                    CatUtils.addFavoriteCatList(mActivity.getApplicationContext(), cat);
                } else {
                    holder.textViewFavorite.setTextColor(oldColors);
                    CatUtils.deleteFavoriteList(mActivity.getApplicationContext(), cat);
                }
            }
        });
    }

    private View.OnClickListener getCatClickListener(@NonNull final ViewHolder holder, @NonNull final Cat cat) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = holder.imageViewCat.getDrawable();
                if (drawable != null) {
                    Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
                    ((CatApplication) mActivity.getApplication()).setObj(bmp);
                }

                Intent intent = new Intent(mActivity, CatDetailActivity.class);
                intent.putExtra(CatUtils.EXTRA_KEY_CAT, cat);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        mActivity, holder.imageViewCat, cat.getCatId()
                );
                mActivity.startActivity(intent, activityOptions.toBundle());
            }
        };
        return listener;
    }
}
