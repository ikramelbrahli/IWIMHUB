package com.example.g_iwim_application.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_iwim_application.Manager.MessageManager;
import com.example.g_iwim_application.MessagesListActivity;
import com.example.g_iwim_application.Models.Message;
import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerViewAdapterMessage extends RecyclerView.Adapter<RecyclerViewAdapterMessage.ViewHolder> {
private static final String TAG = null ;
private List messageList;
private String statut_message;

private MessageManager messageManager = MessageManager.newInstance();
private MessagesListActivity.GetAllMessagesOnCompleteListener.MessageListRecyclerViewOnItemClickListener messageListRecyclerViewOnItemClickListener;
private Context context ;
public RecyclerViewAdapterMessage(List messageList, MessagesListActivity.GetAllMessagesOnCompleteListener.MessageListRecyclerViewOnItemClickListener messageListRecyclerViewOnItemClickListener , String statut_message ) {
        this.messageList = messageList;
        this.messageListRecyclerViewOnItemClickListener = messageListRecyclerViewOnItemClickListener;
        this.statut_message = statut_message;

        }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);
        holder.imgv.setImageResource(R.drawable.ic_baseline_message_24);
        if(statut_message.equals("recu")){
            Log.d("HotelListMainActivity", "inside bin recu" + statut_message);
            String full_name = message.getNom_sender()+message.getPrenom_sender();
            holder.full_name_.setText(full_name);
            holder.message_text.setText(message.getText_message());
        }
        if(statut_message.equals("sent")){
            Log.d("HotelListMainActivity", "inside bind sent" + statut_message);
            String full_name = message.getNom_reciever()+message.getPrenom_reciever();
            holder.full_name_.setText(full_name);
            holder.message_text.setText(message.getText_message());
            Log.d("HotelListMainActivity", "inside bind sent"  + message.getNom_reciever());
        }
        if(statut_message.equals("Publique")) {

            Log.d("HotelListMainActivity", "inside publoc bind" + statut_message);
            String full_name = message.getNom() +" " + message.getPrenom();
            Log.d("Recycler", "inbind" + full_name);
            holder.full_name_.setText(full_name);
            holder.message_text.setText(message.getText_message());
        }
        else{
            Log.d("HotelListMainActivity", "inside else bind" + statut_message);
            if(statut_message.equals("recu")){
                Log.d("HotelListMainActivity", "equals recu");
            }
            if(statut_message.equals("sent")){
                Log.d("HotelListMainActivity", "equals sent");
            }
            else if(statut_message.equals("Publique")){
                Log.d("HotelListMainActivity", "equals public");
            }
        }


            holder.fab.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    @NonNull
    @Override
    public RecyclerViewAdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_list, parent, false);
        itemView.setOnClickListener((View.OnClickListener) messageListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterMessage.ViewHolder(itemView);
    }
    class ViewHolder extends RecyclerView.ViewHolder {


        TextView full_name_;
        TextView message_text ;
        ImageView imgv;

        private FloatingActionButton fab ;

        ViewHolder(View itemView) {

            super(itemView);
            imgv = itemView.findViewById(R.id.image);
            full_name_ = itemView.findViewById(R.id.textView1);
            message_text = itemView.findViewById(R.id.textView2);
            fab = itemView.findViewById(R.id.floatingActionButton3);

        }
    }
}
