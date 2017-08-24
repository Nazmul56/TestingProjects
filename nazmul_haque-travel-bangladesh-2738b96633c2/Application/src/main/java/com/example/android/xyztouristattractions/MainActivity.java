package com.example.android.xyztouristattractions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.xyztouristattractions.common.Utils;
import com.example.android.xyztouristattractions.service.UtilityService;
import com.example.android.xyztouristattractions.ui.AttractionListFragment;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback , NavigationView.OnNavigationItemSelectedListener {
private static final int PERMISSION_REQ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AttractionListFragment())
                    .commit();
        }
        // Check fine location permission has been granted
        if (!Utils.checkFineLocationPermission(this)) {
            // See if user has denied permission in the past
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show a simple snackbar explaining the request instead
                showPermissionSnackbar();
            } else {
                // Otherwise request permission from user
                if (savedInstanceState == null) {
                    requestFineLocationPermission();
                }
            }
        } else {
            // Otherwise permission is granted (which is always the case on pre-M devices)
            fineLocationPermissionGranted();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Permissions request result callback
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQ:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fineLocationPermissionGranted();
                }
        }
    }

    /**
     * Request the fine location permission from the user
     */
    private void requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ);
    }

    /**
     * Run when fine location permission has been granted
     */
    private void fineLocationPermissionGranted() {
        UtilityService.addGeofences(this);
        UtilityService.requestLocation(this);
    }

    /**
     * Show a permission explanation snackbar
     */
    private void showPermissionSnackbar() {
        Snackbar.make(
                findViewById(R.id.container), R.string.permission_explanation, Snackbar.LENGTH_LONG)
                .setAction(R.string.permission_explanation_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestFineLocationPermission();
                    }
                })
                .show();
    }

    /**
     * Show a basic debug dialog to provide more info on the built-in debug
     * options.
     */
    private void showDebugDialog(int titleResId, int bodyResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(bodyResId)
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.test_notification:
                UtilityService.triggerWearTest(this, false);
                showDebugDialog(R.string.action_test_notification,
                        R.string.action_test_notification_dialog);
                return true;
            case R.id.test_microapp:
                UtilityService.triggerWearTest(this, true);
                showDebugDialog(R.string.action_test_microapp,
                        R.string.action_test_microapp_dialog);
                return true;
            case R.id.test_toggle_geofence:
                boolean geofenceEnabled = Utils.getGeofenceEnabled(this);
                Utils.storeGeofenceEnabled(this, !geofenceEnabled);
                Toast.makeText(this, geofenceEnabled ?
                        "Debug: Geofencing trigger disabled" :
                        "Debug: Geofencing trigger enabled", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);

        //noinspection SimplifiableIfStatement
        //int id = item.getItem();
      /*  if (id == R.id.action_settings) {


            return true;
        }*/

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
