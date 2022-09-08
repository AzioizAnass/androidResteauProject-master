package com.example.login.navbar;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.GlobaleVar;
import com.example.login.MenuAdapter;
import com.example.login.MenuInterface;
import com.example.login.PlatModel;
import com.example.login.R;
import com.example.login.RestauProfil;
import com.example.login.login.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;


public class ProfilA extends Fragment implements HistoriqueInterface {

    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;


    public RecyclerView HistoriqueRV;
    public HistoriqueAdapter historiqueAdapter;
    public ArrayList<HistoriqueItem> historique;
    //public RestaurantAdapter restaurantAdapter;
    //public ArrayList<RestaurantModel> restaurants;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();;
    DatabaseReference reference = rootNode.getReference("Utilisateurs");
    DatabaseReference idReference = reference.child(GlobaleVar.id);

    DatabaseReference picReference;

    ImageView profilPic;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference database ;
    String generatedFilePath = "ffff";
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            imageUri = data.getData();
                            profilPic.setImageURI(imageUri);
                            uploadPic();
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_a, container, false);
        Button signOut = view.findViewById(R.id.signout);
        profilPic = (ImageView) view.findViewById(R.id.profilpic) ;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // nom instauration
        //  String nom = getActivity().getIntent().getStringExtra("nom");
        TextView nomText = view.findViewById(R.id.textViewnom);
        nomText.setText(GlobaleVar.varNom);
        //prenom instauration

        //String prenom = getActivity().getIntent().getStringExtra("nom");
        TextView prenomText = view.findViewById(R.id.textViewemail);
        prenomText.setText(GlobaleVar.email);





///////start here
      /*  recyclerViewP = view.findViewById(R.id.rvOrders);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewP.setHasFixedSize(true);
        plats = new ArrayList<>();
        menuAdapter = new MenuAdapter(getContext(), plats, this);
        recyclerViewP.setLayoutManager(manager);
        restaurants = new ArrayList<>();
        recyclerViewP.setAdapter(menuAdapter);
        database = idReference.child("historique");
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


        });*/



        //RecyclerView Historique

       /* HistoriqueRV=view.findViewById(R.id.rvOrders);

        database= FirebaseDatabase.getInstance().getReference("Utilisateurs").child(GlobaleVar.id).child("historique").child(GlobaleVar.idPP);
        HistoriqueRV.setHasFixedSize(true);
        HistoriqueRV.setLayoutManager(new LinearLayoutManager(getContext()));

        historique = new ArrayList<>();
        historiqueAdapter = new HistoriqueAdapter(getContext(), historique, this);
        HistoriqueRV.setAdapter(historiqueAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HistoriqueItem historiqueItem = dataSnapshot.getValue(HistoriqueItem.class);
                    historique.add(historiqueItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */


        idReference = reference.child(GlobaleVar.id);
        //Query checkImage = reference.orderByChild("image").equalTo(generatedFilePath);
        idReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("image") ){

                    //picReference = idReference.child("image");
                    String url = snapshot.child("image").getValue(String.class);
                    new FetchImage(url).start();
                    //alert(url);
                    //Drawable d = LoadImageFromWebOperations(url);



                   /* picReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String photoUrl = dataSnapshot.getValue(String.class);
                            try {
                                Picasso.with(getActivity()).load(photoUrl).into(profilPic);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }



    private void choosePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(intent);
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilPic.setImageURI(imageUri);
            uploadPic();
        }
    } */

    private void uploadPic() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Uploading");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        // Create a reference with an initial file path and name
        StorageReference pathReference = storageReference.child("images/" + randomKey);
        pathReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "image Uploaded.", Snackbar.LENGTH_LONG).show();
                pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                           /* rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("Utilisateurs");
                            idReference = reference.child(GlobaleVar.id); */
                        idReference.child("image").setValue(uri.toString());
                        //alert("success");
                    }
                });

                    /*Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                    if(downloadUri.isSuccessful()){

                        generatedFilePath = downloadUri.getResult().toString();
                        //System.out.println("## Stored path is "+generatedFilePath);
                        alert(generatedFilePath);
                    } */
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Failed To Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double porgressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) porgressPercent + "%");
                    }
                });
// Create a reference to a file from a Cloud Storage URI
        //   StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
        // StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");
    }
    private void alert(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
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

    @Override
    public void onItemClick(int position) {

    }

    /*private void fetchImage(String url) {
        Bitmap bitmap;
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            profilPic.setImageBitmap(bitmap);
            alert("fffffffffffffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }

    } */


    class FetchImage extends Thread {

        String url;
        Bitmap bitmap;

        FetchImage(String url) {

            this.url = url;

        }

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Getting your pic....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            InputStream inputStream = null;
            try {
                inputStream =  (InputStream) new URL(url).getContent();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    profilPic.setImageBitmap(bitmap);

                }
            });


        }
    }

}