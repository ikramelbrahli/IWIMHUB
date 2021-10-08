package com.example.g_iwim_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.g_iwim_application.Manager.EtudiantDB;
import com.example.g_iwim_application.Manager.EtudiantManager;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Manager.UserDB;
import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Professeur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EtudiantDetailsFragment extends DialogFragment {
    private Etudiant etudiant = new Etudiant() ;
    TextView full_name_, CNE_;
    private FloatingActionButton delete ;
    private EtudiantManager etudiantManager = EtudiantManager.newInstance();
    private String statut_user ;
    @Nullable
    @Override

    public View onCreateView(@Nonnull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater , container , savedInstanceState);
        final View view = inflater.inflate(R.layout.etudiant_item_detail_layout , container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String name = bundle.getString("EtudiantDB.FIELD_NOM");
            String prenom = bundle.getString("EtudiantDB.FIELD_PRENOM");

            String CNE = bundle.getString(EtudiantDB.FIELD_CNE);
            String id_etudiant = bundle.getString(EtudiantDB.DOCUMENT_ID);
            String statut_user = bundle.getString(UserDB.FIELD_STATUT);
            Log.d("ContactListMainActivity", "prenom de etudiant sur lequel nous avons cliqué" + id_etudiant);
            etudiant.setNom(name);
            etudiant.setPrenom(prenom);
            etudiant.setCNE(CNE);
            etudiant.setId_etudiant(id_etudiant);
            String full_name =  etudiant.getNom() +  etudiant.getPrenom() ;
            full_name_ = view.findViewById(R.id.name);
            CNE_ = view.findViewById(R.id.CNE);
            full_name_.setText(full_name);
            CNE_.setText(etudiant.getCNE());
            delete = view.findViewById(R.id.delete);
            if(statut_user.equals("Professeur") || statut_user.equals("Etudiant")){
                delete.setVisibility(View.GONE);
            }
            else {
                delete.setOnClickListener(new EtudiantDetailsFragment.DeleteOnClickListener());
            }
        }
        return view ;
    }
    class DeleteOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Log.d("ContactListMainActivity", "" + prof.getId_professeur());
            etudiantManager.deleteDocument(etudiant.getId_etudiant());
            Intent intent = new Intent(getActivity(), StudentsListActivity.class);
            startActivity(intent);
        }
    }
}
