package com.bludevs.twotor;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TutorReq extends AppCompatActivity {
    public String final_topic, final_desc, final_subj, final_date;
    Spinner subjSpin;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private int ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_req);
        subjSpin = (Spinner) findViewById(R.id.Subjectspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Subjects_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        subjSpin.setAdapter(adapter);



        final EditText topic = (EditText) findViewById(R.id.eTopic);
        final EditText desc = (EditText) findViewById(R.id.eExtra);
        final TextView dateText = (TextView) findViewById(R.id.Date);

        Button bDate = (Button) findViewById(R.id.bDate);
        Button bOK = (Button) findViewById(R.id.bOK);

        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        ref = database.getReference("requests");
        bOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final_topic = topic.getText().toString();
                final_desc = desc.getText().toString();
                final_subj = subjSpin.getSelectedItem().toString();
                final_date = dateText.getText().toString();

                RequestMessage request = new RequestMessage(
                        SaveSharedPreferences.getProf(TutorReq.this),
                        SaveSharedPreferences.getName(TutorReq.this),
                        final_topic, final_desc, final_subj, final_date,
                        UUID.randomUUID().toString());

                ref.push().setValue(request);
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
                finish();
            }
        });


        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag("TAG_DATETIME_FRAGMENT");
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm aa", java.util.Locale.getDefault());
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime());

        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new java.text.SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("UPDATE", e.getMessage());
        }

        dateTimeFragment.setOnButtonClickListener(new OnButtonWithNeutralClickListener() {

            @Override
            public void onNeutralButtonClick(Date date) {
                dateText.setText("");
            }

            @Override
            public void onPositiveButtonClick(Date date) {
                dateText.setText(dateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeFragment.startAtCalendarView();
                ;
                Calendar c = Calendar.getInstance();
                dateTimeFragment.setDefaultDateTime(c.getTime());
                dateTimeFragment.show(getSupportFragmentManager(), "TAG_DATETIME_FRAGMENT");
            }
        });

    }
}
