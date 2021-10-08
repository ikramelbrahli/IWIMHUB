package com.example.g_iwim_application.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_iwim_application.Models.Professeur;
import com.example.g_iwim_application.ProfessorListActivity;
import com.example.g_iwim_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterProf extends RecyclerView.Adapter<RecyclerViewAdapterProf.ViewHolder> {
    private static final String TAG = null ;
    private List profList;
    private ProfessorListActivity.GetAllProfesseursOnCompleteListener.ProfessorListRecyclerViewOnItemClickListener professeurListRecyclerViewOnItemClickListener;
    private Context context ;
    public RecyclerViewAdapterProf(List profList, ProfessorListActivity.GetAllProfesseursOnCompleteListener.ProfessorListRecyclerViewOnItemClickListener professeurListRecyclerViewOnItemClickListener) {
        this.profList = profList;
        this.professeurListRecyclerViewOnItemClickListener = professeurListRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterProf.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_list, parent, false);
        itemView.setOnClickListener((View.OnClickListener) professeurListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterProf.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterProf.ViewHolder holder, int position) {
        Professeur professeur = (Professeur) profList.get(position);


        String full_name = professeur.getNom()+ " " +professeur.getPrenom() ;
        Log.d("Recycler", "inbind" + full_name );
        holder.full_name_.setText(full_name);
        holder.departement.setText(professeur.getDepartement());
        Picasso.get()
                .load(professeur.getImageURI())
                .into(holder.image_url , new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // will load image
                    }

                    @Override
                    public void onError(Exception e) {

                    }



                });

    }

    @Override
    public int getItemCount() {
        return profList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView full_name_;
        TextView departement ;
        FloatingActionButton floatingActionButton3;
        ImageView image_url;

        ViewHolder(View itemView) {

            super(itemView);

            full_name_ = itemView.findViewById(R.id.textView1);
            departement = itemView.findViewById(R.id.textView2);
            floatingActionButton3=itemView.findViewById(R.id.floatingActionButton3);
            floatingActionButton3.setVisibility(View.GONE);
            image_url= itemView.findViewById(R.id.image);

        }
    }
}
