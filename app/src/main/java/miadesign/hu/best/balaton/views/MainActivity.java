package miadesign.hu.best.balaton.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.models.networks.DataService;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_STATE = "fragment_save";
    private static final String HOME_TAG = "home";
    private static final String MAP_TAG = "map";
    private static final String FAVS_TAG = "favs";
    private Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        receiver = new Receiver();

        if (savedInstanceState == null) {
            manageFragment(HOME_TAG);
        } else {
            String tag = savedInstanceState.getString(FRAGMENT_STATE);
            assert tag != null;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.setPrimaryNavigationFragment(fragment);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.commitNowAllowingStateLoss();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    manageFragment(HOME_TAG);
                    return true;
                case R.id.navigation_map:
                    manageFragment(MAP_TAG);
                    return true;
                case R.id.navigation_favorite:
                    manageFragment(FAVS_TAG);
                    return true;
            }
            return false;
        });

        Intent intent = new Intent(this, DataService.class);
        startService(intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        outState.putString(FRAGMENT_STATE, fragmentManager.getPrimaryNavigationFragment().getTag());
    }

    @Override
    public void onResume() {

        super.onResume();
        registerReceiver(receiver, new IntentFilter("miadesign.hu.best.balaton"));

    }

    @Override
    public void onPause() {

        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void manageFragment(String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            switch (tag) {
                case HOME_TAG:
                    fragment = new AttractionListFragment();
                    break;
                case MAP_TAG:
                    fragment = new MapFragment();
                    break;
                case FAVS_TAG:
                    fragment = new FavoriteFragment();
                    break;
            }
            fragmentTransaction.add(R.id.content, fragment, tag);
        } else {
            fragmentTransaction.attach(fragment);
        }

        Fragment current = fragmentManager.getPrimaryNavigationFragment();
        if (current != null && current != fragment) {
            fragmentTransaction.detach(current);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();

    }


    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String error = intent.getStringExtra(App.INTENT_ERROR);
            View view = findViewById(R.id.toolbar);
            Snackbar.make(view, error, Snackbar.LENGTH_LONG).show();

        }

    }

}
