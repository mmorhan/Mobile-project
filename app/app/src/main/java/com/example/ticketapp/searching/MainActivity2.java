package com.example.ticketapp.searching;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.user.User;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.example.ticketapp.flight.Flight;
import com.example.ticketapp.flight.FlightFirebaseAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MainActivity2 extends AppCompatActivity{
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    FlightFirebaseAdapter adapter;
    LinearLayout layout;
    Button back;
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //Todo handle Intent
        Bundle intent=getIntent().getExtras();
        String search=intent.getString("search");
        String searched_date=intent.getString("date");


        //drawer
        drawerLayout=findViewById(R.id.drawer_background);
        //Firabase
        Log.d("myinfo",search);
        System.out.println(search);
        Query query=FirebaseDatabase.getInstance().getReference().child("Flight")
               .orderByChild("search").equalTo(search);
        FirebaseRecyclerOptions<Flight> options=
                new FirebaseRecyclerOptions.Builder<Flight>()
                .setQuery(query,Flight.class)
                .build();

        //find a way to filter

        //Todo Go Back
        ImageButton goback=(ImageButton)findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter =new FlightFirebaseAdapter(options);
        recyclerView=findViewById(R.id.flight_recycler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        View myLayout = View.inflate(this,R.layout.flight_list,null);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        ImageView photo=(ImageView)findViewById(R.id.drawer_photo);
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
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity2.this);
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
        Intent intent= new Intent(MainActivity2.this, SettingsActivity.class);
        startActivity(intent);

    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MainActivity2.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MainActivity2.this, MessageActivity.class);
        startActivity(intent);
    }
}