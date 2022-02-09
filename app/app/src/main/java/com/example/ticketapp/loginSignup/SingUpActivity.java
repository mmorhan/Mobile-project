package com.example.ticketapp.loginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketapp.R;
import com.example.ticketapp.content.Content;
import com.example.ticketapp.flight.Flight;
import com.example.ticketapp.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String password2;
    private Button singUp;
    private TextView singup_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mAuth = FirebaseAuth.getInstance();

        singUp = findViewById(R.id.signUp);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ((EditText) findViewById(R.id.personName)).getText().toString();
                surname = ((EditText) findViewById(R.id.personSurname)).getText().toString();
                mail = ((EditText) findViewById(R.id.EmailAddress)).getText().toString();
                password = ((EditText) findViewById(R.id.Password)).getText().toString();
                password2 = ((EditText) findViewById(R.id.Password2)).getText().toString();


                if (!(mail.equals("") || password.equals(""))) {

                    if (password.equals(password2)) {
                        //Todo: add firabase method
                        addFirebase();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords not match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "mail or password can't be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //redirect to login page
        singup_to_login = (TextView) findViewById(R.id.gotologin);
        singup_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private DatabaseReference databaseReference;

    public void addFirebase() {
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("info", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            User myUser = new User();

                            myUser.setId(user.getUid());
                            myUser.setPhoto("");
                            myUser.setName(name);
                            myUser.setLastname(surname);
                            myUser.setEmail(mail);
                            myUser.setPassword(password);
                            myUser.setAddress("");
                            myUser.setPhone("");
                            myUser.setTc("");
                            myUser.setDob("");


                            //add user to database

                            databaseReference = FirebaseDatabase.getInstance().getReference("User");
                            String id = myUser.getId();
                            databaseReference.child(id).setValue(myUser);


                            //TODO INITILIZATE DEFULT CONTENT , FLIGHT AND CUSTOMER SUPPORt

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("User").child(user.getUid()).child("Flight");
                            DatabaseReference ref1 = database.getReference("User").child(user.getUid()).child("Friend");
                            DatabaseReference ref2 = database.getReference("User").child(user.getUid()).child("Content");

                            User customer = new User();
                            customer.setId("1");
                            customer.setName("Customer");
                            customer.setLastname("Support");
                            customer.setEmail("customer@support.com");
                            customer.setPhone("90 555 555 55 55");
                            customer.setPhoto("https://firebasestorage.googleapis.com/v0/b/ticketapp-94d2c.appspot.com/o/24x7-customer-support.png?alt=media&token=fd3dab98-6667-48c3-b3e6-b0d0645421bb");

                            ref1.child("1").setValue(customer);

                            Content content = new Content();
                            content.setSource("App");
                            content.setDescrption("Welcome to our App");
                            content.setId("1");
                            content.setImagepath("https://firebasestorage.googleapis.com/v0/b/ticketapp-94d2c.appspot.com/o/24x7-customer-support.png?alt=media&token=fd3dab98-6667-48c3-b3e6-b0d0645421bb");
                            content.setDate("");

                            ref2.child("1").setValue(content);

                            mAuth.signOut();
                            Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("info", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SingUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}