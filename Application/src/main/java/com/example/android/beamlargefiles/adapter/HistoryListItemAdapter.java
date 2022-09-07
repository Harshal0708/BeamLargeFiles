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
import android.widget.Toast;

import com.example.android.beamlargefiles.R;
import com.example.android.beamlargefiles.activity.ViewTransactionHistoryActivity;
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoryListItemAdapter extends RecyclerView.Adapter<HistoryListItemAdapter.ViewHolder>{

    public ArrayList<HistoryListItem> listdata;
    public Context c;

    public HistoryListItemAdapter(Context context, ArrayList<HistoryListItem> listdata) {
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
        HistoryListItem l = listdata.get(position);
        holder.tvDate.setText("Date : "+l.getDate());
        String names = "";
        String amounts = "";
        int totalAmount = 0;
        ArrayList<Contact> al = l.getItems();
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

        holder.tvNames.setText(""+names);
        holder.tvAmounts.setText(amounts);
        holder.tvTotalAmount.setText(""+totalAmount);

        holder.tvPDF.setOnClickListener(view -> {
            Toast.makeText(c, "Downloading PDF..!!!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate,tvNames,tvAmounts,tvTotalAmount,tvPDF;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            this.tvNames = (TextView) itemView.findViewById(R.id.tvNames);
            this.tvAmounts = (TextView) itemView.findViewById(R.id.tvAmounts);
            this.tvTotalAmount = (TextView) itemView.findViewById(R.id.tvTotalAmount);
            this.tvPDF = (TextView) itemView.findViewById(R.id.tvPDF);
        }
    }

}