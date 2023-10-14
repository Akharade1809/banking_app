package com.example.dscebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, passwordEditText, confirmPasswordEditText, emailEditText, mobileEditText, initialDepositEditText;
    private Button registerButton, cancelButton;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        initialDepositEditText = findViewById(R.id.initialDepositEditText);

        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Implement your registration logic here
                // For example, you can validate the input fields and then store the data in a database

                String fullName = fullNameEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String initialDeposit = initialDepositEditText.getText().toString().trim();


                // Check if any field is empty
                if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || mobile.isEmpty() || initialDeposit.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the password and confirmPassword match
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(RegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    } else {

                        DatabaseHelper dbHelper = null;
                            dbHelper = new DatabaseHelper(RegistrationActivity.this);

                            // Insert user data into the database
                            boolean isInserted = dbHelper.insertUser(fullName, username, password, email, mobile, initialDeposit);

                            if (isInserted) {
                                Toast.makeText(RegistrationActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                SharedPreferencesHelper.setUsername(getApplicationContext(), username);

                                // You can navigate to the login page or perform other actions here

                                Intent intent = new Intent(RegistrationActivity.this, FundTransfer.class);
                                intent.putExtra("user", username); // Pass the username to the next activity
                                startActivity(intent);

                                Intent iuser = new Intent(RegistrationActivity.this, UserProfileActivity.class);
                                intent.putExtra("user1", username); // Pass the username to the next activity
                                startActivity(iuser);



                                SharedPreferencesHelper.setLoggedIn(getApplicationContext(),true);

                                Intent inext = new Intent(getApplicationContext(), HomePage.class);
                                startActivity(inext);
                                finish();


                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }

                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the registration activity
                finish();
            }
        });
    }

//    public boolean insertUser(String fullName, String username, String password, String email, String mobile, String initialDeposit) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//
//        values.put(COLUMN_FULL_NAME, fullName);
//        values.put(COLUMN_USERNAME, username);
//        values.put(COLUMN_PASSWORD, password);
//        values.put(COLUMN_EMAIL, email);
//        values.put(COLUMN_MOBILE, mobile);
//        values.put(COLUMN_INITIAL_DEPOSIT, initialDeposit);
//
//        // Insert the user data into the TABLE_USERS table
//        long result = db.insert(TABLE_USERS, null, values);
//
//        // Check if the insertion was successful
//        return result != -1;
//    }

}