package com.example.tanyai.features.chat.ui.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tanyai.R;
import com.example.tanyai.core.models.ResultModel;
import com.example.tanyai.util.listeners.ItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImagePickerOption extends BottomSheetDialogFragment {
    private ItemClickListener itemClickListener;


    public static ImagePickerOption newInstance() {
        ImagePickerOption fragment = new ImagePickerOption();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_image_options, container, false);

        ImageButton btnGallery, btnCamera;

        btnGallery = view.findViewById(R.id.btnGallery);
        btnCamera = view.findViewById(R.id.btnCamera);

        btnGallery.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.setItemClickListener(0, "gallery", null);
            }
        });

        btnCamera.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.setItemClickListener(0, "camera", null);
            }
        });



        return view;
    }
}
