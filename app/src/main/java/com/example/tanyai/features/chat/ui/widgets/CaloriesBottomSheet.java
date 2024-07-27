package com.example.tanyai.features.chat.ui.widgets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tanyai.R;
import com.example.tanyai.core.models.ResultModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CaloriesBottomSheet extends BottomSheetDialogFragment {
    private ResultModel resultModel;

    public static CaloriesBottomSheet newInstance(ResultModel resultModel) {
        CaloriesBottomSheet fragment = new CaloriesBottomSheet();
        fragment.resultModel = resultModel;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_calories, container, false);
        TextView tvName, tvDesc, tvTotalCalories, tvTotalSugar, tvSaran;

        tvName = view.findViewById(R.id.tvName);
        tvDesc = view.findViewById(R.id.tvDesc);
        tvTotalCalories = view.findViewById(R.id.tvTotalCalories);
        tvTotalSugar = view.findViewById(R.id.tvTotalSugar);
        tvSaran = view.findViewById(R.id.tvSaran);

        tvName.setText(resultModel.getName());
        tvDesc.setText(resultModel.getCatatan());
        tvTotalCalories.setText(resultModel.getKalori());
        tvTotalSugar.setText(resultModel.getGula());
        tvSaran.setText(resultModel.getSaran());

        return view;
    }
}
