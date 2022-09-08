package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.login.R;
import com.example.login.navbar.RecyclerViewInterface;
import com.example.login.navbar.RestaurantAdapter;
import com.example.login.navbar.RestaurantModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private final MenuInterface menuInterface ;

    private Context context;
    private List<PlatModel> mPlats;
    public MenuAdapter(Context c,
                       List<PlatModel> postist ,
                       MenuInterface menuInterface){
        this.context = c;
        this.mPlats = postist;
        this.menuInterface=menuInterface;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        public TextView TextViewPlatName;
        public TextView TextViewPrix ;

        public ViewHolder(@NonNull View itemView  , MenuInterface menuInterface) {
            super(itemView);
            TextViewPlatName = (TextView) itemView.findViewById(R.id.menuNomPlat);
            TextViewPrix= (TextView) itemView.findViewById(R.id.menuPlatPrix);

            img=(ImageView)itemView.findViewById(R.id.menuPlatImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(menuInterface != null){
                        int pos = getAdapterPosition() ;
                        if(pos != RecyclerView.NO_POSITION){
                            menuInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_plat, parent, false);

        return new MenuAdapter.ViewHolder(v,menuInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {

        PlatModel plat = mPlats.get(position);


        TextView textViewPlatName = holder.TextViewPlatName;
        textViewPlatName.setText(plat.getNomPlat());

        TextView textViewPrix = holder.TextViewPrix;
        textViewPrix.setText(plat.getPrix());


        Glide.with(holder.img.getContext()).load(plat.getImagePlat()).into(holder.img);

    }




    @Override
    public int getItemCount() {
        return mPlats.size();
    }
}