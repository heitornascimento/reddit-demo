package app.youse.com.reddit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class PostDetail implements Parcelable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("preview")
    private Preview mPreview;

    @SerializedName("url")
    private String mComments;

    @SerializedName("num_comments")
    private int mCommentsNum;

    @SerializedName("ups")
    private int ups;

    public PostDetail() {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public void setPreview(Preview preview) {
        this.mPreview = preview;
    }

    public Preview getPreview() {
        return mPreview;
    }

    public void setComments(String comments) {
        this.mComments = comments;
    }

    public String getComments() {
        return mComments;
    }

    public void setCommentNum(int number) {
        this.mCommentsNum = number;
    }

    public int getCommentsNum() {
        return mCommentsNum;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getUps() {
        return ups;
    }

    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            PostDetail postDetail = new PostDetail();
            postDetail.mTitle = source.readString();
            postDetail.mThumbnail = source.readString();
            postDetail.mPreview = source.readParcelable(Preview.class.getClassLoader());
            postDetail.mComments = source.readString();
            postDetail.mCommentsNum = source.readInt();
            postDetail.ups = source.readInt();


            return postDetail;
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
        dest.writeString(mTitle);
        dest.writeString(mThumbnail);
        dest.writeParcelable(mPreview, flags);
        dest.writeString(mComments);
        dest.writeInt(mCommentsNum);
        dest.writeInt(ups);
    }
}
