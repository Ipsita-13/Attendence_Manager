package com.example.attendence_manager.Signing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendence_manager.MainActivity;
import com.example.attendence_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
EditText email,password,name;
Button register;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email=findViewById(R.id.email);
        password=findViewById(R.id.Password);
        register=findViewById(R.id.register);
        name=findViewById(R.id.name);
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_u=email.getText().toString();
                String password_u=password.getText().toString();
                String name_u=name.getText().toString();
                if(TextUtils.isEmpty(email_u))
                {
                    Toast.makeText(Registration.this, "Enter EmailId", Toast.LENGTH_SHORT).show();
                }
               else if(TextUtils.isEmpty(password_u))
                {
                    Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
               else if(TextUtils.isEmpty(name_u))
                {
                    Toast.makeText(Registration.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
               else
                {
                    register_user(email_u,password_u);
                }
            }
        });

    }

    private void register_user(String email_u, String password_u)
    {
        auth.createUserWithEmailAndPassword(email_u,password_u).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String teacher_email=email.getText().toString();
                String teacher_password=password.getText().toString();
                String teacher_name=name.getText().toString();
                Map<String,Object> map=new HashMap<>();
                map.put("teacher_name",teacher_name);
                map.put("teacheremail",teacher_email);
                map.put("teacher_password",teacher_password);

                FirebaseDatabase.getInstance().getReference().child("Teachers").push().updateChildren(map);
                Intent i=new Intent(Registration.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
}