package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    DatabaseReference reff;
    TextView currentt,groupnot,emailt,leader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid =user.getUid();

        groupnot = findViewById(R.id.textgroup);
        currentt = findViewById(R.id.texttrip);
        emailt = findViewById(R.id.setemail);
        leader = findViewById(R.id.tv_name);
        currentt.setText("No trip");
        groupnot.setText("None");
        emailt.setText("No user found");
        leader.setText("No User");

        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    String email = dataSnapshot.child("Email").getValue().toString();
                    String nogroup = dataSnapshot.child("Number of member").getValue().toString();
                    String tripname = dataSnapshot.child("Trip Name").getValue().toString();
                    String leaderi = dataSnapshot.child("Leader").getValue().toString();
                    currentt.setText(tripname);
                    groupnot.setText(nogroup);
                    emailt.setText(email);
                    leader.setText(leaderi);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}