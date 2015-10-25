package com.mc.priveil.gourmetpadosmein;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mc.priveil.gourmetpadosmein.Fragments.OfferingFragment;
import com.mc.priveil.gourmetpadosmein.Models.FoodOffering;

import java.util.List;

public class OfferingListActivity extends AppCompatActivity {

    public static final String CLASS_NAME = "MainActivity";
    List<FoodOffering> itemList;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerings);
        setUpToolbar();
        setUpNavDrawer();

      /*  Button but = (Button) findViewById(R.id.button);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
//                ArrayList<FoodOffering> offerings = new ArrayList<>();

        FoodOffering food1 = new FoodOffering("Puri", "North Indian");
//                offerings.add(food1);

        Bundle bundle = new Bundle();
        bundle.putSerializable("offerings", food1);

        OfferingFragment fragment = new OfferingFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();
//            }
//        });


		/* Use application class to maintain global state. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

    }


    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            final android.support.v7.app.ActionBar ab = getSupportActionBar();
            assert ab != null;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            mActionBarDrawerToggle.syncState();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_feed:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new OfferingFragment()).commit();
                                break;
                            case R.id.nav_updates:
                              /*  Intent post = new Intent(OfferingListActivity.this, NewOfferingActivity.class);
                                startActivity(post);*/
                                break;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

    }
}
