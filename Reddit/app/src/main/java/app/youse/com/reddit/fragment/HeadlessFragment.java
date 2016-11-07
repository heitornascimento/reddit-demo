package app.youse.com.reddit.fragment;

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import app.youse.com.reddit.model.Post;

/**
 * Created by heitornascimento on 11/6/16.
 */

public class HeadlessFragment extends Fragment {

    private ArrayList<Post> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(ArrayList<Post> data) {
        this.mData = data;
    }

    public ArrayList<Post> getData() {
        return mData;
    }

}
