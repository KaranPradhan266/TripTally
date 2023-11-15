package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private CardView trip,pie,member,help,expenses,md,h,shareme;
    String ftrip;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String userid = user.getUid();
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        trip = (CardView) findViewById(R.id.cardtrip);
        pie = (CardView) findViewById(R.id.cardpie);
        member = (CardView) findViewById(R.id.cardmember);
        help = (CardView) findViewById(R.id.cardhelp);
        expenses = (CardView)findViewById(R.id.cardexpenser);
        md = (CardView) findViewById(R.id.carddetails);
        h= (CardView) findViewById(R.id.cardhistory);
        shareme = (CardView) findViewById(R.id.cardshare);

        trip.setOnClickListener(this);
        pie.setOnClickListener(this);
        member.setOnClickListener(this);
        help.setOnClickListener(this);
        expenses.setOnClickListener(this);
        md.setOnClickListener(this);
        h.setOnClickListener(this);
        shareme.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.nav_home:
                break;

            case R.id.nav_about:
                Intent intent = new Intent(dashboard.this,about.class);
                startActivity(intent);
                break;

            case R.id.nav_Delete:
                Intent intent1 =new Intent(dashboard.this,delete.class);
                startActivity(intent1);
                break;

            case R.id.nav_feedback:
                Intent intent2 = new Intent(dashboard.this,feedback.class);
                startActivity(intent2);
                break;

            case R.id.nav_Profile:
                Intent intent3 = new Intent(dashboard.this,profile.class);
                startActivity(intent3);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(dashboard.this,login.class));
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.cardtrip:
                Intent intent  = new Intent(dashboard.this,trip.class);
                startActivity(intent);
                break;

            case R.id.cardhelp:
                Intent intent1 = new Intent(dashboard.this,help.class);
                startActivity(intent1);
                break;

            case R.id.cardmember:

                Intent intent2 = new Intent(dashboard.this,adedimember.class);
                startActivity(intent2);
                break;



            case R.id.cardpie:
                Intent intent3 = new Intent(dashboard.this,piechart.class);
                startActivity(intent3);
                break;

            case R.id.carddetails:
                Intent intent4 = new Intent(dashboard.this,money.class);
                startActivity(intent4);
                break;

            case R.id.cardexpenser:
                Intent intent5 = new Intent(dashboard.this,expenses.class);
                startActivity(intent5);
                break;

            case R.id.cardhistory:
                Intent intent6 =new Intent(dashboard.this,history.class);
                startActivity(intent6);
                break;

            case R.id.cardshare:
                Intent intent7 = new Intent(dashboard.this,share.class);
                startActivity(intent7);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }
}