package com.example.attendence_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendence_manager.Activities.Student_details;
import com.example.attendence_manager.Adapter.Student_Adapter;
import com.example.attendence_manager.Model_classes.student_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button absent_btn;
Button present_btn;
TextView name,status,next,attendence;
DatabaseReference dr;
FirebaseDatabase fd;
Context context;
Button stu_details;
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
Spinner spinner_values;
String selected_section="A";
int a=0;
//declarations for adapter setting
    RecyclerView rv;
    ArrayList<student_model> list;
    Student_Adapter sa;
    int i=0,p=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fd=FirebaseDatabase.getInstance();
        dr=fd.getReference();
stu_details=findViewById(R.id.student_details);
stu_details.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(MainActivity.this, Student_details.class);
        startActivity(i);
    }
});
        spinner_values=findViewById(R.id.sections);
        //--date time display code--
        dateTimeDisplay=findViewById(R.id.datedisplay);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
        //------prev date calculation------------------------------------
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String result = s.format(new Date(cal.getTimeInMillis()));
        //---------------------------------------------------------------
        Log.d("prec date",result);
       //--------------spinner addition-----------------------------------------------
        dateTimeDisplay.setText(date);
        String spinner[]={"A","B","students"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_values.setAdapter(arrayAdapter);
        //---------------------------------------------------------------------------
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.student_layout, null);
//        present_btn=view.findViewById(R.id.present_btn);
//        status=view.findViewById(R.id.status);



//----------------------getting spinner values and getting data acc to spinner values------------------------------------------------------
        spinner_values.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                selected_section=item.toString();

               dr.child(dateTimeDisplay.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.hasChild(selected_section) )
                       {
                           Toast.makeText(MainActivity.this, "exists", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           add_data(selected_section);
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

                    //add_data(selected_section);
                GetDataFromFirebase( selected_section);
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("section", selected_section);
                editor.putString("date",dateTimeDisplay.getText().toString());
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //-----here the present status will be uploaded to firebase

//calling the function to get data from firebase
        ClearAll();
       // GetDataFromFirebase();
//spinner_values.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Object item = adapterView.getItemAtPosition(i);
//        selected_section=item.toString();
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
//});
//   rv=findViewById(R.id.rv);
//
////        Map<Integer,String> students=new HashMap<>();
////        students.put(0,"absent");
////        students.put(1,"absent");
////        students.put(2,"absent");
////        students.put(3,"absent");
////        students.put(4,"absent");
//        String students[]={"Atul,","Amy","Adil","Ram","Sam","Devin"};
//        ArrayAdapter ar=new ArrayAdapter(this,R.layout.student_layout,R.id.student_name,students);
//
//        rv.setAdapter(ar);


//
//


    }


//-----------------getting data from firebase----------------------------------------------------------------
   int c=0;
    public void GetDataFromFirebase(String section) {


        Query query = dr.child(dateTimeDisplay.getText().toString()).child(section);
        Log.d("query",query.toString());
            query.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot snapshot) {

String rec="";
                    ClearAll();

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        student_model sm = new student_model();
                         c++;

                        String child = snap.getKey();

                       Log.d("rec contains",String.valueOf(rec.contains(child)));
                       if(!child.equals("section_total") ) {
                           sm.setName(snap.child("student_detail").child("name").getValue().toString());
                           sm.setGender(snap.child("student_detail").child("gender").getValue().toString());
                           sm.setRoll_no(snap.child("student_detail").child("roll").getValue().toString());
                           sm.setStatus(snap.child("student_detail").child("status").getValue().toString());

                           //  Log.d("values", Integer.toString(i));

                           list.add(sm);
                           rec=rec+child;
                           Log.d("child_of_a", child);

                       }

                    }
                    sa = new Student_Adapter(list, getApplicationContext());
                    rv.setAdapter(sa);
                    sa.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


//----------------------------------------------------------------------------------------------------------------

//---------------adding data to firebase according to the date-----------------------------------------------------------
    public void add_data(String section){
        String date=dateTimeDisplay.getText().toString();
        Map<String,Object> map=new HashMap<>();
        String stu[]={"ajay","arun","baidya","bismay"};
        String gender[]={"male","male","female","male"};
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String prev_date = s.format(new Date(cal.getTimeInMillis()));
        dr.child(prev_date).child(section).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0;i<stu.length;i++) {
                    map.put("name", stu[i]);
                    map.put("gender", gender[i]);
                    map.put("status", "absent");
                    map.put("roll", Integer.toString(i + 1));
                 Log.d("student_add",stu[i]);
if(snapshot.child("s"+(i+1)).child("student_detail")
        .hasChild("attendence")) {
     a = Integer.parseInt(snapshot.child("s" + (i + 1)).child("student_detail")
            .child("attendence").getValue().toString());
}
    map.put("attendence", String.valueOf(a));
    Log.d("attendence_value", String.valueOf(a));
    dr.child(date).child(section).child("s" + (i + 1)).child("student_detail").updateChildren(map);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//---------------------------------------------------------------------------------------------------------------

    }
    private void ClearAll()
    {
        if(list!=null)
        {
            list.clear();
        }
        list=new ArrayList<>();
    }
}