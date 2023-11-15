package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class money extends AppCompatActivity {

    DatabaseReference reff,reff1;
    ArrayList<Integer> arrayListpr = new ArrayList<>();
    ArrayList<String> arrayListna = new ArrayList<>();
    ArrayList<Integer> farrayList = new ArrayList<>();
    ArrayList<String> ofarrayList = new ArrayList<>();
    HashMap<String,Integer> map =new HashMap<>();
    ListView lom;
    long sum =0;

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String userid = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Person");
        reff1 = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Person");
        lom  = findViewById(R.id.mlv);

        readdata1(new FirebaseCallback1() {
            @Override
            public void OnCallback1(ArrayList<Integer> list1,ArrayList<String> list2) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(money.this,android.R.layout.simple_list_item_1,ofarrayList);
                lom.setAdapter(arrayAdapter);
                for (int i =0;i<list1.size();i++){
                   sum = sum+list1.get(i);
                }
               // Log.d("tag", String.valueOf(sum));
                long money_distribution = sum/list1.size();
               // Log.d("tag", String.valueOf(money_distribution));



                for (int i = 0;i<list1.size();i++){
                    int valu  = (int) (money_distribution-list1.get(i));
                    farrayList.add(valu);
                }

               // Log.d("tag",farrayList.toString());

                for(int i =0;i<list1.size();i++){
                    String person = list2.get(i);
                    int val = farrayList.get(i);
                    if(val<0){
                        ofarrayList.add(person+" will get: \u20B9"+(val*-1));
                    }
                    else{
                        ofarrayList.add(person+" will give: \u20B9 "+val);
                    }
                }

                Log.d("tag",ofarrayList.toString());


            }
        });






    }

    private void readdata1(final FirebaseCallback1 firebaseCallback1){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int value = (int)ds.getValue(Integer.class);
                        arrayListpr.add(value);
                        String va = ds.getKey();
                        arrayListna.add(va);
                    }
                }

                firebaseCallback1.OnCallback1(arrayListpr,arrayListna);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback1{
        void OnCallback1(ArrayList<Integer>list1,ArrayList<String>list2);
    }

}