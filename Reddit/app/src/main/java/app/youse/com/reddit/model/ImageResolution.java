package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 11/5/16.
 */

public class ImageResolution implements Parcelable {

    @SerializedName("url")
    private String mUrl;

    @SerializedName("width")
    private int mWidth;

    @SerializedName("height")
    private int mHeight;

    public ImageResolution() {

    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            ImageResolution imageResolution = new ImageResolution();
            imageResolution.mUrl = source.readString();
            imageResolution.mWidth = source.readInt();
            imageResolution.mHeight = source.readInt();

            return imageResolution;
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
    }
}
