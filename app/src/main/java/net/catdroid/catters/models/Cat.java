package net.catdroid.catters.models;

import java.io.Serializable;

/**
 * Created by anikaido on 2016/06/11.
 */
public class Cat implements Serializable {

    private String catId = "";
    private String catImageUrl = "";
    private boolean isFavorite = false;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatImageUrl() {
        return catImageUrl;
    }

    public void setCatImageUrl(String catImageUrl) {
        this.catImageUrl = catImageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
