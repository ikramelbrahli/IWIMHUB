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

import com.example.g_iwim_application.Manager.MessageDB;
import com.example.g_iwim_application.Manager.MessageManager;
import com.example.g_iwim_application.Manager.ProfesseurDB;
import com.example.g_iwim_application.Manager.ProfesseurManager;
import com.example.g_iwim_application.Models.Message;
import com.example.g_iwim_application.Models.Professeur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.example.g_iwim_application.Manager.MessageDB.FIELD_STATUT_MESSAGE;

public class MessageForm extends DialogFragment {

    private Message message = new Message() ;
    EditText message_content ;
    String text_message ;
    private Button send ;
    private MessageManager messageManager = MessageManager.newInstance();

    @Nullable
    @Override

    public View onCreateView(@Nonnull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater , container , savedInstanceState);
        final View view = inflater.inflate(R.layout.message_form , container, false);
        Bundle bundle = this.getArguments();
        message_content = view.findViewById(R.id.message);
        send = view.findViewById(R.id.send);

        if (bundle != null) {
            String name = bundle.getString(MessageDB.FIELD_NOM);
            String prenom = bundle.getString(MessageDB.FIELD_PRENOM);
            String user_id= bundle.getString(MessageDB.FIELD_USER_ID);
            String statut = bundle.getString(MessageDB.FIELD_STATUT_MESSAGE);

            message.setNom(name);
            message.setPrenom(prenom);
            message.setUser_id(user_id);
            message.setStatut_message(statut);

            send.setOnClickListener(new MessageForm.SendOnClickListener());
           // Log.d("ContactListMainActivity", "" + prof.getId_professeur());

        }

        return view ;

    }

    private class SendOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            text_message=message_content.getText().toString().trim();
            message.setText_message(text_message);
            messageManager.createDocument(message);
            Intent intent = new Intent(getActivity(), MenuChefActivity.class);
            intent.putExtra(FIELD_STATUT_MESSAGE , message.getStatut_message());
            startActivity(intent);

        }
    }
}
