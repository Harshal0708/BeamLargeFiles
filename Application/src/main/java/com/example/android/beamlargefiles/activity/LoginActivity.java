package com.example.android.beamlargefiles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.beamlargefiles.R;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;

public class LoginActivity extends SampleActivityBase {

    EditText etUname,etPass;
    Button btLogin;
    String uN="",pS="";
    boolean isButtonEnable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUname = findViewById(R.id.etUN);
        etPass = findViewById(R.id.etPASS);
        btLogin = findViewById(R.id.btLogin);

//        etUname.setText("User1");
//        etPass.setText("123");
//        uN="User1";
//        pS="123";
//        btLogin.setEnabled(true);

        etUname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start,int before, int count) {
//                Field2.getText().clear();
                Log.d("UN : ",etUname.getText().toString().trim());
                uN = etUname.getText().toString().trim();
                if(uN.isEmpty()||pS.isEmpty()){
                    btLogin.setEnabled(false);
                }else {
                    btLogin.setEnabled(true);
                }
            }
        });
        etPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start,int before, int count) {
//                Field2.getText().clear();
                Log.d("pass : ",etPass.getText().toString().trim());
                pS = etPass.getText().toString().trim();
                if(uN.isEmpty()||pS.isEmpty()){
                    btLogin.setEnabled(false);
                }else {
                    btLogin.setEnabled(true);
                }
            }
        });

        btLogin.setOnClickListener(view -> {

            String un = etUname.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if(un.equals("User1")||un.equals("User2")||un.equals("User3")||un.equals("User4")){
                if(pass.equals("123")){
                        startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
                }else {
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Invalid Id", Toast.LENGTH_SHORT).show();
            }

        });
    }
}