package com.example.attendence_manager.Signing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendence_manager.MainActivity;
import com.example.attendence_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
EditText email,password;
Button login,register;
FirebaseAuth auth;
    private Button ok;
    private EditText editTextUsername,editTextPassword;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
//    SharedPreferences sharedpreferences;
    int autoSave;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        auth=FirebaseAuth.getInstance();
        register=findViewById(R.id.register);

        ok = (Button)findViewById(R.id.login);

        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            email.setText(loginPreferences.getString("username", ""));
           password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);

        }
        //"autoLogin" is a unique string to identify the instance of this shared preference
//        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
//        int j = sharedpreferences.getInt("key", 0);

        //Default is 0 so autologin is disabled
//        if(j > 0){
//            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(activity);
//        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Registration.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_user=email.getText().toString();
                String password_user=password.getText().toString();
                if(TextUtils.isEmpty(email_user))
                {
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password_user))
                {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    login_user(email_user,password_user);
                }
//                autoSave = 1;
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putInt("key", autoSave);
//                editor.apply();
            }
        });

        //main thing for saving the login creds
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == ok) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(email.getWindowToken(), 0);

            String email_u = email.getText().toString();
            String pass_u = password.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", email_u);
                loginPrefsEditor.putString("password", pass_u);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            doSomethingElse();
        }
//    }
            }
        });
    }

    private void login_user(String email_user, String password_user)
    {
        auth.signInWithEmailAndPassword(email_user,password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Login.this, MainActivity.class);
                    startActivity(i);

                }
                else
                    Toast.makeText(Login.this, "Some Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//        if (view == ok) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
//
//            String email_u = email.getText().toString();
//            String pass_u = password.getText().toString();
//
//            if (saveLoginCheckBox.isChecked()) {
//                loginPrefsEditor.putBoolean("saveLogin", true);
//                loginPrefsEditor.putString("username", email_u);
//                loginPrefsEditor.putString("password", pass_u);
//                loginPrefsEditor.commit();
//            } else {
//                loginPrefsEditor.clear();
//                loginPrefsEditor.commit();
//            }
//
//            doSomethingElse();
//        }
//    }

    public void doSomethingElse() {
        startActivity(new Intent(Login.this, MainActivity.class));
        Login.this.finish();
    }

}