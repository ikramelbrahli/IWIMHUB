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

import com.example.g_iwim_application.Models.Etudiant;
import com.example.g_iwim_application.R;
import com.example.g_iwim_application.StudentsListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import com.squareup.picasso.Picasso;


public class RecyclerViewAdapterEtudiant  extends RecyclerView.Adapter<RecyclerViewAdapterEtudiant.ViewHolder> {
    private static final String TAG = null ;
    private List etudiantList;
    private StudentsListActivity.GetAllEtudiantsOnCompleteListener.EtudiantListRecyclerViewOnItemClickListener etudiantListRecyclerViewOnItemClickListener;
    private Context context ;

    public RecyclerViewAdapterEtudiant(List etudiantList, StudentsListActivity.GetAllEtudiantsOnCompleteListener.EtudiantListRecyclerViewOnItemClickListener etudiantListRecyclerViewOnItemClickListener) {
        this.etudiantList = etudiantList;
        this.etudiantListRecyclerViewOnItemClickListener = etudiantListRecyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_list, parent, false);
        itemView.setOnClickListener((View.OnClickListener) etudiantListRecyclerViewOnItemClickListener);

        return new RecyclerViewAdapterEtudiant.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant etudiant = (Etudiant) etudiantList.get(position);


        String full_name = etudiant.getNom()+"  " + etudiant.getPrenom() ;
        Log.d("Recycler", "inbind" + full_name );
        holder.full_name_.setText(full_name);
        holder.CNE.setText(etudiant.getCNE());

        Picasso.get()
                .load(etudiant.getImageURI())
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
        return etudiantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView full_name_;
        TextView CNE ;
        FloatingActionButton floatingActionButton3;
        ImageView image_url;



        ViewHolder(View itemView) {

            super(itemView);
            full_name_ = itemView.findViewById(R.id.textView1);
            CNE = itemView.findViewById(R.id.textView2);
            floatingActionButton3=itemView.findViewById(R.id.floatingActionButton3);
            floatingActionButton3.setVisibility(View.GONE);
            image_url= itemView.findViewById(R.id.image);
        }
    }
}
