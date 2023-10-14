package com.example.dscebank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);




        ImageButton Logout= findViewById(R.id.logout);
        ImageButton accountOverviewButton = findViewById(R.id.accountOverviewButton);
        ImageButton fundsTransferButton = findViewById(R.id.fundsTransferButton);
        ImageButton billPaymentsButton = findViewById(R.id.billPaymentsButton);
        ImageButton depositWithdrawalButton = findViewById(R.id.depositWithdrawalButton);
        ImageButton customerServiceButton = findViewById(R.id.customerServiceButton);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesHelper.setLoggedIn(getApplicationContext(),false);
                Intent ilogout = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(ilogout);
                finish();
            }
        });

//        userAuthenticationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(homepage.this, Registrationpage.class));
//            }
//        });

        accountOverviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, UserProfileActivity.class));
            }
        });

        fundsTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, FundTransfer.class));
            }
        });

        billPaymentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, BillPaymentsActivity.class));
            }
        });

        depositWithdrawalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, AddFundsActivity.class));
            }
        });

        customerServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ChatSupportActivity.class));
            }
        });
    }
}