package com.example.login.navbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.login.MenuAdapter;
import com.example.login.PlatModel;
import com.example.login.R;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.ViewHolder> {
    private final HistoriqueInterface historiqueInterface ;

    private Context context;
    private List<HistoriqueItem> historique;
    public HistoriqueAdapter( Context c, List<HistoriqueItem> postist,HistoriqueInterface historiqueInterface){
        this.historiqueInterface = historiqueInterface;
        this.context = c;
        this.historique = postist;

    }


   /* public TextView textViewDate;
    public TextView textViewPlat;
    public TextView textViewPrix;*/





    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        //public ImageView imageViewRestaurant ;

        public TextView textViewDate;
        public TextView textViewPlat;
        public TextView textViewPrix;
        //public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(@NonNull View itemView  ,HistoriqueInterface historiqueInterface) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.dateHistorique);
            textViewPlat= (TextView) itemView.findViewById(R.id.nomPlatHistorique);
            textViewPrix= (TextView) itemView.findViewById(R.id.prixHistorique);

            //imageViewRestaurant = itemView.findViewById(R.id.restaurantPic);;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(historiqueInterface != null){
                        int pos = getAdapterPosition() ;
                        if(pos != RecyclerView.NO_POSITION){
                            historiqueInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public HistoriqueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.historique_item, parent, false);
        return new HistoriqueAdapter.ViewHolder(v,historiqueInterface);
    }



    @Override
    public void onBindViewHolder(@NonNull HistoriqueAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        HistoriqueItem historiqueItem = historique.get(position);

        // Set item views based on your views and data model
        TextView textViewMDate = holder.textViewDate;
        textViewMDate.setText(historiqueItem.getDate());

        TextView textViewMPlat = holder.textViewPlat;
        textViewMPlat.setText(historiqueItem.getVarNomPlat());

        TextView textViewMPrix = holder.textViewPrix;
        textViewMPrix.setText(historiqueItem.getVarPrix());



    }

    @Override
    public int getItemCount() {
        return historique.size();
    }
}