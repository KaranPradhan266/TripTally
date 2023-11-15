package com.example.final_group_expenser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delete extends AppCompatActivity {

    DatabaseReference reff,reff2,root;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);



        delete = findViewById(R.id.deletec);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteuser();

            }
        });






    }

    private void deleteuser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid =user.getUid();
        reff2 = FirebaseDatabase.getInstance().getReference().child("User").child(userid);
        reff2.removeValue();
        Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
    }
}