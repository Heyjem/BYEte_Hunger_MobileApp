package com.example.byete_hunger_mobileapp;

import android.content.Context;
import android.media.Image;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.byete_hunger_mobileapp.Chat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    ArrayList<Chat> list;

    public ChatAdapter(Context context, ArrayList<Chat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_message, parent, false);
        return new ChatAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        Chat chat = list.get(position);



        String dateF = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(chat.getTimestmap()));
        holder.textDateTime.setText(dateF);
        if (chat.getFrom() == 0) {
            holder.textMessage.setText(chat.getMessage());
            holder.textMessage.setVisibility(View.VISIBLE);

            holder.textSender.setVisibility(View.INVISIBLE);
            holder.profile.setVisibility(View.INVISIBLE);

        } else {
            holder.textSender.setText(chat.getMessage());
            holder.textSender.setVisibility(View.VISIBLE);
            holder.profile.setVisibility(View.VISIBLE);

            holder.textMessage.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage, textDateTime, textSender;
        ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);

            textSender = itemView.findViewById(R.id.textMessage2);
            profile = itemView.findViewById(R.id.imageProfile2);

        }

    }
}

