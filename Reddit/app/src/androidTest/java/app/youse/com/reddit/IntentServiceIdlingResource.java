package app.youse.com.reddit;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.IdlingResource;

import app.youse.com.reddit.endpoint.RedditEndpoint;

/**
 * Created by heitornascimento on 11/6/16.
 */

public class IntentServiceIdlingResource implements IdlingResource {
    private final Context ctx;
    private IdlingResource.ResourceCallback resourceCallback;

    public IntentServiceIdlingResource(Context context) {
        this.ctx = context;
    }


    @Override
    public String getName() {
        return IntentServiceIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !isIntentServiceRunning();
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    private boolean isIntentServiceRunning() {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (RedditEndpoint.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }

        return false;
    }
}
