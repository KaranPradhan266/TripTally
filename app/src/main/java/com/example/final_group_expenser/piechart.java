package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.ArrayList;

public class piechart extends AppCompatActivity {

    DatabaseReference reff;
    ArrayList<Integer> arrayList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();

        reff = FirebaseDatabase.getInstance().getReference().child("User").child(userid).child("Expenses Things");



        readData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Integer> list1) {

                AnimatedPieView animatedPieView = findViewById(R.id.pieview);
                AnimatedPieViewConfig config =new AnimatedPieViewConfig();
                config.addData(new SimplePieInfo(list1.get(4), Color.parseColor("#FFC300"),"Shopping"));
                config.addData(new SimplePieInfo(list1.get(5), Color.parseColor("#FF5733"),"Taxi & Cabs"));
                config.addData(new SimplePieInfo(list1.get(2), Color.parseColor("#C70039"),"Lunch"));
                config.addData(new SimplePieInfo(list1.get(1), Color.parseColor("#900C3F"),"Dinner"));
                config.addData(new SimplePieInfo(list1.get(0), Color.parseColor("#581845"),"Breakfast"));
                config.addData(new SimplePieInfo(list1.get(3), Color.parseColor("#0496FF"),"Others"));

                config.duration(2000);
                config.drawText(true);
                config.strokeMode(false);
                config.textSize(52);
                animatedPieView.applyConfig(config);
                animatedPieView.start();

                config.selectListener(new OnPieSelectListener<IPieInfo>() {
                    @Override
                    public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                        Toast.makeText(piechart.this, pieInfo.getDesc() + ": Rs " + pieInfo.getValue(), Toast.LENGTH_SHORT).show();
                    }
                });
                config.startAngle(-90);

            }
        });

    }

    private void readData(final FirebaseCallback firebaseCallback){

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int price = (int) ds.getValue(Integer.class);
                        arrayList.add(price);

                    }
                    firebaseCallback.onCallback(arrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<Integer>list1);
    }
}