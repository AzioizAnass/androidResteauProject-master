package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.example.login.navbar.MapActivity;
import com.example.login.navbar.RestaurantAdapter;
import com.example.login.navbar.RestaurantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestauProfil extends AppCompatActivity implements MenuInterface {

    TextView textView1, textView2, textView3;
    public RecyclerView recyclerViewPlats;
    public MenuAdapter menuAdapter;
    public ArrayList<PlatModel> plats;
    DatabaseReference database;
    String IdRestau;
    ImageView localisation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restau_profil);

        DatabaseReference database;

        textView1 = findViewById(R.id.nameRestaurant);
        textView2 = findViewById(R.id.ratingRestaurant);
        textView3 = findViewById(R.id.localisationRestaurant);
        ImageView logoRestau = findViewById(R.id.imageRestaurant);
        localisation = findViewById(R.id.loc);
        localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestauProfil.this, MapActivity.class);
                startActivity(intent);
            }
        });

        int RestauId = getIntent().getIntExtra("restaurantId", 666);
        IdRestau = String.valueOf(RestauId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(IdRestau);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nomRestau = snapshot.child("Nom").getValue().toString();
                String rateRestau = snapshot.child("Rate").getValue().toString();
                String localisationRestau = snapshot.child("Localisation").getValue().toString();
                String logo = snapshot.child("Logo").getValue().toString();
                textView1.setText(nomRestau);
                textView2.setText(rateRestau);
                textView3.setText(localisationRestau);

                Glide.with(RestauProfil.this).load(logo).into(logoRestau);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerViewPlats = findViewById(R.id.rvPlats);
        database = FirebaseDatabase.getInstance().getReference("Restaurants").child(IdRestau).child("Menu");
        recyclerViewPlats.setHasFixedSize(true);
        recyclerViewPlats.setLayoutManager(new LinearLayoutManager(RestauProfil.this));

        plats = new ArrayList<>();
        menuAdapter = new MenuAdapter(RestauProfil.this, plats, this);
        recyclerViewPlats.setAdapter(menuAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PlatModel plat = dataSnapshot.getValue(PlatModel.class) ;
                    plats.add(plat);
                }
                menuAdapter.notifyDataSetChanged();
            };


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


}
    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(RestauProfil.this, PlatProfil.class);
        i.putExtra("platId",position+1);
        i.putExtra("restauId" ,IdRestau);
        startActivity(i);
    }


}