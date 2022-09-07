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
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.utils.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    public static int sSelected = -1;
    public static int addressIndex;
    private Contact[] listdata;
    Context c;
    private static ItemClickListener sClickListener;

    public MyListAdapter(Context context,Contact[] listdata) {
        this.listdata = listdata;
        this.c= context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact myListData = listdata[position];
        holder.textView.setText(": "+listdata[position].getName());
        holder.tvAddress.setText(": "+listdata[position].getAddress());
        holder.tvAmount.setText(": "+listdata[position].getAmount());
        holder.tvAmountLastDate.setText(": "+listdata[position].getLastpdateDate());
        holder.tvACno.setText(": "+listdata[position].getSubhasad_code_no());
        holder.tvMobileNo.setText(": - ");
        if(listdata[position].getComment().equals("000000")){
            holder.tvAmountSub.setText(": 0");
        }else {
            holder.tvAmountSub.setText(": "+listdata[position].getComment());
        }
        String today = getCurrentDate();
        String itemDAte = listdata[position].getLastpdateDate();
        if(itemDAte.equals(today)){
            holder.relativeLayout.setBackgroundResource(R.drawable.border_select);
            holder.checkItem.setVisibility(View.VISIBLE);
        }else{
            holder.relativeLayout.setBackgroundResource(R.drawable.border);
            holder.checkItem.setVisibility(View.GONE);
        }
    }


    public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView checkItem;
        public TextView textView,tvAddress,tvAmount,tvAmountSub,tvAmountLastDate,tvACno,tvMobileNo;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.checkItem = (ImageView) itemView.findViewById(R.id.checkItem);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            this.tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            this.tvAmountSub = (TextView) itemView.findViewById(R.id.tvAmountSub);
            this.tvAmountLastDate = (TextView) itemView.findViewById(R.id.tvAmountLastDate);
            this.tvACno = (TextView) itemView.findViewById(R.id.tvACno);
            this.tvMobileNo = (TextView) itemView.findViewById(R.id.tvMobileNo);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            addressIndex = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view,listdata[getAdapterPosition()]);
            Log.d("Clickced :: ","CLicked "+listdata[getAdapterPosition()]);
        }
    }

    public void setOnItemClickListener(ItemClickListener clickListener) {
        sClickListener = clickListener;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int position, View view,Contact listItem);
    }

}