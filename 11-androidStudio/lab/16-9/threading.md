# Threading

"Application is Not Responding" (ANR) is a common issue in Android development. It happens when the main thread is blocked for a long time. To avoid ANR, we should move time-consuming tasks to a background thread.

## Threads in Android

1. Java Thread
2. AsyncTask
3. HandlerThread

> You cant update UI from a background thread. You should update UI from the main thread as they are not thread-safe.

## AsyncTask

- It's deprecated but it's still useful for simple tasks and backward compatibility.
- AsyncTask is a generic class that has three parameters: `Params`, `Progress`, and `Result`.
- It has four methods: `onPreExecute`, `doInBackground`, `onProgressUpdate`, and `onPostExecute`.
- Three methods run on the main thread(foreground), and `doInBackground` runs on a background thread.
- Don't edit UI from `doInBackground`.

1. `onPreExecute`: Runs on the main thread before `doInBackground`.
2. `doInBackground`: Runs on a background thread and performs the task. It takes `Params` as input in varargs.
3. `onProgressUpdate`: Runs on the main thread and updates the UI. It takes `Progress` as input in varargs. called by invoking `publishProgress` in `doInBackground`. let's say you are downloading a file, you can update the progress of the download while the executing `doInBackground` requesting from time to another to update the progress by calling `publishProgress`.
4. `onPostExecute`: Runs on the main thread after `doInBackground`. It takes `Result` as input.

- `execute`: To start the AsyncTask and pass the parameters.
- `cancel`: prevent the `onPostExecute` from running but it doesn't stop the background task.

> - AsyncTask is not recommended for long-running tasks. It's better to use `Thread` or `HandlerThread`.
>
> - If you are downloading a file, and rotated the screen, the activity will destroyed and recreated, the image object will be lost and then it leads to null pointer exception. To avoid this, you can use `onSaveInstanceState` and `onRestoreInstanceState` to save and restore the object.

## URL

- `URL` class is used to create a URL object. Its like a gateway to the resource on the internet and any other network connection.

## GET request

- To send a GET request, your data sent in the URL. Sensitive data should not be sent in the URL as it's visible to everyone, Use POST request instead.

```java
private Bitmap downloadImage(String url) throws IOException {
    URL imageUrl = new URL(http://example.com/image.jpg);
    HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
        //do something
        InputStream inputStream = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
```

## JSON

- `JSONObject` and `JSONArray` are used to parse JSON data.
- `JSONObject` is an unordered collection of key-value pairs.
- `JSONArray` is an ordered sequence of values.
- Date parsed from the file must be mapped to DTO or POJO.

## Handler

- `Handler` is used to communicate between background thread and main thread to update the UI.
- It's used to send and process `Message` and `Runnable` objects associated with a thread's `MessageQueue`.
- `Handler` is associated with a `Looper` and `Looper` is associated with a `Thread`.
- `Handler` APIs
  - `post`: Add a `Runnable` to the message queue.
  - `sendMessage`: Add a `Message` to the message queue.
  - `postDelayed`: Add a `Runnable` to the message queue after the specified delay.
  - `sendMessageDelayed`: Add a `Message` to the message queue after the specified delay.

## Network Permission

<uses-permission android:name="android.permission.INTERNET" />

> Search "network state and permissions".
