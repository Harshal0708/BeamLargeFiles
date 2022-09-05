package com.example.android.beamlargefiles.activity;

import android.Manifest;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.beamlargefiles.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
        import java.io.IOException;

public class MainActivity10 extends AppCompatActivity {

    // After API 23 the permission request for accessing external storage is changed
    // Before API 23 permission request is asked by the user during installation of app
    // After API 23 permission request is asked at runtime
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    EditText editText;
    TextView textView;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        // findViewById return a view, we need to cast it to EditText View
        editText = (EditText) findViewById(R.id.editText_data);

        textView = (TextView) findViewById(R.id.textView_get_saved_data1);
        btn = (Button) findViewById(R.id.showButton_public);
        btn.setOnClickListener(view -> {
            // Accessing the saved data from the downloads folder
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // geeksData represent the file data that is saved publicly
            File file = new File(folder, "pctx.dat");
            String data = getdata(file);

            String[] lines = data.split("[\r\n]+");

            if (data != null) {
                textView.setText(data);
            } else {
                textView.setText("No Data Found");
            }
        });
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

    public void savePublicly(View view) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
        String editTextData = editText.getText().toString();

        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, "pctx.dat");
        writeTextData(file, editTextData);
        editText.setText("");
    }

    public void savePrivately(View view) {
        String editTextData = editText.getText().toString();

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("GeeksForGeeks");

        // Creating file with name gfg.txt
        File file = new File(folder, "gfg.txt");
        writeTextData(file, editTextData);
        editText.setText("");
    }

    public void viewInformation(View view) {
        // Creating an intent to start a new activity
        Intent intent = new Intent(MainActivity10.this, ViewInformationActivity.class);
        startActivity(intent);
    }

    // writeTextData() method save the data into the file in byte format
    // It also toast a message "Done/filepath_where_the_file_is_saved"
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}