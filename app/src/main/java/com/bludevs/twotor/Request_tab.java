package com.bludevs.twotor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Request_tab extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static RequestAdapter adapt;
    public static ProgressBar PBar;
    private String mParam1;
    private String mParam2;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();
    private ArrayList<String> cur_items = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public Request_tab() {
        // Required empty public constructor
    }

    public static Request_tab newInstance(String param1, String param2) {
        Request_tab fragment = new Request_tab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RequestAdapter getAdapter() {
        return adapt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_requests,container,false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.card_list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapt = new RequestAdapter(getActivity());
        PBar = (ProgressBar) rootView.findViewById(R.id.PBar);
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        ref = database.getReference("requests");
        ref.keepSynced(true);
        rv.setAdapter(adapt);
        listItems = getResources().getStringArray(R.array.Subjects_array);
        checkedItems = new boolean[listItems.length];
        for (int i = 0; i < listItems.length; i++) {
            checkedItems[i] = true;
        }

        /*final ImageButton bFilter = (ImageButton) rootView.findViewById(R.id.bFilter);
        bFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialog);
                mBuilder.setTitle("Subjects");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(which)){
                                mUserItems.add(which);
                            } else {
                                mUserItems.remove(which);
                            }
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(int i = 0; i < mUserItems.size(); i++){
                            cur_items.add(listItems[mUserItems.get(i)]);
                        }
                        adapt.filter(cur_items);

                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog d = mBuilder.create();
                d.show();
            }
        });*/


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

     public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
