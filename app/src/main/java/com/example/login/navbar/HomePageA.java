package com.example.login.navbar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.R;

import java.util.ArrayList;


public class HomePageA extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerView recyclerView;
    public RestaurantAdapter restaurantAdapter;
    public ArrayList<RestaurantModel> restaurants;

    public HomePageA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageA.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageA newInstance(String param1, String param2) {
        HomePageA fragment = new HomePageA();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/* RECYCLERVIEW MIS EN COMMENTAIRE
        restaurantAdapter = new RestaurantAdapter(getContext(), restaurants, this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_a, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewP);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        restaurants = new ArrayList<>();

   */      /*RestaurantModel restaurant1 = new RestaurantModel(R.drawable.r1,"Frerot","Bouvlavrd m6 , 106 , Oujda" , "★★★☆☆");
         RestaurantModel restaurant2 = new RestaurantModel(R.drawable.r2,"Bigup","Bouvlavrd lala salma , 56 , Oujda" , "★★★☆☆");
         RestaurantModel restaurant3 = new RestaurantModel(R.drawable.r3,"EL FIl","Bouvlavrd tarik ibnou ziad , 556 , Oujda" , "★★★☆☆");
         RestaurantModel restaurant4 = new RestaurantModel(R.drawable.r4,"Mistral","Bouvlavrd HASSAN II , 45 , Oujda" , "★★★☆☆");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        restaurants.add(restaurant4);

        recyclerView.setAdapter(new RestaurantAdapter(getContext(), restaurants));*/
        View view = inflater.inflate(R.layout.fragment_home_page_a, container, false);
        return view;
    }
}