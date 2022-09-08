package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPassword extends AppCompatActivity {
    Button submit;
    TextInputLayout password;
    TextInputLayout confpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        submit = (Button) findViewById(R.id.sendpass);
        password = (TextInputLayout) findViewById(R.id.confirmpass);
        confpass = (TextInputLayout) findViewById(R.id.confirmnewpass);

        String phoneNo = getIntent().getStringExtra("phoneNoT");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passtext = password.getEditText().getText().toString();
                String confpasstext = confpass.getEditText().getText().toString();
                if(!passtext.equals(confpasstext)){
                    Context context = getApplicationContext();

                    CharSequence text = "password not matched";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");
                    reference.child(phoneNo).child("password").setValue(passtext);
                    Toast.makeText(NewPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }
            }
        });
    }
}