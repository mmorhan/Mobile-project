package com.example.ticketapp.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ticketapp.R;
import com.example.ticketapp.others.SettingsActivity;
import com.example.ticketapp.content.Content;
import com.example.ticketapp.flight.Flight;
import com.example.ticketapp.message.MessageActivity;
import com.example.ticketapp.user.User;
import com.example.ticketapp.userContent.FavContentFirabaseAdapter;
import com.example.ticketapp.userFlight.UserFlightActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected View mView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    ImageView photo;
    TextView name, email;

    RecyclerView recyclerView;
    FavContentFirabaseAdapter adapter;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        ArrayList<Flight> flights = new ArrayList<>();
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database2.getReference("Flight");
        for (int i = 0; i < 9; i++) {
            Flight flight1 = new Flight();
            flight1.setCompany("Onur Air");
            flight1.setLogo("https://firebasestorage.googleapis.com/v0/b/ticketapp-94d2c.appspot.com/o/png-clipart-onur-air-istanbul-airline-logo-atlasglobal-onur-sasmaz-angle-text-thumbnail.png?alt=media&token=cdbb84fd-42a9-4ab4-ab0f-f26e2b7a702a");
            flight1.setDate(i + 13 + "/6/2021");
            flight1.setDeparture("Istanbul");
            flight1.setArrive("Izmir");
            flight1.setDepartureTime("0"+i+":40");
            flight1.setArriveTime("0"+(i+1)+":00");
            flight1.setPrice(200);
            flights.add(flight1);
        }
        for (int i = 0; i < flights.size(); i++) {
            String id = flights.get(i).getId();
            flights.get(i);
            Flight fligth = flights.get(i);
            databaseReference.child(id).setValue(flights.get(i));
            databaseReference.child(id).child("search").setValue(fligth.getDeparture()+"-"+fligth.getArrive()+"-"+fligth.getDate());
        }
        //TODO:INITIZILATIZING RECYCLERVIEW
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }

        FirebaseRecyclerOptions<Content> options =
                new FirebaseRecyclerOptions.Builder<Content>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(uid).child("Content"), Content.class)
                        .build();
        adapter = new FavContentFirabaseAdapter(options);
        recyclerView = view.findViewById(R.id.fav_recyler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        //TODO: SET PROFILE
        name = (TextView) view.findViewById(R.id.profile_name_surname);
        email = (TextView) view.findViewById(R.id.profile_email);
        photo = (ImageView) view.findViewById(R.id.profile_photo);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("User").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User fuser;
                fuser = dataSnapshot.getValue(User.class);
                if (!fuser.getPhoto().equals(""))
                    Picasso.get().load(fuser.getPhoto()).into(photo);
                name.setText(fuser.getName() + " " + fuser.getLastname());
                email.setText(fuser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });

        //TODO: OPEN SETTINGS
        TextView settings = (TextView) view.findViewById(R.id.settingtab);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            }
        });


        //Todo: Open Flights
        Button button = (Button) view.findViewById(R.id.profile_flights);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), UserFlightActivity.class));
            }
        });
        //Todo: Open Messages
        Button button2 = (Button) view.findViewById(R.id.profile_messages);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), MessageActivity.class));
            }
        });
        return view;


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}