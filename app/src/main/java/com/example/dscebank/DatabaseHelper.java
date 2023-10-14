package com.example.dscebank;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Define your database and table constants here
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_INITIAL_DEPOSIT = "initial_deposit";

    private static final String TABLE_TRANSACTIONS = "transactions";
//    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TRANSFER_TYPE = "transfer_type";
    private static final String COLUMN_TO_ACCOUNT = "to_account";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the 'users' table here
        String createTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_MOBILE + " TEXT, " +
                COLUMN_INITIAL_DEPOSIT + " TEXT)";
        db.execSQL(createTableQuery);


        String createTransactionsTableQuery = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_TRANSFER_TYPE + " TEXT, " +
                COLUMN_TO_ACCOUNT + " TEXT, " +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTransactionsTableQuery);


}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
        // For example, you can add or modify tables here
    }

    public boolean insertTransaction(String username, double amount, String transferType, String toAccount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_TRANSFER_TYPE, transferType);
        values.put(COLUMN_TO_ACCOUNT, toAccount);

        long result = db.insert(TABLE_TRANSACTIONS, null, values);

        // Check if the insertion was successful
        return result != -1;
    }

    @SuppressLint("Range")
    public List<Transaction> getTransactionHistory(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transaction> transactionHistory = new ArrayList<>();

        String[] columns = {
                COLUMN_AMOUNT,
                COLUMN_TRANSFER_TYPE,
                COLUMN_TO_ACCOUNT,
                COLUMN_TIMESTAMP
        };

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_TRANSACTIONS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                COLUMN_TIMESTAMP + " DESC"  // Order by timestamp in descending order
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                 double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                String transferType = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSFER_TYPE));
                String toAccount = cursor.getString(cursor.getColumnIndex(COLUMN_TO_ACCOUNT));
                String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));

                Transaction transaction = new Transaction(amount, transferType, toAccount, timestamp);
                transactionHistory.add(transaction);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return transactionHistory;
    }

    public boolean insertUser(String fullName, String username, String password, String email, String mobile, String initialDeposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_MOBILE, mobile);
        values.put(COLUMN_INITIAL_DEPOSIT, initialDeposit);

        // Insert the user data into the 'users' table
        long result = db.insert(TABLE_USERS, null, values);

        // Check if the insertion was successful
        return result != -1;
    }
    @SuppressLint("Range")
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_FULL_NAME,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_MOBILE,
                COLUMN_INITIAL_DEPOSIT
        };

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_INITIAL_DEPOSIT))
            );
            cursor.close();
        }

        db.close();
        return user;
    }

    public boolean isUsernameUnique(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_USERNAME };

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isUnique = cursor == null || cursor.getCount() == 0;

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return isUnique;
    }

    @SuppressLint("Range")
    public double getInitialAccountBalance(String username) {
        if(username == null){
            return 0.0;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        double initialBalance = 0;

        String[] columns = {COLUMN_INITIAL_DEPOSIT};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            initialBalance = cursor.getDouble(cursor.getColumnIndex(COLUMN_INITIAL_DEPOSIT));
            cursor.close();
        }

        db.close();
        return initialBalance;
    }


    public boolean updateAccountBalance(String username, double newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_INITIAL_DEPOSIT, newBalance);

        // Define the WHERE clause to update the row for the specified username
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};

        // Update the account balance in the 'users' table
        int rowsUpdated = db.update(TABLE_USERS, values, whereClause, whereArgs);

        db.close();

        // Check if the update was successful
        return rowsUpdated > 0;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, null,
                 COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isValid;
    }


}
