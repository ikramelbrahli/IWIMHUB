package com.example.g_iwim_application.Manager;

import android.util.Log;

import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.Models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageManager {
    private static MessageManager messageManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference messageCollectionReference;
    public static final String COLLECTION_NAME = "Message";

    public static MessageManager newInstance() {
        if (messageManager == null) {
            messageManager = new MessageManager();
        }
        return messageManager;
    }
    private MessageManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        messageCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }
    public void getAllPublicMessages(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        Log.d("HotelListMainActivity", "done2" + "workpls");
        messageCollectionReference.whereEqualTo("statut_message" , "Publique").get().addOnCompleteListener(onCompleteListener);
    }

   public void getAllSentPrivateMessages(OnCompleteListener<QuerySnapshot> onCompleteListener , String online_user_id)
    {
        Log.d("HotelListMainActivity", "done2" + "workpls");
        messageCollectionReference.whereEqualTo("statut_message","Private").whereEqualTo("user_id_sender",online_user_id).get().addOnCompleteListener(onCompleteListener);

    }

    public void getAllReceivedPrivateMessages(OnCompleteListener<QuerySnapshot> onCompleteListener , String online_user_id)
    {
        Log.d("HotelListMainActivity", "done2" + "workpls");
        messageCollectionReference.whereEqualTo("statut_message","Private").whereEqualTo("user_id_reciever",online_user_id).get().addOnCompleteListener(onCompleteListener);

    }



    public void createDocument(Message message) {
        messageCollectionReference.add(message);
    }
    public void deleteDocument(String documentId) {
        DocumentReference documentReference = messageCollectionReference.document(documentId);
        documentReference.delete();
    }
}
