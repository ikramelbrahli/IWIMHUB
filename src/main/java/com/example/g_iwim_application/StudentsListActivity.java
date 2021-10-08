package com.example.g_iwim_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_iwim_application.Adapters.RecyclerViewAdapterEtudiant;
import com.example.g_iwim_application.Adapters.RecyclerViewAdapterProf;
import com.example.g_iwim_application.Manager.EtudiantDB;
import com.example.g_iwim_application.Manager.EtudiantManager;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Manager.UserDB;
import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class StudentsListActivity extends AppCompatActivity {
    private static final String TAG = null;
    ListView listView;
    String mTitle[] = {"Student1", "Student1", "Student1", "Student1", "Student1"};
    String mDescription[] = {"description", "description", "description", "description", "description" , "description"};
    int images[] = {R.drawable.student96, R.drawable.student96 , R.drawable.student96, R.drawable.student96, R.drawable.student96, R.drawable.student96};
    private RecyclerView etudiantListRecyclerView;
    TextView full_name, CNE;
    private EtudiantManager etudiantManager;
    private List<Etudiant> etudiantList;
    private Button add_new_student;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private String statut_user ;
    private String prenom_online , nom_online ;
    private String online_user_nom ;
    private String online_user_prenom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        etudiantManager = EtudiantManager.newInstance();
        etudiantListRecyclerView = findViewById(R.id.recyclerview);
        etudiantListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//textview1 =>nom prenom , 2 depart
        full_name = findViewById(R.id.textView1);
        CNE = findViewById(R.id.textView2);
        add_new_student = findViewById(R.id.button8);
        add_new_student.setOnClickListener(new StudentsListActivity.OKButtonOnClickListener());
        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();*/
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                online_user_nom = value.getString("nom");
                online_user_prenom = value.getString("prenom");
                statut_user = value.getString("statut");


            }
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemReselectedListener(navListener);


            add_new_student.setOnClickListener(new StudentsListActivity.OKButtonOnClickListener());


    }
    private BottomNavigationView.OnNavigationItemReselectedListener navListener =
            new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null ;
                    switch(item.getItemId()){
                        case R.id.nav_home :
                            startActivity(new Intent(getApplicationContext(), MenuChefActivity.class));
                           /* selectedFragment = new HomeFragment();
                            Log.d("HotelListMainActivity", "in frag nav");*/
                            break ;


                    }
                   /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                }
            };
    protected void onStart() {
        super.onStart();
        // Populate the ContactListMainActivity with the available data
        etudiantManager.getAllEtudiants(new StudentsListActivity.GetAllEtudiantsOnCompleteListener());
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                statut_user = value.getString("statut");
                prenom_online = value.getString("prenom");
                nom_online = value.getString("nom");
                if(statut_user.equals("Professeur") || statut_user.equals("Etudiant")){
                    Log.d("THIS IS A TEST 2" , statut_user);
                    add_new_student.setVisibility(View.GONE);
                }

            }
        });

    }


    public class GetAllEtudiantsOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    etudiantList = querySnapshot.toObjects(Etudiant.class);
                    Log.d("HotelListMainActivity", "size" + etudiantList.size());
                    populateEtudiantRecyclerView(etudiantList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (etudiantList == null || etudiantList.size() == 0) {
//
            }
        }

        private void populateEtudiantRecyclerView(List<Etudiant> profList) {

            RecyclerViewAdapterEtudiant etudiantListMainRecyclerViewAdapter = new RecyclerViewAdapterEtudiant(profList, new StudentsListActivity.GetAllEtudiantsOnCompleteListener.EtudiantListRecyclerViewOnItemClickListener());
            etudiantListRecyclerView.setAdapter(etudiantListMainRecyclerViewAdapter);
        }

        public class EtudiantListRecyclerViewOnItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {

                int itemIndex = etudiantListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Etudiant etudiant = etudiantList.get(itemIndex);
                EtudiantDetailsFragment pdf = new EtudiantDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("EtudiantDB.FIELD_NOM", etudiant.getNom());
                bundle.putString(EtudiantDB.DOCUMENT_ID , etudiant.getId_etudiant());
                bundle.putString("EtudiantDB.FIELD_PRENOM", etudiant.getPrenom());
                bundle.putString(EtudiantDB.FIELD_CNE, etudiant.getCNE());
                bundle.putString(UserDB.FIELD_STATUT,statut_user);
                bundle.putString(UserDB.FIELD_NOM , nom_online);
                bundle.putString(UserDB.FIELD_PRENOM , prenom_online);
                pdf.setArguments(bundle);
                pdf.show(getSupportFragmentManager(),"MyFragment2");
                Log.d("ContactListMainActivity", "" + etudiant.getNom());
            }
        }
    }


    public class OKButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), AddStudentFormActivity.class));
            finish();
        }
    }
}