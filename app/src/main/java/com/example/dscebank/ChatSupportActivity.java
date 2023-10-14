package com.example.dscebank;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatSupportActivity extends AppCompatActivity {

    private EditText complaintEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_support);

        complaintEditText = findViewById(R.id.complaintEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaint = complaintEditText.getText().toString().trim();
                if (!complaint.isEmpty()) {
                    // Here you would typically send the complaint to your backend
                    // For demonstration, we'll just show a toast message
                    Toast.makeText(ChatSupportActivity.this, "message sent! We will contact you within 24 hours.", Toast.LENGTH_LONG).show();
                    complaintEditText.setText("");
                } else {
                    Toast.makeText(ChatSupportActivity.this, "Please enter your complaint.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}