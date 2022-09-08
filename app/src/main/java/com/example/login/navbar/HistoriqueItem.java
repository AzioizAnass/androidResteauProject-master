package com.example.login.navbar;

public class HistoriqueItem {
    String varNomPlat,varPrix;
    String date;

    public HistoriqueItem() {
    }

    public HistoriqueItem(String varNomPlat,  String varPrix, String date) {
        this.varNomPlat = varNomPlat;
        this.varPrix = varPrix;
        this.date = date;
    }

    public String getVarNomPlat() {
        return varNomPlat;
    }

    public void setVarNomPlat(String varNomPlat) {
        this.varNomPlat = varNomPlat;
    }

    public String getVarPrix() {
        return varPrix;
    }

    public void setVarPrix(String varPrix) {
        this.varPrix = varPrix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}