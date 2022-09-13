package com.example.android.beamlargefiles.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;
import com.example.android.common.activities.SampleActivityBase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewTransactionDetailsActivity extends SampleActivityBase {

    public TextView tvNames,tvAmounts,tvTotalAmount,tvPDF;
    public LinearLayout relativeLayout;
    HistoryListItem vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_details);

        findViewById(R.id.imBack).setOnClickListener(view -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("item");
//            HistoryListItem listItem = (HistoryListItem) value;
            Gson gsonObj = new Gson();
            vehicles = gsonObj.fromJson(value, HistoryListItem.class);
            //The key argument here must match that used in the other activity
        }

        tvNames = (TextView) findViewById(R.id.tvNames);
        tvAmounts = (TextView) findViewById(R.id.tvAmounts);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
//        tvPDF = (TextView) findViewById(R.id.tvPDF);
        relativeLayout = (LinearLayout) findViewById(R.id.relativeLayout);
        TextView btPickDate = findViewById(R.id.btPickDate);
        btPickDate.setText("Transaction Detail("+vehicles.getDate()+")");

        String names = "";
        String amounts = "";
        int totalAmount = 0;
        ArrayList<Contact> al = vehicles.getItems();
        for (int i=0;i<al.size();i++) {
            Contact c = al.get(i);
            if(i==0){
                names =  names +(i+1)+".) "+c.getName()+"("+c.getSubhasad_code_no()+")";
                amounts = amounts + c.getComment();
            }else {
                names =  names + "\n"+(i+1)+".) "+c.getName()+"("+c.getSubhasad_code_no()+")";
                amounts = amounts + "\n"+c.getComment();
            }
            totalAmount = totalAmount +Integer.parseInt(c.getComment());
        }

        tvNames.setText(""+names);
        tvAmounts.setText(amounts);
        tvTotalAmount.setText(""+totalAmount);

//        tvPDF.setOnClickListener(view -> {
//            Toast.makeText(this, "Downloading PDF..!!!", Toast.LENGTH_SHORT).show();
//        });

    }

}