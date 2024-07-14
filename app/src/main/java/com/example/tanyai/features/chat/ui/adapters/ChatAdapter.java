package com.example.tanyai.features.chat.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tanyai.R;
import com.example.tanyai.core.models.PromptModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<PromptModel> promptModelList;

    public ChatAdapter(Context context, List<PromptModel> promptModelList) {
        this.context = context;
        this.promptModelList = promptModelList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        PromptModel promptModel = promptModelList.get(holder.getAdapterPosition());
        holder.rlSender.setVisibility(View.GONE);
        holder.rlReceiver.setVisibility(View.GONE);
        holder.cvIvPrompt.setVisibility(View.GONE);


        if (promptModel.isMe()) {
            holder.rlSender.setVisibility(View.VISIBLE);
            holder.tvTextSender.setText(promptModel.getText());
            holder.getTvTimeStampSender.setText(promptModel.getTimeStamp());
        }else {
            holder.rlReceiver.setVisibility(View.VISIBLE);
            holder.tvTextReceiver.setText(promptModel.getText());
            holder.tvTimeStampReceiver.setText(promptModel.getTimeStamp());
        }

        if (promptModel.getBitmap() != null) {
            holder.cvIvPrompt.setVisibility(View.VISIBLE);
            holder.ivPrompt.setImageBitmap(promptModel.getBitmap());

        }

    }

    @Override
    public int getItemCount() {
        return promptModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTextReceiver, tvTextSender, tvTimeStampReceiver,
        getTvTimeStampSender;
        private ImageView ivPrompt;
        private CardView cvIvPrompt;
        private RelativeLayout rlReceiver, rlSender;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTextReceiver = itemView.findViewById(R.id.tvTextReceiver);
            tvTextSender = itemView.findViewById(R.id.tvTextSender);
            tvTimeStampReceiver = itemView.findViewById(R.id.tvTimeStampReceiver);
            getTvTimeStampSender = itemView.findViewById(R.id.tvTimeStampSender);
            rlReceiver = itemView.findViewById(R.id.rlReceiver);
            rlSender = itemView.findViewById(R.id.rlSender);
            ivPrompt = itemView.findViewById(R.id.ivPrompt);
            cvIvPrompt = itemView.findViewById(R.id.cvIvPrompt);
        }
    }
}
