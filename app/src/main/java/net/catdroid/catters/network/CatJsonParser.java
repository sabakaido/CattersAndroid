package net.catdroid.catters.network;

import net.catdroid.catters.models.Cat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by anikaido on 2016/06/11.
 */
public class CatJsonParser {

    private static final String quo = "\"";

    public ArrayList<Cat> parseJson(String str) throws IOException, JSONException {
        ArrayList<Cat> list = new ArrayList<>();

        JSONObject json = new JSONObject("{" + quo + "cat" + quo + ":" + str + "}");

        JSONArray array = json.getJSONArray("cat");
        Cat currentItem;
        final int len = array.length();
        for (int i = 0; i < len; i++) {
            currentItem = new Cat();
            JSONObject entry = array.getJSONObject(i);
            if (!entry.isNull("id"))
                currentItem.setCatId(entry.getString("id"));
            if (!entry.isNull("url"))
                currentItem.setCatImageUrl(entry.getString("url"));
            list.add(currentItem);
        }

        return list;
    }
}
