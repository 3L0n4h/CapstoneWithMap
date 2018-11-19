package hci.com.tentativecapstoneui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBar actionBar;
    private FloatingActionButton addPostBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mUsers;
    private String fullname;
    private CircleImageView circleimageview;
    Fragment fragment;
    TextView header_name;
    TextView header_email;
    TabLayout tabs;
    NavigationView navigationView;
    Menu nav_Menu;
    String uid = "";
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#de000000")));

//        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
//        ViewPager Pager =  (ViewPager)findViewById(R.id.viewpager);
//
//        tabPager tabPager = new tabPager(getSupportFragmentManager());
//        Pager.setAdapter(tabPager);
//        tabLayout.setupWithViewPager(Pager);
//
//        tabLayout.getTabAt(0).setIcon(R.mipmap.home);
//        tabLayout.getTabAt(1).setIcon(R.mipmap.backpack);
//        tabLayout.getTabAt(2).setIcon(R.mipmap.text);
//        tabLayout.getTabAt(3).setIcon(R.mipmap.mail);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        myRef = FirebaseDatabase.getInstance().getReference("Users");

        nav_Menu = navigationView.getMenu();


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            uid = mAuth.getCurrentUser().getUid();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Students").hasChild(uid)){
                        nav_Menu.findItem(R.id.nav_Home).setVisible(true);
                        nav_Menu.findItem(R.id.nav_pending).setVisible(false);
                        nav_Menu.findItem(R.id.nav_Map).setVisible(false);
                        nav_Menu.findItem(R.id.nav_Help).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Settings).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Aboutt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Ojt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Logout).setVisible(true);
                    }else if(dataSnapshot.child("Admins").hasChild(uid)){
                        nav_Menu.findItem(R.id.nav_Home).setVisible(true);
                        nav_Menu.findItem(R.id.nav_pending).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Map).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Help).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Settings).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Aboutt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Ojt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Logout).setVisible(true);
                        nav_Menu.findItem(R.id.nav_theMap).setVisible(true);
                    }else{
                        nav_Menu.findItem(R.id.nav_Home).setVisible(true);
                        nav_Menu.findItem(R.id.nav_pending).setVisible(false);
                        nav_Menu.findItem(R.id.nav_Map).setVisible(false);
                        nav_Menu.findItem(R.id.nav_Help).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Settings).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Aboutt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Ojt).setVisible(true);
                        nav_Menu.findItem(R.id.nav_Logout).setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        tabs = findViewById(R.id.tabs);
//        ViewPager Pager =  (ViewPager)findViewById(R.id.viewpager);

        tabPager tabPager = new tabPager(getSupportFragmentManager());
//        Pager.setAdapter(tabPager);
//        tabLayout.setupWithViewPager(Pager);

//        addPostBtn = findViewById(R.id.add_post_btn);

        fragment = new One();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        setTitle("News Feed");
//        addPostBtn.setVisibility(View.VISIBLE);

        tabs.getTabAt(0).setIcon(R.mipmap.home);
        tabs.getTabAt(1).setIcon(R.mipmap.backpack);
        tabs.getTabAt(2).setIcon(R.mipmap.text);
        tabs.getTabAt(3).setIcon(R.mipmap.mail);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new One();
                        setTitle("News Feed");
                        break;
                    case 1:
                        fragment = new Two();
                        setTitle("Backpack");
                        break;
                    case 2:
                        fragment = new Three();
                        setTitle("Chat");
                        break;
                    case 3:
                        fragment = new Four();
                        setTitle("Notification");
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new One();
                        setTitle("News Feed");
                        break;
                    case 1:
                        fragment = new Two();
                        setTitle("Backpack");
                        break;
                    case 2:
                        fragment = new Three();
                        setTitle("Chat");
                        break;
                    case 3:
                        fragment = new Four();
                        setTitle("Notification");
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        header_name = headerView.findViewById(R.id.acc_name);
        header_email = headerView.findViewById(R.id.acc_email);
        circleimageview = headerView.findViewById(R.id.acc_image);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mUsers = mDatabase.getReference().child("Users");

        if (mAuth.getCurrentUser() != null) {
            mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("Students").hasChild(mAuth.getCurrentUser().getUid())) {
                        String firstName = dataSnapshot.child("Students").child(mAuth.getCurrentUser().getUid()).child("FirstName").getValue().toString();
                        String lastName = dataSnapshot.child("Students").child(mAuth.getCurrentUser().getUid()).child("LastName").getValue().toString();
                        String email = dataSnapshot.child("Students").child(mAuth.getCurrentUser().getUid()).child("Email").getValue().toString();
                        String imageUrl = dataSnapshot.child("Students").child(mAuth.getCurrentUser().getUid()).child("ImageUrl").getValue().toString();

                        fullname = firstName + " " + lastName;

                        header_name.setText("Welcome, " + fullname);
                        header_email.setText(email);
                        Picasso.with(MainActivity.this).load(imageUrl).into(circleimageview);
                    } else if (dataSnapshot.child("Admins").hasChild(mAuth.getCurrentUser().getUid())) {
                        String firstName = dataSnapshot.child("Admins").child(mAuth.getCurrentUser().getUid()).child("FirstName").getValue().toString();
                        String lastName = dataSnapshot.child("Admins").child(mAuth.getCurrentUser().getUid()).child("LastName").getValue().toString();
                        String email = dataSnapshot.child("Admins").child(mAuth.getCurrentUser().getUid()).child("Email").getValue().toString();
                        String imageUrl = dataSnapshot.child("Admins").child(mAuth.getCurrentUser().getUid()).child("ImageUrl").getValue().toString();

                        fullname = firstName + " " + lastName;

                        header_name.setText("Welcome, " + fullname);
                        header_email.setText(email);
                        Picasso.with(MainActivity.this).load(imageUrl).into(circleimageview);
                    } else {
                        Toast.makeText(MainActivity.this, "User account not found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


//        addPostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent newPostIntent = new Intent(MainActivity.this, PostActivity.class);
//                startActivity(newPostIntent);
//
//            }
//        });

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            Intent button = new Intent(MainActivity.this, MainActivity.class);
            button.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(button);
        } else if (id == R.id.nav_pending) {
            Intent button = new Intent(MainActivity.this, PendingPost.class);
            startActivity(button);
        } else if (id == R.id.nav_Map) {

            tabs.setVisibility(View.GONE);

            setTitle("Upload Class List");
            fragment = new UploadCSV();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        } else if (id == R.id.nav_Help) {
            Intent button = new Intent(MainActivity.this, Help.class);
            startActivity(button);
        } else if (id == R.id.nav_Aboutt) {
            Intent button = new Intent(MainActivity.this, Aboutt.class);
            startActivity(button);
        } else if (id == R.id.nav_Ojt) {
            Intent button = new Intent(MainActivity.this, Ojt.class);
            startActivity(button);
        } else if (id == R.id.nav_Settings) {
            tabs.setVisibility(View.GONE);

            setTitle("Account Settings");
            fragment = new Settings();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        } else if (id == R.id.nav_Logout) {
            FirebaseAuth.getInstance().signOut();
            Intent signOut = new Intent(MainActivity.this, Login.class);
            signOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signOut);
        } else if(id == R.id.nav_theMap){
            Intent theMap = new Intent(MainActivity.this, Map.class);
            startActivity(theMap);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}