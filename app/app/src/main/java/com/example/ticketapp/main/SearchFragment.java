package com.example.ticketapp.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.ticketapp.others.DatePickerFragment;
import com.example.ticketapp.R;
import com.example.ticketapp.searching.MainActivity2;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Variables
    TextInputLayout text_departure;
    AutoCompleteTextView act_departure;
    TextInputLayout text_where;
    AutoCompleteTextView act_where;
    ArrayList<String> array_city;
    ArrayAdapter<String> adapter_city;
    private DatabaseReference mDatabase;
    Button search_date;
    Button search_date2;
    Boolean whichDate = false; // 0 for first 1 for second date
    Button search_flight;
    Switch oneWay;
    RadioButton withOutTransfer;
    String date1, date2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        //adaptor
        text_departure = (TextInputLayout) root.findViewById(R.id.text_departure);
        act_departure = (AutoCompleteTextView) root.findViewById(R.id.act_departure);
        text_where = (TextInputLayout) root.findViewById(R.id.text_where);
        act_where = (AutoCompleteTextView) root.findViewById(R.id.act_where);

        array_city = new ArrayList<>();

        adapter_city = new ArrayAdapter<>(root.getContext(), R.layout.support_simple_spinner_dropdown_item, array_city);

        act_departure.setAdapter(adapter_city);
        act_departure.setThreshold(1);

        act_where.setAdapter(adapter_city);
        act_where.setThreshold(1);

        //firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("city");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("info", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                array_city.add(dataSnapshot.getValue(String.class));
                adapter_city.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("infoo", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                adapter_city.notifyDataSetChanged();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("info", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                array_city.remove(dataSnapshot.getValue(String.class));
                adapter_city.notifyDataSetChanged();
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("info", "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.w("info", "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(root.getContext(), "Failed to load comments.",
//                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addChildEventListener(childEventListener);

        //Todo calender Initilize
        search_date = (Button) root.findViewById(R.id.search_date);
        search_date2 = (Button) root.findViewById(R.id.search_date2);
        search_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDate = false;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(SearchFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        search_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichDate = true;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(SearchFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });
        //Todo: check for one way
        oneWay = (Switch) root.findViewById(R.id.oneWay);
        oneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = oneWay.isChecked();
                if (checked) {
                    search_date2.setVisibility(View.GONE);
                } else {
                    search_date2.setVisibility(View.VISIBLE);
                }
            }
        });


        //Todo go to next page
        search_flight = (Button) root.findViewById(R.id.search_flight);
        search_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String departure = act_departure.getText().toString();
                String where = act_where.getText().toString();


                Intent intent = new Intent(getActivity(), MainActivity2.class);
                String combined;
                combined = departure + "-" + where + "-" + date1;
                intent.putExtra("search", combined);
                intent.putExtra("date",date1);
                startActivity(intent);

            }
        });
        return root;
    }

    @Override

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set((Calendar.MONTH), month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        String currentdate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        if (!whichDate) {
            date1 = currentdate;
            search_date.setText(currentdate);
        } else {
            date2 = currentdate;
            search_date2.setText(currentdate);
        }
    }

}