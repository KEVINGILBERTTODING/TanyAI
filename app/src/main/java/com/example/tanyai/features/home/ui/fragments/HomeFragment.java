package com.example.tanyai.features.home.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyai.R;
import com.example.tanyai.databinding.FragmentHomeBinding;
import com.example.tanyai.features.chat.ui.fragments.ChatFragment;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        listener();

        return binding.getRoot();
    }

    private void listener() {
        binding.btnGo.setOnClickListener(v ->fragmentTransaction(new ChatFragment()));
    }

    private void fragmentTransaction(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.frameMain, fragment)
                .commit();
    }
}

