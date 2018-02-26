package com.bludevs.twotor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccViewHolder extends RecyclerView.ViewHolder {
    private ImageView card_dp;
    private TextView card_name, card_topic, card_desc, card_date, card_subj;
    private String card_ID;
    private Button bDecline;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference ref_req;

    public AccViewHolder(View itemView) {
        super(itemView);
        card_dp = (ImageView) itemView.findViewById(R.id.acc_card_dp);
        card_name = (TextView) itemView.findViewById(R.id.acc_card_name);
        card_topic = (TextView) itemView.findViewById(R.id.acc_card_topic);
        card_desc = (TextView) itemView.findViewById(R.id.acc_card_desc);
        card_subj = (TextView) itemView.findViewById(R.id.acc_card_subj);
        card_date = (TextView) itemView.findViewById(R.id.acc_card_date);

        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);;
        ref_req = database.getReference("requests");

        bDecline = (Button) itemView.findViewById(R.id.bDecline);
        bDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        card_ID = rm.ID;
    }
}
