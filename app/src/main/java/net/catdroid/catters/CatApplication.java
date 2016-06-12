package net.catdroid.catters;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by anikaido on 2016/06/12.
 */
public class CatApplication extends Application {
    private Bitmap obj;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public Bitmap getObj() {
        return obj;
    }

    public void setObj(Bitmap obj) {
        this.obj = obj;
    }

    public void clearObj() { obj = null; }
}
