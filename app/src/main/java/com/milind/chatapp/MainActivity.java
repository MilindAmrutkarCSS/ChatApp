package com.milind.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.milind.chatapp.adapter.MessageListAdapter;
import com.milind.chatapp.model.BotQuestions;
import com.milind.chatapp.model.Message;
import com.milind.chatapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> mMessageList;
    private User selfUser, botUser;
    private Button btnSend;
    private EditText etMessage;
    ArrayList<String> questions;
    BotQuestions botQuestions;

    // position saves the current position of mMessageList.
    int position = 0;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageList = new ArrayList<>();

        initializeViews();

        initializeMessageList();

        botQuestions = new BotQuestions();
        botQuestions.setQuestions();
        questions = botQuestions.getQuestions();

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mMessageRecycler.setAdapter(mMessageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfUser = new User("102", "Me", "https://randomuser.me/api/portraits/thumb/men/65.jpg");
                String usersReply = etMessage.getText().toString();
                Message userMessage;
                if (!TextUtils.isEmpty(usersReply)) {
                    userMessage = new Message("A102", etMessage.getText().toString(), selfUser, "1:17");
                    mMessageList.add(userMessage);

                    // Check to see if there are no more questions in the questions ArrayList
                    if (position == questions.size()) {
                        generateJson();

                    } else {
                        Log.i(TAG, "onClick: position: " + position + " questions.size(): " + questions.size());
                        getTheNextQuestion(position);
                        position++;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Please write your response", Toast.LENGTH_SHORT).show();
                }
                hideKeyboard(etMessage);
            }
        });
    }

    private void getTheNextQuestion(int position) {
        if (mMessageList != null) {
            Message botMessage;
            botMessage = new Message("Q101", questions.get(position), botUser, "11:28");
            mMessageList.add(botMessage);
        }
    }

    private void hideKeyboard(EditText editText) {
        editText.setText("");
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void initializeViews() {
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etChatMessage);
    }

    private void initializeMessageList() {
        selfUser = new User("101", "milind", "https://randomuser.me/api/portraits/thumb/men/65.jpg");
        botUser = new User("102", "bot", "https://randomuser.me/api/portraits/lego/1.jpg");

        if (mMessageList != null) {
            Message botMessage = new Message("Q101", "Hello there. Please provide your name?", botUser, "11:28");
            mMessageList.add(botMessage);
        }
    }

    private void generateJson() {
        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(mMessageList);

        Log.i(TAG, "json: " + json);
    }

}
