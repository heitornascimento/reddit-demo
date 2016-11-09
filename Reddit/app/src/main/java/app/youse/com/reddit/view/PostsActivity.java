package app.youse.com.reddit.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import app.youse.com.reddit.R;
import app.youse.com.reddit.adapter.InfiniteScrollListener;
import app.youse.com.reddit.adapter.PostAdapter;
import app.youse.com.reddit.adapter.RecyclerClickListener;
import app.youse.com.reddit.fragment.HeadlessFragment;
import app.youse.com.reddit.model.Post;
import app.youse.com.reddit.presenter.PostPresenter;
import app.youse.com.reddit.utils.ConnectionUtil;

public class PostsActivity extends BaseActivity implements PostPresenter.ViewPresenter,
        RecyclerClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private PostPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;
    private PostAdapter mAdapter;
    private ArrayList<Post> mData;
    private SwipeRefreshLayout mSwipeRefresh;


    private ProgressBar mLoading;
    private ProgressBar mProgressing;

    private static final String DATA_TAG_POST = "data";
    private HeadlessFragment mHeadlessFragment;
    private TextView mErroData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_layout);
        mData = new ArrayList<>();
        loadView();
        mPresenter = new PostPresenter(this, this);
        checkDataInHeadlessFragment();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Update the reference on the data
        mHeadlessFragment.setData(mData);
    }

    /**
     * Check if the data has already been downloaded in the retain fragment.
     * If so, do not call the API.
     */
    private void checkDataInHeadlessFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        mHeadlessFragment = (HeadlessFragment) fragmentManager.findFragmentByTag(DATA_TAG_POST);

        if (mHeadlessFragment == null) {
            mHeadlessFragment = new HeadlessFragment();
            fragmentManager.beginTransaction().add(mHeadlessFragment, DATA_TAG_POST).commit();
            if (!loadDataPost()) {
                mErroData.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
            }
        } else {
            mData = mHeadlessFragment.getData();
            setViewsVisibilityAndNotifyAdapter();
        }
    }

    @Override
    public void onLoadDataSuccess(List<Post> list) {
        mData.addAll(list);
        setViewsVisibilityAndNotifyAdapter();
        //Retain Data
        mHeadlessFragment.setData(mData);
    }

    /**
     * Set visibility to true when the data is valid and
     * notify adapter.
     */
    private void setViewsVisibilityAndNotifyAdapter() {
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        mProgressing.setVisibility(View.GONE);
        mErroData.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String message) {
        mSwipeRefresh.setRefreshing(false);
        mLoading.setVisibility(View.GONE);
    }


    /**
     * Bind the views
     */
    private void loadView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.posts);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this, this));
        mRecyclerView.addOnScrollListener(new InfiniteScrollListener(llManager) {
            @Override
            public void onLoadMore() {
                if (loadDataPost()) {
                    mProgressing.setVisibility(View.VISIBLE);
                }
            }
        });
        mAdapter = new PostAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefresh.setOnRefreshListener(this);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mProgressing = (ProgressBar) findViewById(R.id.progress);
        mErroData = (TextView) findViewById(R.id.error_load);
    }

    /**
     * Retrieve the recent Android posts
     */
    private boolean loadDataPost() {
        if (checkInternetConnection()) {
            mPresenter.loadPostData();
            return true;
        }

        return false;
    }


    @Override
    public void onItemClick(View v, int position) {
        if (checkInternetConnection()) {
            Post post = mData.get(position);
            String url = post.getPostDetails().getComments();
            CustomTabsIntent intent = ConnectionUtil.buildTabIntent();
            Uri uri = Uri.parse(url);
            intent.launchUrl(this, uri);
        }
    }


    @Override
    public void onRefresh() {
        if (!loadDataPost()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
