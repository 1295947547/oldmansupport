package com.example.oldmansupport.li;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oldmansupport.R;

import java.util.ArrayList;

public class ContactAdapter2 extends BaseAdapter {

	private LayoutInflater mInflater;
	ArrayList<ContactInfo> itemList;

	public ContactAdapter2(Context context,ArrayList<ContactInfo> itemList) {
		mInflater = LayoutInflater.from(context);
		this.itemList = itemList;
	}

	public ArrayList<ContactInfo> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ContactInfo> itemList) {
		this.itemList = itemList;
	}
	public int getCount() {
		return itemList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		convertView = mInflater.inflate(R.layout.activity_contact_list2, null);
		holder = new ViewHolder();
		holder.mname = (TextView) convertView.findViewById(R.id.mname);
		holder.msisdn = (TextView) convertView.findViewById(R.id.msisdn);

		convertView.setTag(holder);
		holder.mname.setText(itemList.get(position).getContactName());
		holder.msisdn.setText(itemList.get(position).getUserNumber());

		return convertView;
	}

	class ViewHolder {
		TextView mname;
		TextView msisdn;
	}

	class ViewProgressHolder {
		TextView text;
	}
}
