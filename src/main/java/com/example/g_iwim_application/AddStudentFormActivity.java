package com.example.g_iwim_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.g_iwim_application.Manager.EtudiantManager;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Professeur;

public class AddStudentFormActivity extends AppCompatActivity {
    private static final String TAG = null;
    private EditText nom ;
    private EditText prenom ;
    private EditText CNE ;
    private Button okButtonn ;
    private Etudiant etudiant= new Etudiant() ;
    private EtudiantManager etudiantManager;
    private EditText imageurl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_form2);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        CNE = findViewById(R.id.CNE);
        imageurl = findViewById(R.id.imageurl);
        okButtonn = findViewById(R.id.okButton);
        okButtonn.setOnClickListener(new AddStudentFormActivity.OKButtonOnClickListener());
    }
    private class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String nom_ = nom.getText().toString().trim();
            etudiant.setNom(nom_);
            String prenom_ = prenom.getText().toString().trim();
            etudiant.setPrenom(prenom_);
            String CNE_ = CNE.getText().toString().trim();
            etudiant.setCNE(CNE_);
            String imageuri = imageurl.getText().toString().trim();
            etudiant.setImageURI(imageuri);
            Log.d(TAG, etudiant.getNom() + " " + etudiant.getPrenom());
            etudiantManager = etudiantManager.newInstance();
            etudiantManager.createDocument(etudiant);
            startActivity(new Intent(getApplicationContext(), StudentsListActivity.class));
            finish();

        }
    }
}