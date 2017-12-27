package com.bludevs.twotor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    public static Activity acti;
    private final Activity activity;
    private List<RequestMessage> requestList = new ArrayList<>();

    public RequestAdapter(Activity act) {
        activity = act;
        acti = activity;

    }

    public void addRequest(RequestMessage request){
        requestList.add(request);
        notifyItemInserted(requestList.size());
        Log.i("COUNT:", Integer.toString(requestList.size()));
    }

    public Boolean checkList(RequestMessage rm) {
        Boolean chk = false;
        for (int i = 0; i < requestList.size(); i++) {
            RequestMessage entry = requestList.get(i);
            if (!(entry == null)) {
                Log.i("UPDATE", "not null");
            }
            if (requestList.size() > 0) {
                Log.i("UPDATE", Integer.toString(requestList.size()));
                Log.i("UPDATE", entry.ID);
                if (entry.ID.equals(rm.ID)) {
                    chk = true;
                } else {
                }
            }
        }
        return chk;
    }


    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cards,parent,false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        RequestMessage rm = requestList.get(position);
        Log.i("UPDATE:",requestList.get(position).subj);
        holder.bind(rm);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        private ImageView card_dp;
        private TextView card_name, card_topic, card_desc, card_date, card_subj;
        private Button bAccept;

        public RequestViewHolder(View itemView) {
            super(itemView);
            card_dp= (ImageView) itemView.findViewById(R.id.card_dp);
            card_name = (TextView) itemView.findViewById(R.id.card_name);
            card_topic = (TextView) itemView.findViewById(R.id.card_topic);
            card_desc = (TextView) itemView.findViewById(R.id.card_desc);
            card_subj = (TextView) itemView.findViewById(R.id.card_subj);
            card_date = (TextView) itemView.findViewById(R.id.card_date);
            bAccept = (Button) itemView.findViewById(R.id.bAccept);
        }

        public void bind(RequestMessage rm){
            Glide.with(RequestAdapter.acti).load(rm.imgURL).into(card_dp);
            card_name.setText(rm.name);
            card_topic.setText(rm.topic);
            card_desc.setText(rm.desc);
            card_subj.setText(rm.subj);
            card_date.setText(rm.date);
        }

    }
}


