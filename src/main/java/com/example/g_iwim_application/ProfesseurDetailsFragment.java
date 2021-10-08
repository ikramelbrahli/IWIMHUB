package com.example.g_iwim_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.g_iwim_application.Manager.EtudiantManager;
import com.example.g_iwim_application.Manager.MessageManager;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Manager.UserDB;
import com.example.g_iwim_application.Manager.UserManager;
import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Message;
import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.Models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.example.g_iwim_application.Manager.MessageDB.FIELD_STATUT_MESSAGE;


public class ProfesseurDetailsFragment extends DialogFragment {
    private Professeur prof = new Professeur() ;
    TextView full_name_, departement_;
    private FloatingActionButton delete ;
    private ProfesseurManager professeurManager = ProfesseurManager.newInstance();
    private MessageManager messageManager = MessageManager.newInstance();
    private UserManager userManager = UserManager.newInstance();
    EditText message_ ;
    private Button send ;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore ;
    private String userID ;
    private String statut_user ;
    private User online_user = new User ();
    private String online_user_prenom ;


    @Nullable
    @Override

    public View onCreateView(@Nonnull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater , container , savedInstanceState);
        final View view = inflater.inflate(R.layout.professeur_item_detail_layout , container, false);
        Bundle bundle = this.getArguments();
       if (bundle != null) {
           online_user_prenom= bundle.getString("online_user_prenom");
            String nom = bundle.getString(ProfesseurDB.FIELD_NOM);
            String prenom = bundle.getString(ProfesseurDB.FIELD_PRENOM);
           Log.d("ContactListMainActivity", "prenom du prof sur lequel nous avons cliqué" + prenom);
            String departement = bundle.getString(ProfesseurDB.FIELD_DEPARTEMENT);
            String id_professeur = bundle.getString(ProfesseurDB.DOCUMENT_ID);
            String id_user = bundle.getString(ProfesseurDB.FIELD_USER_ID);
            String statut_user = bundle.getString(UserDB.FIELD_STATUT);
         String online_user_id = bundle.getString("online_user_id");
            String online_user_nom = bundle.getString("online_user_nom");

            online_user.setPrenom(online_user_prenom);
            online_user.setNom(online_user_nom);
            online_user.setUser_id(online_user_id);


            prof.setUser_id(id_user);
            prof.setNom(nom);
            prof.setPrenom(prenom);
            prof.setDepartement(departement);
            prof.setId_professeur(id_professeur);
           Log.d("ContactListMainActivity", "" + online_user_prenom + "sends to" + prof.getPrenom());
            String full_name =  prof.getNom() +  prof.getPrenom() ;
            message_ = view.findViewById(R.id.message);
            full_name_ = view.findViewById(R.id.name);
            departement_ = view.findViewById(R.id.departement);
            full_name_.setText(full_name);
            departement_.setText(prof.getDepartement());
           delete = view.findViewById(R.id.delete);
           if(statut_user.equals("Professeur") || statut_user.equals("Etudiant")){
               delete.setVisibility(View.GONE);
           }
           else {
               delete.setOnClickListener(new DeleteOnClickListener());

           }
           Log.d("ContactListMainActivity", "" + prof.getId_professeur());
           send = view.findViewById(R.id.send);
           send.setOnClickListener(new SendOnClickListener());

        }


        return view ;

    }

     class DeleteOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("ContactListMainActivity", "" + prof.getId_professeur());
            professeurManager.deleteDocument(prof.getId_professeur());
            Intent intent = new Intent(getActivity(), ProfessorListActivity.class);
            startActivity(intent);
        }
    }

    private class SendOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
           String text_message=message_.getText().toString().trim();

            fAuth = FirebaseAuth.getInstance();
            fstore = FirebaseFirestore.getInstance();
            String user_id_sender =  fAuth.getCurrentUser().getUid();
            Message message = new Message();
            message.setStatut_message("Private");
            message.setUser_id_reciever(prof.getUser_id());
            message.setUser_id_sender(user_id_sender);
            message.setNom_sender(online_user.getNom());
            Log.d("ContactListMainActivity", "" + online_user.getNom());
            message.setPrenom_sender(online_user.getPrenom());
            message.setNom_reciever(prof.getNom());
            message.setPrenom_reciever(prof.getPrenom());
            message.setText_message(text_message);
            message.setPrenom(online_user.getPrenom());
            message.setNom(online_user.getNom());
            messageManager.createDocument(message);
            Intent intent = new Intent(getActivity(),   MessagesListActivity.class);
            intent.putExtra(FIELD_STATUT_MESSAGE , message.getStatut_message());
            startActivity(intent);
        }
    }
}
