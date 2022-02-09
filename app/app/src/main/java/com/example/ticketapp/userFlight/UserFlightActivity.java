package com.example.ticketapp.userFlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.flight.Flight;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFlightActivity extends AppCompatActivity {


    Button pastFlight,futureFlight;
    DrawerLayout drawerLayout;


    FrameLayout user_past_flight,user_future_flight;


    RecyclerView recyclerViewPast;
    UserFlightFirebaseAdapter adapterPast;

    RecyclerView recyclerViewFuture;
    UserFlightFirebaseAdapter adapterFuture;

    FirebaseUser user;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flight);

       pastFlight=(Button)findViewById(R.id.past_flight);
       futureFlight=(Button)findViewById(R.id.future_flight);
       user_past_flight=(FrameLayout)findViewById(R.id.user_past_flight);
       user_future_flight=(FrameLayout)findViewById(R.id.user_future_flight);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDateTime now = LocalDateTime.now();

        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d1 = sdformat.parse("2019-04-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date


        pastFlight.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View v) {

              pastFlight.setBackgroundTintList(ContextCompat.getColorStateList(UserFlightActivity.this,R.color.black));
              futureFlight.setBackgroundTintList(ContextCompat.getColorStateList(UserFlightActivity.this,R.color.gray));
              user_future_flight.setVisibility(View.GONE);
              user_past_flight.setVisibility(View.VISIBLE);
              recyclerViewPast.setVisibility(View.VISIBLE);
           }
       });


       futureFlight.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View v) {
               futureFlight.setBackgroundTintList(ContextCompat.getColorStateList(UserFlightActivity.this,R.color.black));
               pastFlight.setBackgroundTintList(ContextCompat.getColorStateList(UserFlightActivity.this,R.color.gray));
               user_past_flight.setVisibility(View.GONE);
               user_future_flight.setVisibility(View.VISIBLE);
               recyclerViewFuture.setVisibility(View.VISIBLE);
           }
       });
        //Todo Go Back
        ImageButton goback=(ImageButton)findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Drawer
        drawerLayout=findViewById(R.id.drawer_background);

        //Get user id
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        //Todo firabase past Flight

        FirebaseRecyclerOptions<Flight> options=
                new FirebaseRecyclerOptions.Builder<Flight>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Flight"),Flight.class)
                        .build();

        adapterPast=new UserFlightFirebaseAdapter(options);
        recyclerViewPast=findViewById(R.id.user_past_flight_recyler_view);
        recyclerViewPast.setAdapter(adapterPast);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPast.setLayoutManager(layoutManager);

        //Todo firebase future Flight

        FirebaseRecyclerOptions<Flight> options2=
                new FirebaseRecyclerOptions.Builder<Flight>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Flight"),Flight.class)
                        .build();

        adapterFuture=new UserFlightFirebaseAdapter(options2);
        recyclerViewFuture=findViewById(R.id.user_future_flight_recyler_view);
        recyclerViewFuture.setAdapter(adapterFuture);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);

        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewFuture.setLayoutManager(layoutManager2);
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
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(UserFlightActivity.this);
        alertdialog.setTitle("Quit");
        alertdialog.setMessage("Are you Sure?");
        alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
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
        Intent intent= new Intent(UserFlightActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterFuture.startListening();
        adapterPast.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterPast.stopListening();
        adapterFuture.stopListening();
    }
}