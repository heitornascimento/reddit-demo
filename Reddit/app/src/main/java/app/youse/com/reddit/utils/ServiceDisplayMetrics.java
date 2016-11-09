package app.youse.com.reddit.utils;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.List;

import app.youse.com.reddit.model.ImageResolution;

/**
 * Created by heitornascimento on 11/5/16.
 */

public class ServiceDisplayMetrics {

    private static final float MAX_WIDTH = 1.0f;

    /**
     * Calculate the best resolution according to the screen's width only.
     *
     * @param appActivity
     * @param resolutions
     * @return
     */
    public static ImageResolution getResolution(WeakReference<AppCompatActivity> appActivity,
                                                List<ImageResolution> resolutions) {


        if (resolutions == null || resolutions.size() == 0) {
            throw new RuntimeException("Resolution list cannot be null");
        }

        Point size = getPoint(appActivity.get());
        final float screenWidth = size.x;
        ImageResolution bestResolution = null;

        //Provide the how close the screen size is to provided resolutions for the API
        float bestPercent = 0f;
        float percentImage;

        for (ImageResolution ir : resolutions) {
            percentImage = ir.getWidth() / screenWidth;
            if (percentImage > bestPercent && percentImage <= MAX_WIDTH) {
                bestPercent = percentImage;
                bestResolution = ir;
            }
        }

        if (bestResolution != null) {
            return bestResolution;
        }

        throw new RuntimeException("Could not find the best resolution");
    }

    public static Point getPoint(AppCompatActivity appCompatActivity) {
        Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
