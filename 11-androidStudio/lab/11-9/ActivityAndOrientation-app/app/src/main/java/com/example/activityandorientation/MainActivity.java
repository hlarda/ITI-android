package com.example.activityandorientation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static final String PHONE = "phone";
    public static final String MESSAGE = "message";

    Button nextBtn, closeBtn;
    EditText editPhoneTxt, editMsgTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nextBtn = findViewById(R.id.nextBtn);
        closeBtn = findViewById(R.id.closeBtn);

        editPhoneTxt = findViewById(R.id.editPhoneTxt);
        editMsgTxt = findViewById(R.id.editMsgTxt);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = editPhoneTxt.getText().toString();
                String message = editMsgTxt.getText().toString();

                Intent intent = new Intent(MainActivity.this, nextActivity.class);
                intent.putExtra(PHONE, phone);
                intent.putExtra(MESSAGE, message);
                startActivity(intent);
            }
        });
        closeBtn.setOnClickListener(view -> moveTaskToBack(true));
    }
}