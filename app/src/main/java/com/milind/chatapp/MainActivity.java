package com.milind.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.milind.chatapp.adapter.MessageListAdapter;
import com.milind.chatapp.model.Message;
import com.milind.chatapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> mMessageList;
    private User selfUser, botUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageList = new ArrayList<>();

        initializeMessageList();

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mMessageRecycler.setAdapter(mMessageAdapter);

    }

    private void initializeMessageList() {

        /**
         * Initialized the users
         */

        selfUser = new User("101", "milind", "https://randomuser.me/api/portraits/thumb/men/65.jpg");
        botUser = new User("102", "bot", "https://randomuser.me/api/portraits/thumb/men/65.jpg");

        if (mMessageList != null) {

            /**
             * Initialized messages botMessages and userMessages
             */

            Message botMessage = new Message("Q101", "What's your full name?", botUser, "11:28 am");
            Message userMessage = new Message("A101", "Milind Prakash Amrutkar", selfUser, "11:30 am");
            Message botMessage1 = new Message("Q102", "What's your age?", botUser, "11:30 am");
            Message userMessage1 = new Message("A102", "18", selfUser, "11:32 am");

            mMessageList.add(botMessage);
            mMessageList.add(userMessage);
            mMessageList.add(botMessage1);
            mMessageList.add(userMessage1);


        }
    }

}
