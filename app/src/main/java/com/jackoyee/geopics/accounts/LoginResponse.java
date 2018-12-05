package com.jackoyee.geopics.accounts;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Date;

public class LoginResponse {

    @SerializedName("data")
    private Data data;
    @SerializedName("response")
    private boolean response;
    @SerializedName("request_timestamp")
    private String  request_timestamp;
    @SerializedName("request_id")
    private String request_id;
    @SerializedName("requester_uid")
    private  String requester_uid;
    @SerializedName("status")
    private  int status;

    public LoginResponse(Data data, boolean response, String request_id, String requester_uid, String request_timestamp,int status) {
        this.data = data;
        this.response = response;
        this.request_id = request_id;
        this.requester_uid = requester_uid;
        this.request_timestamp=request_timestamp;
        this.status=status;
    }

    public Data getData() {
        return data;
    }
    public boolean isResponse() {
        return response;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getRequester_uid() {
        return requester_uid;
    }

    public String getRequest_timestamp() {
        return request_timestamp;
    }
    public int getStatus() {
        return status;
    }
}
