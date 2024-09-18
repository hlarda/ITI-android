package com.example.imageloaderasync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button downloadBtn;
    EditText editURL;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        downloadBtn = findViewById(R.id.downloadBtn);
        editURL = findViewById(R.id.editURL);
        imageView = findViewById(R.id.imageView);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editURL.getText().toString();
                if (!url.isEmpty()) {
                    new DownloadImg().execute(url);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Bitmap downloadImage(String url) throws IOException {
        URL imageUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        }
        return null;
    }

    private class DownloadImg extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                bitmap = downloadImage(strings[0]);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
        }
    }
}
