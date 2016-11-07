package app.youse.com.reddit.adapter;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

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
        final Post post = mData.get(position);
        final String title = post.getPostDetails().getTitle();
        final Preview preview = post.getPostDetails().getPreview();

        if (preview != null) {
            final List<ImageResolution> imageResolutionList = post.getPostDetails()
                    .getPreview().getmImageList().get(0)
                    .getImageResolutionList();
            if (imageResolutionList != null && imageResolutionList.size() > 0) {

                /*String formatUrl = UrlUtil.formatString(ServiceDisplayMetrics.
                        getResolution(mWeakReference, imageResolutionList).getUrl());
                Picasso.with(mWeakReference.get()).load(formatUrl)
                        .into(holder.icon);*/
                BitmapManager manager = BitmapManager.getInstance(mWeakReference.get());
                String formatUrl = UrlUtil.formatString(ServiceDisplayMetrics.
                        getResolution(mWeakReference, imageResolutionList).getUrl());
                Bitmap bitmap = manager.loadImageFromCache(title, formatUrl, mViewHolder.icon);
                mViewHolder.icon.setImageBitmap(bitmap);
            }
        } else {
            //holder.icon.setVisibility(View.GONE);
            mViewHolder.icon.setVisibility(View.GONE);
        }
        mViewHolder.title.setText(title);
        mViewHolder.comments.setText(String.valueOf(post.getPostDetails().getCommentsNum()));
        mViewHolder.ups.setText(String.valueOf(post.getPostDetails().getUps()));
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
