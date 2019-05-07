package com.milind.chatapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.milind.chatapp.R;
import com.milind.chatapp.adapter.MessageListAdapter;
import com.milind.chatapp.model.BotQuestions;
import com.milind.chatapp.model.Message;
import com.milind.chatapp.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<Message> mMessageList;
    private User selfUser, botUser;
    private Button btnSend;
    private EditText etMessage;
    ArrayList<String> questions;
    BotQuestions botQuestions;
    Message botMessage;

    // "position" saves the current position of mMessageList. With the help of this position counter we're retrieving the questions from
    // questions list.
    int position = 0;

    int userMessageId = 101;
    int botMessageId = 101;

    private Realm mRealm;

    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar myChildToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMessageList = new ArrayList<>();

        initializeViews();

        initializeMessageList();

        //initializing realm
        Realm.init(this);
        // creating an instance of Realm
        mRealm = Realm.getDefaultInstance();

        // Here we're creating an object of BotQuestions to get our questions and initializing them in questions ArrayList
        botQuestions = new BotQuestions();
        botQuestions.setQuestions();
        questions = botQuestions.getQuestions();

        mMessageRecycler = findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);

        mMessageRecycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());
                selfUser = new User("101", "Milind", "https://randomuser.me/api/portraits/thumb/men/65.jpg");
                String usersReply = etMessage.getText().toString();
                Message userMessage;
                if (!TextUtils.isEmpty(usersReply)) {
                    userMessage = new Message(getMessageId("me"), etMessage.getText().toString(), selfUser, getCurrentTime());
                    mMessageList.add(userMessage);
                    mMessageAdapter.notifyDataSetChanged();
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(userMessage);
                        }
                    });

                    // Check to see if there are no more questions in the questions ArrayList. And here we're generating the JSON.
                    if (position == questions.size()) {
                        generateJson();
                        etMessage.setText("");
                    } else {
                        //Log.i(TAG, "onClick: position: " + position + " questions.size(): " + questions.size());
                        getTheNextQuestion(position);
                        position++;
                        etMessage.setText("");
                    }
                } else {
                    Toast.makeText(ChatActivity.this, "Please write your response", Toast.LENGTH_SHORT).show();
                }
                //hideKeyboard(etMessage);
            }
        });
    }

    //for getting the next question from questions arraylist
    private void getTheNextQuestion(int position) {
        mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());
        if (mMessageList != null) {
            botMessage = new Message(getMessageId("bot"), questions.get(position), botUser, getCurrentTime());
            mMessageList.add(botMessage);
        }
    }

    //for hiding the keyboard after typing is complete on EditText
    private void hideKeyboard(EditText editText) {
        editText.setText("");
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //For generating message Id's
    private String getMessageId(String userType) {
        String generatedMessageId;
        if (userType.equalsIgnoreCase("bot")) {
            generatedMessageId = "Q" + botMessageId;
            botMessageId++;
            return generatedMessageId;
        } else {
            generatedMessageId = "A" + userMessageId;
            userMessageId++;
            return generatedMessageId;
        }
    }

    //for initializing views
    private void initializeViews() {
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etChatMessage);
    }

    //for initializing message list
    private void initializeMessageList() {
        botUser = new User("102", "bot", "https://randomuser.me/api/portraits/lego/1.jpg");

        if (mMessageList != null) {
            botMessage = new Message(getMessageId("bot"), "Hello there. Please provide your name?", botUser, getCurrentTime());
            mMessageList.add(botMessage);
        }
    }

    // for getting the current time
    private String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm ");
        String localTime = dateFormat.format(currentTime);
        return localTime;
    }

    // for generating the json
    private void generateJson() {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(mMessageList);
        Log.i(TAG, "json: " + json);

    }

    // method to add all ArrayList of messages to Realm
    private void addMessagesToDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Message message : mMessageList) {
                    realm.copyToRealm(message);
                }
            }
        });
        Log.i(TAG, "addMessagesToDatabase: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

}