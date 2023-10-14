package com.example.dscebank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Example: Start the main activity after a delay
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000); // Splash screen duration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(getApplicationContext(), loginActivity.class));
                    finish();



//                    if (!SharedPreferencesHelper.isLoggedIn(getApplicationContext())) {
//                        // If not logged in, redirect to the login screen
//                        startActivity(new Intent(getApplicationContext(), loginActivity.class));
//                        finish();
//                    }
//                    else {
//
//                        Intent intent = new Intent(SplashActivity.this, HomePage.class);
//                        startActivity(intent);
//                        finish(); // Finish the splash screen activity
//                    }
                    // Start the main activity
                }
            }
        };
        timer.start();
    }
}
