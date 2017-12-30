package com.ezz.bakingapp.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by samar ezz on 12/15/2017.
 */

public class ServicesCaller {

    private static ServicesCaller servicesCaller;
    private RequestQueue requestQueue;
    public static final String RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public enum Tag {
        RECIPE
    }

    private ServicesCaller(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized ServicesCaller getInstance(Context context) {
        if (servicesCaller == null) {
            servicesCaller = new ServicesCaller(context);
        }
        return servicesCaller;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    public void getRecipes(String url, Response.Listener<String> successListener, Response.ErrorListener errorListener, Tag tag) {
        StringRequest recipesStringRequest = new StringRequest(Request.Method.GET, url, successListener, errorListener);
        recipesStringRequest.setTag(tag);
        addToRequestQueue(recipesStringRequest);
    }


    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
