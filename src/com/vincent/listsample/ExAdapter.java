package com.vincent.listsample;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private List<Map<String, String>> listGroup;
	private List<List<Map<String, String>>> listChild;
	
	private Bitmap Icon;
	
	public ExAdapter(Context context, List<Map<String, String>> listGroup,List<List<Map<String, String>>> listChild)
	{
		this.context = context;
		this.listGroup = listGroup;
		this.listChild = listChild;
		Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.coffee_icon);
		Icon = icon;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return listGroup.size();
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return listGroup.get(groupPosition);
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	
	@SuppressLint("InflateParams") @Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ex_group, null);
			
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.sampleText);
			holder.image = (ImageView) convertView.findViewById(R.id.sampleImage);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		/*  (The old way, not efficient!)
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.ex_group, null);
		
		TextView sampleText = (TextView) layout.findViewById(R.id.sampleText);
		ImageView sampleImage = (ImageView) layout.findViewById(R.id.sampleImage);
		*/
		
		try {
			holder.image.setImageBitmap(Icon);
			//sampleImage.setImageBitmap(Icon);
			((MainListActivity) context).showMemory();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			Icon.recycle();
			Log.e("Memory","Out!");
		}
		String sample = (String) listGroup.get(groupPosition).get("groupSample");
		holder.text.setText(sample);
		//sampleText.setText(sample);
		
		convertView.setBackgroundColor(Color.WHITE);
		if (isHundreds(groupPosition))
			convertView.setBackgroundColor(Color.GRAY);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return listChild.get(groupPosition).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return listChild.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	
	@SuppressLint("InflateParams") @Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.ex_child, null);
		
		TextView sampleText = (TextView) layout.findViewById(R.id.sampleText2);
		
		@SuppressWarnings("unchecked")
		String childText = ((Map<String, String>)getChild(groupPosition, childPosition)).get("childSample");
		sampleText.setText(childText);
		
		return layout;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	static class ViewHolder
	{
		TextView text;
		ImageView image;
	}
	
	private boolean isHundreds(int position)
	{
		int target = 100;
		int total = listGroup.size() / target;
		
		for (int i = 1; i <= total; i++)
		{
			if (position+1 == target * i)
				return true;
		}
		return false;
	}
}
