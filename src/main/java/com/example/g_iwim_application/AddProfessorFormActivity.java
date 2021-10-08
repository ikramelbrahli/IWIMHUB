package com.example.g_iwim_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Professeur;

public class AddProfessorFormActivity extends AppCompatActivity {
    private static final String TAG = null ;
    private EditText nom ;
    private EditText prenom ;
    private EditText departement ;
    private Button okButtonn ;
    private Professeur professeur = new Professeur() ;
    private ProfesseurManager professeurManager;
    private EditText imageurl ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_professor_form);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        departement = findViewById(R.id.departement);
        imageurl = findViewById(R.id.imageurl);
        okButtonn = findViewById(R.id.okButton);
        okButtonn.setOnClickListener(new OKButtonOnClickListener());
    }
    private class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String nom_ = nom.getText().toString().trim();
            professeur.setNom(nom_);
            String prenom_ = prenom.getText().toString().trim();
            professeur.setPrenom(prenom_);
            String departement_ = departement.getText().toString().trim();
            professeur.setDepartement(departement_);
            String imageuri = imageurl.getText().toString().trim();
            professeur.setImageURI(imageuri);
            Log.d(TAG, professeur.getNom() + " " + professeur.getPrenom());
            professeurManager = ProfesseurManager.newInstance();
            String prof = "professeur" ;
            professeur.setStatut_professeur(prof);
            professeurManager.createDocument(professeur);
            startActivity(new Intent(getApplicationContext(), ProfessorListActivity.class));
            finish();

        }
    }
}