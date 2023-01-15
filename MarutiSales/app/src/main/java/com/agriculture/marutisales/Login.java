package com.agriculture.marutisales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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


public class Login extends AppCompatActivity
{
    EditText email,password;
    Button login,createaccount;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setting the references of the attributes
        email=(EditText)findViewById(R.id.emaillog) ;
        password=(EditText)findViewById(R.id.passwordlog) ;
        login=(Button) findViewById(R.id.loginbutton);
        createaccount=(Button) findViewById(R.id.signup);
        mauth=FirebaseAuth.getInstance();



    }
    public void login(View view)
    {
        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();
        if(TextUtils.isEmpty(useremail))
        {
            Toast.makeText(Login.this, "Enter Email Id", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userpassword))
        {
            Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
        }

        mauth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(Login.this, MainActivity.class));
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Login.this, "Some Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void signup(View view)
    {
        startActivity(new Intent(Login.this,Register.class));
    }


}