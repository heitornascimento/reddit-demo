package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by heitornascimento on 11/5/16.
 */

public class Preview implements Parcelable {

    @SerializedName("images")
    private List<RedditImage> mImageList;

    public Preview() {

    }

    public List<RedditImage> getmImageList() {
        return mImageList;
    }

    public void setmImageList(List<RedditImage> mImageList) {
        this.mImageList = mImageList;
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            Preview preview = new Preview();
            source.readList(preview.mImageList, RedditImage.class.getClassLoader());
            return preview;
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
        dest.writeList(mImageList);
    }
}
