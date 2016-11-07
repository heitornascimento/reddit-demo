package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class Post implements Parcelable {

    @SerializedName("kind")
    private String mKind;

    @SerializedName("data")
    private PostDetail mPostDetails;

    public Post() {

    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String mKind) {
        this.mKind = mKind;
    }

    public PostDetail getPostDetails() {
        return mPostDetails;
    }

    public void setPostDetails(PostDetail mPostDetails) {
        this.mPostDetails = mPostDetails;
    }

    public static final Parcelable.Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            Post post = new Post();
            post.mKind = source.readString();
            post.mPostDetails = source.readParcelable(PostDetail.class.getClassLoader());
            return post;
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKind);
        dest.writeParcelable(mPostDetails, flags);
    }
}
