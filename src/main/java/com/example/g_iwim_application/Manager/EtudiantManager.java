package com.example.g_iwim_application.Manager;

import android.util.Log;

import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EtudiantManager {
    private static EtudiantManager etudiantManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference etudiantCollectionReference;
    public static final String COLLECTION_NAME = "Etudiant";

    public static EtudiantManager newInstance() {
        if (etudiantManager == null) {
            etudiantManager = new EtudiantManager();
        }
        return etudiantManager;
    }
    private EtudiantManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        etudiantCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllEtudiants(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        Log.d("HotelListMainActivity", "done2" + "workpls");
        etudiantCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }
    public void createDocument(Etudiant etudiant) {
        etudiantCollectionReference.add(etudiant);
    }
    public void deleteDocument(String documentId) {
        DocumentReference documentReference = etudiantCollectionReference.document(documentId);
        documentReference.delete();
    }

}
