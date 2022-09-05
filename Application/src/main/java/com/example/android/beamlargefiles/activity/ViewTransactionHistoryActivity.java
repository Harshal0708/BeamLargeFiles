package com.example.android.beamlargefiles.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.adapter.HistoryListItemAdapter;
import com.example.android.beamlargefiles.adapter.MyListAdapter;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;
import com.example.android.beamlargefiles.utils.DatabaseHandler;
import com.example.android.common.activities.SampleActivityBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewTransactionHistoryActivity extends SampleActivityBase {

    RecyclerView recyclerView;
    HistoryListItemAdapter adapter;
    Contact[] myListData2;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_history);

        findViewById(R.id.imBack).setOnClickListener(view -> {
            finish();
        });

        recyclerView = findViewById(R.id.rvList);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();

        Map<String, List<Contact>> studlistGrouped =
                contacts.stream().collect(Collectors.groupingBy(w -> w.lastpdateDate));

        setAdapterData(studlistGrouped);

//        for (Contact cn : contacts) {
//            String log = "Id: " + cn.getID() +" ,subhasad_code_no: " + cn.getSubhasad_code_no() + " ,Name: " + cn.getName() + " ,Amount: " + cn.getAmount()+ " ,Comment: " + cn.getComment();
//            // Writing Contacts to log
//            Log.d("LogData: ", log);
//        }
//
//        myListData2 = new Contact[contacts.size()];
//        ArrayList<Contact> toDayList = new ArrayList<Contact>();
//        for(int i=0; i<contacts.size();i++){
//            Contact cn = contacts.get(i);
//            myListData2[i] =  new Contact(cn.getID(),cn.getSubhasad_code_no(),
//                    cn.getName(),cn.getAddress(),
//                    cn.getAmount(),cn.getComment(),
//                    cn.getTotalCollectrdAmount(),cn.getLastpdateDate());
//            if(cn.getLastpdateDate().equals( "-")){
//            }else {
//                toDayList.add(cn);
//            }
//        }
//        ArrayList<HistoryListItem> hlData = new ArrayList<>();
//        for (int i=0;i<toDayList.size();i++) {
//            Contact c = toDayList.get(i);
////                hlData.add(new HistoryListItem(toDayList.get(i).getLastpdateDate(),c));
//        }


    }


    public void setAdapterData(Map<String, List<Contact>> listData){
        adapter = new HistoryListItemAdapter(this,listData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}