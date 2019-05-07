package com.milind.chatapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Realm mRealm;

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

    }

    public void openNext(View view) {
        readMessagesFromDatabase();
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

    private void readMessagesFromDatabase() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Message> results = realm.where(Message.class).findAll();

                Log.i(TAG, "==== readMessagesFromDatabase === ");
                for (Message message : results) {
                    Log.i(TAG, "MessageID: " + message.getMessageID() +
                            " Message: " + message.getMessage() +
                            " createdAt: " + message.getCreatedAt());
                }
            }
        });
    }

}
