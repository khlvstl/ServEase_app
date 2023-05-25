package com.example.smartkartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class StaffLogin extends AppCompatActivity {
    EditText spuser, sppwd;
    TextView spstatus;
    Button spsignin;
    String sna, spa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        spuser = findViewById(R.id.spuser);
        sppwd = findViewById(R.id.sppwd);
        spstatus = findViewById(R.id.spstatus);
        spsignin = findViewById(R.id.spsignin);
        spstatus.setText("");

        spsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStaff.getStaff();
                sna = spuser.getText().toString();
                spa = sppwd.getText().toString();

                if (TextUtils.isEmpty(sna))
                    spstatus.setText("Please enter your name");
                else if (TextUtils.isEmpty(spa))
                    spstatus.setText("Please enter your password");
                else {
                    AddStaff.databaseStaff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int success = 0;
                            for (DataSnapshot staffSnapshot : dataSnapshot.getChildren()) {
                                StaffReg staffReg = staffSnapshot.getValue(StaffReg.class);
                                final String dpn = staffReg.getStaffname();
                                final String dpa = staffReg.getPassword();

                                if (dpn != null && dpa != null && dpn.equals(sna) && dpa.equals(spa)) {
                                    AcceptOrders.getDelivery();
                                    AcceptOrders.databaseOngoingDelivery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int is_busy = 0;
                                            for (DataSnapshot deliverySnapshot : dataSnapshot.getChildren()) {
                                                DeliverOrder deliverOrder = deliverySnapshot.getValue(DeliverOrder.class);
                                                if (deliverOrder != null && deliverOrder.getDeliveryStaffName().equals(dpn)) {
                                                    Intent intent = new Intent(StaffLogin.this, CurrentOrderStatus.class);
                                                    intent.putExtra("STAFFNAME", dpn);
                                                    intent.putExtra("STAFFPASSWORD", dpa);
                                                    startActivity(intent);
                                                    is_busy = 1;
                                                }
                                            }
                                            if (is_busy == 0) {
                                                Intent intent = new Intent(StaffLogin.this, AcceptOrders.class);
                                                intent.putExtra("STAFFNAME", dpn);
                                                intent.putExtra("STAFFPASSWORD", dpa);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                    success = 1;
                                    Intent intent = new Intent(StaffLogin.this, CurrentOrderStatus.class);
                                    intent.putExtra("STAFFNAME", dpn);
                                    intent.putExtra("STAFFPASSWORD", dpa);
                                    startActivity(intent);
                                }
                            }
                            if (success == 0)
                                spstatus.setText("Invalid Credentials");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(StaffLogin.this, RegLogChoice.class));
    }
}
