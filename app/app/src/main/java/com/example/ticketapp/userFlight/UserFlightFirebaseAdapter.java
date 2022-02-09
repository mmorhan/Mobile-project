package com.example.ticketapp.userFlight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.R;
import com.example.ticketapp.flight.Flight;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class UserFlightFirebaseAdapter extends FirebaseRecyclerAdapter<Flight, UserFlightFirebaseAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserFlightFirebaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Flight model) {
        Picasso.get().load(model.getLogo()).into(holder.flight_list_logo);
        holder.flight_list_departure.setText(model.getDeparture());
        holder.flight_list_arrive.setText(model.getArrive());
        holder.flight_list_price.setText(model.getArrive());
        holder.flight_list_arrive_time.setText(model.getArriveTime());
        holder.flight_list_departure_time.setText(model.getDepartureTime());
        holder.flight_list_price.setText(model.getPrice() + "$");
        holder.flight_list_company.setText(model.getCompany());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_list, parent, false);

        return new myViewHolder(v);
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView flight_list_logo;
        TextView flight_list_departure, flight_list_arrive, flight_list_departure_time, flight_list_arrive_time, flight_list_price, flight_list_company;
        View view;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            flight_list_logo = itemView.findViewById(R.id.flight_list_logo);
            flight_list_departure = itemView.findViewById(R.id.flight_list_departure);
            flight_list_company = itemView.findViewById(R.id.flight_list_company);
            flight_list_arrive = itemView.findViewById(R.id.flight_list_arrive);
            flight_list_departure_time = itemView.findViewById(R.id.flight_list_departure_time);
            flight_list_arrive_time = itemView.findViewById(R.id.flight_list_arrive_time);
            flight_list_price = itemView.findViewById(R.id.flight_list_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"You can't do anything",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
