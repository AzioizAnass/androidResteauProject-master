package com.example.login.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyForgotPass extends AppCompatActivity {

    Button verify;
    PinView phoneNoEntered;
    String verificationCodeBySystem;
    FirebaseAuth firebaseAuth ;
    String phoneNo;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_forgot_pass);
        phoneNoEntered = findViewById(R.id.code1);
        verify = (Button) findViewById(R.id.conff1);
        phoneNo = getIntent().getStringExtra("phoneNoP");
        //phoneNoEntered.setText("44444");
        //senVerificationCodeToUser(phoneNo);

        otpSend(phoneNo);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = phoneNoEntered.getText().toString();
                if(code.isEmpty() || code.length() < 6){

                    phoneNoEntered.setError("Wrong OTP...");
                    phoneNoEntered.requestFocus();
                    return;
                }
                verifyCode(code);

                Intent intent = new Intent(getApplicationContext(), NewPassword.class);
                intent.putExtra("phoneNoT", phoneNo);
                startActivity(intent);
                //Animatoo.animateCard(verifyForgotPass.this);
                finish();
            }
        });
    }

   /* private void senVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+212" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Activity) TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    } */


    private void otpSend(String phoneNo) {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                //alert("code arivée bro");
                phoneNoEntered.setText(code);
                verifyCode(code);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(verifyForgotPass.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                //  super.onCodeSent(s, forceResendingToken);

                verificationCodeBySystem = s;
            }

        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth )
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);
    }
    private void signInTheUserByCredentials(PhoneAuthCredential credential){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(verifyForgotPass.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(verifyForgotPass.this, NewPassword.class);
                            intent.putExtra("phoneNoT", phoneNo);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(verifyForgotPass.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void alert(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(verifyForgotPass.this);
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
}