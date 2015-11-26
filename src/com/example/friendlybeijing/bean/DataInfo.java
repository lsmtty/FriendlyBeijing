package com.example.friendlybeijing.bean;

import java.util.ArrayList;


public class DataInfo {
	
	public int retcode;
	public ArrayList<NewsMenuData> data;
		
	/**
	 * 侧边栏数据对象
	 * @author 思敏
	 *
	 */
	public class NewsMenuData{
		public String id;
		public String title;
		public String type;
		public String url;
		public ArrayList<NewsTagData> children;
	}
	
	/**
	 * 新闻页面内容类
	 * @author 思敏
	 *
	 */
	public class NewsTagData
	{
		public String id;
		public String title;
		public String type;
		public String url;

	}
	
}
