package com.example.g_iwim_application.Models;

import com.google.firebase.firestore.DocumentId;

public class Professeur  {
    @DocumentId
    private String id_professeur ;
    private String nom ;
    private String prenom ;
    private String departement ;
    private String imageURI ;
    private String user_id ;
    private String statut_professeur ;

    public String getStatut_professeur() {
        return statut_professeur;
    }

    public void setStatut_professeur(String statut_professeur) {
        this.statut_professeur = statut_professeur;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId_professeur() {
        return id_professeur;
    }

    public void setId_professeur(String id_professeur) {
        this.id_professeur = id_professeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
