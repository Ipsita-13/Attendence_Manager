package com.example.attendence_manager.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.attendence_manager.Model_classes.student_model;
import com.example.attendence_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Student_details extends AppCompatActivity {
Spinner spinner;
FirebaseDatabase fr;
DatabaseReference dr;
EditText search;
ImageView click_search;
TextView female,total_att,section_total,att_percent;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        String sections[]={"A","B","students"};
        spinner=findViewById(R.id.sections_spinner);
        ArrayAdapter<String> aa=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,sections);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        fr=FirebaseDatabase.getInstance();
        dr=fr.getReference();
        search=findViewById(R.id.search);
        click_search=findViewById(R.id.click_search);
        female=findViewById(R.id.female_txt);
        att_percent=findViewById(R.id.female_percent);
       total_att=findViewById(R.id.total_attendence);
       section_total=findViewById(R.id.calculated_att);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                 String sec=item.toString();

                 click_search.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         String name=search.getText().toString();
                         Log.d("search name",name);
                         get_student_details(name,sec);
                     }
                 });
                 total_att.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         get_section_total(sec);



                     }
                 });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        


    }
    int total;
    boolean istrue=false;
    public void get_section_total(String sec)
    {

        DatabaseReference info=FirebaseDatabase.getInstance().getReference();
        Map<String,Object> map=new HashMap<>();
        info.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {


                for (DataSnapshot snap:snapshot.getChildren())
                {
                    String child=snap.getKey();
Log.d("child of get section",child);
                    if(child.equals(date))
                    {
                        istrue=true;
                        for(int i=1;i<5;i++)
                        {
                            String present = snap.child(sec).child("s" + i)
                                    .child("student_detail").child("status").getValue().toString();
                         Log.d("present_val",present+" "+date+" "+sec);
                            if(present.equals("Present"))
                            {
                                total++;
                                Log.d("section_total",String.valueOf(total));
                            }


                        }

                    }
                    if (istrue==true)
                        break;



                }



section_total.setText(String.valueOf(total));
map.put("total",total);
info.child(date).child(sec).child("section_total").setValue(section_total.getText().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void get_student_details(String name, String sec)
    {

     //   Query q=dr.getRoot().child(sec);
        int i=1;
     dr.child(date).child(sec).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             String child= snapshot.getKey();
             Log.d("children_of_a",child);
             for(int i=1;i<5;i++)
             {
                 //1. get the name
                 String name_a=snapshot.child("s"+i).child("student_detail").child("name").getValue().toString();
                 if(name.equals(name_a))
                 {
                     //get the attendence
                     int a=Integer.parseInt(snapshot.child("s"+i).child("student_detail").child("attendence").
                             getValue().toString());
                     Log.d("attendence_of_student",String.valueOf(a));
                     att_percent.setText(String.valueOf(a)+"/365");
                     break;
                 }

             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });



    }





}