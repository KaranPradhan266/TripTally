package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class history extends AppCompatActivity {

    DatabaseReference reff;
    ListView lom;
    ArrayList<String> amounta =new ArrayList<>();
    ArrayList<String> persona = new ArrayList<>();
    ArrayList<String> thinga = new ArrayList<>();
    ArrayList<String> result = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lom = findViewById(R.id.hlv);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final LinearLayout mainContent = findViewById(R.id.maincontent);
        final TextView textView = findViewById(R.id.data);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(history.this, android.R.layout.simple_list_item_1, result);

        lom.setAdapter(arrayAdapter);

            reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History");



            reff.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    mainContent.setVisibility(View.VISIBLE);
                    if (dataSnapshot.exists()) {


                        Integer amount = dataSnapshot.child("Amount").getValue(Integer.class);
                        String Person = dataSnapshot.child("Person").getValue(String.class);
                        String Thing = dataSnapshot.child("Thing").getValue(String.class);
                        String time  = dataSnapshot.child("time").getValue(String.class);
                        result.add(Person + " paid \u20B9 " + amount + " in " + Thing+" on "+time);
                        arrayAdapter.notifyDataSetChanged();


                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }
}