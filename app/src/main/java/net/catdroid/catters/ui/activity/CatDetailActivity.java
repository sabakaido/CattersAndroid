package net.catdroid.catters.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.catdroid.catters.CatApplication;
import net.catdroid.catters.R;
import net.catdroid.catters.models.Cat;
import net.catdroid.catters.utils.CatUtils;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatDetailActivity extends CatBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // リスト画面から送られてきたねこ情報を取得
        Cat cat = (Cat) getIntent().getSerializableExtra(CatUtils.EXTRA_KEY_CAT);
        String catId = cat.getCatId();
        String catImageUrl = cat.getCatImageUrl();

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.getLayoutParams().height = CatUtils.getDisplayWidth(getApplicationContext());

        // ねこ画像設定
        ImageView imageView = (ImageView) findViewById(R.id.ivBigImage);
        imageView.setTransitionName(catId);

        Bitmap bmp = ((CatApplication) getApplication()).getObj();
        if (bmp != null) {
            imageView.setImageBitmap(bmp);
        }
        if (cat != null && !TextUtils.isEmpty(catImageUrl)) {
            Picasso.with(this).load(catImageUrl).into(imageView);
        }

        setTitle(catId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((CatApplication) getApplication()).clearObj();
    }
}
