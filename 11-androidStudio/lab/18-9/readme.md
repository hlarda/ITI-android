# Reftrofit

## Previously

- Data Storage
- Internet connection

## What is Retrofit?

- Retrofit is a type-safe REST client for Android, Java and Kotlin developed by Square. It is a library that makes it easy to consume RESTful web services in an Android app.
- It is a type-safe HTTP client for Android and Java.
- It is used to make network calls to a RESTful API.

## Volley

- Volley is a networking library developed by Google
- It is used to handle network operations
- It operates 3 threads in the background:
  - Network thread
  - Main thread
  - Cache thread
- It Checks for the cache first and then makes a network call if the cache is not available to get the data.
- Volley stands for volley of arrows, which means a lot of requests, Fast, Efficient, and Easy to use.
- It handles connection errors and retries the request.

## Volley vs Retrofit

- Retrofit is Volley on steroids. It is a more powerful library than Volley. It is more efficient and faster than Volley. It is a type-safe library that makes it easy to consume RESTful web services in an Android app.
- It's developed by Square.
- RESTful API is a type of web service that is lightweight, maintainable, and scalable. It is a software architectural style that defines a set of constraints to be used for creating Web services.
- It is not recommended to use them when downloading large files or streaming data.

||Volley|Retrofit|
|---|---|---|
|Developed by|Google|Square|
|Type|Networking Library|Networking Library|
|Cache|Yes|No|
|Thread|3|1|
|Retry Policy|Yes|No|
|Responses|JSON, XML, String|Boolean, Integer, String, List, Object, Collection, etc.|
|Error Handling|Yes|Yes|
|Network Image Loading|Yes|No|
|Parsing|Manual-Returns JSON|Automatic|

- Retrofit has not many features like Volley, but you can use third-party libraries like Picasso, Glide, etc. for image loading as it is a canvas for making network calls UNLIKE Volley which has inbuilt image loading capabilities.

## Implementation

- add gson and retrofit dependencies in app/build.gradle for automatic parsing of JSON data.

    ```gradle
    dependencies {
        implementation 'com.squareup.retrofit2:retrofit:2.9.0'
        implementation 'com.google.code.gson:gson:2.8.6'
        implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    }
    ```

- Provide internet permission in AndroidManifest.xml

    ```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    ```

1. **Create model class for JSON data**
    - variables should be the same as the JSON data keys or use `@SerializedName("key")` annotation.

        ```java
        @SerializedName("userId")
        private int userId;
        ```

2. Create an interface for defining the API endpoints using Retrofit annotations.
    - `@GET("posts")` is the endpoint for the API.
        - `GET` is the type of request.
        - `posts` is the endpoint.
    - `https://jsonplaceholder.typicode.com/posts`
      - `https://jsonplaceholder.typicode.com` is the base URL.
      - `posts` is URI or endpoint. It is the path to the resource on the server.

        ```java
        public interface InterfaceClassContatinsMethods {
            @GET("posts")
            Call<List<Post>> getPosts();
        }
        ```

    - `Call<List<Post>>` is the return type of the method.
    - `Call` is a Retrofit class that represents a request.
    - `List<Post>` is the type of data that will be returned in the response.
    - `Callback` is an interface that is used to handle the response.
    - `Response` is a Retrofit class that represents the response from the server.

    ```java
    @GET("users/{name}/commits")
    Call<List<Commit>> getCommits(@Path("name") String name);
    ```

    ```java
    @GET("users")
    Call<User> getUserById(@Query("id") int id);
    ```

    ```java
    @POST("users")
    Call<User> createUser(@Body User user);
    ```

3. Create a Retrofit builder class to create a Retrofit instance.

    ```java
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ```

    - `BASE_URL` is the base URL of the API.
    - `addConverterFactory(GsonConverterFactory.create())` is GSON used to convert the JSON data to Java objects(POJOs).
    - `build()` is used to create the Retrofit instance.
4. Create an instance of the interface.

    ```java
    InterfaceClassContatinsMethods jsonPlaceHolderApi = retrofit.create(InterfaceClassContatinsMethods.class);
    ```

    >> Web service is code on server without any UI.

5. In main activity

    ```java
    Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
    call.enqueue(new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (!response.isSuccessful()) {
                textViewResult.setText("Code: " + response.code());
                return;
            }
            List<Post> posts = response.body();
            for (Post post : posts) {
                String content = "";
                content += "ID: " + post.getId() + "\n";
                content += "User ID: " + post.getUserId() + "\n";
                content += "Title: " + post.getTitle() + "\n";
                content += "Text: " + post.getText() + "\n\n";
                textViewResult.append(content);
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            textViewResult.setText(t.getMessage());
        }
    });
    ```

    - `call.enqueue()` is used to send the network request asynchronously UNLIKE `call.execute()` which is used to send the network request synchronously blocking the main thread.
    - `onResponse()` is called when the response is received.
    - `onFailure()` is called when the request fails.
    - `response.isSuccessful()` is used to check if the response is successful.
    - `response.body()` is used to get the response body.
    - `response.code()` is used to get the response code.

## DEMO

1. create POJO class for JSON data with variables same as JSON keys with getter and setter methods. When changing the key name, use `@SerializedName("key")` annotation.

2. Create an interface for defining the API endpoints using Retrofit annotations.

    ```java
    public interface JsonPlaceHolderApi {
        @GET("posts")
        Call<List<Post>> getPosts();
    }
    ```

3. Create a Retrofit builder class to create a Retrofit instance.

    ```java
    pubic class MainActivity extends AppCompatActivity {
        private TextView textViewResult;
        private JsonPlaceHolderApi jsonPlaceHolderApi;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            textViewResult = findViewById(R.id.text_view_result);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            getPosts();
        }

        private void getPosts() {
            Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if (!response.isSuccessful()) {
                        textViewResult.setText("Code: " + response.code());
                        return;
                    }

                    List<Post> posts = response.body();
                    for (Post post : posts) {
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "User ID: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";

                        textViewResult.append(content);
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
                }
            });
        }
    }
    ```

## For Caching

- Add OkHttp dependency in app/build.gradle

    ```gradle
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'
    ```

- Create an instance of OkHttp client and add it to the Retrofit builder.

    ```java
    int cacheSize = 10 * 1024 * 1024; // 10 MB
    Cache cache = new Cache(getCacheDir(), cacheSize);

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    ```

> Search `Paging` in Retrofit for pagination. For fetching large data, use Paging dividing the response into pages.

## Glide

- Glide is an image loading library developed by Bumptech.
- It is used to load images from the internet, local storage, or any other source.

```java
Glide.with(context).load(movies.get(position).getImage())
    .apply(RequestOptions.centerCropTransform())
    .placeholder(R.drawable.placeholder)
    .into(holder.imageView);
    .error(R.drawable.error);
```
