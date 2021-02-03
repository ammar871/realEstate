package com.ammarreal.realestates.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.ammarreal.realestates.R;
import com.ammarreal.realestates.databinding.ActivityHomeBinding;
import com.ammarreal.realestates.fragments.Add_Real_Fragment;
import com.ammarreal.realestates.fragments.FavoraiteFragment;
import com.ammarreal.realestates.fragments.HomeFragment;
import com.ammarreal.realestates.fragments.SittingFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        loadFragment(new HomeFragment());
        //novgatio
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

//        Intent server = new Intent(Home.this, ListionOrder.class);
//        startService(server);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.center:
                fragment = new HomeFragment();

                break;

            case R.id.fav:

                fragment = new FavoraiteFragment();

                break;
            case R.id.ssetting:

                fragment = new SittingFragment();


                break;

            case R.id.offer:

                fragment = new Add_Real_Fragment();


                break;
            default:

        }
        return loadFragment(fragment);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layotrelattiv, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
