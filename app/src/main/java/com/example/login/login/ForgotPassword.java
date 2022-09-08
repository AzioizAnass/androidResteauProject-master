package com.example.login.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
    TextInputLayout phoneText;
    Button submit;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        phoneText = (TextInputLayout) findViewById(R.id.pressemail);
        submit = (Button) findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumber();
            }
        });
    }

    private void verifyPhoneNumber() {
        String phone = phoneText.getEditText().getText().toString().trim();
        if(phone.isEmpty()){
            phoneText.setError("Email is required");
            phoneText.requestFocus();
            return;
        }
        FirebaseDatabase rootNode;
        DatabaseReference reference;
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Utilisateurs");
        Query checkUser = reference.orderByChild("telephone").equalTo(phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phoneText.setError(null);
                    Intent intent = new Intent(getApplicationContext(), verifyForgotPass.class);
                    intent.putExtra("phoneNoP", phone);
                    startActivity(intent);
                } else {
                    phoneText.setError("no such user exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



   /* private void reserPassword() {
        String emailT = email.getText().toString().trim();
        if(emailT.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailT).matches()){
            email.setError("invalid Email");
            email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(emailT).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } */
}