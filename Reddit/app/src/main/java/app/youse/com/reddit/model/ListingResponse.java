package app.youse.com.reddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class ListingResponse {

    @SerializedName("kind")
    private String mKind;

    @SerializedName("data")
    private DataResponse mDataResponse;

    public ListingResponse() {

    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String mKind) {
        this.mKind = mKind;
    }

    public DataResponse getChildren() {
        return mDataResponse;
    }

    public void setChildren(DataResponse dataResponse) {
        this.mDataResponse = dataResponse;
    }
}
