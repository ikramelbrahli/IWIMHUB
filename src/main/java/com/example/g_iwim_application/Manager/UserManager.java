package com.example.g_iwim_application.Manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.g_iwim_application.Models.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserManager {
    private static UserManager userManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference userCollectionReference;
    public static final String COLLECTION_NAME = "User";

    public static UserManager newInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }
    private UserManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        userCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getUser(String id)
    {

        Log.d("HotelListMainActivity", "done2" + "workpls");
        DocumentReference docRef = userCollectionReference .document("id");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                      User user = new User();
                      user.setNom(document.getString("nom"));
                      user.setPrenom(document.getString("prenom"));


                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

    }
    public void createDocument(User user) {
        userCollectionReference.add(user);
    }
    public void deleteDocument(String documentId) {
        DocumentReference documentReference = userCollectionReference.document(documentId);
        documentReference.delete();
    }
}
