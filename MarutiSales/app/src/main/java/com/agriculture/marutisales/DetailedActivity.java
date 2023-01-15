package com.agriculture.marutisales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedActivity extends AppCompatActivity {

    private TextView Name,Price,Description;
     Button buynow;
     String prodname,prodprice;
    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Name=(TextView)findViewById(R.id.nametxt);
        Price=(TextView) findViewById(R.id.pricetxt);
        Description=(TextView) findViewById(R.id.descriptiontxt);
        dr= FirebaseDatabase.getInstance().getReference();
        buynow=(Button)findViewById(R.id.buynow_btn);




           Name.setText(getIntent().getExtras().getString("Name"));
            prodname=Name.getText().toString();
           Price.setText(getIntent().getExtras().getString("Price"));
           prodprice=Price.getText().toString();
           Description.setText(getIntent().getExtras().getString("Description"));




    }


    public void buynow(View view)
    {
        Product p=new Product();
        Intent intent=new Intent(DetailedActivity.this,OrderActivity.class);
        intent.putExtra("Name",prodname);
        intent.putExtra("Price",prodprice);
        startActivity(intent);
    }
}