package com.example.activityandorientation;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class nextActivity extends AppCompatActivity {
    private static final String PREF_NAME = "PREF_NAME";
    private static final String INTERNAL_FILE = "INTERNAL_FILE";

    Button backBtn;
    Button rSharedPrefBtn, wSharedPrefBtn, rInternalStorage, wInternalStorage, rDbBtn, wDbBtn;
    TextView phoneView, msgView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_next);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backBtn         = findViewById(R.id.backBtn);
        rSharedPrefBtn  = findViewById(R.id.rSharedPrefBtn);
        wSharedPrefBtn  = findViewById(R.id.wSharedPrefBtn);
        rInternalStorage= findViewById(R.id.rInternalStorage);
        wInternalStorage= findViewById(R.id.wInternalStorage);
        rDbBtn          = findViewById(R.id.rDbBtn);
        wDbBtn          = findViewById(R.id.wDbBtn);

        phoneView       = findViewById(R.id.phoneView);
        msgView         = findViewById(R.id.msgView);

        dbHelper = new DbHelper(nextActivity.this);

        String phone    = getIntent().getStringExtra(MainActivity.PHONE);
        String message  = getIntent().getStringExtra(MainActivity.MESSAGE);

        phoneView.setText(phone);
        msgView.setText(message);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        wSharedPrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.PHONE,phone);
                editor.putString(MainActivity.MESSAGE,message);
                editor.apply();
                phoneView.setText("");
                msgView.setText("");
            }
        });
        rSharedPrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                phoneView.setText(sharedPreferences.getString(MainActivity.PHONE,"N/A"));
                msgView.setText(sharedPreferences.getString(MainActivity.MESSAGE,"N/A"));
            }
        });
        wInternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = openFileOutput(INTERNAL_FILE, Context.MODE_PRIVATE);
                    DataOutputStream dos = new DataOutputStream(fos);
                    dos.writeUTF(phone);
                    dos.writeUTF(message);
                    phoneView.setText("");
                    msgView.setText("");
                    fos.close();
                    dos.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(nextActivity.this, "Couldn't create file", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(nextActivity.this, "Couldn't write to the file", Toast.LENGTH_SHORT).show();
                }

            }
        });
        rInternalStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fis = openFileInput(INTERNAL_FILE);
                    DataInputStream dis = new DataInputStream(fis);
                    phoneView.setText(dis.readUTF());
                    msgView.setText(dis.readUTF());
                } catch (FileNotFoundException e) {
                    Toast.makeText(nextActivity.this, "Couldn't create file", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(nextActivity.this, "Couldn't read from the file", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertData(phone, message);
                phoneView.setText("");
            }
        });
        rDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dbHelper.getPhoneByMessage(message);

                if (cursor != null && cursor.moveToFirst()) {
                    phoneView.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                } else {
                    Toast.makeText(nextActivity.this, "No phone found for this message", Toast.LENGTH_SHORT).show();
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        });

    }
}