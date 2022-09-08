package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.login.login.UserHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Paiement extends AppCompatActivity {
TextView commande ;

    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootNode.getReference("Utilisateurs");
    DatabaseReference idReference = reference.child(GlobaleVar.id);
    String nomRestau , nomPlat ,prixPlat, platDescription, imagePlat;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        Button annulerBtn , confirmerBtn ;

        commande= findViewById(R.id.tvCommande);
        annulerBtn= findViewById(R.id.annulerBtn);
        confirmerBtn = findViewById(R.id.confirmerBtn);

        String cmdID = getIntent().getStringExtra("plat_name") ;
        GlobaleVar.idPP = cmdID;
        String idRestau = getIntent().getStringExtra("restaurant_id");
        //commande.setText(cmdID);
        //.child(idRestau)

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(idRestau);
        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 nomRestau = snapshot.child("Nom").getValue().toString();
                 nomPlat = snapshot.child("Menu").child(cmdID).child("NomPlat").getValue().toString();
                 prixPlat = snapshot.child("Menu").child(cmdID).child("Prix").getValue().toString();
                 platDescription = snapshot.child("Menu").child(cmdID).child("DescriptionPlat").getValue().toString();
                 imagePlat = snapshot.child("Menu").child(cmdID).child("ImagePlat").getValue().toString();

                commande.setText(nomPlat +" chez "+ nomRestau);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        confirmerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            PlatHelperClass platHelperClass = new PlatHelperClass(nomPlat, prixPlat, dtf.format(now));
                            //picReference = idReference.child("image");
                            idReference.child("historique").child(cmdID).setValue(platHelperClass);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iintent = new Intent(Paiement.this , PlatProfil.class);
                startActivity(iintent);
            }
        });
        //confirmerBtn.setOnClickListener();
    }
}