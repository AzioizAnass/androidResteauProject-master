package com.example.login.navbar;

public class RestaurantModel {
    private String Logo;
    private String Nom;
    private String Localisation;
    private String Rate;
    private String Description ;


    public RestaurantModel(String logo, String nom, String localisation, String rate) {
        Logo = logo;
        Nom = nom;
        Localisation = localisation;
        Rate = rate;
    }

    public RestaurantModel() {

    }

    public  String getLogo() { return Logo;}

    public String getNom() {
        return Nom;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public String getRate() {
        return Rate;
    }

    public String getDescription() {
        return Description;
    }
}
