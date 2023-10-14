package com.example.dscebank;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFundsActivity extends AppCompatActivity {

    private EditText fundsAmountEditText;
    private Button addFundsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);

        fundsAmountEditText = findViewById(R.id.fundsAmountEditText);
        addFundsButton = findViewById(R.id.addFundsButton);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        addFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = fundsAmountEditText.getText().toString().trim();
                double amt = Double.parseDouble(amountStr);

                if (!amountStr.isEmpty()) {
                    // Convert the entered amount to double
                    double amount = Double.parseDouble(amountStr);
                    String username = SharedPreferencesHelper.getUsername(getApplicationContext());
                    double initailbalance = dbHelper.getInitialAccountBalance(username);

                    initailbalance = initailbalance+amt;

                    if(dbHelper.updateAccountBalance(username,initailbalance)){
                        String message = "Rs :" + amt + "credited to the bank account";
                        Toast.makeText(AddFundsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddFundsActivity.this, "adding funds failed", Toast.LENGTH_SHORT).show();
                    }

                    finish();  // Close the activity
                } else {
                    Toast.makeText(AddFundsActivity.this, "Please enter an amount.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
