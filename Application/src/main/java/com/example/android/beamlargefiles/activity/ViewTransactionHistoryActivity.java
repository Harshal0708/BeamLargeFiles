package com.example.android.beamlargefiles.activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.adapter.HistoryListItemAdapter;
import com.example.android.beamlargefiles.adapter.MyListAdapter;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;
import com.example.android.beamlargefiles.utils.DatabaseHandler;
import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewTransactionHistoryActivity extends SampleActivityBase implements HistoryListItemAdapter.ItemClickListener,HistoryListItemAdapter.ItemClickListener2 {

    RecyclerView recyclerView;
//    TextView tvNodata;
    HistoryListItemAdapter adapter;

    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generatePDF(HistoryListItem listItem) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(20);
        title.setColor(ContextCompat.getColor(this, R.color.black));
//        canvas.drawText(getResources().getString(R.string.app_name), 209, 80, title);
        canvas.drawText("LATE SITARAM .G. MALI SEVING CREDIT SO. LTD", 200, 70, title);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        canvas.drawText("Date : "+listItem.getDate(), 200, 100, title);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(15);

        int xAxiStart =120;
        int xAxiEnd =620;

        int yAxiStart =200;
        int yAxiEnd =200;

        int totalAmount = 0;

        for (int i=0;i<listItem.getItems().size();i++) {
            Contact items = listItem.getItems().get(i);
            canvas.drawText((i+1)+".) "+items.getName()+"("+items.getSubhasad_code_no()+")", xAxiStart, yAxiStart, title);
            canvas.drawText(""+items.getComment(), xAxiEnd, yAxiEnd, title);
            yAxiStart = yAxiStart+20;
            yAxiEnd = yAxiEnd+20;
            totalAmount = totalAmount +Integer.parseInt(items.getComment());
        }

        canvas.drawText("_______________________________________________________________________________", xAxiStart, yAxiEnd-10, title);
        canvas.drawText("Total ", xAxiStart, yAxiStart+10, title);
        canvas.drawText(""+totalAmount, xAxiEnd, yAxiEnd+10, title);
        pdfDocument.finishPage(myPage);
        File vdfdirectory = new File(Environment.getExternalStorageDirectory() + "/Loan_Tracker/Download");
        if (!vdfdirectory.exists()) {
            vdfdirectory.mkdirs();
        }

        File file = new File(vdfdirectory, "DC_"+listItem.getDate().replace('/','_')+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writeStorage && readStorage) {
//                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_history);
        findViewById(R.id.imBack).setOnClickListener(view -> {
            finish();
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 80, 80, false);
        if (checkPermission()) {
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        recyclerView = findViewById(R.id.rvList);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp.getString("key", null)!=null){
            String jsonText2 = "{Contact:["+sp.getString("key", null)+"]}";
            JSONObject jsnobject = null;
            try {
                jsnobject = new JSONObject(jsonText2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<Object> listdata = new ArrayList<Object>();
            try {
                JSONArray jsonArray = jsnobject.getJSONArray("Contact");
                if (jsonArray != null) {
                    for (int i=0;i<jsonArray.length();i++){
                        listdata.add(jsonArray.get(i));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<Contact> updatedList = new ArrayList<>(listdata.size());
            Gson gson = new Gson();
            for (int i=0;i<listdata.size();i++) {
                JSONObject obj = (JSONObject) listdata.get(i);
                Contact user= gson.fromJson(String.valueOf(obj), Contact.class);
                updatedList.add(user);
            }
            Map<String, List<Contact>> studlistGrouped = updatedList.stream().collect(Collectors.groupingBy(w -> w.lastpdateDate));
            setAdapterData(studlistGrouped);
        }
    }

    public void setAdapterData(Map<String, List<Contact>> listData){
        ArrayList<HistoryListItem> list = new ArrayList<>();
        for (String key : listData.keySet()) {
            if(key.equals("-")){
            }else {
                list.add(new HistoryListItem(Integer.parseInt(key.substring(0,2)),key,new ArrayList<Contact>(listData.get(key))));
            }
        }
        Collections.sort(list);
        adapter = new HistoryListItemAdapter(this,list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemClickListener2(this);
//        if(list.size()!=0){
//            tvNodata.setVisibility(View.GONE);
//        }else {
//            tvNodata.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onItemClickListener(int position, View view, HistoryListItem listItem) {
        Intent in = new Intent(ViewTransactionHistoryActivity.this,ViewTransactionDetailsActivity.class);
        in.putExtra("item",listItem.toString());
        startActivity(in);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClickListener2(int position, View view, HistoryListItem listItem) {
        generatePDF(listItem);
//        Toast.makeText(this, "Downloading PDF..!!!", Toast.LENGTH_SHORT).show();
    }
}