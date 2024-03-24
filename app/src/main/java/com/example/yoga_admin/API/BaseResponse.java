package com.example.yoga_admin.API;

import okhttp3.Response;

public class BaseResponse {
    protected Response raw;

    public BaseResponse(Response raw)
    {
        this.raw = raw;
    }

    public String bodyText()
    {
        return raw.body().toString();
    }

    protected Response raw()
    {
        return raw;
    }
}
