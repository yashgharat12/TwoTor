package com.bludevs.twotor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bludevs.twotor.ForumMessage;
import com.bludevs.twotor.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

/**
 * Created by syre on 2/27/18.
 */

public class ForumViewHolder extends RecyclerView.ViewHolder {
    private final Activity activity;

    TextView name, message;
    ImageView img;

    public ForumViewHolder(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;

        name = (TextView) itemView.findViewById(R.id.Text1);
        message = (TextView) itemView.findViewById(R.id.Text2);
        img = (ImageView) itemView.findViewById(R.id.forum_prof);
    }

    public void bind(ForumMessage fm){
        name.setText(fm.name);
        message.setText(fm.msg);
        Glide.with(ForumAdapter.getActivity()).load(fm.imgURL).into(img);

    }
}
