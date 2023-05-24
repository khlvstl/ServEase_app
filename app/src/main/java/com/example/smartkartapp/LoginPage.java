package com.example.smartkartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    EditText email, pass;
    Button signin;
    TextView status;
    String em,pa;
    static String tmpname;
    //static DatabaseReference databaseCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pwd);
        signin = (Button) findViewById(R.id.btnsignin);
        //databaseCurrentUser= FirebaseDatabase.getInstance().getReference("getUserLogin");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPage.getuser();
                em = email.getText().toString();
                pa = pass.getText().toString();
                RegisterPage.databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int x = 0;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            MemberReg memberReg = userSnapshot.getValue(MemberReg.class);
                            String dpn = memberReg.getUsername();
                            String dph = memberReg.getPhone();
                            String dpa = memberReg.getPassword();
                            if (dph.equals(em) && dpa.equals(pa)) {
                                /*{
                                    String id=databaseCurrentUser.push().getKey();
                                    GetUserLogin getUserLogin=new GetUserLogin(dpn,dph,id);
                                    databaseCurrentUser.child(id).setValue(getUserLogin);
                                }*/

                                Intent i=new Intent(LoginPage.this,HomePageActivity.class);
                                i.putExtra("NAME",dpn);
                                i.putExtra("PHONE",dph);
                                i.putExtra("PASSWORD",dpa);
                                i.putExtra("CALLINGACTIVITY","LoginPage");
                                startActivity(i);
                                x = 1;
                            }
                        }
                        if (x == 0)
                            status.setText("Invalid Credentials");
                    }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                   });

            }
        });
    }
   public void onBackPressed(){
       startActivity(new Intent(LoginPage.this,RegLogChoice.class));
   }
}