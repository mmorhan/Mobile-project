package com.example.ticketapp.message;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MessageFirebaseAdapter extends FirebaseRecyclerAdapter<MyMessage, MessageFirebaseAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageFirebaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MyMessage model) {
        holder.message_date.setText(model.getDate());
        holder.message_content.setText(model.getMessage());

        FirebaseUser user;

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        if (model.getSenterId().equalsIgnoreCase(uid)){
           //sent by user
            holder.message_layout.setHorizontalGravity(Gravity.START);
        }
        else{
           //sent by other END
            holder.message_layout.setHorizontalGravity(Gravity.END);
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_2, parent, false);
        return new myViewHolder(v);
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView message_content,message_date;
        LinearLayout message_layout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            message_content= itemView.findViewById(R.id.message_content);
            message_date= itemView.findViewById(R.id.message_time);
            message_layout= itemView.findViewById(R.id.message_layout_id);

        }
    }
}
