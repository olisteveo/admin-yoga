package com.example.yoga_admin.API.Responses;

import com.example.yoga_admin.API.BaseResponse;

import okhttp3.Response;

public class PingResponse extends BaseResponse {

    public PingResponse(Response raw)
    {
        super(raw);
    }

    public boolean statusOK()
    {
        return raw().isSuccessful();
    }
}
