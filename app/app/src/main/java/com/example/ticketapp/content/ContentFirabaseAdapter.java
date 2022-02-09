package com.example.ticketapp.content;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ContentFirabaseAdapter extends FirebaseRecyclerAdapter<Content, ContentFirabaseAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContentFirabaseAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Content model) {
        Picasso.get().load(model.getImagepath()).into(holder.content_header_image);
        holder.content_header.setText(model.getHeader());
        holder.content_description.setText(model.getDescrption());
        holder.content_date.setText(model.getDate());
        holder.content_source.setText(model.getSource());
        holder.content_id.setText(model.getId());
        holder.image_path.setText(model.getImagepath());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list, parent, false);
        return new myViewHolder(v);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView content_header_image;
        TextView content_header, content_description, content_date, content_source, content_favorite, content_readmore;
        EditText content_id,image_path;
        Button addFav;
        Button readMore;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            content_header_image = itemView.findViewById(R.id.content_header_image);
            content_header = itemView.findViewById(R.id.content_header);
            content_description = itemView.findViewById(R.id.content_description);
            content_date = itemView.findViewById(R.id.content_date);
            content_source = itemView.findViewById(R.id.content_source);
            content_favorite = itemView.findViewById(R.id.content_favorites);
            content_readmore = itemView.findViewById(R.id.content_readmore);
            content_id =itemView.findViewById(R.id.content_id);
            image_path=itemView.findViewById(R.id.image_path);

            addFav = (Button) itemView.findViewById(R.id.content_favorites);
            addFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Content myModel=new Content();
                    myModel.setId(content_id.getText().toString());
                    myModel.setDescrption(content_description.getText().toString());
                    myModel.setDate(content_date.getText().toString());
                    myModel.setImagepath(image_path.getText().toString());
                    myModel.setHeader(content_header.getText().toString());
                    myModel.setSource(content_source.getText().toString());
                    //Todo get current user and add this content to his/her list
                    FirebaseUser user;

                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = null;
                    if (user != null) {
                        uid = user.getUid();
                    }
                    //Todo add content to user's favs
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("User").child(user.getUid()).child("Content");



                    DatabaseReference childref=ref.child(myModel.getId());
                    childref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                // The child doesn't exist
                                ref.child(myModel.getId()).setValue(myModel);
                                Toast.makeText(v.getContext(),"added Favorites",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(v.getContext(),"already added Favorites",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });



            }
        });

        readMore=(Button)itemView.findViewById(R.id.content_readmore);
            readMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
            Toast.makeText(v.getContext(), "Read", Toast.LENGTH_SHORT).show();
        }
        });


    }
}
}
