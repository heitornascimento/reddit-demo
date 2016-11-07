package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heitornascimento on 11/5/16.
 */

public class RedditImage implements Parcelable {

    @SerializedName("resolutions")
    private List<ImageResolution> mImageResolutionList;

    public RedditImage() {

    }

    public List<ImageResolution> getImageResolutionList() {
        return mImageResolutionList;
    }

    public void setImageResolutionList(List<ImageResolution> mImageResolutionList) {
        this.mImageResolutionList = mImageResolutionList;
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            RedditImage redditImage = new RedditImage();
            source.readList(redditImage.mImageResolutionList, ImageResolution.class.getClassLoader());
            return redditImage;
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
        dest.writeList(mImageResolutionList);
    }
}
