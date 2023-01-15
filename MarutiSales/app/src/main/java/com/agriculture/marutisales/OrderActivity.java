package com.agriculture.marutisales;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    EditText cname,caddress,cnumber,cemail;

    TextView corder,corderprice;
    Button order_btn,yourorder;
    FirebaseFirestore db;
DocumentReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        cname = (EditText) findViewById(R.id.customername);
        caddress = (EditText) findViewById(R.id.customeraddress);
        cnumber = (EditText) findViewById(R.id.customerphonenumber);
        cemail = (EditText) findViewById(R.id.customeremail);
        corder = (TextView) findViewById(R.id.orderedproduct);
        order_btn = (Button) findViewById(R.id.ordernow);
        corder.setText(getIntent().getExtras().getString("Name"));
        yourorder = (Button) findViewById(R.id.yourorder);
        corderprice=(TextView)findViewById(R.id.orderedproductprice);
        corderprice.setText(getIntent().getExtras().getString("Price"));
        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = cname.getText().toString();
                String email = cemail.getText().toString();
                db = FirebaseFirestore.getInstance();
                String address = caddress.getText().toString();
                String phone_number = cnumber.getText().toString();
                String productname = corder.getText().toString();
                String productprice=corderprice.getText().toString();
                Map<String, Object> order = new HashMap<>();
                order.put("username", username);
                order.put("email", email);
                order.put("address", address);
                order.put("phone number", phone_number);
                order.put(" product", productname);
                order.put("order status", "active");
                order.put("product price",productprice);


                db.collection("Order").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        if (TextUtils.isEmpty(username)) {
                            Toast.makeText(OrderActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(address)) {
                            Toast.makeText(OrderActivity.this, "Enter address", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(phone_number)) {
                            Toast.makeText(OrderActivity.this, "Enter phonenumber", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(email)) {
                            Toast.makeText(OrderActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(OrderActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(OrderActivity.this, "Order could not placed due to some error" + error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


  yourorder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          String user=cname.getText().toString();
          String product=corder.getText().toString();
          String price=getIntent().getExtras().getString("Price");
          Intent intent=new Intent(OrderActivity.this,UserOrders.class);
          intent.putExtra("Name",product);
          intent.putExtra("Price",price);
          intent.putExtra("username",user);
          startActivity(intent);
      }
  });

    }





}