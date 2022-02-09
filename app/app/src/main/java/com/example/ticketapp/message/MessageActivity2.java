package com.example.ticketapp.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageActivity2 extends AppCompatActivity {

    DrawerLayout drawerLayout;


    RecyclerView recyclerView;
    MessageFirebaseAdapter adapter;
    FirebaseUser user;

    Button send;
    EditText message_content;
    ImageView person_image;
    TextView person_name;

    ImageButton goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);


        drawerLayout = findViewById(R.id.drawer_background);
        person_name = (TextView) findViewById(R.id.message_person_name);
        person_image = (ImageView) findViewById(R.id.message_person_image);
        send = (Button) findViewById(R.id.message_send);
        message_content = ((EditText) findViewById(R.id.message_value));

        //go back
        goback = (ImageButton) findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Todo intent
        Bundle intent = getIntent().getExtras();
        String personId = intent.getString("personId");
        String personName = intent.getString("personName");
        String personImagePath = intent.getString("personImagePath");
        //

        ImageView imageView = (ImageView) findViewById(R.id.messagebg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/ticketapp-94d2c.appspot.com/o/797193.jpg?alt=media&token=362b71c5-5edb-4298-a394-a287ea54df90").into(imageView);


        //Get current User
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        String finalUid = uid;


        //Todo Set person information
        Picasso.get().load(personImagePath).into(person_image);
        person_name.setText(personName);


        String message = message_content.getText().toString();
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message_value = message_content.getText().toString().trim();
                //Create Message
                if (message_value.length() != 0) {
                    MyMessage message = new MyMessage();
                    message.setMessage(message_content.getText().toString());

                    Long tsLong = System.currentTimeMillis();
                    String id = tsLong.toString();

                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                    Date date = new Date();

                    String currdate = formatter.format(date);
                    message.setId(id);
                    message.setDate(currdate);
                    message.setRecieverId(personId);
                    message.setSenterId(finalUid);

                    //add current user
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("User").child(finalUid).child("Message").child(personId);
                    ref.child(message.getId()).setValue(message);

                    //add reicever
                    DatabaseReference ref2 = database.getReference("User").child(personId).child("Message").child(finalUid);
                    ref2.child(message.getId()).setValue(message);
                    message_content.setText("");
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    Toast.makeText(MessageActivity2.this, "Can't Send Empty Message", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Todo : Firebase

        FirebaseRecyclerOptions<MyMessage> options =
                new FirebaseRecyclerOptions.Builder<MyMessage>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Message").child(personId), MyMessage.class)
                        .build();
        adapter = new MessageFirebaseAdapter(options);
        recyclerView = findViewById(R.id.message_recyler_view_2);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MessageActivity2.this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);

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
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MessageActivity2.this);
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
        Intent intent = new Intent(MessageActivity2.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToMyFlight(View view) {
        Intent intent = new Intent(MessageActivity2.this, UserFlightActivity.class);
        startActivity(intent);
    }

    public void goToCustomer(View view) {
        Intent intent = new Intent(MessageActivity2.this, MessageActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}