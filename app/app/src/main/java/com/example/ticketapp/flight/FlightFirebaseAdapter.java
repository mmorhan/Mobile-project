package com.example.ticketapp.flight;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.searching.MainActivity3;
import com.example.ticketapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class FlightFirebaseAdapter extends FirebaseRecyclerAdapter<Flight, FlightFirebaseAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FlightFirebaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Flight model) {
        Picasso.get().load(model.getLogo()).into(holder.flight_list_logo);
        holder.flight_list_departure.setText(model.getDeparture());
        holder.flight_list_arrive.setText(model.getArrive());
        holder.flight_list_arrive_time.setText(model.getArriveTime());
        holder.flight_list_departure_time.setText(model.getDepartureTime());
        holder.flight_list_price.setText(model.getPrice() + "$");
        holder.flight_list_company.setText(model.getCompany());
        holder.flight_list_flightId.setText(model.getId());
        holder.flight_list_logoPath.setText(model.getId());

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
        EditText flight_list_flightId,flight_list_logoPath;
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
            flight_list_flightId= itemView.findViewById(R.id.flight_list_flightId);
            flight_list_logoPath=itemView.findViewById(R.id.flight_list_logoPath);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



//                    Flight flight=new Flight();
//                    flight.setId(flight_list_flightId.getText().toString());
//                    flight.setDeparture(flight_list_departure.getText().toString());
//                    flight.setDepartureTime(flight_list_departure_time.getText().toString());
//                    flight.setLogo(flight_list_logoPath.getText().toString());
//                    flight.set

                    Intent intent=new Intent(v.getContext(), MainActivity3.class);
                    intent.putExtra("flight_id",flight_list_flightId.getText().toString());
                    intent.putExtra("dep",flight_list_departure.getText().toString());
                    intent.putExtra("arr",flight_list_arrive.getText().toString());
                    intent.putExtra("deptime",flight_list_departure_time.getText().toString());
                    intent.putExtra("arrtime",flight_list_arrive_time.getText().toString());
                    intent.putExtra("price",flight_list_price.getText().toString());
                    intent.putExtra("comp",flight_list_company.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }


    }
}
