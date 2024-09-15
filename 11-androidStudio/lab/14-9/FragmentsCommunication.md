# Fragments

## Lab

### A: Create static fragment

new -> fragment -> fragment(blank)

### 3. Delete all redundant code created 

Leave only event handler methods

```java
public class StaticCounterFragment extends Fragment {

    public StaticCounterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_static_counter, container, false);
    }
}
```

> Do NOT delete the Static fragment android name from main activity layout.xml

### 4. Edit the layout fragment layout

### 5. return to main activity layout

Add fragment container view to the main activity layout and resize.


### B: Create dynamic fragment

all the same as static fragment but with dynamic fragment delete the fragment from main activity layout.xml `android:name="com.example.fragmentscommunication.DynamicViewerFragment"`

## Fragment

![alt text](image-1.png)

1. onAttach: attach the fragment to the activity.
2. onCreate: any create any non-UI objects.
3. onCreateView: inflate the layout.

 >in all the above methods, the activity is not yet created. So, we can't access the activity's views. Before accessing the activity's views, Check if the view is null or not. Its accessible after the onCreateView method.

![alt text](image-2.png)