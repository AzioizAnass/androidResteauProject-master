package com.example.login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUp extends AppCompatActivity {

    TextInputLayout familyname, firstname,telephone,  email, password, confPassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button confirmbtn = (Button) findViewById(R.id.confirm);
        familyname = findViewById(R.id.familyname);
        firstname = findViewById(R.id.firstname);
        telephone = findViewById(R.id.telephone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwd);
        confPassword = findViewById(R.id.confpasswd);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Utilisateurs");
                String familynameV = familyname.getEditText().getText().toString();
                String firstnameV = firstname.getEditText().getText().toString();
                String telephoneVV = telephone.getEditText().getText().toString();
                String emailV = email.getEditText().getText().toString();
                String passwordV = password.getEditText().getText().toString();
                String conf = confPassword.getEditText().getText().toString();
                String code = ccp.getSelectedCountryCode();
                String telephoneV = "+"  + code + telephoneVV;
                if(!isempty(familyname) | !isempty(firstname) | !isempty(telephone) | !isempty(email) |  !isempty(password) | !isempty(confPassword)){
                    return;
                } else {

                    if(!conf.equals(passwordV) ){
                        Context context = getApplicationContext();

                        CharSequence text = "password not matched";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {

                        UserHelperClass helperClass = new UserHelperClass(familynameV, firstnameV, telephoneV, emailV, passwordV);
                        reference.child(telephoneV).setValue(helperClass);
                        Intent intent = new Intent(SignUp.this, ConfirmEmail.class);
                        intent.putExtra("phoneNo", telephoneV);
                        startActivity(intent);
                        //Animatoo.animateCard(SignUp.this);


                        //Intent intent1 = new Intent(SignUp.this, ConfirmEmail.class);
                        //startActivity(intent1);
                    }
                }

                /*isempty(familynameV);
                isempty(firstnameV);
                isempty(telephoneV);
                isempty(emailV);
                isempty(passwordV);
                isempty(conf); */


            }
        });
    }


    public boolean isempty(TextInputLayout item){
        String val = item.getEditText().getText().toString();

        if (val.isEmpty()){
            item.setError("Ce champs doit etre rempli");
            return false;
        } else {
            item.setError(null);
            //phone.setErrorEnabled(false);
            return true;
        }
    }
}