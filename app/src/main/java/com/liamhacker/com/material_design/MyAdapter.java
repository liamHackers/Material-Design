package com.liamhacker.com.material_design;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by lilingyong on 16/9/7.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
    private List<String> mData;
    private Context mContext;
    private LayoutInflater mlayoutInflater;


    public MyAdapter(List<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mlayoutInflater = LayoutInflater.from(mContext);
        Log.d("hhhh",mData.get(2));
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_layout, parent, false);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent);
        myViewHolder mviewHolder = new myViewHolder(view);
        return mviewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

            holder.tv.setText(mData.get(position));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tv;

        public myViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.TV);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
