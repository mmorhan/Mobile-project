package com.example.ticketapp.searching;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ticketapp.main.MainActivity;
import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.userFlight.UserFlightActivity;


public class MainActivity7 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button back;
    TextView seatno;



        //The key argument here must match that used in the other activity


    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    Button checkin;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        Intent intent = getIntent();

        String SeatNumber = intent.getStringExtra("EXTRA_SESSION_ID");
        //drawer
        drawerLayout=findViewById(R.id.drawer_background);

        //variables
seatno=(TextView) findViewById(R.id.textView16);
seatno.setText("Seat Number: "+SeatNumber);
        checkin=(Button)findViewById(R.id.button7);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity7.this, MainActivity.class);
                startActivity(intent);
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
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity7.this);
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
        Intent intent= new Intent(MainActivity7.this, SettingsActivity.class);
        startActivity(intent);
    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MainActivity7.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MainActivity7.this, MessageActivity.class);
        startActivity(intent);
    }



}