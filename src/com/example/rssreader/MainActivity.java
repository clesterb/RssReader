package com.example.rssreader;
	import java.io.IOException;
	import java.net.MalformedURLException;
	import java.util.ArrayList;

	import org.xmlpull.v1.XmlPullParserException;

	import android.os.Bundle;
	import android.os.Handler;
	import android.app.Activity;
	import android.content.Context;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.View;
	import android.widget.ArrayAdapter;
	import android.widget.ListView;

	public class MainActivity extends Activity 
	{

		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			final ListView lv = (ListView) findViewById(R.id.rss_list);

			final Activity memRefThis = this;
			
			(new Thread(new Runnable(){

				ArrayList<RSSItem> RSSItems;

				@Override
				public void run() {
					try {
						this.RSSItems = RSSPullParser.parse("http://rss.cnn.com/rss/cnn_topstories.rss");

					} catch (MalformedURLException e) 
					{
						e.printStackTrace();
					} 
					catch (XmlPullParserException e) 
					{
						e.printStackTrace();
					} 
					catch (IOException e) 
					{	
						e.printStackTrace();
					}
					MainActivity.this.runOnUiThread(new Runnable(){
						@Override
						public void run() 
						{

							lv.setAdapter( new RSSItemAdapter( memRefThis , RSSItems ) );

						}
					});
				}
	        })).start();
		}
	}
	


