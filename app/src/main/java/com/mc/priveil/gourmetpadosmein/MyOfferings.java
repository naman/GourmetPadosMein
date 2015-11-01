package com.mc.priveil.gourmetpadosmein;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MyOfferings extends AppCompatActivity {
    /*
        private DrawerLayout mDrawerLayout;
        private Toolbar mToolbar;
        private ActionBarDrawerToggle mActionBarDrawerToggle;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offerings);
      /*  setUpToolbar();
        setUpNavDrawer();
        		*//* Use application class to maintain global state. *//*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/
        Toast.makeText(MyOfferings.this, "My Hostings", Toast.LENGTH_SHORT).show();
    }

  /*  private void setUpToolbar() {
        Log.i("Testing12", "Came in setUpToolBar");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        Log.i("Testing12", "Came out setUpToolBar");
    }

    private void setUpNavDrawer() {
        Log.i("Testing12", "Came in setUpNav");
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
        Log.i("Testing12", "Came out setUpNav");
    }

    private void setupDrawerContent(NavigationView navigationView) {
        Log.i("Testing12", "Came in setUpDrawer");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.offering_list:
                                Intent n = new Intent(OfferingForm.this, OfferingListActivity.class);
                                n.putExtra(MESSAGE_NAME, name);
                                n.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n);
                                break;

                            case R.id.profile:
                                Intent ui = new Intent(OfferingForm.this, UserInfo.class);
                                ui.putExtra(MESSAGE_NAME, name);
                                ui.putExtra(MESSAGE_EMAIL, email);

                                startActivity(ui);
                                break;
                            case R.id.my_offerings:
                                Intent n1 = new Intent(OfferingForm.this, MyOfferings.class);
                                n1.putExtra(MESSAGE_NAME, name);
                                n1.putExtra(MESSAGE_EMAIL, email);
                                startActivity(n1);

                                break;

                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        Log.i("Testing12", "Came out setUpDrawer");
    }
*/

}
