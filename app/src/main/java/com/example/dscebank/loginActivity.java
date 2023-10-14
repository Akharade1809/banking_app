package com.example.dscebank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    TextView registertv;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registertv = findViewById(R.id.registertextview);

        registertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ireg = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(ireg);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered account number, username, and password
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verify the entered details from the database
                if (verifyLogin(username, password)) {

                    SharedPreferencesHelper.setUsername(getApplicationContext(), username);

                    // Successful login, navigate to the next activity (e.g., DashboardActivity)
                    Intent inext = new Intent(loginActivity.this, HomePage.class);
                    startActivity(inext);
                    finish(); // Finish the LoginActivity

                    SharedPreferencesHelper.setLoggedIn(getApplicationContext(),true);
                } else {
                    // Invalid login, show an error message (e.g., using a Toast)
                    Toast.makeText(loginActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to verify the entered login details against the database
    private boolean verifyLogin(String username, String password) {
        boolean isValid = dbHelper.checkUser(username,password);
        return isValid; // Change this to perform actual verification
    }
}
