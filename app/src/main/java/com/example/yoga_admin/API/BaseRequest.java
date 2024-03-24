package com.example.yoga_admin.API;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseRequest implements Callback {

    protected Activity activity;
    protected OkHttpClient client;

    public BaseRequest(Activity activity)
    {
        this.activity = activity;
    }
    protected void getUrl(String url){
        Request request = new Request.Builder().url(url).build();
        Log.i("HTTP GET Request", url);
        Log.i("HTTP GET Request", request.toString());
        client.newCall(request).enqueue(this);
    }

    protected void postUrl(String url){

    }

    protected BaseResponse wrapRawResponse(Response response)
    {
        return new BaseResponse(response);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.getStackTraceString(e);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        BaseResponse wrappedResponse = this.wrapRawResponse(response);
    }
}
