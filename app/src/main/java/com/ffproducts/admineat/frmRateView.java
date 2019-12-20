package com.ffproducts.admineat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class frmRateView extends AppCompatActivity {

    ListView myorders;
    private Spinner comboitemname;
    private ImageView imgdate;
    ValueEventListener listner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerdatalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_rate_view);

        myorders=(ListView) findViewById(R.id.listorders);

        Query get = FirebaseDatabase.getInstance().getReference("Customer_Rate");


        spinnerdatalist = new ArrayList<>();
        adapter=new ArrayAdapter<String>(frmRateView.this,android.R.layout.simple_spinner_dropdown_item,spinnerdatalist);

        myorders.setAdapter(adapter);

        listner=get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    //  spinnerdatalist.add(item.getValue().toString());

                    // spinnerdatalist.add(item.child("cusname").getValue().toString());
                    spinnerdatalist.add(item.child("email").getValue().toString());
                    spinnerdatalist.add(item.child("rate").getValue().toString());
                    spinnerdatalist.add(item.child("comment").getValue().toString());






                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
