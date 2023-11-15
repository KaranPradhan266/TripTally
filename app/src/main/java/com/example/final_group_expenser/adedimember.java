package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adedimember extends AppCompatActivity {

    EditText groupno,tripto;
    Button addmem;
    DatabaseReference reff,reff2,root;
    long maxiid =0;
    EditText name;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adedimember);


        addmem = findViewById(R.id.addmem);
        name = findViewById(R.id.entername);

         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         final String userid =user.getUid();



        reff2 = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Members Name");




        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxiid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addmem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_entername = name.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Members Name").child(String.valueOf(maxiid+1)).setValue(txt_entername);
                Toast.makeText(adedimember.this, "Member: "+txt_entername+" added successfully;", Toast.LENGTH_SHORT).show();
            }
        });






    }
}