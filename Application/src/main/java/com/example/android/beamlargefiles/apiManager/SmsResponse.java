package com.example.android.beamlargefiles.apiManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmsResponse {

    @SerializedName("RESPONSE")
    @Expose
    private Response response;
    @SerializedName("STATUS")
    @Expose
    private String status;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
