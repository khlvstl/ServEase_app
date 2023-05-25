package com.example.smartkartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    EditText etname, etphone, etpass;
    Button register;
    static DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        databaseUsers = FirebaseDatabase.getInstance().getReference("memberReg");
        register = (Button) findViewById(R.id.btnregister);
        etname = (EditText) findViewById(R.id.etuname);
        etphone = (EditText) findViewById(R.id.etEmail);
        etpass = (EditText) findViewById(R.id.etPwd);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
            }
        });
    }

    public void reg() {
        String name = etname.getText().toString();
        String phone = etphone.getText().toString();
        String password = etpass.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please Enter Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            String id = databaseUsers.push().getKey();
            MemberReg memberReg = new MemberReg(id, name, password, phone);
            assert id != null;
            databaseUsers.child(id).setValue(memberReg);
            Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
            redirectToLoginPage();
        }
    }

    private void redirectToLoginPage() {
        Intent intent = new Intent(RegisterPage.this, LoginPage.class);
        startActivity(intent);
        finish(); // Optional: If you want to finish the RegisterPage activity
    }

    public static void getuser() {
        databaseUsers = FirebaseDatabase.getInstance().getReference("memberReg");
    }
}
