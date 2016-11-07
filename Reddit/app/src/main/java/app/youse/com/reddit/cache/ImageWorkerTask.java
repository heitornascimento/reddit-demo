package app.youse.com.reddit.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by heitornascimento on 11/6/16.
 */

public class ImageWorkerTask implements Runnable {

    private String mUrl;
    private String profile;
    private Handler handler;

    public ImageWorkerTask(String profile, String url, Handler handler) {
        this.mUrl = url;
        this.profile = profile;
        this.handler = handler;
    }

    /**
     * Download the image and store in the cache.
     */
    @Override
    public void run() {
        if (mUrl != null && !mUrl.isEmpty()) {
            URL url;
            try {
                url = new URL(mUrl);
                HttpURLConnection connection;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input;
                input = connection.getInputStream();
                Bitmap imageProfile = BitmapFactory.decodeStream(input);
                if (imageProfile != null) {
                    CacheImage.getInstance().addImageInCache(profile, imageProfile);
                    //Get back to the main thread to update image view
                    Message message = Message.obtain();
                    message.obj = imageProfile;
                    handler.sendMessage(message);
                    imageProfile = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
