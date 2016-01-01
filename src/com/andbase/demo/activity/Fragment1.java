
package com.andbase.demo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.andbase.R;
import com.andbase.demo.adapter.ImageListAdapter;
import com.andbase.global.Constant;
import com.andbase.global.MyApplication;


public class Fragment1 extends Fragment {
	
	private MyApplication application;
	private Activity mActivity = null;
	private List<Map<String, Object>> list = null;
	private List<Map<String, Object>> newList = null;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mListView = null;
	private int currentPage = 1;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	private ImageListAdapter myListViewAdapter = null;
	private int total = 50;
	private int pageSize = 5;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		 mActivity = this.getActivity();
		 application = (MyApplication) mActivity.getApplication();
		 
		 View view = inflater.inflate(R.layout.pull_to_refresh_list, null);
		 for (int i = 0; i < 22; i++) {
	        	mPhotoList.add(Constant.BASEURL+"content/templates/amsoft/images/rand/"+i+".jpg");
		 }
		 //获取ListView对象
         mAbPullToRefreshView = (AbPullToRefreshView)view.findViewById(R.id.mPullRefreshView);
         mListView = (ListView)view.findViewById(R.id.mListView);
        
         //设置监听器
         mAbPullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				refreshTask();
			}
		});
         mAbPullToRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {
			
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				loadMoreTask();
				
			}
		});
        
         //设置进度条的样式
         mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
         mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
         //mAbPullListView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular2));
         //mAbPullListView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular2));
         //ListView数据
    	 list = new ArrayList<Map<String, Object>>();
    	
    	 //使用自定义的Adapter
    	 myListViewAdapter = new ImageListAdapter(mActivity, list,R.layout.item_list,
				new String[] { "itemsIcon", "itemsTitle","itemsText" }, new int[] { R.id.itemsIcon,
						R.id.itemsTitle,R.id.itemsText });
    	 mListView.setAdapter(myListViewAdapter);
    	 //item被点击事件
    	 mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
    	 });

		 return view;
	} 
	
	

	@Override
	public void onStart() {
		super.onStart();
		
    	//第一次下载数据
		refreshTask();
	}

	public void refreshTask(){
        AbTask mAbTask = new AbTask();
    	AbTaskItem item = new AbTaskItem();
    	item.setListener(new AbTaskListener() {

			@Override
			public void update() {
				list.clear();
				if(newList!=null && newList.size()>0){
	                list.addAll(newList);
	                myListViewAdapter.notifyDataSetChanged();
	                newList.clear();
   		    	}
				mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void get() {
	   		    try {
	   		    	Thread.sleep(1000);
	   		    	currentPage = 1;
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	
	   		    	for (int i = 0; i < 10; i++) {
	   		    		map = new HashMap<String, Object>();
	   					map.put("itemsIcon",mPhotoList.get(i));
		   		    	map.put("itemsTitle", "item"+i);
		   		    	map.put("itemsText", "item..."+i);
		   		    	newList.add(map);
	   				}
	   		    } catch (Exception e) {
	   		    }
		  };
		});
        
        mAbTask.execute(item);
    }
    
    public void loadMoreTask(){
    	
        AbTask mAbTask = new AbTask();
        AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskListener() {

			@Override
			public void update() {
				if(newList!=null && newList.size()>0){
					list.addAll(newList);
					myListViewAdapter.notifyDataSetChanged();
					newList.clear();
                }
				mAbPullToRefreshView.onFooterLoadFinish();
			}

			@Override
			public void get() {
	   		    try {
	   		    	currentPage++;
	   		    	Thread.sleep(1000);
	   		    	newList = new ArrayList<Map<String, Object>>();
	   		    	Map<String, Object> map = null;
	   		    	
	   		    	for (int i = 0; i < 10; i++) {
	   		    		map = new HashMap<String, Object>();
	   					map.put("itemsIcon",mPhotoList.get(new Random().nextInt(mPhotoList.size())));
		   		    	map.put("itemsTitle", "item上拉"+i);
		   		    	map.put("itemsText", "item上拉..."+i);
		   		    	newList.add(map);
	   				}
	   		    	
	   		    } catch (Exception e) {
	   		    	currentPage--;
	   		    	newList.clear();
	   		    }
		  };
		});
        
        mAbTask.execute(item);
    }

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}

