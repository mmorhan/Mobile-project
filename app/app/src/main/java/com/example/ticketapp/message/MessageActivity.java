package com.example.ticketapp.message;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.user.User;
import com.example.ticketapp.user.UserFirabaseAdapter;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    UserFirabaseAdapter adapter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        drawerLayout=findViewById(R.id.drawer_background);
        //Todo Go Back
        ImageButton goback=(ImageButton)findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //todo firebase


        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Friend"), User.class)
                        .build();

        adapter=new UserFirabaseAdapter(options);
        recyclerView=findViewById(R.id.message_recyler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(MessageActivity.this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

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
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MessageActivity.this);
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
        Intent intent= new Intent(MessageActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    public  void goToMyFlight (View view){
        Intent intent= new Intent(MessageActivity.this, UserFlightActivity.class);
        startActivity(intent);
    }
    public  void goToCustomer(View view){
        Intent intent= new Intent(MessageActivity.this,MessageActivity.class);
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