package com.example.dutysync;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SideNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_navigation);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Assignduty()).commit();
            navigationView.setCheckedItem(R.id.nav_assign);

      }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.nav_assign){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Assignduty()).commit();
            return true;

        } else if (id==R.id.nav_postatten) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Postattendence()).commit();
            return true;

        } else if (id==R.id.nav_viewatten) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Viewattendence()).commit();
            return true;

        } else if (id==R.id.nav_logout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Logout()).commit();
            return true;

        }drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}