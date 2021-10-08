package com.example.g_iwim_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_iwim_application.Manager.MessageDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import static com.example.g_iwim_application.Manager.UserDB.FIELD_STATUT;

public class LoginActivity extends AppCompatActivity {
    EditText prenom, nom, email, password;
    Button btnLogin;
    TextView register;
    FirebaseAuth fAuth;
    String nom_, prenom_ , statut_;
    private User user = new User();
    FirebaseFirestore fstore;
    String userID;
    private ProfesseurManager pf = ProfesseurManager.newInstance();
    private Professeur prof = new Professeur();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.inputPassword);
        email = findViewById(R.id.inputEmail);
        register = findViewById(R.id.gotoRegister2);
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();



        if(fAuth.getCurrentUser() != null){
           /* userID = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("User").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

                public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                    nom_ = value.getString("nom");
                    prenom_ = value.getString("prenom");
                    statut_ = value.getString("statut");
                    user.setStatut(statut_);
                    user.setUser_id(userID);
                    if(user.getStatut()=="Professeur") {
                        prof.setUser_id(userID);
                        prof.setPrenom(prenom_);
                        prof.setNom(nom_);
                        prof.setStatut_professeur("Professeur");
                        prof.setDepartement("IWIM");
                    }


                }
            });*/
            startActivity(new Intent(getApplicationContext() , MenuChefActivity.class)) ;
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString().trim();
                String password_ = password.getText().toString().trim();
                if(TextUtils.isEmpty(email_)){
                    email.setError("Email is Required");
                }
                if(TextUtils.isEmpty(password_)){
                    password.setError("Password is Required");
                }
                if(password.length() < 6 ){
                    password.setError("Short password") ;
                }
                fAuth.signInWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this , "User Logged in" , Toast.LENGTH_SHORT).show();
                         //   if(user.getStatut()=="Professeur") {
                               // pf.createDocument(prof);
                            Intent intent = new Intent(getApplicationContext(),MenuChefActivity.class);

                            startActivity(intent);
                          //  }
                        }
                        else{
                            Toast.makeText(LoginActivity.this , "Error ! " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;
            }
        }) ;

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext() , RegisterActivity.class));
            }
        });
    }
}