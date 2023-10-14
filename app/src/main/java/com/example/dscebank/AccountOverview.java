package com.example.dscebank;

import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AccountOverview extends AppCompatActivity {
    private TextView availableBalanceTextView;
    private TextView transactionHistoryTextView;
    private DatabaseHelper dbHelper;
    private String username;
    double availableBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);

        dbHelper = new DatabaseHelper(this);
        username =SharedPreferencesHelper.getUsername(this);
        if(username != null){
            availableBalance = dbHelper.getInitialAccountBalance(username);
        }
        else{
            Toast.makeText(this, "null username", Toast.LENGTH_SHORT).show();
        }

        availableBalanceTextView = findViewById(R.id.availableBalanceTextView);
        //transactionHistoryTextView = findViewById(R.id.transactionHistoryTextView);

        // Get and display available balance
        availableBalanceTextView.setText("Available Balance: Rs " + availableBalance);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
