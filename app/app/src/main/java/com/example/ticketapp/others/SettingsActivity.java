package com.example.ticketapp.others;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketapp.R;
import com.example.ticketapp.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.content.ContentResolver;

import android.webkit.MimeTypeMap;

import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {


    TextView personName, personSurname, personEmail, personTC, personPhone, personPassword,changePhoto;
    ImageView photo;
    Button save, disconnect,personDob,back;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mDatabase;
    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    ProgressDialog progressDialog ;
    int Image_Request_Code = 7;
    Uri FilePathUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storageReference = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_settings);

changePhoto=(TextView) findViewById(R.id.changephoto3);
        photo = (ImageView) findViewById(R.id.photo3);
        personName = (TextView) findViewById(R.id.personName3);
        personSurname = (TextView) findViewById(R.id.Surname3);
        personEmail = (TextView) findViewById(R.id.emailAddress3);
        personPassword = (TextView) findViewById(R.id.password3);
        personTC = (TextView) findViewById(R.id.tcnumber3);
        personPhone = (TextView) findViewById(R.id.phone3);
        save=(Button)findViewById(R.id.save3);
personDob=(Button)findViewById(R.id.dateinfo);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String  uid = null;
        if (user != null) {
            uid = user.getUid();
        }

        //Todo Go Back
        ImageButton goback=(ImageButton)findViewById(R.id.go_back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
                personName.setText(fuser.getName());
                personSurname.setText(fuser.getLastname());
                personEmail.setText(fuser.getEmail());
                personPassword.setText(fuser.getPassword());
                personPhone.setText(fuser.getPhone());
                personTC.setText(fuser.getTc());
                personDob.setText(fuser.getDob());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });







        personDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int yy=calendar.get(Calendar.YEAR);
                int mm=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                Toast.makeText(getApplicationContext(),"dhskfdajs",Toast.LENGTH_LONG);
                DatePickerDialog datePickerDialog=new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;

                        personDob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },yy,mm,day);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE,"Select",datePickerDialog);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE,"Cancel",datePickerDialog);
                datePickerDialog.show();

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");

        progressDialog = new ProgressDialog(SettingsActivity.this);// context name as per your project name


        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
                {
                    if (FilePathUri != null) {

                        progressDialog.setTitle("Image is Uploading...");
                        progressDialog.show();
                        StorageReference storageReference2 = storageReference.child(user.getUid() + "." + GetFileExtension(FilePathUri));
                        storageReference2.putFile(FilePathUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        String TempImageName = user.getUid();
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                        @SuppressWarnings("VisibleForTests")
                                        upload_info imageUploadInfo = new upload_info(TempImageName, taskSnapshot.getUploadSessionUri().toString());
                                        String ImageUploadId = databaseReference.push().getKey();
                                        databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String photoStringLink = uri.toString();
                                                ref.child("photo").setValue(photoStringLink);
                                            }
                                        });

                                    }
                                });
                    } else {

                        Toast.makeText(SettingsActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

                    }
                }
            }








        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=personName.getText().toString();
                String surname=personSurname.getText().toString();
                String email=personEmail.getText().toString();
                String pass=personPassword.getText().toString();
                String phone=personPhone.getText().toString();
                String tc=personTC.getText().toString();
                String dob=personDob.getText().toString();

                ref.child("name").setValue(name);
                ref.child("lastname").setValue(surname);
                ref.child("password").setValue(pass);
                ref.child("phone").setValue(phone);
                ref.child("tc").setValue(tc);
                ref.child("dob").setValue(dob);

                Toast.makeText(SettingsActivity.this,"Updated",Toast.LENGTH_LONG).show();
                recreate();
            }
        });





    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                photo.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }









}