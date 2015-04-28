package cn.itjh.luchat.usertoken.util.io.rong.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtil {

    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();
    private static JsonElement jsonEl = null;
    private static JsonObject jsonObj = null;

    public static String toJson(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static Object fromJson(String str, Type type) {
        return gson.fromJson(str, type);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String jsonGetStrByKey(String json, String key) {
        jsonEl = parser.parse(json);
        jsonObj = jsonEl.getAsJsonObject();
        return jsonObj.get(key).getAsString();
    }

    public static String jsonGetElementByKey(String json, String key) {
        jsonEl = parser.parse(json);
        jsonObj = jsonEl.getAsJsonObject();
        return jsonObj.get(key).toString();
    }
}
