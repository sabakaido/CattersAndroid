package net.catdroid.catters.network;

import net.catdroid.catters.models.Cat;

import java.util.List;

/**
 * Created by anikaido on 2016/06/11.
 */
public abstract class CatRequestStatusListener {
    public abstract void OnRequestSuccess(List<Cat> data);

    public abstract void OnRequestTimeout(List<Cat> data);

    public abstract void OnRequestError(List<Cat> data);
}
