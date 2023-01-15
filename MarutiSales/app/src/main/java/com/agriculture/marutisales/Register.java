package com.agriculture.marutisales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity
{
    Button createaccount;
    EditText namer,emailr,passwordr;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        namer=(EditText) findViewById(R.id.namereg);
        emailr=(EditText)findViewById(R.id.emailreg);
        passwordr=(EditText) findViewById(R.id.passwordreg);
        createaccount=(Button)findViewById(R.id.createacc);
        auth=FirebaseAuth.getInstance();



    }
    public void createacc(View view)
    {
        String username=namer.getText().toString();
        String useremail=emailr.getText().toString();
        String userpassword=passwordr.getText().toString();
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(Register.this, "Enter username", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(useremail))
        {
            Toast.makeText(Register.this, "Enter Email id", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(userpassword))
        {
            Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
       else
        {
            auth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register.this, "Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(Register.this, "Not Created"+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }


    }
}