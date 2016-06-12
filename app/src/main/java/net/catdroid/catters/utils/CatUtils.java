package net.catdroid.catters.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.catdroid.catters.models.Cat;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatUtils {

    private static SharedPreferences sSharedPreferences;
    private static final String KEY_SP = "key_shared_preference";
    private static final String KEY_SP_FAVORITE_LIST = "key_favorite_list";

    public static final String EXTRA_KEY_CAT = "extra_key_cat";

    public static int getDisplayWidth(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }

    public static boolean isNetwork(@NonNull Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }

            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }

            return info.isConnected();
        } catch (SecurityException e) {
            return true;
        }
    }

    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = context.getSharedPreferences(KEY_SP, Context.MODE_PRIVATE);
        }

        return sSharedPreferences;
    }

    public static ArrayList<Cat> getFavoriteCatList(@NonNull Context context) {
        Gson gson = new Gson();
        String json = getSharedPreferences(context).getString(KEY_SP_FAVORITE_LIST, "");
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<Cat>>() {
        }.getType();
        ArrayList<Cat> list = gson.fromJson(json, type);
        return list;
    }

    public static void addFavoriteCatList(@NonNull Context context, Cat cat) {
        ArrayList<Cat> favoriteList = getFavoriteCatList(context);
        if (favoriteList == null) {
            return;
        }

        favoriteList.add(cat);
        setFavoriteCatList(context, favoriteList);
    }

    public static void deleteFavoriteList(@NonNull Context context, Cat cat) {
        ArrayList<Cat> favoriteList = getFavoriteCatList(context);
        if (favoriteList == null) {
            return;
        }

        final int size = favoriteList.size();
        int index = -1;
        for (int i = 0; i < size; i++) {
            Cat item = favoriteList.get(i);
            String id = item.getCatId();
            if (id.equals(cat.getCatId())) {
                index = i;
                break;
            }
        }

        if (index < 0) {
            return;
        }

        favoriteList.remove(index);
        setFavoriteCatList(context, favoriteList);
    }

    private static void setFavoriteCatList(@NonNull Context context, ArrayList<Cat> favoriteList) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteList);
        editor.putString(KEY_SP_FAVORITE_LIST, json);
        editor.commit();
    }
}
