package com.example.dscebank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        // Get the username from the intent

        // Initialize views
        TextView userNameTextView = findViewById(R.id.userNameTextView);
        TextView accountBalanceTextView = findViewById(R.id.accountBalanceTextView);
        TextView contactNoTextView = findViewById(R.id.contactNoTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);


        String username = SharedPreferencesHelper.getUsername(this);
        if (username==null){
            Toast.makeText(this, "username is null", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            user = dbHelper.getUserByUsername(username);
        }

        // Try to get the user information from the database



        if (user != null) {
            // Set user information to TextViews
            userNameTextView.setText(user.getUsername());
            emailTextView.setText(user.getEmail());
            contactNoTextView.setText(user.getContactNumber());
            accountBalanceTextView.setText(user.getAccountNumber());
        } else {
            // Handle the case where the user is not found in the database
            Toast.makeText(this, "User not found in the database", Toast.LENGTH_SHORT).show();
        }

        // Set up a button to navigate to the AccountOverview activity
        Button accountOverviewButton = findViewById(R.id.transactionHistoryButton);
        accountOverviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountOverview.class);
                startActivity(intent);
            }
        });
    }
}
