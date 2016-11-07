package app.youse.com.reddit.RecyclerView;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * Created by heitornascimento on 11/6/16.
 */

public class RecyclerViewCountAssertion implements ViewAssertion {

    private final int countExpected;

    public RecyclerViewCountAssertion(int count){
        countExpected = count;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {

        RecyclerView recyclerView = (RecyclerView)view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Assert.assertThat(adapter.getItemCount(), Matchers.is(countExpected));

    }
}
