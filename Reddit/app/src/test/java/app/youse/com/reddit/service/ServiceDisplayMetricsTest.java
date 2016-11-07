package app.youse.com.reddit.service;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import app.youse.com.reddit.model.ImageResolution;
import app.youse.com.reddit.utils.ServiceDisplayMetrics;

//import static org.mockito.Mockito.mock;

import static  org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by heitornascimento on 11/5/16.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServiceDisplayMetrics.class)
public class ServiceDisplayMetricsTest {

    private WeakReference<AppCompatActivity> weakReference;
    private List<ImageResolution> listResolutions;

    @Test
    public void setup() {
        weakReference = mock(WeakReference.class);
        //listResolutions = mock(List.class);
        AppCompatActivity activity = mock(AppCompatActivity.class);
    }

    //@Test(expected = RuntimeException.class)
    public void shouldThrowsExceptionImageResolutionNull() throws Exception {
        ServiceDisplayMetrics.getResolution(weakReference, listResolutions);
    }


    @Test
    public void shouldSelectTheBestResolution() {
        ImageResolution ir1 = new ImageResolution();
        ir1.setHeight(675);
        ir1.setWidth(1080);

        ImageResolution ir2 = new ImageResolution();
        ir1.setHeight(500);
        ir1.setWidth(900);

        listResolutions = new ArrayList<>();
        listResolutions.add(ir1);
        listResolutions.add(ir2);

        mockStatic(ServiceDisplayMetrics.class);

        Point point = mock(Point.class);
        point.set(1080, 900);

        //when(ServiceDisplayMetrics.getPoint(mock(AppCompatActivity.class))).thenReturn(point);
        //ImageResolution imageResolution = ServiceDisplayMetrics.getResolution(weakReference, listResolutions);




        /*
        ServiceDisplayMetrics spy = PowerMockito.spy(new ServiceDisplayMetrics());

        Point point = mock(Point.class);
        point.set(1080,900);
        try {
            PowerMockito.when(spy,
                    MemberMatcher.method(ServiceDisplayMetrics.class, "getPoint", AppCompatActivity.class))
                    .withArguments(any(AppCompatActivity.class)).thenReturn(point);

        } catch (Exception e) {
          */


    }
}
