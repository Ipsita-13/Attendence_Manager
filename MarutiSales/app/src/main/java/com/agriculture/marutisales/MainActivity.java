package com.agriculture.marutisales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.ar.core.Track;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcv;
    ProductAdapter productadapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbase = FirebaseDatabase.getInstance().getReference();
        rcv = findViewById(R.id.rv);
        //to display the recycler view linearly
        rcv.setLayoutManager(
                new LinearLayoutManager(this));
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Product> options
                = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(mbase.child("Products"), Product.class)
                .build();
        productadapter=new ProductAdapter(options);
        rcv.setAdapter(productadapter);
        rcv.setItemAnimator(null);




    }


    @Override
    protected void onStart() {
        super.onStart();

          productadapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        productadapter.stopListening();
    }


}
