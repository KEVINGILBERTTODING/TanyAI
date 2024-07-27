package com.example.tanyai.features.home.ui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tanyai.BuildConfig;
import com.example.tanyai.R;
import com.example.tanyai.features.home.ui.fragments.HomeFragment;
import com.example.tanyai.util.constants.ai.ConstantsAi;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentTransaction(new HomeFragment());




    }

    private void fragmentTransaction(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, fragment)
                .commit();
    }



}