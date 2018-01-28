package com.bludevs.twotor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by syre on 1/27/18.
 */
public class RequestViewHolder extends RecyclerView.ViewHolder {

    private ImageView card_dp;
    private TextView card_name, card_topic, card_desc, card_date, card_subj;
    private Button bAccept;

    public RequestViewHolder(View itemView) {
        super(itemView);
        card_dp = (ImageView) itemView.findViewById(R.id.card_dp);
        card_name = (TextView) itemView.findViewById(R.id.card_name);
        card_topic = (TextView) itemView.findViewById(R.id.card_topic);
        card_desc = (TextView) itemView.findViewById(R.id.card_desc);
        card_subj = (TextView) itemView.findViewById(R.id.card_subj);
        card_date = (TextView) itemView.findViewById(R.id.card_date);

        bAccept = (Button) itemView.findViewById(R.id.bAccept);
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HELLO", card_topic.getText().toString());
            }
        });
    }

    public void bind(RequestMessage rm) {
        Glide.with(RequestAdapter.getActivity()).load(rm.imgURL).into(card_dp);
        card_name.setText(rm.name);
        card_topic.setText(rm.topic);
        card_desc.setText(rm.desc);
        card_subj.setText(rm.subj);
        card_date.setText(rm.date);
    }

}