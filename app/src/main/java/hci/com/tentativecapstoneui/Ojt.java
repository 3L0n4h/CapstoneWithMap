package hci.com.tentativecapstoneui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class Ojt extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ActionBar actionBar;
    CardView objective;
    CardView policy;
    CardView guidelines;
    CardView grade;


    //ImageView imageView3;
    //ImageView imageView1;
    //ImageView imageView2;
    //ImageView imageView4;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffbb33")));

        objective = (CardView) findViewById(R.id.cardView1);
        objective.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent i = new Intent(Ojt.this, OjtObjective.class);
                startActivity(i);
            }
        });

        policy = (CardView) findViewById(R.id.cardView2);
        policy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent i = new Intent(Ojt.this, OjtPolicy.class);
                startActivity(i);
            }
        });

        guidelines = (CardView) findViewById(R.id.cardView3);
        guidelines.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent i = new Intent(Ojt.this, OjtViewpager.class);
                startActivity(i);
            }
        });

        grade = (CardView) findViewById(R.id.cardView4);
        grade.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent i = new Intent(Ojt.this, OjtGrade.class);
                startActivity(i);
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ojt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            Intent button = new Intent(Ojt.this, MainActivity.class);
            startActivity(button);
        } else if (id == R.id.nav_Map) {
            Intent button = new Intent(Ojt.this, Map.class);
            startActivity(button);
        } else if (id == R.id.nav_Help) {
            Intent button = new Intent(Ojt.this, Help.class);
            startActivity(button);
        } else if (id == R.id.nav_Aboutt) {
            Intent button = new Intent(Ojt.this, Aboutt.class);
            startActivity(button);
        } else if (id == R.id.nav_Ojt) {
            Intent button = new Intent(Ojt.this, Ojt.class);
            startActivity(button);
        }else if (id == R.id.nav_Settings) {
            Intent button = new Intent(Ojt.this, Settings.class);
            startActivity(button);
        }else if (id == R.id.nav_Logout) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}