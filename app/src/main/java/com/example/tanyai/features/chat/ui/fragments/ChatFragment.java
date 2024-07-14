package com.example.tanyai.features.chat.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tanyai.BuildConfig;
import com.example.tanyai.R;
import com.example.tanyai.core.models.PromptModel;
import com.example.tanyai.databinding.FragmentChatFragmentBinding;
import com.example.tanyai.features.chat.ui.adapters.ChatAdapter;
import com.example.tanyai.util.constants.ai.ConstantsAi;
import com.example.tanyai.util.date.DateUtil;
import com.example.tanyai.util.keyboard.KeyboardUtils;
import com.example.tanyai.util.toast.ToastUtil;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatFragment extends Fragment {

    private FragmentChatFragmentBinding binding;
    private List<PromptModel>  promptModelList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private GenerativeModel generativeModel;
    private GenerativeModelFutures modelFutures;
    private String TAG = ChatFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatFragmentBinding.inflate(inflater, container, false);
        init();
        listener();
        showChat();
        return binding.getRoot();
    }

    private void init() {

        generativeModel = new GenerativeModel(ConstantsAi.AI_MODEL, BuildConfig.apiKey);
        modelFutures = GenerativeModelFutures.from(generativeModel);
        binding.tvGreeting.setText(DateUtil.getGreeting());
    }

    private void listener() {

        binding.btnSend.setOnClickListener(v -> promptValidate());

    }

    private void promptValidate() {
        String text = binding.etText.getText().toString();
        String timeStamp = DateUtil.getCurrentDateTime();
        if (text.isEmpty()) {

            return;
        }
        if (timeStamp == null || timeStamp.isEmpty()) {
            ToastUtil.showToast("Tanggal tidak valid", requireContext());
            return;
        }
        PromptModel promptModel = new PromptModel(
                text,
                true,
                timeStamp
        );


        resetState();

        storePromptToList(promptModel);
        storeToModel(promptModel.getText());


    }

    private void storePromptToList(PromptModel promptModel) {
        promptModelList.add(promptModel);
        chatAdapter.notifyDataSetChanged();
    }

    private void resetState() {
        binding.etText.setText("");
        KeyboardUtils.hideKeyboard(requireActivity());
        binding.mainProgressBar.setVisibility(View.VISIBLE);

        binding.cvSend.setVisibility(View.GONE);

        binding.lrEmpty.setVisibility(View.GONE);
        binding.mainNestedScroll.post(new Runnable() {
            @Override
            public void run() {
                binding.mainNestedScroll.smoothScrollTo(0, binding.mainNestedScroll.getChildAt(0).getHeight());
            }
        });
    }

    private void showChat() {
        chatAdapter = new ChatAdapter(requireContext(), promptModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rv.setAdapter(chatAdapter);
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setHasFixedSize(true);
    }

    private void storeToModel(String prompt) {


        Content content = new Content.Builder()
                .addText(prompt)
                .build();


        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = modelFutures.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {

            @Override
            public void onSuccess(GenerateContentResponse result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.mainProgressBar.setVisibility(View.GONE);
                        binding.cvSend.setVisibility(View.VISIBLE);

                        String resultText = result.getText();

                        PromptModel promptModel = new PromptModel(
                                resultText,
                                false,
                                DateUtil.getCurrentDateTime()
                        );

                        storePromptToList(promptModel);
                        System.out.println(resultText);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null) {
                            ToastUtil.showToast("Terjadi kesalahan", getActivity());
                            binding.cvSend.setVisibility(View.VISIBLE);
                            binding.mainProgressBar.setVisibility(View.GONE);


                        }
                    }
                });

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        }, executor);
    }


}