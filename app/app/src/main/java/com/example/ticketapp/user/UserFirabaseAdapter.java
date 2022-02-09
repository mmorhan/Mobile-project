package com.example.ticketapp.user;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.R;
import com.example.ticketapp.message.MessageActivity2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserFirabaseAdapter extends FirebaseRecyclerAdapter<User, UserFirabaseAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserFirabaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull User model) {
        Picasso.get().load(model.getPhoto()).into(holder.person_header_image);
        holder.person_name.setText(model.getName() + " " + model.getLastname());
        holder.personId.setText(model.getId());
        holder.personemail.setText(model.getEmail());
        holder.personImagePath.setText(model.getPhoto());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list, parent, false);
        return new myViewHolder(v);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView person_header_image;
        TextView person_name, personemail;
        EditText personId, personImagePath;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            person_header_image = itemView.findViewById(R.id.message_profile_image);
            person_name = itemView.findViewById(R.id.message_person_name);
            personemail = itemView.findViewById(R.id.message_person_name_email);
            personId = itemView.findViewById(R.id.message_person_id);
            personImagePath = itemView.findViewById(R.id.message_person_image_path);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Todo get current user and add this content to his/her list
                    FirebaseUser user;
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = null;
                    if (user != null) {
                        uid = user.getUid();
                    }

                    Intent intent = new Intent(v.getContext(), MessageActivity2.class);
                    intent.putExtra("personId", personId.getText().toString());
                    intent.putExtra("personName", person_name.getText().toString());
                    intent.putExtra("personImagePath", personImagePath.getText().toString());
                    v.getContext().startActivity(intent);

                }
            });


        }




    }
}
