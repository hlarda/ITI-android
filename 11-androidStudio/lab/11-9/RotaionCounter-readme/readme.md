# Configuration Change Counter

## 1. onSaveInstanceState()

it is called before stopping the activity, so it is a good place to save the configuration changes.

```java
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(COUNTER, counter);
}
```

## 2. Use the saved bundle at onCreate()

```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState != null) {
        counter = savedInstanceState.getInt(COUNTER, 0);
        counter++;
    }
    counterView.setText(String.valueOf(counter));
}
```
