package com.example.durbinlabs.cusomnavigationdrawer;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static int[] drawer_icons = {android.R.drawable.alert_dark_frame,
            android.R.drawable.alert_light_frame,
            android.R.drawable.dark_header};

    TextView tv_selected_navigation;

    ArrayList<String> navigation_items;
    private DrawerListAdapter drawerListAdapter;
    private ListView lv_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        SetDrawer();
    }


    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_selected_navigation = (TextView) findViewById(R.id.tv_selected_navigation);

        navigation_items = new ArrayList<>();

//adding menu items for naviations
        navigation_items.add("Call");
        navigation_items.add("Favorite");
        navigation_items.add("Search");

        lv_drawer = (ListView) findViewById(R.id.lv_drawer);

    }

    private void SetDrawer() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawerListAdapter = new DrawerListAdapter(MainActivity.this, navigation_items, drawer_icons);
        lv_drawer.setAdapter(drawerListAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (navigation_items.get(position).equalsIgnoreCase("Call")) {

                    tv_selected_navigation.setText("Selected Call");

                } else if (navigation_items.get(position).equalsIgnoreCase("Favorite")) {

                    tv_selected_navigation.setText("Selected Favorite");

                } else if (navigation_items.get(position).equalsIgnoreCase("Search")) {

                    tv_selected_navigation.setText("Selected Search");

                }

            }
        });

    }
}