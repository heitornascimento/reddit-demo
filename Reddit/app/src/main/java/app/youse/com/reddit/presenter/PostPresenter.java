package app.youse.com.reddit.presenter;

import android.os.Bundle;

import java.util.List;

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


    public PostPresenter(ViewPresenter view) {
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
            mView.onFailure();
        }
    }

    @Override
    public void onFailed(String message) {
        mView.onFailure();
    }


    public interface ViewPresenter {
        void onLoadDataSuccess(List<Post> list);

        void onFailure();
    }
}
