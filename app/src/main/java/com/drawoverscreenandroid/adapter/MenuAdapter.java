package com.drawoverscreenandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.drawoverscreenandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends BaseAdapter {

    public List<GetSet> _data;
    Context _c;
    ViewHolder v;

    public MenuAdapter(ArrayList<GetSet> list, Context context) {
        _data = list;
        _c = context;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.items_main, null);

        } else {
            view = convertView;
        }
        v = new ViewHolder();
        v.menuBtn = (ImageButton) view.findViewById(R.id.image);
        v.list_item = (TableRow) view.findViewById(R.id.list_item);
        v.name = (TextView) view.findViewById(R.id.name);

        final GetSet data = (GetSet) _data.get(position);
        if (data.getDrawable() != null) {
            v.menuBtn.setImageBitmap(data.getDrawable());
        } else {
            v.menuBtn.setImageResource(R.mipmap.ic_launcher);
        }
        v.name.setText(data.getName());
        v.list_item.setFocusable(true);

        v.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Close menu */
                Toast.makeText(_c, "Item Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("CloseContactList");
                intent.putExtra("close", "true");
                LocalBroadcastManager.getInstance(_c).sendBroadcast(intent);

                /* Add your action yo want to perform after list item click */
            }
        });
        return view;
    }

    static class ViewHolder {
        ImageView menuBtn;
        TextView name;
        TableRow list_item;
    }
}
