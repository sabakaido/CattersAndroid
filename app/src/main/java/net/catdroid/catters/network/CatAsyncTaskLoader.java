package net.catdroid.catters.network;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import net.catdroid.catters.R;
import net.catdroid.catters.models.Cat;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatAsyncTaskLoader extends AsyncTaskLoader<Pair<Integer, List<Cat>>> {

    private final String API = "http://catroid.net:3000/get/CatApartment";
    private String mMaxCatId;
    private Context mContext;

    public CatAsyncTaskLoader(Context context, String mMaxCatId) {
        super(context);
        mContext = context;
        mMaxCatId = mMaxCatId;
    }

    @Override
    public Pair<Integer, List<Cat>> loadInBackground() {
        CatJsonParser parser = new CatJsonParser();

        List<Cat> data = new ArrayList<>();
        int httpStatusCode = 0;
        final Resources res = mContext.getResources();
        final int timeOutConnection = res.getInteger(R.integer.cat_request_connect_timeout);
        final int timeOutRead = res.getInteger(R.integer.cat_request_read_timeout);

        try {
            String api = !TextUtils.isEmpty(mMaxCatId) ? API + "?max_id=" + mMaxCatId : API;
            Request request = new Request.Builder()
                    .url(api)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .readTimeout(timeOutRead, TimeUnit.SECONDS)
                    .connectTimeout(timeOutConnection, TimeUnit.SECONDS)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            httpStatusCode = response.code();
            String resStr = response.body().string();
            data = parser.parseJson(resStr);
        } catch (IOException e) {
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError oom) {
            System.gc();
        } catch (JSONException e) {
        }

        return Pair.create(httpStatusCode, data);
    }
}
