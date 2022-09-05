package com.example.android.beamlargefiles.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.activity.ViewTransactionHistoryActivity;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoryListItemAdapter extends RecyclerView.Adapter<HistoryListItemAdapter.ViewHolder>{

    private HashMap<String, List<Contact>> listdata;
    Context c;

    public HistoryListItemAdapter(Context context, HashMap<String, List<Contact>> listdata) {
        this.listdata = listdata;
        this.c= context;
    }

    public HistoryListItemAdapter(ViewTransactionHistoryActivity context, Map<String, List<Contact>> listData) {
        this.listdata = listdata;
        this.c= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.history_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        HashMap<String, ArrayList<Contact>>
        Contact c = (Contact) listdata.get(position);
//        HistoryListItem myListData = listdata.get(position);
//        holder.textView.setText(": "+myListData.getItems());
//        holder.tvAddress.setText(": "+myListData.getDate());
    }


    public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,tvAddress;
//        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
//            relativeLayout = (LinearLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

}