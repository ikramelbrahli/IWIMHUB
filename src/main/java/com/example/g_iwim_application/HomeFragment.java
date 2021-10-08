package com.example.g_iwim_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HomeFragment extends Fragment {
   public View onCreateView(@Nonnull LayoutInflater inflater , @javax.annotation.Nullable ViewGroup container , @Nullable Bundle savedInstanceState){
       super.onCreateView(inflater , container , savedInstanceState);
       return inflater.inflate(R.layout.activity_menu_chef , container , false) ;
   }
}
