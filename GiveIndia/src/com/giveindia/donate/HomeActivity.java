package com.giveindia.donate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	String[][] locData = new String[][]     {{"19.095021","72.921828"},
		    {"18.975668","72.812611"},
		    {"19.063327","72.844285"},
		    {"19.052312","72.824035"},
		    {"19.010781","72.816315"},
		    {"19.058711","72.893638"},
		    {"19.060749","72.911944"},
		    {"19.076053","72.877936"},
		    {"18.939585","72.828487"},
		    {"18.969111","72.811933"},
		    {"19.007918","72.826996"},
		    {"19.008466","72.822922"},
		    {"19.22299","72.840042"},
		    {"19.120824","72.910482"},
		    {"18.971926","72.822951"},
		    {"18.960824","72.817909"},
		    {"18.986777","72.836609"},
		    {"19.006518","72.823842"},
		    {"18.948569","72.794487"},
		    {"19.036516","72.846721"},
		    {"18.951346","72.821352"},
		    {"18.9235","72.831888"}} ;
	private Timer myTimer;
	AsyncTask<String, Void, String> webMgr;
	
	String[] list = new String[20];//{"Item1", "Item 2", "Item3", "ITEM4", "ITEM5", "Item6","Item7", "Item8", "Item9", "Item10"};
	int lastloaded = 0;
	boolean requested=true;
	
	
	//private String url = "https://mapsengine.google.com/map/edit?mid=zx71zJJPwdY8.kN-jpCAVDT9c";
	String url = "http://www.google.com/";
	
	public HomeActivity(){
		for(int i=0;i<20;i++){
			list[i]="Loading..";
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
     ImageView img = (ImageView) findViewById(R.id.imageview);
     new DownloadImageTask(img).execute(genLocQuery());
     
     myTimer = new Timer();
     myTimer.schedule(new TimerTask() {
         @Override
         public void run() {
         	reload_caller();
         }

     }, 0, 10000);
     
     ImageView iv = (ImageView) findViewById(R.id.imageview);
     iv.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			Intent myIntent = new Intent(HomeActivity.this, SearchActivity.class);
			startActivity(myIntent);
		}
	});
     new RequestTask(HomeActivity.this).execute("http://akarthik10.heliohost.org/scripts/cfg/cfg.php?action=timeline&q=giveindia");
    
     Toast.makeText(HomeActivity.this, "Please touch map to search!", Toast.LENGTH_LONG).show();
    }
    
    
    private void reload_caller()
    {
    	Runnable Timer_Tick = new Runnable() {
            public void run() {
            	
            		reload();
            	
            

            }
        };
        this.runOnUiThread(Timer_Tick);
        
    }
    
    public void populateList()
   	{
   		
       	 ListView lv = (ListView) findViewById(R.id.feedview);
            
            ArrayList<String> feed_array_list = new ArrayList<String>();
            
            for(int i=lastloaded;i<lastloaded+5;i++)
            {
            feed_array_list.add(list[i]);
            
            // This is the array adapter, it takes the context of the activity as a first // parameter, the type of list view as a second parameter and your array as a third parameter
            ArrayAdapter<String> arrayAdapter =      
            new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, feed_array_list);
            lv.setAdapter(arrayAdapter); 
            
            }
            Utility.setListViewHeightBasedOnChildren(lv);
   	}
    
    public void reload(){
        
    	if(requested==true){
    		//webMgr.execute("https://script.google.com/macros/s/AKfycbw3hs0kxKMBJTZTObqJEo6pwI1MwYQvI1LYxvpiAYmHV4UcqgJz/exec?action=timeline&q=giveindia");
    		//webMgr = new HTTPRequest(this).execute("http://akarthik10.heliohost.org/scripts/cfg/cfg.php?action=timeline&q=giveindia");
    		//new RequestTask(MainActivity.this).execute("http://akarthik10.heliohost.org/scripts/cfg/cfg.php?action=timeline&q=giveindia");
    		
    	}
    	lastloaded=(lastloaded+5)%list.length;
		populateList();
		//Toast.makeText(HomeActivity.this,""+lastloaded , Toast.LENGTH_LONG).show();
		
		
    	
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public String genLocQuery(){
    	String query="http://maps.googleapis.com/maps/api/staticmap?center="+locData[19][0]+","+locData[19][1]+"&zoom=12&size=700x570&maptype=roadmap&sensor=false";
		int i;
		for(i=0;i<locData.length;i++){
			query+="&markers=color:red%7Clabel:"+ ((char)(65+i)) +"%7C"+locData[i][0]+","+locData[i][1];
		}
		Log.d("",query);
    	return query;
    	
    }
    
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	    TextView mTextView;
	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	       
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("ErrorImage", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        
	    	bmImage.setImageBitmap(result);
	        
	    }
	}
    
    
    
class RequestTask extends AsyncTask<String, String, String>{

    	
    	HomeActivity mContext;
    	
    	public RequestTask(Context context) {
    		// TODO Auto-generated constructor stub
    		this.mContext=(HomeActivity) context;
    	}
    	
        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString.replace("&amp;", "&").replace("&quot;", "\"").replace("&#039;", "'").replace("&lt;", "<").replace("&gt;", ">");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            int j=0;
            Pattern p = Pattern.compile("<description>(.*?)<\\/description>", Pattern.DOTALL);
        	ArrayList<String> list = new ArrayList<String>();
        	Matcher m = p.matcher(result);
        	Log.d("ANI", ""+m.groupCount());
        	m.find();
        	//Toast.makeText(mContext, m.groupCount()+"", Toast.LENGTH_LONG).show();
        	 while (m.find()) { 
        	     
        		
        			if(j<20)
        			
        				mContext.list[j]=m.group(1).toString().replaceAll("-?\\s?http:\\/\\/t\\.co[^\\s]*", " ");
        	    // Toast.makeText(mContext, m.group(i).toString(), Toast.LENGTH_LONG).show();
        		
        		 j++;
        	 }
        	
        	
    	
        
           // Toast.makeText(mContext, j+"", Toast.LENGTH_LONG).show();
            mContext.requested=true;
        }
    }

    
public static class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
    
    
    
}
