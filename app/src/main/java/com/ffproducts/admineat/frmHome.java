package com.ffproducts.admineat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frmHome extends AppCompatActivity {

    private CardView addAdmin;
    private CardView logout;
    private CardView addCake;
    private CardView btnorderview;
    private CardView btnrateview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_home);

        adminCall();
        logout();
        addCake();
        orderview();
        rateview();
    }

    //callToOrderView
    public void orderview(){
        btnorderview=findViewById(R.id.btnorderview);
        btnorderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmHome.this,frmOrderView.class));

            }
        });
    }
    //callToRateView
    public void rateview(){
        btnrateview=findViewById(R.id.rate);
        btnrateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmHome.this,frmRateView.class));

            }
        });
    }

    //callToAdmin
    public void adminCall(){
        addAdmin=findViewById(R.id.btnaddadmin);
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmHome.this,frmAddUser.class));

            }
        });
    }

    //logOut
    public void logout(){
        logout=findViewById(R.id.btnlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sweetAlert();
                }
                catch (Exception e)
                {
                    Toast.makeText(frmHome.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //addItem
    public void addCake(){
        addCake=findViewById(R.id.btnCake);
        addCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmHome.this,frmItem.class));

            }
        });
    }

    //sweetAlert
    public void sweetAlert(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Log out")
                .setContentText("Log out of EatAndTreatAdmin?")
                .setConfirmText("Logout").setConfirmButtonBackgroundColor(R.color.colorPrimary).setConfirmButtonTextColor(R.color.colorPrimary)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(frmHome.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();




                    }
                }).setCancelButtonBackgroundColor(R.color.colorPrimary).setCancelButtonTextColor(R.color.colorPrimary)
                .setCancelButton("Discard", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
