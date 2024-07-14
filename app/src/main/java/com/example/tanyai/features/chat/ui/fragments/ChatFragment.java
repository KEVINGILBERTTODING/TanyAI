package com.example.tanyai.features.chat.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyai.BuildConfig;
import com.example.tanyai.R;
import com.example.tanyai.core.models.PromptModel;
import com.example.tanyai.core.models.ResultModel;
import com.example.tanyai.databinding.FragmentChatFragmentBinding;
import com.example.tanyai.features.chat.ui.adapters.ChatAdapter;
import com.example.tanyai.features.chat.ui.widgets.CaloriesBottomSheet;
import com.example.tanyai.util.constants.ai.ConstantsAi;
import com.example.tanyai.util.date.DateUtil;
import com.example.tanyai.util.keyboard.KeyboardUtils;
import com.example.tanyai.util.toast.ToastUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatFragment extends Fragment {

    private FragmentChatFragmentBinding binding;
    private List<PromptModel> promptModelList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private GenerativeModel generativeModel;
    private GenerativeModelFutures modelFutures;
    private Dialog progressDialog;
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
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new Dialog(requireContext());
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    private void listener() {

        binding.btnSend.setOnClickListener(v -> promptValidate());
        binding.btnCalories.setOnClickListener(v -> {
            ImagePicker.with(requireActivity())
                    .cameraOnly()
                    .createIntent(intent -> {
                        startForProfileImageResult.launch(intent);
                        return null;
                    });


        });


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
        storeToModel(promptModel.getText(), null, false);


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

    private void storeToModel(String prompt, Bitmap bitmap, boolean isCountCalories) {


        if (isCountCalories) {
            Content content = new Content.Builder()
                    .addText(prompt)
                    .addImage(bitmap)
                    .build();
            Executor executor = Executors.newSingleThreadExecutor();
            ListenableFuture<GenerateContentResponse> response = modelFutures.generateContent(content);
            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {

                @Override
                public void onSuccess(GenerateContentResponse result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            binding.cvCalories.setVisibility(View.VISIBLE);

                            String resultText = result.getText();
//                            ToastUtil.showToast(resultText, requireContext());
                            setDataCalories(resultText);


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
                                progressDialog.dismiss();

                                ToastUtil.showToast("Terjadi kesalahan", getActivity());

                                binding.cvCalories.setVisibility(View.VISIBLE);



                            }
                        }
                    });

                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            }, executor);

        } else {
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

    private void setDataCalories(String resultText) {
        ResultModel resultModel = parseResultModel(resultText);
        if (resultModel != null) {
            CaloriesBottomSheet caloriesBottomSheet = CaloriesBottomSheet.newInstance(resultModel);
            caloriesBottomSheet.show(requireActivity().getSupportFragmentManager(), caloriesBottomSheet.getTag());
        }else {
            ToastUtil.showToast("Terjadi kesalahan", requireContext());
        }

    }

    public static ResultModel parseResultModel(String info) {
      try {
          String[] parts = info.split("\\|");
          String nama = parts[0].trim();
          String kalori = parts[1].replace("Kalori", "").trim();
          String gula = parts[2].replace("Gula", "").trim();
          String catatan = parts[3].replace("Catatan", "").trim();
          String saran = parts[4].replace("Saran", "").trim();

          return new ResultModel(nama, kalori, gula, catatan, saran);
      }catch (Exception e){
          Log.d("TAG", "parseResultModel: " + e.getMessage());
          return null;
      }
    }

    private void promptCaloriesValdation(Bitmap bitmap, String prompt) {
        if (bitmap == null) {
            ToastUtil.showToast("Gambar tidak valid", requireContext());

            return;
        }

        if (prompt == null || prompt.isEmpty()) {
            ToastUtil.showToast("Prompt invalid", requireContext());
            return;
        }
        progressDialog.dismiss();
        binding.cvCalories.setVisibility(View.GONE);

        progressDialog.show();
        storeToModel(prompt, bitmap, true);
    }

    private final ActivityResultLauncher<Intent> startForProfileImageResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri fileUri = data.getData();

                    if (fileUri != null) {
                        try {
                            String prompt = "itu makanan apa dan berapa kalorinya dan kandungan gulanya, tidak perlu penjelasan langsung nama makanannya, kalori dan gula, contoh hasilnya: indomie goreng | Kalori  | Gula  | Catatan berikan noted tentang makanana itu | Saran berikan saran tentang makanan itu";
                            Bitmap bitmap = uriToBitmap(fileUri);
                            promptCaloriesValdation(bitmap, prompt);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                } else if (resultCode == ImagePicker.RESULT_ERROR) {


                    ToastUtil.showToast("Gagal mengambil gambar", requireContext());

                } else {
                    ToastUtil.showToast("Terjadi kesalahan", requireContext());

                }
            });

    private Bitmap uriToBitmap(Uri uri) throws IOException {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return bitmap;
    }






}