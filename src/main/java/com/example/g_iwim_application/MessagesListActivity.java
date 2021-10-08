package com.example.g_iwim_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.g_iwim_application.Adapters.RecyclerViewAdapterMessage;
import com.example.g_iwim_application.Adapters.RecyclerViewAdapterProf;
import com.example.g_iwim_application.Manager.EtudiantDB;
import com.example.g_iwim_application.Manager.MessageDB;
import com.example.g_iwim_application.Manager.MessageManager;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Message;
import com.example.g_iwim_application.Models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.g_iwim_application.Manager.MessageDB.FIELD_STATUT_MESSAGE;

public class MessagesListActivity extends AppCompatActivity {
    private static final String TAG = null;
    private FloatingActionButton add_message ;
    String nom, prenom;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    private Message message = new Message();
    private MessageManager messageManager;
    private List<Message> messageList;
    private RecyclerView messageListRecyclerView;
    private String statut_message ;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);
        add_message = findViewById(R.id.floatingActionButton);
        add_message.setOnClickListener(new MessagesListActivity.AddMessageOnClickListener());
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        message.setUser_id(userID);
        textview = findViewById(R.id.topText2);

        DocumentReference documentReference = fstore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                nom = value.getString("nom");
               prenom = value.getString("prenom");
               message.setNom(nom);
               message.setPrenom(prenom);
               message.setStatut_message("Publique");

            }
        });
        messageManager = MessageManager.newInstance();
        messageListRecyclerView = findViewById(R.id.recyclerview);
        messageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle bundle = getIntent().getExtras();
        statut_message =  bundle.getString(FIELD_STATUT_MESSAGE);
        Log.d(TAG , "statut_message"+ statut_message);
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

        // Populate the ContactListMainActivity with the available data
        //messageManager.getAllPublicMessages(new MessagesListActivity.GetAllMessagesOnCompleteListener());
        if(statut_message.equals("sent")){
            Log.d("HotelListMainActivity", "inside sent" + statut_message);
            textview.setText("Messages envoyées");
           add_message.setVisibility(View.GONE);
            messageManager.getAllSentPrivateMessages(new MessagesListActivity.GetAllMessagesOnCompleteListener() , userID );
        }
        if(statut_message.equals("recu")){
            add_message.setVisibility(View.GONE);
            textview.setText("Messages reçus");
            Log.d("HotelListMainActivity", "inside recu");
            messageManager.getAllReceivedPrivateMessages(new MessagesListActivity.GetAllMessagesOnCompleteListener() , userID );
        }
        else if(statut_message.equals("Publique")){
            textview.setText("Messages partagés");
            messageManager.getAllPublicMessages(new MessagesListActivity.GetAllMessagesOnCompleteListener());

        }


    }


    public class GetAllMessagesOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                // Get the query snapshot from the task result
                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {
                    messageList = querySnapshot.toObjects(Message.class);
                    Log.d("HotelListMainActivity", "size" +messageList.size());
                    populateMessageRecyclerView(messageList);

                }

            } else {
                Log.w(TAG, "Error getting documents: ", task.getException());
            }

            if (messageList == null || messageList.size() == 0) {
//
            }
        }

        private void populateMessageRecyclerView(List<Message> messageList) {

            RecyclerViewAdapterMessage messageListMainRecyclerViewAdapter = new RecyclerViewAdapterMessage(messageList, new MessagesListActivity.GetAllMessagesOnCompleteListener.MessageListRecyclerViewOnItemClickListener(), statut_message );
            messageListRecyclerView.setAdapter(messageListMainRecyclerViewAdapter);

        }

        public class MessageListRecyclerViewOnItemClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {

                int itemIndex = messageListRecyclerView.indexOfChild(view);
                Log.d("ContactListMainActivity", "" + itemIndex);
                Message message = messageList.get(itemIndex);
              /*  ProfesseurDetailsFragment pdf = new ProfesseurDetailsFragment();
                Bundle bundle = new Bundle();

                bundle.putString(MessageDB.FIELD_NOM, message.getNom());
                bundle.putString(MessageDB.FIELD_PRENOM , message.getPrenom());
                bundle.putString(MessageDB.FIELD_TEXT_MESSAGE, message.getText_message());
                pdf.setArguments(bundle);
                pdf.show(getSupportFragmentManager(),"MyFragment");*/
            }
        }



    }

    public class AddMessageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            MessageForm pdf = new MessageForm();
            Bundle bundle = new Bundle();
            bundle.putString(MessageDB.FIELD_NOM, message.getNom());
            bundle.putString(MessageDB.FIELD_USER_ID , message.getUser_id());
            bundle.putString(MessageDB.FIELD_PRENOM, message.getPrenom());
            bundle.putString(MessageDB.FIELD_STATUT_MESSAGE, message.getStatut_message());

            pdf.setArguments(bundle);
            pdf.show(getSupportFragmentManager(),"MyFragment");
        }
    }
}