package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class expenses extends AppCompatActivity {

    Spinner spinfm,spinfp;
    DatabaseReference reff,reff1,sr,dr,lr,or,tr,br,mr;
    Button btn;
    EditText price;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList1 = new ArrayList<>();
    long maixid =0 ;
    String[] items = new String[]{"Shopping","Taxis & cabs","Lunch","Dinner","Breakfast","Others"};
    int totalfs=0;
    int tshop=0,to=0,td=0,tl=0,tt=0,tb=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        //ArrayAdapter<String>

        spinfm = findViewById(R.id.spinner);
        spinfp = findViewById(R.id.spinner2);
        price = findViewById(R.id.price);
        btn =findViewById(R.id.donebtn);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();


        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(expenses.this,android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinfm.setAdapter(arrayAdapter);
        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Members Name");

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
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

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(expenses.this,android.R.layout.simple_spinner_dropdown_item,items);
        spinfp.setAdapter(arrayAdapter1);


        reff1 = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History");
        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    maixid = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member = spinfm.getSelectedItem().toString();
                String thing  = spinfp.getSelectedItem().toString();
                final int money = Integer.parseInt(price.getText().toString());
                SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("dd/MM HH:mm");

                String now = ISO_8601_FORMAT.format(new Date());

                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History").child(String.valueOf(maixid+1)).child("Person").setValue(member);
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History").child(String.valueOf(maixid+1)).child("Amount").setValue(money);
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History").child(String.valueOf(maixid+1)).child("Thing").setValue(thing);
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses History").child(String.valueOf(maixid+1)).child("time").setValue(now);



                /*
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(email).child("Expenses Things").child("Shopping");
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(email).child("Expenses Things").child("BreakFast");
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(email).child("Expenses Things").child("Lunch");
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(email).child("Expenses Things").child("Taxis & cabs");
                FirebaseDatabase.getInstance().getReference().child("User").child(userid).child(email).child("Expenses Things").child("Others");*/


                if(thing =="Shopping"){

                        sr = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Shopping");
                        sr.runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                long temp = 0;
                                if(mutableData.getValue()!=null) {
                                    temp = mutableData.getValue(long.class);

                                }
                                temp = temp+money;
                                mutableData.setValue(temp);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                            }
                        });

                }

                if (thing=="Breakfast"){
                    br = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Breakfast");
                    br.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            long temp = 0;
                            if(mutableData.getValue()!=null) {
                                temp = mutableData.getValue(long.class);

                            }
                            temp = temp+money;
                            mutableData.setValue(temp);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }


                if (thing=="Lunch"){
                    lr = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Lunch");
                    lr.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            long temp = 0;
                            if(mutableData.getValue()!=null) {
                                temp = mutableData.getValue(long.class);

                            }
                            temp = temp+money;
                            mutableData.setValue(temp);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });                }


                if (thing=="Dinner"){
                    dr = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Dinner");
                    dr.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            long temp = 0;
                            if(mutableData.getValue()!=null) {
                                temp = mutableData.getValue(long.class);

                            }
                            temp = temp+money;
                            mutableData.setValue(temp);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });                }


                if (thing=="Others"){
                    or = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Others");
                    or.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            long temp = 0;
                            if(mutableData.getValue()!=null) {
                                temp = mutableData.getValue(long.class);

                            }
                            temp = temp+money;
                            mutableData.setValue(temp);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }


                if (thing=="Taxis & cabs"){
                    tr = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things").child("Taxis & cabs");
                    tr.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            long temp = 0;
                            if(mutableData.getValue()!=null) {
                                temp = mutableData.getValue(long.class);

                            }
                            temp = temp+money;
                            mutableData.setValue(temp);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }

               mr =  FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Person").child(member);
               mr.runTransaction(new Transaction.Handler() {
                   @NonNull
                   @Override
                   public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                       long temp = 0;
                       if(mutableData.getValue()!=null){
                           temp = mutableData.getValue(long.class);
                       }
                       temp = temp+money;
                       mutableData.setValue(temp);
                       return Transaction.success(mutableData);
                   }

                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                   }
               });

                Toast.makeText(expenses.this, "Expenses added successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}