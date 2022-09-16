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
import com.example.android.beamlargefiles.models.Contact;
import com.example.android.beamlargefiles.models.HistoryListItem;

import java.util.ArrayList;

public class HistoryListItemAdapter extends RecyclerView.Adapter<HistoryListItemAdapter.ViewHolder>{

    public ArrayList<HistoryListItem> listdata;
    public Context c;
    private static ItemClickListener sClickListener;
    private static ItemClickListener2 sClickListener2;
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
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvDate,tvNames,tvAmounts,tvTotalAmount;
        ImageView tvPDF;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            this.tvNames = (TextView) itemView.findViewById(R.id.tvNames);
            this.tvAmounts = (TextView) itemView.findViewById(R.id.tvAmounts);
            this.tvTotalAmount = (TextView) itemView.findViewById(R.id.tvTotalAmount);
            this.tvPDF = (ImageView) itemView.findViewById(R.id.tvPDF);
            relativeLayout = (LinearLayout) itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);
            tvPDF.setOnClickListener(view -> {
                sClickListener2.onItemClickListener2(getAdapterPosition(), view, listdata.get(getAdapterPosition()));
            });
        }

        @Override
        public void onClick(View view) {
            sClickListener.onItemClickListener(getAdapterPosition(), view, listdata.get(getAdapterPosition()));
            Log.d("Clickced :: ","CLicked "+ listdata.get(getAdapterPosition()));
        }
    }

    public void setOnItemClickListener(ItemClickListener clickListener) {
        sClickListener = clickListener;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener2(ItemClickListener2 clickListener2) {
        sClickListener2 = clickListener2;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int position, View view,HistoryListItem listItem);
    }

    public interface ItemClickListener2 {
        void onItemClickListener2(int position, View view,HistoryListItem listItem);
    }

}