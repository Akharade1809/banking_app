package com.example.dscebank;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BillPaymentsActivity extends AppCompatActivity {

    private Spinner billNameSpinner;
    private EditText billAmountEditText;
    private Button payButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments);

        billNameSpinner = findViewById(R.id.billNameSpinner);
        billAmountEditText = findViewById(R.id.billAmountEditText);
        payButton = findViewById(R.id.payButton);

        // Define an array of bill names (you can populate this array dynamically)
        String[] billNames = {"Electricity", "Water", "Internet", "Gas"};

        // Create an ArrayAdapter to populate the Spinner with bill names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, billNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billNameSpinner.setAdapter(adapter);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected bill name and bill amount
                String billName = billNameSpinner.getSelectedItem().toString();
                String billAmountStr = billAmountEditText.getText().toString().trim();

                // Check if the bill amount field is empty
                double billAmount = 0;
                if (billAmountStr.isEmpty()) {
                    Toast.makeText(BillPaymentsActivity.this, "Please enter the bill amount.", Toast.LENGTH_SHORT).show();
                } else {
                    // Convert bill amount to a double
                    billAmount = Double.parseDouble(billAmountStr);

                    String username = SharedPreferencesHelper.getUsername(BillPaymentsActivity.this);
                    DatabaseHelper dbHelper = new DatabaseHelper(BillPaymentsActivity.this);
                    double initialBalance = dbHelper.getInitialAccountBalance(username);
                    initialBalance = initialBalance-billAmount;

                    if (dbHelper.updateAccountBalance(username, initialBalance)) {
                        // Record the bill payment transaction
                        //dbHelper.insertTransaction(username, "Bill Payment: " + billName, -billAmount);

                        // Show a success message
                        String message = "Paid Rs " + billAmount + " for " + billName;
                        Toast.makeText(BillPaymentsActivity.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        // Handle the case where the database update fails
                        Toast.makeText(BillPaymentsActivity.this, "Failed to update the balance in the database", Toast.LENGTH_LONG).show();
                    }
                }



                // Perform the bill payment logic here
                // You can update the user's balance and store transaction details in the database

                // Show a success message
                String message = "Paid Rs " + billAmount + " for " + billName;
                Toast.makeText(BillPaymentsActivity.this, message, Toast.LENGTH_LONG).show();

                finish();





                // You can also update the UI to reflect the new balance or transaction history

            }
        });
    }
}
