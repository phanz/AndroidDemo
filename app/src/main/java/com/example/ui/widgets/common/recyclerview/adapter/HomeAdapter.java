package com.example.ui.widgets.common.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demo.R;

import java.util.List;

/**
 * Created by phanz on 2017/4/28.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {

    List<String> mDataList;
    LayoutInflater mInflater;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItenLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public HomeAdapter(Context context,List<String> dataList){
        mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_recycler_view_home,parent,false);
        MyHolder holder = new MyHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        String value = mDataList.get(position);
        holder.text.setText(value);

        if(mOnItemClickListener != null){
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.text,pos);
                }
            });

            holder.text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItenLongClick(holder.text,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addData(int position) {
        mDataList.add(position, "Insert One");
        notifyItemInserted(position);
    }


    public void removeData(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.id_num);
        }
    }
}
