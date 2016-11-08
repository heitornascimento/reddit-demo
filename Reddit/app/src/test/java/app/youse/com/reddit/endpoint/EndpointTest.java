package app.youse.com.reddit.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import app.youse.com.reddit.model.ListingResponse;
import app.youse.com.reddit.presenter.PostPresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by heitornascimento on 11/7/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class EndpointTest {


    @Mock
    private RedditService service;

    @Mock
    private Call<ListingResponse> mockCall;

    @Mock
    private PostPresenter.ViewPresenter viewPresenter;

    @Captor
    private ArgumentCaptor<Callback<ListingResponse>> argumentCapture;

    @Mock
    private RedditEndpoint.EndpointResult endpointResult;

    @Mock
    private ResponseBody responseBody;

    private ListingResponse listingResponse;

    private RedditEndpoint redditEndpoint;

    private int paging = 25;
    private String after = "after";


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        listingResponse = new ListingResponse();
        redditEndpoint = new RedditEndpoint(endpointResult, service);
    }

    @Test
    public void shouldLoadDataPost() {
        when(service.getPosts(paging, after)).thenReturn(mockCall);
        Response<ListingResponse> response = Response.success(listingResponse);

        redditEndpoint.loadPosts(paging, after);
        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onResponse(null, response);
        verify(endpointResult).onSuccess(listingResponse.getChildren());
    }

    @Test
    public void shouldDoNothingLoadPost() {
        when(service.getPosts(paging, after)).thenReturn(mockCall);
        Response<ListingResponse> response = Response.error(500, responseBody);

        redditEndpoint.loadPosts(paging, after);
        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onResponse(null, response);
        verifyZeroInteractions(endpointResult);
    }

    @Test
    public void shouldFailureLoadPost() {
        when(service.getPosts(paging, after)).thenReturn(mockCall);
        Throwable throwable = new Throwable(new RuntimeException());

        redditEndpoint.loadPosts(paging, after);

        verify(mockCall).enqueue(argumentCapture.capture());
        argumentCapture.getValue().onFailure(null, throwable);
        verify(endpointResult).onFailed(throwable.getMessage());
    }
}
