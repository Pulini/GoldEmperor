package com.goldemperor.ScanCode.CxStockIn.deleteslide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldemperor.ScanCode.CxStockIn.widget.OnDeleteListioner;
import com.goldemperor.R;

import java.util.LinkedList;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

	private Context mContext;
	public LinkedList<String[]> mlist = null;
	//public Map<String, String> mBarcodeMap= null;
	private OnDeleteListioner mOnDeleteListioner;
	private boolean delete = false;

	// private Button curDel_btn = null;
	// private UpdateDate mUpdateDate = null;

	public MyAdapter(Context mContext,LinkedList<String[]> mlist) {
		this.mContext = mContext;
		this.mlist = mlist;
		//this.mBarcodeMap=mBarcodeMap;

	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setOnDeleteListioner(OnDeleteListioner mOnDeleteListioner) {
		this.mOnDeleteListioner = mOnDeleteListioner;
	}

	public int getCount() {

		return mlist.size();
	}

	public Object getItem(int pos) {
		return mlist.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(final int pos, View convertView, ViewGroup p) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.cxstockin_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.item_text);
			viewHolder.delete_action = (TextView) convertView
					.findViewById(R.id.delete_action);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mOnDeleteListioner != null)
					mOnDeleteListioner.onDelete(pos);

			}
		};
		viewHolder.delete_action.setOnClickListener(mOnClickListener);
		viewHolder.name.setText(mlist.get(pos)[1]);
		return convertView;
	}

	public static class ViewHolder {
		public TextView name;
		public TextView delete_action;

	}

}
