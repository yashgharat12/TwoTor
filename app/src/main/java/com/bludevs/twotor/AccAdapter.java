package com.bludevs.twotor;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    public static Activity activity;
    private ArrayList<RequestMessage> accList = new ArrayList<>();
    private ArrayList<RequestMessage> sortedList = new ArrayList<>();

    public AccAdapter(Activity act) {
        activity = act;
    }

    public static Activity getActivity(){
        return activity;
    }

    public void sortAcc(){
        Collections.sort(accList, new Comparator<RequestMessage>() {
            DateFormat f = new SimpleDateFormat(
                    "MMM d, yyyy hh:mm aa", java.util.Locale.getDefault());

            @Override
            public int compare(RequestMessage o1, RequestMessage o2) {
                int ret = 0;
                try {
                    ret = f.parse(o1.date).compareTo(f.parse(o2.date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
        sortedList = accList;
        notifyDataSetChanged();
    }

    public void addAcc(RequestMessage acc){
        accList.add(acc);
        notifyItemInserted(accList.size());
        sortAcc();
    }

    public void updateList(){
        notifyDataSetChanged();
    }

    public boolean checkList(RequestMessage rm){
        boolean chk = false;
        for(int i = 0; i < accList.size(); i++){
            RequestMessage entry = accList.get(i);
            if(getItemCount() > 0 && entry.ID.equals(rm.ID)){
                chk = true;
            }
        }
        return chk;
    };

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cards,parent,false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        RequestMessage rm = sortedList.get(position);
        holder.bind(rm);
    }

    @Override
    public int getItemCount() {
        return accList.size();
    }
}
