
package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.login.login.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlatProfil extends AppCompatActivity {
TextView textView1,textView2,textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat);

        String restauId = getIntent().getStringExtra("restauId");
        int PlatId = getIntent().getIntExtra("platId", 666);
        //GlobaleVarPlat.idPlat = PlatId;
        String platId = String.valueOf(PlatId);
        //String restauId = String.valueOf(RestauId) ;
        Button commander ;
        commander = (Button)findViewById(R.id.commanderBtn);
        DatabaseReference database;

        textView1 = findViewById(R.id.platNom);
        textView2 = findViewById(R.id.platPrix);
        textView3 = findViewById(R.id.platDescription);
        ImageView ImagePlat = findViewById(R.id.platImage);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(restauId).child("Menu").child(platId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nomPlat = snapshot.child("NomPlat").getValue().toString();
                String prixPlat = snapshot.child("Prix").getValue().toString();
                String platDescription = snapshot.child("DescriptionPlat").getValue().toString();
                String imagePlat = snapshot.child("ImagePlat").getValue().toString();


                //assign to global var

               /* GlobaleVarPlat.varNomPlat =nomPlat;
                GlobaleVarPlat.varDescription =platDescription;
                GlobaleVarPlat.varPrix =prixPlat; */


                textView1.setText(nomPlat);
                textView2.setText(prixPlat);
                textView3.setText(platDescription);
                Glide.with(PlatProfil.this).load(imagePlat).into(ImagePlat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlatProfil.this, Paiement.class);
                i.putExtra("plat_name", platId) ;
                i.putExtra("restaurant_id",restauId);
                startActivity(i);
            }
        });

    }

}