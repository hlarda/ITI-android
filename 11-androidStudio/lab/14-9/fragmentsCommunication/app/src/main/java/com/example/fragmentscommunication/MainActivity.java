package com.example.fragmentscommunication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements Communicator {

    private static final String DYNAMIC_FRAGMENT_TAG = "dynamicFragmentView";
    private StaticCounterFragment staticCounterFragment;
    private DynamicViewerFragment dynamicViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            staticCounterFragment = new StaticCounterFragment();
            dynamicViewerFragment = new DynamicViewerFragment();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.staticfra, staticCounterFragment, "staticCounterFragment");
            fragmentTransaction.add(R.id.dynamicfra, dynamicViewerFragment, DYNAMIC_FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else {
            staticCounterFragment = (StaticCounterFragment) getSupportFragmentManager().findFragmentByTag("staticCounterFragment");
            dynamicViewerFragment = (DynamicViewerFragment) getSupportFragmentManager().findFragmentByTag(DYNAMIC_FRAGMENT_TAG);
        }
    }

    @Override
    public void respond(int counter) {
            dynamicViewerFragment.updateCounter(counter);
    }
}
