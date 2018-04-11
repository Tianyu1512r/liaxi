package com.example.jane.myapplication1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jane on 2017/11/29.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public MyAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view=View.inflate(context,R.layout.item,null);
            holder=new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.tv);

            view.setTag(holder);

        }else{
            holder=(ViewHolder) view.getTag();
        }

        holder.textView.setText(data.get(i));

        return view;
    }

    class ViewHolder{
        TextView textView;
    }
}
