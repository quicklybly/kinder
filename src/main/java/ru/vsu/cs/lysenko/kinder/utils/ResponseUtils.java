package ru.vsu.cs.lysenko.kinder.utils;

import org.json.JSONObject;

public class ResponseUtils {
    private ResponseUtils() {
    }

    public static JSONObject prepareResponse() {
        JSONObject response = new JSONObject();
        response.put("data", new JSONObject());
        response.put("status", "ok");
        return response;
    }
}
