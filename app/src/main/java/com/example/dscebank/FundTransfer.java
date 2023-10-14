package com.example.dscebank;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FundTransfer extends AppCompatActivity {

    private Spinner transferTypeSpinner;
    private EditText transferAmountEditText, transferToAccountEditText;
    private Button transferButton;
    private String selectedTransferType;
    private TextView updatedBalanceTextView,usernameTextView;
    private double initialBalance; // Initialize with the actual initial balance

    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);

        transferTypeSpinner = findViewById(R.id.transferTypeSpinner);
        transferAmountEditText = findViewById(R.id.transferAmountEditText);
        transferToAccountEditText = findViewById(R.id.transferToAccountEditText);
        transferButton = findViewById(R.id.transferButton);
        updatedBalanceTextView = findViewById(R.id.updatedBalanceTextView);
        usernameTextView = findViewById(R.id.usernameTextview);

        dbHelper = new DatabaseHelper(this);

        // Initialize Spinner with options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transfer_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transferTypeSpinner.setAdapter(adapter);

        transferTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedTransferType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });


        String username = SharedPreferencesHelper.getUsername(this);


        usernameTextView.setText(username);

        if (username != null) {
            initialBalance = dbHelper.getInitialAccountBalance(username); // Fetch the initial balance from the database
            updatedBalanceTextView.setText("Updated Balance: Rs:" + initialBalance);
        } else {
            // Handle the case where username is null (e.g., show an error message or return to the previous activity)
            // For now, I'm showing a toast message as an example
            Toast.makeText(this, "Username is null", Toast.LENGTH_LONG).show();
        }

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = transferAmountEditText.getText().toString();
                String toAccount = transferToAccountEditText.getText().toString();

                if (!amountStr.isEmpty() && !toAccount.isEmpty()) {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= initialBalance) {
                        // Simulate a fund transfer by deducting the amount from the initial balance
                        initialBalance -= amount;

                        // Update the UI with the updated balance
                        updatedBalanceTextView.setText("Updated Balance: Rs:" + initialBalance);

                        // Update the user's balance in the database
                        if (dbHelper.updateAccountBalance(username, initialBalance)) {
                            String message = "Transferred Rs:" + amount + " via " + selectedTransferType + " to " + toAccount;
                            Toast.makeText(FundTransfer.this, message, Toast.LENGTH_LONG).show();

//                            boolean istransactionenter = dbHelper.insertTransaction(username,amount,selectedTransferType,toAccount);
//
//                            if(istransactionenter){
//                                Toast.makeText(FundTransfer.this, "transaction complete", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(FundTransfer.this, "not entered", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            // Handle the case where the database update fails
//                            Toast.makeText(FundTransfer.this, "Failed to update the balance in the database", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(FundTransfer.this, "Insufficient funds.", Toast.LENGTH_LONG).show();
                    }

                    finish();
                }
            }
        });
    }
}
