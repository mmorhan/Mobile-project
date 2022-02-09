package com.example.ticketapp.searching;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.example.ticketapp.flight.Flight;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity6 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button back;
    String SeatNumber = "";

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    FirebaseUser user;
    Button finishcheckin;
    //Todo get this dynamically
    int selected = 0;
    int seat;

    ViewGroup layout;
    String seats = "/"
            + "_______/"
            + "AAA_AAA/"
            + "AAA_AAA/"
            + "AUU_AAA/"
            + "AAA_AUA/"
            + "AAA_AAA/"
            + "AUA_AAA/"
            + "AAA_AUA/"
            + "AAA_UUA/"
            + "_______";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    String selectedIds = "";


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        //TODO handle intent
        Bundle intent = getIntent().getExtras();
        String flightId = intent.getString("flight_id");


        //drawer
        drawerLayout = findViewById(R.id.drawer_background);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        String finalUid = uid;

        //variables
        finishcheckin = (Button) findViewById(R.id.finishCheckin);
        finishcheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //todo ticket bought add flightId to user's flight
                final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference ref2 = database2.getReference("User").child(user.getUid()).child("Flight");


                //todo:and update current flag seat and add passenger

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Flight").child(flightId);
// Attach a listener to read the data at our posts reference
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Flight flight;
                        flight = dataSnapshot.getValue(Flight.class);

                        //Flight
                        ref.child("seat").child(String.valueOf(seat)).setValue(finalUid);

                        //user's
                        Flight myFlight = new Flight();
                        myFlight.setLogo(flight.getLogo());
                        myFlight.setDepartureTime(flight.getDepartureTime());
                        myFlight.setDeparture(flight.getDeparture());
                        myFlight.setArriveTime(flight.getArriveTime());
                        myFlight.setArrive(flight.getArrive());
                        myFlight.setId(flightId);
                        myFlight.setCompany(flight.getCompany());
                        myFlight.setPrice(flight.getPrice());
                        myFlight.setDate(flight.getDate());
                        ref2.child(flightId).setValue(myFlight);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());

                    }
                });

                if (!SeatNumber.equals("")) {

                    Intent intent = new Intent(MainActivity6.this, MainActivity7.class);
                    intent.putExtra("EXTRA_SESSION_ID", SeatNumber);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity6.this, "Please select a seat", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.selected_seat_photo);
        //buttons seats


        layout = findViewById(R.id.layoutSeat);

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.mipmap.selected_seat_photo);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);

                view.setOnClickListener(new seatClick());
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.mipmap.seat_photo);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(new seatClick());
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
        //Todo Go Back
        ImageButton goback = (ImageButton) findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public class seatClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {


            if ((int) view.getTag() == STATUS_AVAILABLE) {
                if (selectedIds.contains(view.getId() + ",")) {
                    selectedIds = selectedIds.replace(+view.getId() + ",", "");
                    selected--;
                    SeatNumber = "";
                    view.setBackgroundResource(R.mipmap.seat_photo);
                } else {
                    if (selected == 0) {
                        selectedIds = selectedIds + view.getId() + ",";
                        selected++;
                        SeatNumber = String.valueOf(view.getId());
                        view.setBackgroundResource(R.mipmap.selected_seat_photo);
                    } else {
                        Toast.makeText(MainActivity6.this, "You can't' select multiple seats", Toast.LENGTH_SHORT).show();
                    }

                }
            } else if ((int) view.getTag() == STATUS_BOOKED) {
                Toast.makeText(MainActivity6.this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
            }
        }
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
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity6.this);
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
        Intent intent = new Intent(MainActivity6.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToMyFlight(View view) {
        Intent intent = new Intent(MainActivity6.this, UserFlightActivity.class);
        startActivity(intent);
    }

    public void goToCustomer(View view) {
        Intent intent = new Intent(MainActivity6.this, MessageActivity.class);
        startActivity(intent);
    }
}