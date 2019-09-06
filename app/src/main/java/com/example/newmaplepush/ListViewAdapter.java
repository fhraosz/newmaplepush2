package com.example.newmaplepush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<ListVo> ListVO = new ArrayList<ListVo>();
    public ListViewAdapter(){

    }
    @Override
    public int getCount(){
        return ListVO.size();
    }

    @Override
    public Object getItem(int position) {
        return ListVO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.manage_main, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);

        ListVo listViewItem = ListVO.get(position);

        name.setText(listViewItem.getName());

        return convertView;
    }

    public void addVo(String name){
        ListVo item = new ListVo();

        item.setName(name);

        ListVO.add(item);
    }
}
