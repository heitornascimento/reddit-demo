package app.youse.com.reddit.adapter;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import app.youse.com.reddit.R;
import app.youse.com.reddit.cache.BitmapManager;
import app.youse.com.reddit.model.ImageResolution;
import app.youse.com.reddit.model.Post;
import app.youse.com.reddit.model.Preview;
import app.youse.com.reddit.utils.ServiceDisplayMetrics;
import app.youse.com.reddit.utils.UrlUtil;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mData;
    private ViewHolder mViewHolder;
    private WeakReference<AppCompatActivity> mWeakReference;

    //Animation
    private int lastPosition = -1;

    public PostAdapter(List<Post> data, AppCompatActivity ctx) {
        mData = data;
        mWeakReference = new WeakReference<>(ctx);
    }

    public void setData(List<Post> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_layout, parent, false);

        mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //runEnterAnimation(mViewHolder.itemView, position);
        final Post post = mData.get(position);
        final String title = post.getPostDetails().getTitle();
        final Preview preview = post.getPostDetails().getPreview();

        if (preview != null) {
            final List<ImageResolution> imageResolutionList = post.getPostDetails()
                    .getPreview().getmImageList().get(0)
                    .getImageResolutionList();
            if (imageResolutionList != null && imageResolutionList.size() > 0) {

                BitmapManager manager = BitmapManager.getInstance(mWeakReference.get());
                String formatUrl = UrlUtil.formatString(ServiceDisplayMetrics.
                        getResolution(mWeakReference, imageResolutionList).getUrl());
                Bitmap bitmap = manager.loadImageFromCache(title, formatUrl, mViewHolder.icon);
                mViewHolder.icon.setImageBitmap(bitmap);
            }
        } else {
            mViewHolder.icon.setVisibility(View.GONE);
        }
        mViewHolder.title.setText(title);
        mViewHolder.comments.setText(String.valueOf(post.getPostDetails().getCommentsNum()));
        mViewHolder.ups.setText(String.valueOf(post.getPostDetails().getUps()));
        setFadeAnimation(mViewHolder.itemView, position);
    }

    private void setFadeAnimation(View view, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(300);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private TextView comments;
        private TextView ups;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            comments = (TextView) itemView.findViewById(R.id.comments_number);
            ups = (TextView) itemView.findViewById(R.id.ups_number);
        }
    }
}
