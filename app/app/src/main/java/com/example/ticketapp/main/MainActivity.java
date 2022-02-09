package com.example.ticketapp.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ticketapp.loginSignup.LoginActivity;
import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.user.User;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    PagerAdapter pagerAdapter;
    TabLayout tabLayout;
    TabItem main,search,profile;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    boolean signed=false;
    private FirebaseAuth mAuth;
    private ImageButton main_to_login;
    private FirebaseUser user;
    @Override
    public void onStart() {
        super.onStart();
    }

    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(signed) {
            Log.d("myinfo","Im here 3"+signed);
            Log.d("myinfo","signed "+signed);

        }



        //drawer
        drawerLayout=findViewById(R.id.drawer_background);
        photo=(ImageView)findViewById(R.id.drawer_photo);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(uid);
// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User fuser;
                fuser = dataSnapshot.getValue(User.class);
                if (!fuser.getPhoto().equals(""))
                    Picasso.get().load(fuser.getPhoto()).into(photo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //Tablayout
        tabLayout=findViewById(R.id.tabLayout);
        main=findViewById(R.id.main);
        search=findViewById(R.id.search);
        profile=findViewById(R.id.profile);
        viewPager=findViewById(R.id.viewPager);
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }







    //Todo drawer
    public void onClickMenu(View view) {
        openDrawer(drawerLayout);

    }

    public void openDrawer (DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void onClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void quit(View view) {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity.this);
        alertdialog.setTitle("Quit");
        alertdialog.setMessage("Are you Sure?");
        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertdialog.show();
    }

    public void drawer_settings(View view) {
                Intent intent= new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MainActivity.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MainActivity.this, MessageActivity.class);
        startActivity(intent);
    }


}