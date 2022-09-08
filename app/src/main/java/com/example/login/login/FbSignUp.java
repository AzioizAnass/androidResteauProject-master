package com.example.login.login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.login.GlobaleVar;
import com.example.login.R;
import com.example.login.navbar.hostNav;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FbSignUp extends AppCompatActivity {
    SignInButton googleSignin;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    // [END declare_auth]
   /* @Override
    protected void onStart() {


        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), hostNav.class);
            startActivity(intent);
        }
    } */
    GoogleSignInClient mGoogleSignInClient;

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            // Google Sign In was successful, authenticate with Firebase

                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            //Toast.makeText(FbSignUp.this, "op1 not Success", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Google sign in failed", e);
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_sign_up);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        googleSignin = (SignInButton) findViewById(R.id.conff);
        googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });
    }
    private void sign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        launchSomeActivity.launch(signInIntent);

    }
    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
             Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
             try {
                 // Google Sign In was successful, authenticate with Firebase

                 GoogleSignInAccount account = task.getResult(ApiException.class);
                 Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                 firebaseAuthWithGoogle(account.getIdToken());
             } catch (ApiException e) {
                 // Google Sign In failed, update UI appropriately
                 //Toast.makeText(FbSignUp.this, "op1 not Success", Toast.LENGTH_SHORT).show();
                 Log.w(TAG, "Google sign in failed", e);
             }
         }
     } */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(FbSignUp.this, "Login Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Intent intent = new Intent(getApplicationContext(), hostNav.class);
                            // startActivity(intent);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(FbSignUp.this, "Login not Success", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }
    private void updateUI(FirebaseUser user) {
        if(user != null){
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Utilisateurs");
            GlobaleVar.varNom = user.getDisplayName();
            GlobaleVar.email = user.getEmail();
            GlobaleVar.id = user.getUid();
            Query checkUser = reference.orderByChild("id").equalTo(user.getUid());
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        String id = user.getUid();

                        GoogleUserHelperClass googleUserHelperClass = new GoogleUserHelperClass(name, email, id);
                        reference.child(id).setValue(googleUserHelperClass);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(getApplicationContext(), hostNav.class);
            startActivity(intent);

        } else {
            Toast.makeText(FbSignUp.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }
}