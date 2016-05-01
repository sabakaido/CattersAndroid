package net.catdroid.catters.managers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.catdroid.catters.models.home.CatImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anikaido on 2016/03/06.
 */
public class CattersAsyncTaskLoader extends AsyncTaskLoader<List<CatImage>> {
    private String requestUrl;

    public CattersAsyncTaskLoader(Context context, String requestUrl) {
        super(context);

        this.requestUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    public List<CatImage> loadInBackground() {
        String result = null;

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        List<CatImage> catImageList = new ArrayList<CatImage>();

        try {
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            response.body().close();

            JSONArray rootArray = new JSONArray(result);

            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject jsonObject = rootArray.getJSONObject(i);

                CatImage catImage = new CatImage();
                catImage.setId(jsonObject.getString("id"));
                catImage.setImageUrl(jsonObject.getString("url"));

                catImageList.add(catImage);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return catImageList;
    }
}