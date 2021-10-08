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

import com.example.g_iwim_application.Adapters.RecyclerViewAdapterProf;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Manager.UserDB;
import com.example.g_iwim_application.Manager.UserManager;
import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.Models.User;
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

import static com.example.g_iwim_application.Manager.MessageDB.FIELD_STATUT_MESSAGE;

public class ProfessorListActivity extends AppCompatActivity {
    private static final String TAG = null;
    ListView listView;
    private RecyclerView profListRecyclerView;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private User user ;
    TextView full_name, departement;
    private ProfesseurManager professeurManager;
    private UserManager userManager ;
    private List<Professeur> profList;
    private Button add_new_prof;
    private String statut_message ;
    private String statut_user ;
    private String online_user_nom ;
    private String online_user_prenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_professor_list);
        professeurManager = ProfesseurManager.newInstance();
        profListRecyclerView = findViewById(R.id.recyclerview);
        profListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        full_name = findViewById(R.id.textView1);
        departement = findViewById(R.id.textView2);

      //  Bundle bundle = getIntent().getExtras();
       // statut_message =  bundle.getString(FIELD_STATUT_MESSAGE);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        add_new_prof = findViewById(R.id.button8);
        String prof = "Professeur" ;
        String etudiant = "Etudiant";
        Log.d("etudiant" , etudiant + statut_user);



        add_new_prof.setOnClickListener(new ProfessorListActivity.OKButtonOnClickListener());
        Log.d("logged in user", "id user" + userID );

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemReselectedListener(navListener);
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




    @Override
    protected void onStart() {
        super.onStart();

        professeurManager.getAllProfesseurs(new ProfessorListActivity.GetAllProfesseursOnCompleteListener());
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                statut_user = value.getString("statut");
                online_user_prenom = value.getString("prenom");
                online_user_nom = value.getString("nom");

                Log.d("THIS IS A TEST" , statut_user);
                if(statut_user.equals("Professeur") || statut_user.equals("Etudiant")){
                    Log.d("THIS IS A TEST 2" , statut_user);
                    add_new_prof.setVisibility(View.GONE);
                }


            }
        });


    }


    public class GetAllProfesseursOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    profList = querySnapshot.toObjects(Professeur.class);
                    Log.d("HotelListMainActivity", "size" + profList.size());
                    populateProfRecyclerView(profList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (profList == null || profList.size() == 0) {
//
            }
        }

        private void populateProfRecyclerView(List<Professeur> profList) {

            RecyclerViewAdapterProf profListMainRecyclerViewAdapter = new RecyclerViewAdapterProf(profList, new ProfessorListActivity.GetAllProfesseursOnCompleteListener.ProfessorListRecyclerViewOnItemClickListener());
            profListRecyclerView.setAdapter(profListMainRecyclerViewAdapter);
        }

        public class ProfessorListRecyclerViewOnItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {

                int itemIndex = profListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Professeur prof = profList.get(itemIndex);
                ProfesseurDetailsFragment pdf = new ProfesseurDetailsFragment();
                Bundle bundle = new Bundle();

                bundle.putString(ProfesseurDB.FIELD_NOM, prof.getNom());

                bundle.putString(ProfesseurDB.DOCUMENT_ID , prof.getId_professeur());
                bundle.putString(ProfesseurDB.FIELD_PRENOM, prof.getPrenom());
                Log.d("ContactListMainActivity", "prenom du prof" + prof.getPrenom());
                bundle.putString(ProfesseurDB.FIELD_DEPARTEMENT, prof.getDepartement());
                bundle.putString(ProfesseurDB.FIELD_USER_ID, prof.getUser_id());
                Log.d("ContactListMainActivity", "Id _user du prof" + prof.getUser_id());
                bundle.putString(ProfesseurDB.FIELD_STATUT_PROFESSEUR, prof.getStatut_professeur());
                bundle.putString(UserDB.FIELD_STATUT,statut_user);
                bundle.putString("online_user_id",userID);
                Log.d("ContactListMainActivity", "Id _user du onlineuser" + userID);
                bundle.putString("online_user_prenom",online_user_prenom);
                bundle.putString("online_user_nom",online_user_nom);
                Log.d("ContactListMainActivity", "prenom de online user" + online_user_prenom);
                pdf.setArguments(bundle);
                pdf.show(getSupportFragmentManager(),"MyFragment");
            }
        }



    }

    class OKButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), AddProfessorFormActivity.class));
            finish();

        }

    }




}

