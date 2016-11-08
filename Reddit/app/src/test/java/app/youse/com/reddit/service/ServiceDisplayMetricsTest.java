package app.youse.com.reddit.service;

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.ref.WeakReference;
import java.util.List;

import app.youse.com.reddit.model.ImageResolution;
import app.youse.com.reddit.utils.ServiceDisplayMetrics;

import static org.powermock.api.mockito.PowerMockito.mock;

//import static org.mockito.Mockito.mock;

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
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowsExceptionImageResolutionNull() throws Exception {
        ServiceDisplayMetrics.getResolution(weakReference, listResolutions);
    }

}
