package com.agriculture.marutisales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UserOrders extends AppCompatActivity {
    TextView userproduct,userprice,username;
    Button cancelorder;
   FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);
        username=(TextView) findViewById(R.id.username);
        userprice=(TextView) findViewById(R.id.userprice);
        userproduct=(TextView) findViewById(R.id.userproduct);
        String product=getIntent().getExtras().getString("Name");
        String price=getIntent().getExtras().getString("Price");
        String user=getIntent().getExtras().getString("username");
        userproduct.setText(String.valueOf(product));
        userprice.setText(String.valueOf(price));
        username.setText(String.valueOf(user));
        db=FirebaseFirestore.getInstance();
        cancelorder=(Button)findViewById(R.id.cancelorder);
        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updatadata("active","cancelled");
            }
        });



    }

    private void Updatadata(String active, String cancelled)
    {
        Map<String,Object> orderstat=new HashMap<>();
        orderstat.put("order status",cancelled);
        db.collection("Order").whereEqualTo("order status",active).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty())
                {
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentid=documentSnapshot.getId();
                    db.collection("Order").document(documentid).update(orderstat).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UserOrders.this, "Order Canecelled", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserOrders.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(UserOrders.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}