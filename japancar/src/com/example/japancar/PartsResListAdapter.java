package com.example.japancar;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PartsResListAdapter extends BaseAdapter {

     private static ArrayList<ItemsForList> items;
     private LayoutInflater lInflater;
     public ImageLoader imageLoader; 
    
	public PartsResListAdapter(Context _context, ArrayList<ItemsForList> _items){
		items = _items;
		lInflater = LayoutInflater.from(_context);
		imageLoader=new ImageLoader(_context);
	
	}	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
				
		ViewHolder holder;
		if (convertView == null) {
			convertView = lInflater.inflate(R.layout.results_items, null);
			holder = new ViewHolder();
			holder.company = (TextView) convertView.findViewById(R.id.parts_res_text);
			holder.model = (TextView) convertView.findViewById(R.id.parts_res_text2);
			holder.price = (TextView) convertView.findViewById(R.id.parts_res_price);
			holder.name = (TextView) convertView.findViewById(R.id.parts_res_name);
			holder.photo = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.company.setText(items.get(position).getCompany());
		holder.model.setText(items.get(position).getModel());
		holder.name.setText(items.get(position).getName());
		holder.price.setText("Цена :"+items.get(position).getPrice());
		imageLoader.DisplayImage(items.get(position).getPhoto(), holder.photo);

		
		return convertView;
		
	
	}
	static class ViewHolder {
		TextView company;
		TextView model;
		TextView name;
		TextView price;
        ImageView photo;
	}
    }

