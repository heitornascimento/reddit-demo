package app.youse.com.reddit.endpoint;

import app.youse.com.reddit.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class Injector {

    public static Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static RedditService provideRedditService() {
        return provideRetrofit(Constants.baseUrl).create(RedditService.class);
    }
}
