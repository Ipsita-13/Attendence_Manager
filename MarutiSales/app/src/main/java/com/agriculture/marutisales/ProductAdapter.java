package com.agriculture.marutisales;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductAdapter extends FirebaseRecyclerAdapter<Product,ProductAdapter.myviewholder>
{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);

    }
Context context;

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Product model) {

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
       Product pro=getItem(position);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(v.getContext(),DetailedActivity.class);
               intent.putExtra("Name",model.getName());
               intent.putExtra("Price",model.getPrice());
               intent.putExtra("Description",model.getDescription());
               v.getContext().startActivity(intent);
           }
       });





    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cardisplay,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView name,price,description;
        View v;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.productname);
            price=(TextView)itemView.findViewById(R.id.productprice);
            description=(TextView)itemView.findViewById(R.id.descriptiontxt);





        }


    }
}