package com.yiful.questionnaireproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnRegister, btnLogin;
    SQLiteDatabase database;
    UserDbHelper dbHelper;
    static int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new UserDbHelper(this);
        database = dbHelper.getWritableDatabase();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String search_sql = "select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.USERNAME + " = '" + username +"'";
                Cursor cursor = database.rawQuery(search_sql, null);
                if(cursor.getCount()>0){
                    etUsername.setText("");
                    etPassword.setText("");
                    Toast.makeText(LoginActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }else{ //save in database
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(dbHelper.ID, index);
                    contentValues.put(dbHelper.USERNAME, username);
                    contentValues.put(dbHelper.PASSWORD, password);
                    database.insert(dbHelper.TABLE_NAME, null, contentValues);

                    index++;
                    Toast.makeText(LoginActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String search_sql = "select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.USERNAME + " = '" + username +"'";
                Cursor cursor = database.rawQuery(search_sql, null);
                if(cursor.getCount() == 0){
                    Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                }else{
                    cursor.moveToFirst();
                    if(cursor.getString(cursor.getColumnIndex(dbHelper.PASSWORD)).equals(password)){
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }else Toast.makeText(LoginActivity.this, "Password is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
