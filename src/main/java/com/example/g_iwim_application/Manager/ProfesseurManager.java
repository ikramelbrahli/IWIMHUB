package com.example.g_iwim_application.Manager;

import android.util.Log;

import com.example.g_iwim_application.Models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfesseurManager {
    private static ProfesseurManager professeurManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference professeurCollectionReference;
    public static final String COLLECTION_NAME = "Professeur";

    public static ProfesseurManager newInstance() {
        if (professeurManager == null) {
            professeurManager = new ProfesseurManager();
        }
        return professeurManager;
    }
    private ProfesseurManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        professeurCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllProfesseurs(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        Log.d("HotelListMainActivity", "done2" + "workpls");

        professeurCollectionReference.get().addOnCompleteListener(onCompleteListener);

    }
    public void createDocument(Professeur prof) {
        professeurCollectionReference.add(prof);
    }

    public void deleteDocument(String documentId) {
        DocumentReference documentReference = professeurCollectionReference.document(documentId);
        documentReference.delete();
    }

}
