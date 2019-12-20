package com.ffproducts.admineat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class frmAddUser extends AppCompatActivity {

    private EditText email;
    private EditText pwd;
    private EditText pwd1;
    private Button btnadd;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_add_user);

        firebaseAuth=FirebaseAuth.getInstance();

        addAdmin();
    }


    //add admin
    public void addAdmin(){
        email=findViewById(R.id.txtemail);
        pwd=findViewById(R.id.txtpwd);
        pwd1=findViewById(R.id.txtpwd1);
        btnadd=findViewById(R.id.txtdiscount);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String size = pwd.getText().toString();


                if(email.getText().toString().equals("")){

                    Toast.makeText(frmAddUser.this, "Fill Email", Toast.LENGTH_SHORT).show();

                }
                else if(! isEmailValid(email.getText().toString())){
                    Toast.makeText(frmAddUser.this, "Please enter an email correct format", Toast.LENGTH_SHORT).show();

                }

                else if(pwd.getText().toString().equals("") && size.length() < 6){
                    Toast.makeText(frmAddUser.this, "Fill password", Toast.LENGTH_SHORT).show();

                }
                else if( !pwd1.getText().toString().equals(pwd.getText().toString())){
                    Toast.makeText(frmAddUser.this,"Password didn't match",Toast.LENGTH_SHORT).show();

                }
                else{

                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(frmAddUser.this,"User Addedd successfully",Toast.LENGTH_LONG).show();
                                email.setText("");
                                pwd.setText("");
                                pwd1.setText("");
                            }
                            else{
                                Toast.makeText(frmAddUser.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }

    //email validation
    public boolean isEmailValid(String text){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(text).matches();
    }


}
