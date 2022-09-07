package com.example.android.beamlargefiles.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.adapter.MyListAdapter;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.utils.DatabaseHandler;
import com.example.android.common.activities.SampleActivityBase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashbordActivity extends SampleActivityBase implements MyListAdapter.ItemClickListener, DatePickerDialog.OnDateSetListener {

    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    private static final String VCF_DIRECTORY = "/Loan_Tracker";
    private File vcfFile;
    public static final String TAG = "VCarsSavePhoneActivity";
    File myExternalFile;
    FrameLayout btImportFile;
    ImageView btImportFile1;
    FrameLayout flListData;
    RecyclerView recyclerView;
    EditText etSearch;
    Contact[] myListData2;
    MyListAdapter adapter;
    TextView btPickDate, tvTotalTransacion, tvTotalTransacionAmount;
    int totalCollection = 0, totalCollectionAmount = 0;
    String myData = "";
    AlertDialog alertDialog;
    private int request_code = 1;
    String m_sDate;
    private Calendar myCalendar = Calendar.getInstance();
    LinearLayout llTrnsactionListByDate;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        isStoragePermissionGranted();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            popup();
        });

        btPickDate = findViewById(R.id.btPickDate);
        m_sDate = getCurrentDate();
        btPickDate.setText("Date : " + getCurrentDate());
        tvTotalTransacion = findViewById(R.id.tvTotalTransacion);
        tvTotalTransacionAmount = findViewById(R.id.tvTotalTransacionAmount);
        llTrnsactionListByDate = findViewById(R.id.llTrnsactionListByDate);
        llTrnsactionListByDate.setOnClickListener(view -> {
            startActivity(new Intent(DashbordActivity.this, ViewTransactionHistoryActivity.class));
        });

        DatePickerDialog.OnDateSetListener dateListener = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            m_sDate = dateParser.format(mCalendar.getTime()).toString();
            btPickDate.setText("Date : " + m_sDate);

            DatabaseHandler db = new DatabaseHandler(DashbordActivity.this);
            List<Contact> contacts = db.getAllContacts();
            ArrayList<Contact> listdata = new ArrayList<Contact>();
            for (int j = 0; j < contacts.size(); j++) {
                Contact cn = contacts.get(j);
//            if(cn.getAddress().equals(m_sDate)){
                listdata.add(cn);
//            }
            }
            Log.d("List :: ", listdata.toString());
            Contact[] listData2 = new Contact[listdata.size()];
            totalCollection = listdata.size();
            ArrayList<Contact> toDayList = new ArrayList<Contact>();
            for (int k = 0; k < listdata.size(); k++) {
                listData2[k] = listdata.get(k);
                totalCollectionAmount = totalCollectionAmount + listdata.get(k).getAmount();
                String m_sDate2 = listdata.get(k).getLastpdateDate().replace('-', '/');
//                if(m_sDate.equals(m_sDate2)){
//                    toDayList.add(listdata.get(k));
//                }
                if (m_sDate2.equals("-")) {
                } else {
                    toDayList.add(listdata.get(k));
                }
            }

            setTodayTotal(toDayList);

            setAdapterData(listData2);
            adapter.notifyDataSetChanged();


        };


        btPickDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));                 //following line to restrict future date selection
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DatabaseHandler db = new DatabaseHandler(DashbordActivity.this);
                List<Contact> contacts = db.getAllContacts();
                ArrayList<Contact> listdata = new ArrayList<Contact>();
                for (int j = 0; j < contacts.size(); j++) {
                    Contact cn = contacts.get(j);
                    String sCode = String.valueOf(cn.getSubhasad_code_no());
                    if (cn.getName().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))
                            || (sCode.contains(etSearch.getText().toString().trim()))
                    ) {
                        listdata.add(cn);
                    }
                }
                Contact[] listData2 = new Contact[listdata.size()];
                ArrayList<Contact> toDayList = new ArrayList<Contact>();
                for (int k = 0; k < listdata.size(); k++) {
                    listData2[k] = listdata.get(k);
//                    if(getCurrentDate().equals(listdata.get(k).getLastpdateDate())){
//                        toDayList.add(listdata.get(k));
//                    }
                    if (listdata.get(k).getLastpdateDate().equals("-")) {
                    } else {
                        toDayList.add(listdata.get(k));
                    }
                }
                setTodayTotal(toDayList);
                setAdapterData(listData2);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        Log.d("Reading: ", "Reading all contacts..");
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,subhasad_code_no: " + cn.getSubhasad_code_no() + " ,Name: " + cn.getName() + " ,Amount: " + cn.getAmount() + " ,Comment: " + cn.getComment();
            // Writing Contacts to log
            Log.d("LogData: ", log);
        }

        myListData2 = new Contact[contacts.size()];
        ArrayList<Contact> toDayList = new ArrayList<Contact>();
        for (int i = 0; i < contacts.size(); i++) {
            Contact cn = contacts.get(i);
            myListData2[i] = new Contact(cn.getID(), cn.getSubhasad_code_no(),
                    cn.getName(), cn.getAddress(),
                    cn.getAmount(), cn.getComment(),
                    cn.getTotalCollectrdAmount(), cn.getLastpdateDate());
            if (cn.getLastpdateDate().equals("-")) {
            } else {
                toDayList.add(cn);
            }
        }
        setTodayTotal(toDayList);
        setAdapterData(myListData2);

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);

        tv1.setOnClickListener(view -> {
            tv1.setBackgroundResource(R.color.purple_700);
            tv2.setBackgroundResource(R.color.white);
            tv1.setTextColor(getResources().getColor(R.color.white));
            tv2.setTextColor(getResources().getColor(R.color.black));
            btImportFile.setVisibility(View.GONE);
            flListData.setVisibility(View.VISIBLE);
        });

        tv2.setOnClickListener(view -> {
            tv2.setBackgroundResource(R.color.purple_700);
            tv1.setBackgroundResource(R.color.white);
            tv2.setTextColor(getResources().getColor(R.color.white));
            tv1.setTextColor(getResources().getColor(R.color.black));
            btImportFile.setVisibility(View.VISIBLE);
            flListData.setVisibility(View.GONE);
        });


        btImportFile = findViewById(R.id.btImportFile);
        btImportFile1 = findViewById(R.id.btImportFile1);

        btImportFile1.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DashbordActivity.this);
            builder.setMessage("Do you want to add data?")
                    .setTitle("Import Data")
                    .setCancelable(false)
                    .setPositiveButton("YES", (dialog, which) -> {
//                        openExplore();

                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor mEdit1 = sp.edit();
                        String jsonText2 = sp.getString("key", null);
                        mEdit1.putString("key", null);
                        mEdit1.apply();

                        for (Contact myListData : contacts) {
                            Contact c = new Contact(myListData.getID(), myListData.getAmount(), myListData.getComment());
                            db.deleteContact(c);
                        }

                        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File file = new File(folder, "pctx.dat");
                        String data = getdata(file);
                        String[] lines = data.split("[\r\n]+");
                        for (int i = 1; i < lines.length; i++) {
                            String[] aa = lines[i].split(",");
                            db.addContact(new Contact(1, Integer.parseInt(aa[0].trim()), aa[2], aa[4], Integer.parseInt(aa[3]), aa[1], Integer.parseInt(aa[1]), "-"));
                        }
                        tv1.setBackgroundResource(R.color.purple_700);
                        tv2.setBackgroundResource(R.color.white);
                        btImportFile.setVisibility(View.GONE);
                        flListData.setVisibility(View.VISIBLE);
                        etSearch.setText("");
                        finish();
                        Intent intent = new Intent(DashbordActivity.this, DashbordActivity.class);
                        startActivity(intent);
                        adapter.notifyDataSetChanged();
                        showAlert("Data Imported successfully");
                        dialog.cancel();
                    })
                    .setNegativeButton("NO", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });

        flListData = findViewById(R.id.flListData);
        TextView tvData = findViewById(R.id.tvViewData);
        findViewById(R.id.btViewInfoData).setOnClickListener(view -> {
            try {
                FileInputStream fis = new FileInputStream(myExternalFile);
                int a = 1;
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = "";
                while ((strLine = br.readLine()) != null) {
                    myData = myData + strLine + "\n";
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvData.setText(myData);

            try {
                File vdfdirectory = new File(
                        Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
                if (!vdfdirectory.exists()) {
                    vdfdirectory.mkdirs();
                }
                File vcfFile = new File(vdfdirectory, "android_" + Calendar.getInstance().getTimeInMillis() + ".txt");
                FileWriter fw = null;
                myExternalFile = new File(getExternalFilesDir(filepath), filename);
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    for (Contact cn : contacts) {
                        String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Amount: " + cn.getAmount() + " ,Comment: " + cn.getComment() + "\n";
                        fos.write(log.getBytes());
                    }
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fw = new FileWriter(vcfFile);
                FileInputStream fis = new FileInputStream(myExternalFile);
                int a = 1;
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = "";
                while ((strLine = br.readLine()) != null) {
                    myData = myData + strLine + "\n";
                }
                fw.write("BEGIN:" + myData + "\r\n");
                in.close();
                fw.close();
                tvData.setText(myData);
                showAlert("File Saved in Loan_Tracker Folder!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(" IOException : ", "");
            }

        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg).setTitle(R.string.app_name);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.cancel();
                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//                        dialog.cancel();
//                    }
//                })
        ;
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.app_name);
        alert.show();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        m_sDate = dateParser.format(mCalendar.getTime()).toString();
        btPickDate.setText("Date : " + m_sDate);

        DatabaseHandler db = new DatabaseHandler(DashbordActivity.this);
        List<Contact> contacts = db.getAllContacts();
        ArrayList<Contact> listdata = new ArrayList<Contact>();
        for (int j = 0; j < contacts.size(); j++) {
            Contact cn = contacts.get(j);
//            if(cn.getAddress().equals(m_sDate)){
            listdata.add(cn);
//            }
        }
        Log.d("List :: ", listdata.toString());
        Contact[] listData2 = new Contact[listdata.size()];
        totalCollection = listdata.size();
        ArrayList<Contact> toDayList = new ArrayList<Contact>();
        for (int k = 0; k < listdata.size(); k++) {
            listData2[k] = listdata.get(k);
            totalCollectionAmount = totalCollectionAmount + listdata.get(k).getAmount();
            String m_sDate2 = listdata.get(k).getLastpdateDate().replace('-', '/');
//            if(m_sDate.equals(m_sDate2)){
//                toDayList.add(listdata.get(k));
//            }
            if (m_sDate2.equals("-")) {
            } else {
                toDayList.add(listdata.get(k));
            }
        }
        setTodayTotal(toDayList);

        setAdapterData(listData2);
        adapter.notifyDataSetChanged();

    }

    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void importFile(Uri uri) {
        String fileName = getFileName(uri);
        try {
            File f = new File(uri.getPath());
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myfile = new File(Environment.getExternalStorageDirectory() + "/" + "Loan_Tracker");
            File file = new File(folder, fileName);
            String data = getdata(file);
            String[] lines = data.split("[\r\n]+");
            DatabaseHandler db = new DatabaseHandler(this);
            for (int i = 1; i < lines.length; i++) {
                String[] aa = lines[i].split(",");
                db.addContact(new Contact(1, Integer.parseInt(aa[0].trim()), aa[2], aa[4], Integer.parseInt(aa[3]), aa[1], Integer.parseInt(aa[1]), "-"));
            }
            showAlert("Data Imported successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 2 || resultCode != RESULT_OK) {
            return;
        } else {
            importFile(data.getData());
        }
    }

    public void showFileChooser() {
        Intent intent =
//                new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, 2);

//        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }
        cursor.moveToFirst();
        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        cursor.close();
        return fileName;
    }

    void openExplore() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                Log.e(TAG, "get permision   ");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
            } else {
                Log.e(TAG, "get permision-- already granted ");
                showFileChooser();
            }
        } else {
            showFileChooser();
        }
    }

    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public void setTodayTotal(ArrayList<Contact> toDayList) {

        int todayCollectedCount = toDayList.size();
        int todayCollectedAmount = 0;
        for (Contact i : toDayList) {
            todayCollectedAmount = todayCollectedAmount + i.getTotalCollectrdAmount();
        }
        tvTotalTransacion.setText("" + todayCollectedCount);
        tvTotalTransacionAmount.setText("" + todayCollectedAmount);
    }

    public void setAdapterData(Contact[] listData) {
        adapter = new MyListAdapter(this, listData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        if (listData.length != 0) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    @Override
    public void onItemClickListener(int position, View view, Contact listItem) {
        Contact selectedItem = listItem;//myListData2[position];
        String m_sDate2 = selectedItem.getLastpdateDate();

        if (m_sDate.equals(m_sDate2)) {
            showAlert("You will not be able to update this Account amount");
        } else if (getCurrentDate().equals(m_sDate)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.customview, viewGroup, false);
            EditText etAmount = dialogView
                    .findViewById(
                            R.id.etAmount);
            EditText etComment = dialogView
                    .findViewById(
                            R.id.etComment);
            TextView tvAmount = dialogView
                    .findViewById(
                            R.id.tvAmount);
            Button btn
                    = dialogView
                    .findViewById(
                            R.id.buttonOk);
            Button buttonSms
                    = dialogView
                    .findViewById(
                            R.id.buttonSms);
            tvAmount.setText(selectedItem.getName());
            if(selectedItem.getComment().equals("000000")){
                etComment.setText("0");
            }else {
                etComment.setText("" + selectedItem.getComment());
            }
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            btn.setOnClickListener(view1 -> {
                updateAmount(selectedItem, etComment.getText().toString().trim(), "save");
                alertDialog.hide();
            });
            buttonSms.setOnClickListener(view1 -> {
                updateAmount(selectedItem, etComment.getText().toString().trim(), "sms");
                alertDialog.hide();
            });
            alertDialog.show();
        } else {
            showAlert("You have to select today date to update this item's amount");
        }
    }

    void popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(DashbordActivity.this).inflate(R.layout.exportpopup, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();

        Button btExportFile
                = dialogView
                .findViewById(R.id.btEmail1);
        Button btSave
                = dialogView.findViewById(R.id.btSave1);
        btExportFile.setOnClickListener(view -> {
            alertDialog.hide();
            sendEmail();
        });
        btSave.setOnClickListener(view -> {
            if (isStoragePermissionGranted()) {
                savevCardData();
            }
            alertDialog.hide();
        });

    }

    public void sendEmail() {
        FileWriter fw = null;
        File file = null;
        FileWriter fw2 = null;
        try {
            File vdfdirectory = new File(
                    Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
            // have the object build the directory structure, if needed.
            if (!vdfdirectory.exists()) {
                vdfdirectory.mkdirs();
            }
            file = new File(vdfdirectory, "share_file.dat");
            FileOutputStream fOut = new FileOutputStream(file);
            vcfFile = new File(vdfdirectory, "android_share_file.dat");
            fw = new FileWriter(vcfFile);
            fw2 = new FileWriter(file);
            DatabaseHandler db = new DatabaseHandler(this);
            List<Contact> contacts = db.getAllContacts();
            try {
                for (Contact cn : contacts) {
                    String log = cn.getID() + "," + cn.getComment() + "," + cn.getName() + "," + cn.getAmount() + "," + cn.getAddress() + "," + cn.getComment() + "\n";
                    fw.write(log);
                    fw2.write(log);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fw.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(" IOException : ", "");
        }

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            Uri path = Uri.fromFile(vcfFile);
            File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android_share_file.txt");
            Uri path2 = Uri.fromFile(filelocation);
            emailIntent.setAction(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_STREAM, vcfFile);
            emailIntent.setType("text/plain");
            startActivity(Intent.createChooser(emailIntent, "Sending email..."));
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
//                savevCardData();
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public void savevCardData() {

        try {
            File vdfdirectory = new File(
                    Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
            if (!vdfdirectory.exists()) {
                vdfdirectory.mkdirs();
            }
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy_KK:mm:ss_a", Locale.getDefault());
            String currentDateAndTime = df.format(new Date());
            String replaceStr1 = currentDateAndTime.replace(':', '_');
            vcfFile = new File("/storage/emulated/0/Loan_Tracker", "pcrx.dat");
            FileWriter fw = null;
            fw = new FileWriter(vcfFile);
            DatabaseHandler db = new DatabaseHandler(this);
            List<Contact> contacts = db.getAllContacts();
            try {
                ArrayList<Contact> toDayList = new ArrayList<Contact>();
                for (int i = 0; i < contacts.size(); i++) {
                    Contact cn = contacts.get(i);
                    myListData2[i] = new Contact(cn.getID(),
                            cn.getSubhasad_code_no(),
                            cn.getName(), cn.getAddress(),
                            cn.getAmount(), cn.getComment(),
                            cn.getTotalCollectrdAmount(), cn.getLastpdateDate());
                    if (cn.getLastpdateDate().equals("-")) {
                    } else {
                        toDayList.add(cn);
                    }
                }
                int todayCollectedCount = toDayList.size();
                int todayCollectedAmount = 0;
                for (Contact i : toDayList) {
                    todayCollectedAmount = todayCollectedAmount + i.getTotalCollectrdAmount();
                }

                int countRemainig1 = 6 - String.valueOf(todayCollectedCount).length();
                String addZero1 = "";
                for (int i = 0; i < countRemainig1; i++) {
                    addZero1 = addZero1.concat("0");
                }
                int countRemainig2 = 6 - String.valueOf(todayCollectedAmount).length();
                String addZero2 = "";
                for (int i = 0; i < countRemainig2; i++) {
                    addZero2 = addZero2.concat("0");
                }

                int countRemainig112 = 13 - String.valueOf(todayCollectedAmount).length();
                String addZero112 = "";
                for (int i = 0; i < countRemainig112; i++) {
                    addZero112 = addZero112.concat(" ");
                }

                String cDate = getCurrentDate().replace('/', '.');
                fw.write("  1000" +
                        "," + addZero1 + todayCollectedCount +
                        "," + addZero2 + (int) todayCollectedAmount + addZero112 +
                        ",001001" +
                        "," + cDate +
                        "," + "12341234\n");
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                String jsonText2 = "{Contact:[" + sp.getString("key", null) + "]}";
                //Converting jsonData string into JSON object
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listdata.add(jsonArray.get(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<Contact> updatedList = new ArrayList<>(listdata.size());
                Gson gson = new Gson();
                for (int i = 0; i < listdata.size(); i++) {
                    JSONObject obj = (JSONObject) listdata.get(i);
                    Contact user = gson.fromJson(String.valueOf(obj), Contact.class);
                    updatedList.add(user);
                }

                for (Contact cn : contacts) {
                    for (int i = 0; i < updatedList.size(); i++) {
                        if (updatedList.get(i).getID() == cn.getID() && updatedList.get(i).getLastpdateDate().equals(cn.getLastpdateDate())) {
                            cn = updatedList.get(i);
                            updatedList.remove(i);
                        }
                    }

                    int count = cn.getComment().length();
                    int countRemainig = 6 - count;
                    String addZero = "";
                    for (int i = 0; i < countRemainig; i++) {
                        addZero = addZero.concat("0");
                    }

                    int count11 = (int) (Math.log10(cn.getSubhasad_code_no()) + 1);
                    int countRemainig11 = 6 - count11;
                    String addZero11 = "";
                    for (int i = 0; i < countRemainig11; i++) {
                        addZero11 = addZero11.concat(" ");
                    }

                    int count22 = cn.getName().length();
                    int countRemainig22 = 16 - count22;
                    String addZero22 = "";
                    for (int i = 0; i < countRemainig22; i++) {
                        addZero22 = addZero22.concat(" ");
                    }

                    int count227 = String.valueOf(cn.getAmount()).length();
                    int countRemainig227 = 6 - count227;
                    String addZero227 = "";
                    for (int i = 0; i < countRemainig227; i++) {
                        addZero227 = addZero227.concat("0");
                    }

                    String log = addZero11 + cn.getSubhasad_code_no() + "," + addZero + cn.getComment() + "," + cn.getName() + addZero22 + "," + addZero227 + (int) cn.getAmount() + "," + cn.getLastpdateDate() + "," + addZero + cn.getComment() + "  " + "\n";
                    String d1 = getCurrentDate();
                    String d2 = cn.getAddress();
                    if (cn.getComment().equals("000000")) {  //m_sDate

                    } else {
                        fw.write(log);
                    }
                }

                for (Contact cn : updatedList) {
                    int count = cn.getComment().length();
                    int countRemainig = 6 - count;
                    String addZero = "";
                    for (int i = 0; i < countRemainig; i++) {
                        addZero = addZero.concat("0");
                    }

                    int count11 = (int) (Math.log10(cn.getSubhasad_code_no()) + 1);
                    int countRemainig11 = 6 - count11;
                    String addZero11 = "";
                    for (int i = 0; i < countRemainig11; i++) {
                        addZero11 = addZero11.concat(" ");
                    }

                    int count22 = cn.getName().length();
                    int countRemainig22 = 16 - count22;
                    String addZero22 = "";
                    for (int i = 0; i < countRemainig22; i++) {
                        addZero22 = addZero22.concat(" ");
                    }

                    int count227 = String.valueOf(cn.getAmount()).length();
                    int countRemainig227 = 6 - count227;
                    String addZero227 = "";
                    for (int i = 0; i < countRemainig227; i++) {
                        addZero227 = addZero227.concat("0");
                    }

                    String log = addZero11 + cn.getSubhasad_code_no() + "," + addZero + cn.getComment() + "," + cn.getName() + addZero22 + "," + addZero227 + (int) cn.getAmount() + "," + cn.getLastpdateDate() + "," + addZero + cn.getComment() + "  " + "\n";
                    String d1 = getCurrentDate();
                    String d2 = cn.getAddress();
                    if (cn.getComment().equals("000000")) {  //m_sDate
                    } else {
                        fw.write(log);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            fw.close();
            showAlert("File Saved in Loan_Tracker Folder!");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    EXTERNAL_STORAGE_PERMISSION_CODE);
//
//            // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
//            // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
//            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//
//            // Storing the data in file with name as geeksData.txt
//            File file = new File(folder, "pcrx.dat");
////            writeTextData(file, editTextData);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(" IOException : ", "");
        }
    }

    public void updateAmount(Contact selectedItem, String amount, String buttonEvent) {
        int totalA = selectedItem.getAmount() + Integer.parseInt(amount);
        int totalCollectedAmount = selectedItem.getTotalCollectrdAmount() + Integer.parseInt(amount);
        Contact currentItem = new Contact(selectedItem.getID(), selectedItem.getSubhasad_code_no(), selectedItem.getName(), selectedItem.getAddress(),
//                selectedItem.getAmount(), selectedItem.getComment(), selectedItem.getTotalCollectrdAmount(), m_sDate);
                totalA, amount, totalCollectedAmount, m_sDate);
        Contact updatedItem = new Contact(selectedItem.getID(), selectedItem.getSubhasad_code_no(), selectedItem.getName(),
                selectedItem.getAddress(), totalA, amount, totalCollectedAmount, m_sDate);
        DatabaseHandler db = new DatabaseHandler(this);
        db.updateContact(updatedItem);

        List<Contact> contacts = db.getAllContacts();
        ArrayList<Contact> toDayList = new ArrayList<Contact>();
        for (int i = 0; i < contacts.size(); i++) {
            Contact cn = contacts.get(i);
            myListData2[i] = new Contact(cn.getID(),
                    cn.getSubhasad_code_no(),
                    cn.getName(), cn.getAddress(),
                    cn.getAmount(), cn.getComment(),
                    cn.getTotalCollectrdAmount(), cn.getLastpdateDate());
            if (cn.getLastpdateDate().equals("-")) {
            } else {
                toDayList.add(cn);
            }
        }
        setTodayTotal(toDayList);

        setAdapterData(myListData2);
        adapter.notifyDataSetChanged();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        String jsonText2 = sp.getString("key", null);
        if (jsonText2 == null) {
            jsonText2 = "";
            mEdit1.putString("key", jsonText2.concat(currentItem.toString()));
        } else {
            mEdit1.putString("key", jsonText2.concat("," + currentItem));
        }
        mEdit1.apply();

        if (buttonEvent.equals("sms")) {
            showAlert("Amount Updated Successfully & SMS Sent Successfully");
        } else { //save
            showAlert("Amount Updated Successfully");
        }
    }

}