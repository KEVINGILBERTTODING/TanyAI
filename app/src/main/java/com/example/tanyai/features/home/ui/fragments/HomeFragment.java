package com.example.tanyai.features.home.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyai.R;
import com.example.tanyai.core.models.UserSharedModel;
import com.example.tanyai.core.services.UserServices;
import com.example.tanyai.databinding.FragmentHomeBinding;
import com.example.tanyai.features.chat.ui.fragments.ChatFragment;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserServices userServices;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();
        listener();

        return binding.getRoot();
    }

    private void init() {
        initUserService();
    }

    private void initUserService() {
        userServices = new UserServices();
        userServices.initilizeUserService(getContext());
        try {
            validateUserData(userServices.getUserInfo());
        }catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void validateUserData(UserSharedModel userSharedModel) {
        if (!userSharedModel.isFirstTime()) {
            fragmentTransaction(new ChatFragment());

        }

    }

    private void listener() {
        binding.btnGo.setOnClickListener(v -> {
            UserSharedModel userSharedModel = new UserSharedModel(
                    false
            );
            userServices.saveUserInfo(userSharedModel);
            fragmentTransaction(new ChatFragment());
        });
    }

    private void fragmentTransaction(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.frameMain, fragment)
                .commit();
    }
}

