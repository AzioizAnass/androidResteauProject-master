package com.example.login.login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.GlobaleVar;
import com.example.login.R;
import com.example.login.SplashScreen;
import com.example.login.navbar.hostNav;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
  /*  @Override
protected void onStart() {


        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), hostNav.class);
            startActivity(intent);
        }
    }
   GoogleSignInClient mGoogleSignInClient;

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // your operation....
                    }
                }
            }); */
    TextInputLayout phone, password;
    TextView forgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = (TextInputLayout ) findViewById(R.id.username);
        password = (TextInputLayout ) findViewById(R.id.password);
        forgotPass = (TextView) findViewById(R.id.forgotpass) ;


        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        Button signupbtn = (Button) findViewById(R.id.signupbtn);
        ImageView google = (ImageView) findViewById(R.id.google);
        //ImageView fb = (ImageView) findViewById(R.id.fb);

        validateUsername();
        validatePassword();

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance(); */
        /*loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().equals("11") && password.getText().toString().equals("11")) {
                    //correct
                    Intent intent = new Intent(MainActivity.this, hostNav.class);
                    startActivity(intent);
                    finish();

                } else
                    //incorrect
                    alert("Username or password incorrect");

            }
        }); */
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
                //Animatoo.animateCard(MainActivity.this);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                //Animatoo.animateCard(MainActivity.this);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FbSignUp.class);
                startActivity(intent);
                // Intent intent = new Intent(MainActivity.this, FbSignUp.class);
                //startActivity(intent);
            }
        });

      /*  fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FbSignUp.class);
                startActivity(intent);
            }
        }); */
    }








    private void alert(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("Message");
        dlg.setMessage(message);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dlg.create();
        dlg.show();
    }

    private Boolean validateUsername(){
        String val = phone.getEditText().getText().toString();
        if (val.isEmpty()){
            phone.setError("Ce champs doit etre rempli");
            phone.setErrorEnabled(true);
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()){
            phone.setError("Ce champs doit etre rempli");
            phone.setErrorEnabled(true);
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        if ( !validateUsername() | !validatePassword()){
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        String userEnteredTel = phone.getEditText().getText().toString();
        String userEnteredpassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");

        Query checkUser = reference.orderByChild("telephone").equalTo(userEnteredTel);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    phone.setError(null);
                    phone.setErrorEnabled(false);
                    final String passwordFromDB = snapshot.child(userEnteredTel).child("password").getValue(String.class);


                    //alert(userEnteredpassword);
                    if(!passwordFromDB.equals(userEnteredpassword)){
                        password.setError("Wrong Password");
                        phone.requestFocus();
                    }
                    else {

                        String nomFromDB = snapshot.child(userEnteredTel).child("familyname").getValue(String.class);
                        String prenomFromDB = snapshot.child(userEnteredTel).child("firstname").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredTel).child("email").getValue(String.class);

                        //String passwordFromDB = snapshot.child(userEnteredTel).child("mdp").getValue(String.class);*/
                        Intent intent = new Intent(getApplicationContext(), hostNav.class);
                        GlobaleVar.varNom = nomFromDB + " " + prenomFromDB;
                        GlobaleVar.email = emailFromDB;
                        GlobaleVar.id = userEnteredTel;
                        intent.putExtra("id", nomFromDB);
                        intent.putExtra("prenom", prenomFromDB);
                       /* intent.putExtra("phone", userEnteredTel);
                        intent.putExtra("email", emailFromDB); */
                        startActivity(intent);
                        finish();

                    }
                } else {

                    phone.setError("No such User Exits");
                    phone.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}