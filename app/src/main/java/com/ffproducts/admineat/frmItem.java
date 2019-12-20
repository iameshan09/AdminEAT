package com.ffproducts.admineat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class frmItem extends AppCompatActivity {
    private EditText name;
    private EditText price;
    private EditText discount;
    private Button btnadd;

    DatabaseReference reff;
    Items items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_item);

        name=findViewById(R.id.txtname);
        price=findViewById(R.id.txtprice);
        discount=findViewById(R.id.txtdiscount);
        btnadd=findViewById(R.id.btnadd);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(); }
        });
    }

    //validate
    public void validate(){
        if(! isNameValid(name.getText().toString()) ){
            Toast.makeText(frmItem.this,"Invalid Item name",Toast.LENGTH_SHORT).show();
        }
        else if(! isPriceValid(price.getText().toString())||price.getText().toString().equals("") ||price.getText().toString().equals("0")){
            Toast.makeText(frmItem.this,"Invalid type of price",Toast.LENGTH_SHORT).show();

        }
        else if(! isPriceValid(discount.getText().toString())||discount.getText().toString().equals("")){
            Toast.makeText(frmItem.this,"Invalid type of discount",Toast.LENGTH_SHORT).show();

        }
        else {
            verifyItem();


        }
    }


    //verify item
    public void verifyItem(){
        try {

            DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("Items");

            reff.orderByChild("name").equalTo(name.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Toast.makeText(frmItem.this,"Item name already exhist",Toast.LENGTH_SHORT).show();

                    } else {

                     send();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        catch (Exception e){
            Toast.makeText(frmItem.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    //send
    public void send(){
        try{


                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                final String formattedDate = df.format(c);

                items=new Items();
                reff= FirebaseDatabase.getInstance().getReference().child("Items");


                String na=name.getText().toString().trim();
                int pr=Integer.parseInt(price.getText().toString().trim());
                int di=Integer.parseInt(discount.getText().toString().trim());

                items.setDate(formattedDate);
                items.setName(na);
                items.setPrice(pr);
                items.setDiscount(di);
                reff.push().setValue(items);

                name.setText("");
                price.setText("");
                discount.setText("");
                Toast.makeText(frmItem.this,"Item added successfully",Toast.LENGTH_SHORT).show();


    }
        catch (Exception e){
        Toast.makeText(frmItem.this, e.getMessage(), Toast.LENGTH_SHORT).show();

    }
    }

    //regex
    public boolean isNameValid(String text){

        return text.matches("^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$");
    }
    public boolean isPriceValid(String text){

        return text.matches("^[0-9]*$");
    }
}
