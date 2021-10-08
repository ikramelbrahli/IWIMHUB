package com.example.g_iwim_application.Models;

import com.google.firebase.firestore.DocumentId;

public class Message {
    @DocumentId
    private String message_id ;
    private String text_message ;
    private String user_id ;
    private String user_id_reciever ;
    private String nom ;
    private String prenom ;
    private String statut_message ;
    private String user_id_sender ;
    private String nom_sender ;
    private String prenom_sender ;
    private String nom_reciever ;
    private String prenom_reciever ;

    public String getNom_sender() {
        return nom_sender;
    }

    public void setNom_sender(String nom_sender) {
        this.nom_sender = nom_sender;
    }

    public String getPrenom_sender() {
        return prenom_sender;
    }

    public void setPrenom_sender(String prenom_sender) {
        this.prenom_sender = prenom_sender;
    }

    public String getNom_reciever() {
        return nom_reciever;
    }

    public void setNom_reciever(String nom_reciever) {
        this.nom_reciever = nom_reciever;
    }

    public String getPrenom_reciever() {
        return prenom_reciever;
    }

    public void setPrenom_reciever(String prenom_reciever) {
        this.prenom_reciever = prenom_reciever;
    }

    public String getUser_id_reciever() {
        return user_id_reciever;
    }

    public void setUser_id_reciever(String user_id_reciever) {
        this.user_id_reciever = user_id_reciever;
    }

    public String getUser_id_sender() {
        return user_id_sender;
    }

    public void setUser_id_sender(String user_id_sender) {
        this.user_id_sender = user_id_sender;
    }

    public String getStatut_message() {
        return statut_message;
    }

    public void setStatut_message(String statut_message) {
        this.statut_message = statut_message;
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

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
