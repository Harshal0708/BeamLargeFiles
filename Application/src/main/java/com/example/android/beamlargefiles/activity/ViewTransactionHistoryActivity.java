package com.example.android.beamlargefiles.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.adapter.HistoryListItemAdapter;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;
import com.example.android.beamlargefiles.utils.DatabaseHandler;
import com.example.android.common.activities.SampleActivityBase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewTransactionHistoryActivity extends SampleActivityBase {

    RecyclerView recyclerView;
//    TextView tvNodata;
    HistoryListItemAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_history);

        findViewById(R.id.imBack).setOnClickListener(view -> {
            finish();
        });

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
//        if(list.size()!=0){
//            tvNodata.setVisibility(View.GONE);
//        }else {
//            tvNodata.setVisibility(View.VISIBLE);
//        }
    }

    public ArrayList<HistoryListItem> reverseArrayList(ArrayList<HistoryListItem> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<HistoryListItem> revArrayList = new ArrayList<HistoryListItem>();
        for (int i = alist.size() - 1; i >= 0; i--) {
            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

}