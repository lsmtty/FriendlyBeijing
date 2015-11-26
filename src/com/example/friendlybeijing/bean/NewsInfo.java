package com.example.friendlybeijing.bean;

import java.util.ArrayList;

public class NewsInfo {
	public int retcode;
	public Data data;
	public String title;
	public class Data 
	{
		public String countcommenturl;
		public String more;
		public ArrayList<News> news;
		public ArrayList<TopNews> topnews;
	}
	
	public class News
	{
		public String comment;
		public String commentlist;
		public String commenturl;
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "News [comment=" + comment + ", commentlist=" + commentlist
					+ ", commenturl=" + commenturl + ", id=" + id
					+ ", listimage=" + listimage + ", pubdate=" + pubdate
					+ ", title=" + title + ", type=" + type + ", url=" + url
					+ "]";
		}
		
	}
	
	public class TopNews
	{
		public String comment;
		public String commentlist;
		public String commenturl;
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TopNews [comment=" + comment + ", commentlist="
					+ commentlist + ", commenturl=" + commenturl + ", id=" + id
					+ ", topimage=" + topimage + ", pubdate=" + pubdate
					+ ", title=" + title + ", type=" + type + ", url=" + url
					+ "]";
		}
		 
	}

	@Override
	public String toString() {
		return "NewsInfo [retcode=" + retcode + ", data=" + data + ", title="
				+ title + "]";
	}
	
	
}
