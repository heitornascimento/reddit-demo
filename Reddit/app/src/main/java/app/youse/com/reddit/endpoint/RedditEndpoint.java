package app.youse.com.reddit.endpoint;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import app.youse.com.reddit.model.DataResponse;
import app.youse.com.reddit.model.ListingResponse;
import app.youse.com.reddit.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RedditEndpoint extends IntentService {

    private static final String ACTION_ANDROID_NEW = "app.youse.com.reddit.endpoint.action.ANDROID_NEW";
    private static final String PAGINATION = "app.youse.com.reddit.endpoint.extra.PAGINATION";
    private static final String AFTER = "app.youse.com.reddit.endpoint.extra.AFTER";
    private static final String RECEIVER = "app.youse.com.reddit.endpoint.extra.RECEIVER";

    private final RedditService service = Injector.provideRedditService();
    private ResultReceiver mReceiver;
    private Bundle mResponseBundle;

    public RedditEndpoint() {
        super("RedditEndpoint");
    }

    /**
     * Invoke the listing api.
     *
     * @param context
     * @param pagination
     * @param after
     */
    public static void startActionListing(Context context, int pagination,
                                          String after, ResultReceiver receiver) {
        Intent intent = new Intent(context, RedditEndpoint.class);
        intent.putExtra(RECEIVER, receiver);
        intent.setAction(ACTION_ANDROID_NEW);
        intent.putExtra(PAGINATION, pagination);
        intent.putExtra(AFTER, after);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ANDROID_NEW.equals(action)) {
                final int paging = intent.getIntExtra(PAGINATION, 0);
                final String after = intent.getStringExtra(AFTER);
                mReceiver = intent.getParcelableExtra(RECEIVER);
                loadPosts(paging, after);
            }
        }
    }

    private void loadPosts(int paging, String after) {
        service.getPosts(paging, after).enqueue(new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                sendSuccess(response.body().getChildren());
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                sendError(t.getMessage());
            }
        });
    }

    /**
     * Send the data back to the presenter
     *
     * @param dataResponse
     */
    private void sendSuccess(DataResponse dataResponse) {
        mResponseBundle = new Bundle();
        mResponseBundle.putParcelable(Constants.DATA_POST, dataResponse);
        if (mReceiver != null) {
            mReceiver.send(Constants.RESULT_CODE_OK, mResponseBundle);
        }
    }

    /**
     * Send message error back to the presenter
     *
     * @param message
     */
    private void sendError(String message) {
        mResponseBundle = new Bundle();
        mResponseBundle.putString(Constants.DATA_POST, message);
        if (mReceiver != null) {
            mReceiver.send(Constants.RESULT_CODE_ERROR, mResponseBundle);
        }
    }


}
