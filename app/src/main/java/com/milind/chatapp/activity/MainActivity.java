package com.milind.chatapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.milind.chatapp.R;
import com.milind.chatapp.adapter.ChatListAdapter;
import com.milind.chatapp.model.Chat;
import com.milind.chatapp.model.Message;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Realm mRealm;
    private RecyclerView mRecyclerView;
    private RealmList<Chat> chatRealmList;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myChildToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

        //initializing realm
        Realm.init(this);
        // creating an instance of Realm
        mRealm = Realm.getDefaultInstance();

        initializeViews();

        chatRealmList = new RealmList<>();

        chatListAdapter = new ChatListAdapter(this, chatRealmList);
        readMessagesFromDatabase();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(chatListAdapter);

    }

    private void initializeViews() {
        mRecyclerView = findViewById(R.id.recyclerview_chat_list);
    }

    public void checkData(View view) {
        Toast.makeText(this, "Check logs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        readMessagesFromDatabase();
    }

    private void readMessagesFromDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                chatRealmList.clear();
                RealmResults<Chat> results = realm.where(Chat.class).findAll();

                Log.i(TAG, "==== readMessagesFromDatabase === ");
                for (Chat chat : results) {
                    Log.i(TAG, "ChatId: " + chat.getChatId());
                    for (Message message: chat.getMessages()) {
                        Log.i(TAG, "MessageId: " + message.getMessageID());
                    }
                    chatRealmList.add(chat);
                }
            }
        });
        chatListAdapter.notifyDataSetChanged();
    }
}
