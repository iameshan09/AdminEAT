package com.ffproducts.admineat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pwd;
    private Button login;

    private TextView forgotP;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
             startActivity(new Intent(MainActivity.this,frmHome.class));
            finish();
          }
           else{
               login();
               resetPassword();
           }


    }

    //Login
    public void login() {
        try{

            email = (EditText) findViewById(R.id.txtemail);
            pwd = (EditText) findViewById(R.id.txtpwd1);
            login = (Button) findViewById(R.id.txtdiscount);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (email.getText().toString().equals("")) {

                        Toast.makeText(MainActivity.this, "Fill an Email", Toast.LENGTH_SHORT).show();

                    } else if (!isEmailValid(email.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Please enter an email correct format", Toast.LENGTH_SHORT).show();

                    } else if (pwd.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Fill password", Toast.LENGTH_SHORT).show();

                    } else {

                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(MainActivity.this, frmHome.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }

                }
            });

        }
        catch(Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //ResetPasssword
    public void resetPassword(){
        try{
            forgotP =  findViewById(R.id.tvsignup);
            forgotP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,frmResetPassword.class));

                }
            });

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    //email validation
    public boolean isEmailValid(String text){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(text).matches();
    }
}
