package com.example.android.beamlargefiles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.android.beamlargefiles.R;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;

import java.io.File;

public class LoginActivity extends SampleActivityBase {

    EditText etUname,etPass;
    Button btLogin;
    String uN="",pS="";
    boolean isButtonEnable = false;
    ImageView showHide;
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
                uN = etUname.getText().toString().trim();
//                if(uN.isEmpty()||pS.isEmpty()){
//                    btLogin.setEnabled(false);
//                }else {
//                    btLogin.setEnabled(true);
//                }
            }
        });
        etPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start,int before, int count) {
//                Field2.getText().clear();
                pS = etPass.getText().toString().trim();
//                if(uN.isEmpty()||pS.isEmpty()){
//                    btLogin.setEnabled(false);
//                }else {
//                    btLogin.setEnabled(true);
//                }
            }
        });

        btLogin.setOnClickListener(view -> {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Daily_collection");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            }
            String un = etUname.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if(un.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
                return;
            }

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

        showHide = findViewById(R.id.showHide);
        showHide.setOnClickListener(view -> {
         showHidePassword();
        });
    }

    void showHidePassword(){
        if(etPass.getInputType() == InputType.TYPE_CLASS_TEXT) {
            etPass.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showHide.setImageResource(R.drawable.vieww);
            showHide.setColorFilter(ContextCompat.getColor(this, R.color.gray),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }else {
            showHide.setColorFilter(ContextCompat.getColor(this, R.color.buttonColor),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            showHide.setImageResource(R.drawable.hidden);
            etPass.setInputType( InputType.TYPE_CLASS_TEXT );
        }
        etPass.setSelection(etPass.getText().length());
    }

}