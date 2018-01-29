package com.bludevs.twotor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by syre on 1/27/18.
 */
public class RequestViewHolder extends RecyclerView.ViewHolder {

    private ImageView card_dp;
    private TextView card_name, card_topic, card_desc, card_date, card_subj;
    private String card_ID;
    private Button bAccept;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    public RequestViewHolder(View itemView) {
        super(itemView);
        card_dp = (ImageView) itemView.findViewById(R.id.card_dp);
        card_name = (TextView) itemView.findViewById(R.id.card_name);
        card_topic = (TextView) itemView.findViewById(R.id.card_topic);
        card_desc = (TextView) itemView.findViewById(R.id.card_desc);
        card_subj = (TextView) itemView.findViewById(R.id.card_subj);
        card_date = (TextView) itemView.findViewById(R.id.card_date);

        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        ref = database.getReference("accepted");

        bAccept = (Button) itemView.findViewById(R.id.bAccept);
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestMessage rm = RequestAdapter.findRequest(card_ID);
                ref.push().setValue(rm);
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String s) {
                        RequestMessage rm = snapshot.getValue(RequestMessage.class);
                        AccAdapter adapt = Accepted_tab.getAdapter();
                        if (!adapt.checklist(rm)) {
                            adapt.addAcc(rm);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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