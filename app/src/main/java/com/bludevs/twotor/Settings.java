package com.bludevs.twotor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Settings extends AppCompatActivity {
    private GoogleApiClient GPC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        GPC = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API).build();
        GPC.connect();

        final TextView username = (TextView) findViewById(R.id.dName);
        final TextView mail = (TextView) findViewById(R.id.dEmail);
        final ImageView prof = (ImageView) findViewById(R.id.dProfile);
        final Button bLogout = (Button) findViewById(R.id.bLogout);

        username.setText(SaveSharedPreferences.getName(this));
        mail.setText(SaveSharedPreferences.getUserName(this));
        Glide.with(this).load(SaveSharedPreferences.getProf(this)).into(prof);

        bLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signOut();
            }
        });

    }
    private void signOut(){
        if(GPC.isConnected()){
            Auth.GoogleSignInApi.signOut(GPC).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    Intent intent = new Intent(Settings.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                    SaveSharedPreferences.LogOut(Settings.this);
                }
            });
        } else {
            Log.i("UPDATE", "GPC NOT CONNECTED");
        }
    }
}
