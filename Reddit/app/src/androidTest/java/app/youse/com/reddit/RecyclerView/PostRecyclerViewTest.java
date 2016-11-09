package app.youse.com.reddit.RecyclerView;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.youse.com.reddit.IntentServiceIdlingResource;
import app.youse.com.reddit.R;
import app.youse.com.reddit.adapter.PostAdapter;
import app.youse.com.reddit.view.PostsActivity;

import static android.support.test.espresso.Espresso.*;

/**
 * Created by heitornascimento on 11/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class PostRecyclerViewTest {

    @Rule
    public final ActivityTestRule<PostsActivity> postsActivityActivityTestRule =
            new ActivityTestRule<>(PostsActivity.class);
    private IdlingResource idlingResource;

    private final static int INIT_COUNT = 25;
    private final static int PAGING_COUNT = 50;


    @Before
    public void registerIntentServiceIdlingResource() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        idlingResource = new IntentServiceIdlingResource(ctx);
        //registerIdlingResources(idlingResource);
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
       // unregisterIdlingResources(idlingResource);
    }


    @Test
    public void shouldContainRedditPost() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.posts)).check(new RecyclerViewCountAssertion(INIT_COUNT));
    }

    @Test
    public void shouldStartInfiniteScroll() {
        for (int i = 0; i < 5; i++) {
            onView(ViewMatchers.withId(R.id.posts)).perform(ViewActions.swipeUp());
        }
        onView(ViewMatchers.withId(R.id.posts)).check(new RecyclerViewCountAssertion(PAGING_COUNT));
    }


}
