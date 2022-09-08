package com.example.login.navbar;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.login.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    String userLang, userlong;
    String restaulang, retauLong;
    GoogleMap googleMap;
    LatLng polylineEndLatLng;
    Button btnroad, zoom;
    MarkerOptions options;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        btnroad = findViewById(R.id.road);
        btnroad.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                               getRoad();
                                       }
        });
      /*  zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getZoom();
            }
        }); */
        SupportMapFragment fragment = SupportMapFragment.newInstance();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        //getResteau();
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
            //getRoad();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


       /* BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(2);
        item.setChecked(true); */


        //if (navHostFragment != null) {

           /* bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() != R.id.mapActivity){
                        Intent intent = new Intent(MapActivity.this, hostNav.class);
                        intent.putExtra("Id", item.getItemId());
                        startActivity(intent);
                        finish();
                        //activityNavigator.navigate(activityNavigator.createDestination().setIntent(new Intent(this, MapActivity.class)), null, null, null);
                    }

                    return false;
                }

            });  */
        // NavController navController = navHostFragment.getNavController();

        // Setup NavigationUI here
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //}

      /*  getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit(); */
    }

   /* public void getZoom() {
        //zoom
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 100));
        //add marker
        googleMap.addMarker(options);
    } */

 /* private void getResteau() {
polylineEndLatLng = new LatLng(34.67730937720811, -1.9278987153445948);

        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        googleMap.addMarker(endMarker);
    } */

    public void getRoad() {
        try {
            Uri uri = Uri.parse("https://www.google.co.ma/maps/dir/" + userLang + "," + userlong + "/" + "McDonald's");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e){
            Uri uri= Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if(location != null){
                    //syn map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //alert("gggg");
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            userLang = Double.toString(location.getLatitude());
                            userlong = Double.toString(location.getLongitude());
                            //alert(userLang + " " + userlong);
                            options = new MarkerOptions().position(latLng)
                                    .title("daamn you hungry bro")
                                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_baseline_person_pin_24));
                               /* LatLng latLng1 = new LatLng(34.65694851802015, -1.9040031580083816);
                                MarkerOptions options2 = new MarkerOptions().position(latLng)
                                        .title("Al affaf"); */
                            polylineEndLatLng = new LatLng(34.67697740866424, -1.9278785987749458);
                            restaulang = Double.toString(34.67730937720811);
                            retauLong = Double.toString(-1.9278987153445948);
                            //alert(restaulang + " " + retauLong);
                            MarkerOptions endMarker = new MarkerOptions()
                                    .title("I agree bro")
                                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.mcdo));
                            endMarker.position(polylineEndLatLng);
                            googleMap.addMarker(endMarker);
                            //zoom
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //add marker
                            googleMap.addMarker(options);
                            //googleMap.addMarker(options2);
                        }
                    });
                }
            }
        });



    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        //vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        vectorDrawable.setBounds(0, 0,70,70);
        Bitmap bitmap=Bitmap.createBitmap(70, 70,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
    private void alert(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(MapActivity.this);
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