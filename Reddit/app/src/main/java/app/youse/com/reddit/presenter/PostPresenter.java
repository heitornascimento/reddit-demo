package app.youse.com.reddit.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;
import java.util.List;

import app.youse.com.reddit.endpoint.EndpointResult;
import app.youse.com.reddit.endpoint.RedditEndpoint;
import app.youse.com.reddit.model.DataResponse;
import app.youse.com.reddit.model.Post;
import app.youse.com.reddit.utils.Constants;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class PostPresenter implements EndpointResult.Receiver {

    private ViewPresenter mView;
    private WeakReference<Context> mWeakReference;

    //Pagination params
    private int count = 0;
    private String after = null;
    private static final int PAGING_COUNT = 25;

    private Bundle bundle;


    public PostPresenter(Context ctx, ViewPresenter view) {
        this.mView = view;
        mWeakReference = new WeakReference<>(ctx);
    }

    public void loadPostData() {
        EndpointResult receiver = new EndpointResult(new Handler());
        receiver.setReceiver(this);
        RedditEndpoint.startActionListing(mWeakReference.get(), count, after, receiver);
    }

    @Override
    public void onReceiveResult(int codeResult, Bundle data) {
        if (codeResult == Constants.RESULT_CODE_OK) {
            bundle = data;
            DataResponse dataResponse = data.getParcelable(Constants.DATA_POST);
            if (dataResponse != null) {
                count += PAGING_COUNT;
                after = dataResponse.getAfter();
                mView.onLoadDataSuccess(dataResponse.getChildren());
            } else {
                mView.onFailure();
            }
        } else {
            mView.onFailure();
        }

    }

    public Bundle getData() {
        return bundle;
    }


    public interface ViewPresenter {
        void onLoadDataSuccess(List<Post> list);

        void onFailure();
    }
}
