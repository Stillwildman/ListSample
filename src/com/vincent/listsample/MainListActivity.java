package com.vincent.listsample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MainListActivity extends Activity
{
	private ExpandableListView exList;
	private ExAdapter exAdapter;
	private EditText numberInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_layout);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		createList(5000);
		
		numberInput = (EditText) findViewById(R.id.numberInput);
		numberInput.setFocusable(true);
		numberInput.setFocusableInTouchMode(true);
		
		numberInput.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count){
				if (numberInput.getText().toString().isEmpty())
					createList(5000);
				else {
					int number = Integer.parseInt(s.toString());
					createList(number);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	
	public void createList(int count)
	{
		exList = (ExpandableListView) findViewById(R.id.sampleExList);
		
		List<Map<String, String>> listGroup = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> listChild = new ArrayList<List<Map<String, String>>>();
		
		//ArrayList<String> listSampleGroup = new ArrayList<String>();
		//ArrayList<String> listSampleChild = new ArrayList<String>();
		
		String sampleText = getResources().getString(R.string.TestingText);
		
		for (int i = 1; i <= count; i++)
		{
			//listSampleGroup.add(sampleText + i);
			Map<String, String> listGroupItem = new HashMap<String, String>();
			
			listGroupItem.put("groupSample", sampleText + " " + i);
			listGroup.add(listGroupItem);
			
			List<Map<String, String>> listChildItems = new ArrayList<Map<String, String>>();
			Map<String, String> listChildItem = new HashMap<String, String>();
			
			listChildItem.put("childSample", ""+i);
			listChildItems.add(listChildItem);
			listChild.add(listChildItems);
		}
		
		exAdapter = new ExAdapter(this, listGroup, listChild);
		//exList.setIndicatorBounds(0,100);
		exList.setAdapter(exAdapter);
	}
	
	public void showMemory()
	{
		TextView memTip = (TextView) findViewById(R.id.memTip);
		
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		//int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1024);
		long availableMem = mi.availMem;
		//String freeMem = String.valueOf(freeMemory) + "MB"; 
		String avaMem = String.valueOf(Formatter.formatFileSize(this, availableMem));
		
		memTip.setText("Memory: " + avaMem);
	}
}
