package com.example.downloadimghandler;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private List<Product> productList;
    private int currentIndex = 0;

    private TextView titleView, descriptionView, priceView, brandView;
    private RatingBar ratingBar;
    private ImageView imageView;
    private Button btnLeft, btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        titleView = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);
        priceView = findViewById(R.id.price);
        brandView = findViewById(R.id.brand);
        ratingBar = findViewById(R.id.ratingBar);
        imageView = findViewById(R.id.imageView);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);

        new GetProducts().execute();

        btnLeft.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
            updateButtons();
        });

        btnRight.setOnClickListener(v -> {
            if (currentIndex < productList.size() - 1) {
                currentIndex++;
                updateUI();
            }
            updateButtons();
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class GetProducts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpsHandler httpsHandler = new HttpsHandler();
            String jsonStr = httpsHandler.makeServiceCall();

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray products = jsonObj.getJSONArray("products");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String title = c.getString("title");
                        String description = c.getString("description");
                        String price = c.getString("price");
                        String brand = c.getString("brand");
                        String imageUrl = c.getString("thumbnail");
                        String rating = c.getString("rating");

                        Product product = new Product(title, description, price, brand, rating, imageUrl);
                        productList.add(product);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!productList.isEmpty()) {
                updateUI();
                updateButtons();
            }
        }
    }

    private void updateUI() {
        Product currentProduct = productList.get(currentIndex);
        titleView.setText(currentProduct.getTitle());
        descriptionView.setText(currentProduct.getDescription());
        priceView.setText(currentProduct.getPrice());
        brandView.setText(currentProduct.getBrand());
        ratingBar.setRating(Float.parseFloat(currentProduct.getRating()));
        new DownloadImg().execute(currentProduct.getImageUrl());
    }

    private void updateButtons() {
        btnLeft.setEnabled(currentIndex > 0);
        btnRight.setEnabled(currentIndex < productList.size() - 1);
    }

    private Bitmap downloadImage(String url) throws IOException {
        URL imageUrl = new URL(url);

        HttpsURLConnection connection = (HttpsURLConnection) imageUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
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
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
