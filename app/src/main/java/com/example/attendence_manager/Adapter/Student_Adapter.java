package com.example.attendence_manager.Adapter;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendence_manager.MainActivity;
import com.example.attendence_manager.Model_classes.student_model;
import com.example.attendence_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.viewHolder> {
    FirebaseDatabase fd;
    DatabaseReference dr;
    Spinner sp;
    int i=0;
    public Student_Adapter(ArrayList<student_model> list, Context context) {
        this.list=list;
        this.context=context;
    }
    ArrayList<student_model> list;
    Context context;



    @NonNull
    @Override
    public Student_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here the layout will be inflated
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_Adapter.viewHolder holder, int position) {
        student_model student=list.get(position);
        holder.name.setText("Name = "+student.getName());
        holder.gender.setText("Gender = "+student.getGender());
        holder.roll.setText("Roll No = "+student.getRoll_no());
//        holder.status.setText("Status = "+student.getStatus());
        dr=FirebaseDatabase.getInstance().getReference();
       //--------setting the attendence value------------------------------------------------------
        //-----------------------------------------------------------------------------------------
//getting prev date-------------------------------------------------------------------------------
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String prev_date = s.format(new Date(cal.getTimeInMillis()));
//------------------------------------------------------------------------------------------------

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "position"+position, Toast.LENGTH_SHORT).show();
//            }
////        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dr.child("B").child("s"+(position+1)).child("status").setValue("Present");
//            }
//        });

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String sec = sharedPreferences.getString("section", "default value");
        String date=sharedPreferences.getString("date","default value");

        Log.d("section",sec);
        MainActivity ma=new MainActivity();
//        ma.GetDataFromFirebase(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             i++;
             //call here the method to set i value set the method in mainactivity.
                SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("att", String.valueOf(i));
                editor.apply();
             Log.d("i value",String.valueOf(i));
holder.itemView.setBackgroundColor(Color.LTGRAY);

dr.child(prev_date).child(sec).child("s"+(position+1)).child("student_detail")
        .child("attendence").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
      // Log.d("attendence",snapshot.getValue().toString());
       int a=Integer.parseInt(snapshot.getValue().toString())+1;
       dr.child(date).child(sec).child("s"+(position+1)).child("student_detail")
                       .child("attendence").setValue(String.valueOf(a));
        dr.child(date).child(sec).child("s"+(position+1)).child("student_detail")
                .child("status").setValue("Present");
       Log.d("new value",String.valueOf(a));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(ma, "error"+error.toException(), Toast.LENGTH_SHORT).show();
        Log.d("error",error.toException().toString());
    }
});

            }
        });
//
//holder.present.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        dr.child(sec).child("s"+(position+1)).child("status").setValue("");
//    }
//});



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,status,roll,gender,attendence;
        Button present;
        Spinner sp;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.student_name);
            status=itemView.findViewById(R.id.status);
            roll=itemView.findViewById(R.id.roll_no);
            gender=itemView.findViewById(R.id.gender);
           // present=itemView.findViewById(R.id.present_btn);
            sp=itemView.findViewById(R.id.sections);


//

        }
    }
}
