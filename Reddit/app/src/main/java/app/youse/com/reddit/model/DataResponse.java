package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class DataResponse implements Parcelable {

    @SerializedName("children")
    private List<Post> mPost;

    @SerializedName("after")
    private String mAfter;

    @SerializedName("before")
    private String mBefore;


    public List<Post> getChildren() {
        return mPost;
    }

    public void setChildren(List<Post> mChildren) {
        this.mPost = mChildren;
    }

    public String getAfter() {
        return mAfter;
    }

    public void setAfter(String mAfter) {
        this.mAfter = mAfter;
    }

    public String getBefore() {
        return mBefore;
    }

    public void setBefore(String mBefore) {
        this.mBefore = mBefore;
    }

    public static final Parcelable.Creator<DataResponse> CREATOR = new Creator<DataResponse>() {
        @Override
        public DataResponse createFromParcel(Parcel source) {
            DataResponse dataResponse = new DataResponse();
            source.readList(dataResponse.mPost, DataResponse.class.getClassLoader());
            dataResponse.mAfter = source.readString();
            dataResponse.mBefore = source.readString();

            return dataResponse;
        }

        @Override
        public DataResponse[] newArray(int size) {
            return new DataResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mPost);
        dest.writeString(mAfter);
        dest.writeString(mBefore);
    }
}
