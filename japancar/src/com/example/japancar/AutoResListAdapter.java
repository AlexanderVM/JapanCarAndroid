package com.example.japancar;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AutoResListAdapter extends BaseAdapter {

	private static ArrayList<ItemsForList> items;
    private LayoutInflater lInflater;
    public ImageLoader imageLoader; 
    
	public AutoResListAdapter(Context _context, ArrayList<ItemsForList> _items){
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
			convertView = lInflater.inflate(R.layout.auto_list_items, null);
			holder = new ViewHolder();
			holder.car = (TextView) convertView.findViewById(R.id.auto_list_mark);
			holder.photo = (ImageView) convertView.findViewById(R.id.auto_list_photo);
			holder.year = (TextView) convertView.findViewById(R.id.auto_list_year);
			holder.price = (TextView) convertView.findViewById(R.id.auto_list_price);
			holder.town = (TextView) convertView.findViewById(R.id.auto_list_town);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.car.setText(items.get(position).getCompany()+" "+items.get(position).getModel());
		holder.year.setText(items.get(position).getYear());
		holder.price.setText(items.get(position).getPrice());
		holder.town.setText(items.get(position).getTown());
		imageLoader.DisplayImage(items.get(position).getPhoto(), holder.photo);

		return convertView;
		
	
	}
	static class ViewHolder {
		TextView car;
        ImageView photo;
        TextView year;
        TextView price;
        TextView town;
	}
}

