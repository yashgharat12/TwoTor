package com.bludevs.twotor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TutorReq extends AppCompatActivity {
    public String final_topic, final_desc, final_subj;
    Spinner subjSpin;

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
        final TextView date = (TextView) findViewById(R.id.Date);

        Button bDate = (Button) findViewById(R.id.bDate);
        Button bOK = (Button) findViewById(R.id.bOK);

        bOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final_topic = topic.getText().toString();
                final_desc = desc.getText().toString();
                final_subj = subjSpin.getSelectedItem().toString();

                RequestMessage request = new RequestMessage(
                        SaveSharedPreferences.getProf(TutorReq.this),
                        SaveSharedPreferences.getName(TutorReq.this),
                        final_topic,final_desc,final_subj);
                RequestAdapter adapter = Request_tab.adapt;
                adapter.addRequest(request);

                finish();
            }
        });

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
