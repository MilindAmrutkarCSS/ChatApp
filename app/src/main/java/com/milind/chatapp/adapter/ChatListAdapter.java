package com.milind.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.milind.chatapp.R;
import com.milind.chatapp.activity.ChatActivity;
import com.milind.chatapp.model.Chat;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;


/**
 * Created by Milind Amrutkar on 5/8/2019.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>{

    private static final String TAG = "ChatListAdapter";
    private Context context;
    private RealmList<Chat> chatList;

    public ChatListAdapter(Context context, RealmList<Chat> chatRealmList) {
        this.context = context;
        this.chatList = chatRealmList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_messages_single_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.btnChat.setText(chatList.get(position).getChatId());
        holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        Button btnChat;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            btnChat = itemView.findViewById(R.id.btnChat);
        }

    }
}
