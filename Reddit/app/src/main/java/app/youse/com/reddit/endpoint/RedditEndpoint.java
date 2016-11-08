package app.youse.com.reddit.endpoint;

import app.youse.com.reddit.model.DataResponse;
import app.youse.com.reddit.model.ListingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RedditEndpoint {

    private final RedditService service;
    private EndpointResult mPresenter;

    public RedditEndpoint(EndpointResult presenter, RedditService service) {
        this.service = service;
        this.mPresenter = presenter;
    }

    public void loadPosts(int paging, String after) {
        service.getPosts(paging, after).enqueue(new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                if(response.isSuccessful()){
                    mPresenter.onSuccess(response.body().getChildren());
                }
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                mPresenter.onFailed(t.getMessage());
            }
        });
    }


    public interface EndpointResult {
        void onSuccess(DataResponse dataResponse);

        void onFailed(String message);
    }

}
