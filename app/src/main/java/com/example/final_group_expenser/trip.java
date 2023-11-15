package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class trip extends AppCompatActivity {

    Button addbtn;
    EditText tripname,memberno,leader;
    long maxid =0 ;
    DatabaseReference reff,reff2;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String userid = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        addbtn = findViewById(R.id.addtripbtn);
        tripname = findViewById(R.id.tripname);
        memberno = findViewById(R.id.noofmember);
        leader = findViewById(R.id.leadername);




        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_tripname = tripname.getText().toString();
                String txt_noofmember = memberno.getText().toString();
                String txt_leader = leader.getText().toString();
                HashMap<String,Object>map = new HashMap<>();



                if(txt_tripname.isEmpty() || txt_noofmember.isEmpty()){
                    Toast.makeText(trip.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email = user.getEmail();
                        String femail = email;
                    map.put("Email",femail);
                    map.put("Trip Name",txt_tripname);
                    map.put("Number of member",txt_noofmember);
                    map.put("Leader",txt_leader);

                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).updateChildren(map);



                    /*
                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(String.valueOf(maxid+1)).child("Email").setValue(femail);
                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(String.valueOf(maxid+1)).child("Trip").child("Trip Name").setValue(txt_tripname);
                    FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(String.valueOf(maxid+1)).child("Trip").child("Number of members").setValue(txt_noofmember);
                     */








                    Toast.makeText(trip.this, "Your records have saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}