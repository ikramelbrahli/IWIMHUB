package com.example.g_iwim_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_iwim_application.Manager.EtudiantManager;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = null;
    EditText prenom, nom, email, password , cne;
    Button btnLogin;
    TextView login;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore ;
    String userID ;

    private String statut ;
    private  String email_ , statut_, password_, nom_,prenom_ , cne_;
    private User user_ = new User();
    private ProfesseurManager pf = ProfesseurManager.newInstance();
    private Professeur prof = new Professeur();
    private EtudiantManager em = EtudiantManager.newInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prenom = findViewById(R.id.inputPrenom);
        password = findViewById(R.id.inputPassword);
        login = findViewById(R.id.gotoRegister);
        nom = findViewById(R.id.inputNom);
        email = findViewById(R.id.inputEmail);
        cne = findViewById(R.id.CNE);
        login = findViewById(R.id.gotoRegister);

        Spinner spinner =findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this , R.array.statut , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Log.d(TAG , "statut spinner" + statut) ;
        btnLogin = findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext() , MenuChefActivity.class)) ;
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email_ = email.getText().toString().trim();
                 password_ = password.getText().toString().trim();
                statut= spinner.getSelectedItem().toString();
                 nom_= nom.getText().toString().trim();
                prenom_= prenom.getText().toString().trim();
                cne_ = cne.getText().toString().trim();
                if(TextUtils.isEmpty(email_)){
                    email.setError("Email is Required");
                }
                if(TextUtils.isEmpty(password_)){
                    password.setError("Password is Required");
                }
                if(password.length() < 6 ){
                    password.setError("Short password") ;
                }
                if(TextUtils.isEmpty(cne_)){
                    cne_ = " " ;
                }
                fAuth.createUserWithEmailAndPassword(email_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this , "User Created" , Toast.LENGTH_SHORT).show();
                            userID =fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("User").document(userID) ;
                            Map<String , Object> user = new HashMap<>();
                            user.put("nom",nom_);
                            user.put("prenom",prenom_);
                            user.put("email",email_);
                            user.put("password",password_);
                            user.put("statut" , statut);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG , "User profile created for" + userID) ;
                                    Log.d(TAG , "statut" + statut) ;
                                    String test = "Professeur" ;
                                    String student = "Etudiant" ;
                                    String chef = "chef";
                                    if(statut.equals(test)){

                                        prof.setUser_id(userID);
                                        prof.setPrenom(prenom_);
                                        prof.setNom(nom_);
                                        prof.setStatut_professeur("Professeur");
                                        prof.setDepartement("IWIM");
                                        pf.createDocument(prof);
                                        startActivity(new Intent(getApplicationContext() , LoginActivity.class)) ;
                                    }
                                    if(statut.equals(student)){
                                        Etudiant et =  new Etudiant();
                                        et.setUser_id(userID);
                                        et.setPrenom(prenom_);
                                        et.setNom(nom_);
                                        et.setCNE(cne_);
                                        em.createDocument(et);
                                        startActivity(new Intent(getApplicationContext() , LoginActivity.class)) ;
                                    }


                                }
                            });

                        }
                        else{
                            Toast.makeText(RegisterActivity.this , "Error ! " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;
            }
        }) ;
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext() , LoginActivity.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}