package com.bludevs.twotor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syre on 2/27/18.
 */

public class ForumAdapter extends RecyclerView.Adapter<ForumViewHolder> {
    private static Activity activity;
    private static List<ForumMessage> forumList = new ArrayList<>();

    public ForumAdapter(Activity act) {
        activity = act;
    }

    public static Activity getActivity() {
        return activity;
    }

    public void addMessage(ForumMessage fm){
        forumList.add(fm);
        notifyItemInserted(forumList.size());
    }


    @Override
    public ForumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumViewHolder(activity.getLayoutInflater()
                .inflate(R.layout.forum_row, parent, false),activity);
    }

    @Override
    public void onBindViewHolder(ForumViewHolder holder, int position) {
        holder.bind(forumList.get(position));
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }
}


