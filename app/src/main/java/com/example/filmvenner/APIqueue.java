package com.example.filmvenner;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class APIqueue {

    private static APIqueue instance;
    private  RequestQueue requestQueue;
   // private ImageLoader imageLoader;
    private static Context ctx;

    private APIqueue(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public  <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

    public static synchronized APIqueue getInstance(Context context){
        if(instance == null){
            instance = new APIqueue(context);
        }
        return instance;
    }

}
