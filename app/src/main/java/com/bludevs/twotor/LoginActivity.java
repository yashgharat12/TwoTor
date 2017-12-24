package com.bludevs.twotor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQ_CODE = 6969;
    public static String Name = "", Email = "", prof = "";
    private FirebaseAuth mAuth;
    private FirebaseApp app;
    private GoogleSignInClient mGSC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button bLogin = (Button) findViewById(R.id.bLogin);

        app = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(app);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGSC = GoogleSignIn.getClient(LoginActivity.this, options);
        mAuth = FirebaseAuth.getInstance();

        //googleAPIClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,options).build();
        //googleAPIClient.connect();
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signIn();
            }
        }
        );


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void firebaseAuthGoogle(GoogleSignInAccount acct) {
        AuthCredential creds = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(creds).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(true);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("LOGIN: ", "FAILED");
                }
            }
        });
    }

    private void signIn(){
        Intent intent = mGSC.getSignInIntent();
        startActivityForResult(intent, REQ_CODE);
        }
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
           // firebaseAuthWithGoogle(account);
            Name = account.getDisplayName();
            Email = account.getEmail();
            prof = account.getPhotoUrl().toString();
            SaveSharedPreferences.setPrefUser(this, Email);
            SaveSharedPreferences.setName(this, Name);
            SaveSharedPreferences.setProf(this, prof);
            updateUI(true);
        } else {
            updateUI(false);
        }
    }
    private void updateUI(boolean isLogin){
        if(isLogin){
            Intent i_Main = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(i_Main);
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode,resCode,data);
        GoogleSignInAccount acct = null;

        if(reqCode == REQ_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                acct = account;

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("LOGIN", "Google sign in failed:" + e);
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            Name = acct.getDisplayName();
            Email = acct.getEmail();
            prof = acct.getPhotoUrl().toString();
            SaveSharedPreferences.setPrefUser(this, Email);
            SaveSharedPreferences.setName(this, Name);
            SaveSharedPreferences.setProf(this, prof);
            updateUI(true);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("SOMETAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SOMETAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SOMETAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
