package app.youse.com.reddit.presenter;

import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.List;

import app.youse.com.reddit.R;
import app.youse.com.reddit.endpoint.Injector;
import app.youse.com.reddit.endpoint.RedditEndpoint;
import app.youse.com.reddit.model.DataResponse;
import app.youse.com.reddit.model.Post;
import app.youse.com.reddit.utils.Constants;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class PostPresenter implements RedditEndpoint.EndpointResult {

    private ViewPresenter mView;

    //Pagination params
    private int count = 0;
    private String after = null;
    private static final int PAGING_COUNT = 25;

    private WeakReference<Context> mWeakReference;


    public PostPresenter(Context ctx,
                         ViewPresenter view) {
        this.mWeakReference = new WeakReference<>(ctx);
        this.mView = view;
    }

    public void loadPostData() {
        RedditEndpoint redditEndpoint = new RedditEndpoint(this, Injector.provideRedditService());
        redditEndpoint.loadPosts(count, after);
    }

    @Override
    public void onSuccess(DataResponse dataResponse) {
        if (dataResponse != null) {
            count += PAGING_COUNT;
            after = dataResponse.getAfter();
            mView.onLoadDataSuccess(dataResponse.getChildren());
        } else {
            mView.onFailure(mWeakReference.get().getString(R.string.could_not_load_data));
        }
    }

    @Override
    public void onFailed(String message) {
        mView.onFailure(message);
    }


    public interface ViewPresenter {
        void onLoadDataSuccess(List<Post> list);

        void onFailure(String msg);
    }
}
