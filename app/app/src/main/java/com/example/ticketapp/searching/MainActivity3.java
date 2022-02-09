package com.example.ticketapp.searching;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.ticketapp.user.User;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class MainActivity3 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
private FirebaseUser user;
    private Button dob,next,useProfile,back;
    private EditText name,surname,tc;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //drawer
        drawerLayout=findViewById(R.id.drawer_background);

        //variables

        name=(EditText) findViewById(R.id.personName4);
        surname=(EditText)findViewById(R.id.personSurname3);
        tc=(EditText)findViewById(R.id.tcnumber2);


        dob = (Button)findViewById(R.id.dateinfo2);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int yy=calendar.get(Calendar.YEAR);
                int mm=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       month+=1;

                       dob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },yy,mm,day);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE,"Select",datePickerDialog);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE,"Cancel",datePickerDialog);
                datePickerDialog.show();

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        String  uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(uid);

        //Todo handle Intent
        Bundle intent=getIntent().getExtras();
        String flightId=intent.getString("flight_id");

       useProfile=(Button)findViewById(R.id.button9);

       useProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {





               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       User fuser;
                       fuser = dataSnapshot.getValue(User.class);
                       if (fuser.getTc().equals("")){
                           Toast.makeText(getApplicationContext(),"Please enter your profile",Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(MainActivity3.this, SettingsActivity.class);
                       startActivity(i);}
                       else{
                       name.setText(fuser.getName());
                       surname.setText(fuser.getLastname());
                       tc.setText(fuser.getTc());
                       dob.setText(fuser.getDob());
                   }}

                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                       System.out.println("The read failed: " + databaseError.getCode());

                   }
               });

           }
       });



        next=(Button)findViewById(R.id.button8);
        next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(name.getText().toString().trim().length()!=0&&surname.getText().toString().trim().length()!=0 && tc.getText().toString().trim().length()==11&&dob.getText().toString().trim().length()!=0&&!dob.getText().equals("//"))

              {
                   Intent intent =new Intent(MainActivity3.this,MainActivity4.class);
                   intent.putExtra("name",name.getText().toString());
                   intent.putExtra("surname",surname.getText().toString());
                   intent.putExtra("Tc",tc.getText().toString());
                   intent.putExtra("date",dob.getText());
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
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity3.this);
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
        Intent intent= new Intent(MainActivity3.this,SettingsActivity.class);
        startActivity(intent);

    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MainActivity3.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MainActivity3.this, MessageActivity.class);
        startActivity(intent);
    }

}