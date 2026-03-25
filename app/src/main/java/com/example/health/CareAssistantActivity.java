package com.example.health;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class CareAssistantActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private ImageButton sendBtn;
    private ArrayList<String> chatMessages;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_assistant);

        Toolbar toolbar = findViewById(R.id.toolbarCareAssistant);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = findViewById(R.id.listViewChat);
        editText = findViewById(R.id.editTextChatMessage);
        sendBtn = findViewById(R.id.buttonChatSend);

        chatMessages = new ArrayList<>();
        chatMessages.add("Care Assistant: Hello! How can I help you with your health today?");
        
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        listView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if (!message.isEmpty()) {
                    chatMessages.add("You: " + message);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    generateResponse(message);
                }
            }
        });
    }

    private void generateResponse(String userMessage) {
        String response = "Care Assistant: ";
        userMessage = userMessage.toLowerCase();

        if (userMessage.contains("fever")) {
            response += "Drink plenty of fluids and rest. If it's high, consider paracetamol and consult a doctor.";
        } else if (userMessage.contains("headache")) {
            response += "Rest in a quiet, dark room. Stay hydrated. If persistent, please see a specialist.";
        } else if (userMessage.contains("cough") || userMessage.contains("cold")) {
            response += "Try warm water with honey. Steam inhalation can also help.";
        } else if (userMessage.contains("stomach") || userMessage.contains("pain")) {
            response += "Avoid heavy foods. Drink clear liquids. If pain is severe, visit the emergency room.";
        } else if (userMessage.contains("hello") || userMessage.contains("hi")) {
            response += "Hi there! I'm here to provide general health suggestions. What's on your mind?";
        } else {
            response += "I'm sorry, I don't have specific advice for that. Please consult a professional doctor.";
        }

        final String finalResponse = response;
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatMessages.add(finalResponse);
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }
        }, 1000);
    }
}
