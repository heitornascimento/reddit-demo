package app.youse.com.reddit.endpoint;

import app.youse.com.reddit.model.ListingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by heitornascimento on 11/2/16.
 */

public interface RedditService {

    @GET("r/Android/new/.json")
    Call<ListingResponse> getPosts(@Query("count") int page, @Query("after") String after);

}
