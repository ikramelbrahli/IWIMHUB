package com.example.g_iwim_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.g_iwim_application.Manager.MessageDB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MessageType extends DialogFragment {
    private String statut_message ;
    private String user_id ;
    private Button private_mess_recieved ;
    private Button private_mess_sent ;
    private Button public_mess ;
    private MessageDB mdb = new MessageDB();
    @Nullable
    @Override

    public View onCreateView(@Nonnull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.message_type, container, false);
        public_mess = view.findViewById(R.id.public_message);
        public_mess.setOnClickListener(new MessageType.PublicMessageOnClickListener());
        private_mess_recieved = view.findViewById(R.id.recieved);
        private_mess_recieved.setOnClickListener(new MessageType.PrivateReceivedMessageOnClickListener());
        private_mess_sent = view.findViewById(R.id.sent);
        private_mess_sent.setOnClickListener(new MessageType.PrivateSentMessageOnClickListener());
        return view ;
    }
    class PublicMessageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MessagesListActivity.class);
            statut_message = "Publique";
            intent.putExtra(mdb.FIELD_STATUT_MESSAGE , statut_message);
            startActivity(intent);
        }
    }

     class PrivateReceivedMessageOnClickListener implements View.OnClickListener {
         @Override
         public void onClick(View v) {
             statut_message="recu" ;
             Intent intent = new Intent(getActivity(), MessagesListActivity.class);
             intent.putExtra(mdb.FIELD_STATUT_MESSAGE , statut_message);
             startActivity(intent);
         }
     }
    class PrivateSentMessageOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            statut_message="sent" ;
            Log.d("HotelListMainActivity", "inside sent listener");
            Intent intent = new Intent(getActivity(), MessagesListActivity.class);
            intent.putExtra(mdb.FIELD_STATUT_MESSAGE , statut_message);
            startActivity(intent);
        }
    }
}
