package com.bludevs.twotor;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements Request_tab.OnFragmentInteractionListener, Forum_tab.OnFragmentInteractionListener {
    private static RequestAdapter adapt;
    ViewPager viewPager;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        if (SaveSharedPreferences.getUserName(MainActivity.this).length() == 0) {
            startActivity(intent);
        } else {
        }

        ImageButton bSettings = (ImageButton) findViewById(R.id.bSettings);
        ImageButton bAdd = (ImageButton) findViewById(R.id.bAdd);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Forum"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pagerrrr);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(padapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        ref = database.getReference("requests");
        ref.keepSynced(true);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                RequestMessage rm = snapshot.getValue(RequestMessage.class);
                RequestAdapter adapt = Request_tab.getAdapter();
                if (!adapt.checkList(rm)) {
                    adapt.addRequest(rm);
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

        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Settings.class);
                PendingIntent pIntent = TaskStackBuilder.create(MainActivity.this)
                        .addNextIntentWithParentStack(i)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                startActivity(i);


            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TutorReq.class);
                PendingIntent pIntent = TaskStackBuilder.create(MainActivity.this)
                        .addNextIntentWithParentStack(i)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                startActivity(i);
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

