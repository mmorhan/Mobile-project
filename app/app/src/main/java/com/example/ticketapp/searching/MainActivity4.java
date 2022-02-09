package com.example.ticketapp.searching;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity4 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button back;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    EditText creditCard, name, cvv, date;
    CheckBox rememberMe;
    Button next;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        //drawer
        drawerLayout = findViewById(R.id.drawer_background);

        //variables


        creditCard = findViewById(R.id.ccnumber);
        name = findViewById(R.id.ccnumber2);
        cvv = findViewById(R.id.ccnumber3);
        date = findViewById(R.id.ccnumber4);
        rememberMe = (CheckBox) findViewById(R.id.checkBox2);


        //Todo handle Intent
        Bundle intent = getIntent().getExtras();
        String flightId = intent.getString("flight_id");


        next = (Button) findViewById(R.id.button4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(name.getText().toString().trim().length()!=0&&cvv.getText().toString().trim().length()==3 && creditCard.getText().toString().trim().length()==16&&date.getText().toString().trim().length()==5)
                {


                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                intent.putExtra("flight_id",flightId);
                startActivity(intent);
                }
               else{
                    Toast.makeText(getApplicationContext(),"Please enter correctly",Toast.LENGTH_SHORT).show();
                }

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

    public void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void onClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void quit(View view) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity4.this);
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
        Intent intent= new Intent(MainActivity4.this, SettingsActivity.class);
        startActivity(intent);

    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MainActivity4.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MainActivity4.this, MessageActivity.class);
        startActivity(intent);
    }

}