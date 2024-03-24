package com.example.yoga_admin.API.Requests;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.yoga_admin.API.BaseRequest;
import com.example.yoga_admin.API.BaseResponse;
import com.example.yoga_admin.API.Responses.PingResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class PingRequest extends BaseRequest {

    public static String URL = "https://api.taxicode.com/ping/";

    public PingRequest(Activity activity)
    {
        super(activity);
    }

    public void makeRequest()
    {
        pingCheckGet();
    }

    public void pingCheckPost(){
        postUrl(URL);
    }

    public void pingCheckGet(){
        getUrl(URL);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.getStackTraceString(e);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        BaseResponse wrappedResponse = this.wrapRawResponse(response);
        Log.i("HTTP Data", wrappedResponse.bodyText());
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.i("HTTP Data", wrappedResponse.bodyText());
                    }
                }
        );
    }
    @Override
    protected BaseResponse wrapRawResponse(Response response)
    {
        return (BaseResponse) new PingResponse(response);
    }


}
