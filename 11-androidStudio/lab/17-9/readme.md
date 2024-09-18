# List view

- viw group displays a list of items in a group.
- Adapter: is a bridge between UI component and data source that helps us to fill data in UI component. It holds the data and send the data to an Adapter view then view can takes the data from the adapter view and shows the data on different views like ListView, GridView, Spinner etc.

Data source -> Adapter -> View

## Adapter Types

- ArrayAdapter is a type of adapter that works more like an ArrayList. It takes an array of objects and uses it to fill the ListView.
- BaseAdapter is a generic adapter that allows you to do pretty much anything. It's the most flexible adapter.

```Java
String[] data = {"one", "two", "three"};

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ListView listView = findViewById(R.id.listView);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            Toast.makeText(MainActivity.this, "Item clicked: " + data[position], Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Item clicked: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
        }
    });
}
```

## Apply some level of customization

## Images in list view

> Same photo and different text.

- to add images to the list view, we need to create a custom adapter.
- new layout resource file: day_row.xml
- make the layout vertical linear layout
- add an ImageView and a TextView. make the layout weightsum 4.

```Java
String[] data = {"one", "two", "three"};

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ListView listView = findViewById(R.id.listView);
    // ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data);
    //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.day_row, R.id.textView, data);
    CakeAdapter adapter = new CakeAdapter(getApplicationContext(), cakes);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            //Toast.makeText(MainActivity.this, "Item clicked: " + data[position], Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, "Item clicked: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Item clicked: " + cakes[position].getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Item clicked: " + adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();  //override toString in Cake class
        }
    });
}
```

## Custom Adapter

> If you want to customize the list view, you need to create a custom adapter complex display for more than one view.

- create new layout resource file with constraint layout.
- add img and 2 text views.
- constraint layout -> height(wrap_content), width(match_parent)
- create a new class cake as a DTO.

```Java

public class Cake {
    private String name;
    private String description;
    private int image;

    public Cake(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
```

- in main activity, create a list of cakes and create a custom adapter.
- drop your photos in recourses drawable folder.

```Java
Cake[] cakes = {
    new Cake("Cake 1", "This is cake 1", R.drawable.cake1),
    new Cake("Cake 2", "This is cake 2", R.drawable.cake2),
    new Cake("Cake 3", "This is cake 3", R.drawable.cake3),
    new Cake("Cake 4", "This is cake 4", R.drawable.cake4),
};

- create a new class CakeAdapter extends ArrayAdapter.

```Java
public class CakeAdapter extends ArrayAdapter<Cake> {
    private final Context context;
    private final Cake[] cakes;

    public CakeAdapter(Context context, Cake[] cakes) {
        super(context, R.layout.cake_row, R.id.txtTittle, cakes);
        this.context = context;
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View currentRow, @NonNull ViewGroup listView) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           row      = inflater.inflate(R.layout.cake_row, listView, false);

        ImageView imageView = row.findViewById(R.id.imageView);
        TextView textView1  = row.findViewById(R.id.textView1);
        TextView textView2  = row.findViewById(R.id.textView2);

        imageView.setImageResource(cakes[position].getImage());
        textView1.setText(cakes[position].getName());
        textView2.setText(cakes[position].getDescription());

        return row;
    }
}
```

## Recycle View

- ViewHolder is a design pattern to increase the performance of the ListView. It's an object that holds the references to the views in each row. When you use a ViewHolder, you can avoid calling findViewById() frequently.
- instead of creating a new view every time, we can reuse the views and update the data when scrolling.
- RecyclerView is a more advanced and flexible version of ListView. It's a container for displaying large data sets that can be scrolled efficiently by maintaining a limited number of views.
- event handling is allowed for each item in the list item.

```Java
public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.CakeViewHolder> {
    private final Context context;
    private final Cake[] cakes;

    public CakeAdapter(Context context, Cake[] cakes) {
        this.context = context;
        this.cakes = cakes;
    }

    @NonNull
    @Override
    public CakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View           view     = inflater.inflate(R.layout.cake_row, parent, false);
        return new CakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CakeViewHolder holder, int position) {
        holder.imageView.setImageResource(cakes[position].getImage());
        holder.textView1.setText(cakes[position].getName());
        holder.textView2.setText(cakes[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return cakes.length;
    }

    public static class CakeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView  textView1;
        TextView  textView2;
        public ConstraintLayout constraintLayout;
        public View view;

        public CakeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
```

- in main activity, create a recycler view and set the adapter.

```Java
public class MainActivity extends AppCompatActivity {
    Cake[] cakes = {
        new Cake("Cake 1", "This is cake 1", R.drawable.cake1),
        new Cake("Cake 2", "This is cake 2", R.drawable.cake2),
        new Cake("Cake 3", "This is cake 3", R.drawable.cake3),
        new Cake("Cake 4", "This is cake 4", R.drawable.cake4),
        new Cake("Cake 5", "This is cake 5", R.drawable.cake4),
        new Cake("Cake 6", "This is cake 6", R.drawable.cake4),
        new Cake("Cake 7", "This is cake 7", R.drawable.cake4),
        new Cake("Cake 8", "This is cake 7", R.drawable.cake4)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CakeAdapter adapter = new CakeAdapter(getApplicationContext(), cakes);
        recyclerView.setAdapter(adapter);
    }
}
```
