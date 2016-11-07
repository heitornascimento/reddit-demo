package app.youse.com.reddit.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by heitornascimento on 11/6/16.
 */

public class CacheImage {

    private static CacheImage instance = new CacheImage();
    private LruCache<String, Bitmap> mMemoryCache;
    private int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;

    private CacheImage() {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static CacheImage getInstance() {
        return instance;
    }

    public void addImageInCache(String profile, Bitmap image) {
        if (mMemoryCache.get(profile) == null) {
            mMemoryCache.put(profile, image);
        }
    }

    public Bitmap getImageFromCache(String profile) {
        Bitmap bitmap = mMemoryCache.get(profile);
        return bitmap;
    }

}
