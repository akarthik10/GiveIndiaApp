package com.giveindia.donate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	double lat=0,lon=0;
	double[] priority=new double[22];
	int[] priorityList = new int[22];
	double[][] lowestSix = new double[6][2];
	final double[] latitude = {

	19.095021, 18.975668, 19.063327, 19.052312, 19.010781, 19.058711,
			19.060749, 19.076053, 18.939585, 18.969111, 19.007918, 19.008466,
			19.22299, 19.120824, 18.971926, 18.960824, 18.986777, 19.006518,
			18.948569, 19.036516, 18.951346, 18.9235

	};
	final double[] longitude = { 72.921828, 72.812611, 72.844285, 72.824035,
			72.816315, 72.893638, 72.911944, 72.877936, 72.828487, 72.811933,
			72.826996, 72.822922, 72.840042, 72.910482, 72.822951, 72.817909,
			72.836609, 72.823842, 72.794487, 72.846721, 72.821352, 72.831888

	};
	
	String [] ngos = {
			"Teach For India",
			"ISKCON Food Relief Foundation",
			"Kherwadi Social Welfare Association",
			"Light of Life Trust",
			"National Association for the Blind, India",
			"National Society for Equal Opportunities for the Handicapped",
			"Sahaara, Mumbai",
			"Salaam Baalak Trust- Mumbai",
			"Salaam Bombay Foundation",
			"Apnalaya",
			"Ummeed Child Development Centre",
			"Life Trust",
			"Umang Charitable Trust",
			"UDAAN India Foundation",
			"Community Outreach Programme (CORP)",
			"Dignity Foundation",
			"The Akanksha Foundation",
			"Muktangan",
			"St Jude India Childcare Centres",
			"MESCO?Modern Educational Social & Cultural Organization",
			"Sankalp Rehabilitation Trust",
			"Mumbai Mobile Creches"

		};
	
	String[] states={	
			"Maharashtra",
		    "Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
				"Maharashtra",
		};
	
	String[] locations={
			"Godrej Industries Complex, Gate No. 2., Pirojshanagar, Eastern Express Highway, Vikhroli (E)",
			"19, Jaywant Industrial Estate, 63 Tardeo Road, Tardeo",
			"Parishramalaya Teen Bangla Rd, Bandra (E)",
			"181, Digital Planet, Hill Road, Bandra (West)",
			"11-12, Khan Abdul Gaffar Khan Road, Worli Seaface Worli",
			"Postal Colony Road, Chembur",
			"4/2, Assisi Nagar, PL Lokhande Marg, Govandi",
			"C/o P T Welfare Centre, Asha Sadan Marg, Umerkhadi",
			"5/6, Rewa Chambers, 31, New Marine Lines",
			"7-8A, New Jaiphalwadi SRA Co-op Hsg Society, Behind Police Quarters, Tardeo",
			"Ground Floor, Mantri Pride 1-B, 1/62, N M Joshi Marg (Delisle Road) Subhash Nagar, Lower Parel",
			"BB-1, Neelam Centre SK Ahire Marg, Worli",
			"93/924 Siddhivinayak Society, Mahavir Nagar, Kandivali West",
			"2202, Odyssey 2, Hiranandani Gardens, Powai",
			"Methodist Centre,1ST Floor, 21, YMCA Road, Mumbai Central",
			"BMC School Building, Topiwala Lane, Mumbai - 400007",
			"Voltas House ?C?, T B kadam Marg, Chinchpokli",
			"I-11/12 Paragon Condominium Pandurang Budkar Marg, Worli",
			"7 B Landsend, 29 Dongersey Road, Malabar Hill",
			"Udyog Mandir 2, Gr floor Gala # 2 Mogul Lane off Tulsi Pipe Road, Behind Johnson & Johnson, Mahim",
			"1st Floor, S.S. Bengali Municipal School Thakurdwar Road, Charni Road (E)",
			"1st floor, Abbas Building, Mereweather Road, Colaba."

	};
	
	
	String [] primarycause={
			"Education",
			"Education",
			"Livelihoods",
			"Education",
			"Disabled",
			"Disabled",
			"Children",
			"Children",
			"Education (Advocacy)",
			"Children",
			"Disabled",
			"Education",
			"Disabled",
			"Education",
			"Children",
			"Elderly",
			"Education",
			"Education",
			"Health",
			"Education",
			"Health",
			"Education"

	};
	
	String [] colors = {"gray", "blue","purple", "green", "yellow", "orange"};

	
	
	String[] coord = new String[2]; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		   GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();

		   String networkOperator = telephonyManager.getNetworkOperator();
		  // String mcc = networkOperator.substring(0, 3);
		  // String mnc = networkOperator.substring(3);
		  

		  // int cid = cellLocation.getCid();
		  //int lac = cellLocation.getLac();
		   
		   String mcc="404", mnc = "92", lac="1202", cid="31693";
		
		new RequestTask(this).execute("http://akarthik10.heliohost.org/scripts/cfg/loc.php?mcc="+mcc+"&mnc="+mnc+"&lac="+lac+"&cid="+cid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}
	
	
	public String prepareQuery(){
		String query="http://maps.googleapis.com/maps/api/staticmap?center="+(lat-0.02)+","+(lon-0.001)+"&zoom=12&size=700x800&maptype=roadmap&sensor=false";
		query+="&markers=color:red%7Clabel:Y%7C"+lat+","+lon;
		for(int i=0;i<6;i++){
			query+="&markers=color:"+colors[i]+"%7Clabel:"+ ((char)(65+i)) +"%7C"+latitude[priorityList[i]]+","+longitude[priorityList[i]];
		}
		Log.d("",query);
		//Toast.makeText(SearchActivity.this, latitude[priorityList[0]]+"", Toast.LENGTH_LONG).show();
		
		return query;
	}
	
	
	public void calcPriority(){
		
		for(int i=0;i<22;i++){
			priority[i]=calcDistance(lat, lon, latitude[i], longitude[i]);
		}
		
		double[] lowestSix = new double[6];
	    Arrays.fill(lowestSix, Double.MAX_VALUE);

	    for(double n : priority) {
	        if(n < lowestSix[5]) {
	            lowestSix[5] = n;
	            Arrays.sort(lowestSix);
	        }
	    }
	    
	    int j=0;
	    for(int i=0;i<22;i++){
	    	for(int k=0;k<6;k++){
	    	if(lowestSix[k]==priority[i]){
	    		priorityList[k]=i;
	    		if(j>6)
	    			break;
	    	}
	    }
	    }
	    
		    
		   // for(int i=0;i<6;i++)
		   // Toast.makeText(this, "Distance:"+lowestSix[i]+" i:"+priorityList[i] , Toast.LENGTH_LONG).show();
	    ImageView img = (ImageView) findViewById(R.id.dimageview);
	     new DownloadImageTask(img).execute(prepareQuery());
	    
	}
	
	public int[] getIndices(double[] originalArray)
	{
	    int len = originalArray.length;

	    double[] sortedCopy = originalArray.clone();
	    int[] indices = new int[len];

	    // Sort the copy
	    Arrays.sort(sortedCopy);

	    // Go through the original array: for the same index, fill the position where the
	    // corresponding number is in the sorted array in the indices array
	    for (int index = 0; index < len; index++)
	        indices[index] = Arrays.binarySearch(sortedCopy, originalArray[index]);

	    return indices;
	}
	
	private double getRedian(double angleInDegree) {
		return Math.PI * angleInDegree / 180;
	}
	
	private double calcDistance(double src_lat, double src_lon, double dest_lat, double dest_lon) {
		
			double x = src_lat, y = src_lon;
			double dist_lon, a, c, d;
			dist_lon = y - dest_lon;
			double dist_lat = x - dest_lat;
			double k = getRedian(dist_lat / 2);

			a = ((Math.sin(k)) * (Math.sin(k)))
					+ Math.cos(getRedian(dest_lat))
					* Math.cos(getRedian(x))
					* ((Math.sin(getRedian(dist_lon / 2))) * (Math
							.sin(getRedian(dist_lon / 2))));
			c = 2 * Math.atan2(getRedian(Math.sqrt(a)),
					getRedian(Math.sqrt(1 - a)));
			d = 6373 * c;
			return d;
		
	}
	
	
public class RequestTask extends AsyncTask<String, String, String>{

    	
    	SearchActivity mContext;
    	
    	public RequestTask(Context context) {
    		// TODO Auto-generated constructor stub
    		this.mContext=(SearchActivity) context;
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
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!result.equalsIgnoreCase("0")){
            	coord = result.split("\\|");
            }
            lat = Double.parseDouble(coord[0]);
            lon = Double.parseDouble(coord[1]);
            Toast.makeText(mContext,"Your location:"+ lat+"  "+lon, Toast.LENGTH_LONG).show();
            calcPriority();
        	 }
        	
       
    	
        
           
        }


private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    
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
        
    	Toast.makeText(SearchActivity.this, "You're at Y (red)!", Toast.LENGTH_LONG).show();
    	bmImage.setImageBitmap(result);
    	
    	
    	final Bitmap bitmap = ((BitmapDrawable)bmImage.getDrawable()).getBitmap();
    	bmImage.setOnTouchListener(new OnTouchListener(){

    	        @Override
    	        public boolean onTouch(View v, MotionEvent event){
    	        	
    	        int x = (int)event.getX()-240;
    	        int y = (int)event.getY()-300;
    	        int[][]  clickData = new int[][]{{16,-164},{169,71},{97,173},{24,203},{195,133},{-19,225}};
    	        int lIndex=-1;
    	        for(int i=0;i<6;i++){
    	        	if(Math.abs(x-clickData[i][0])<10 &&  Math.abs(y-clickData[i][1])<10){
    	        		lIndex=priorityList[i];
    	        	}
    	        }
    	       
    	      if(lIndex!=-1){
    	    	  //Toast.makeText(SearchActivity.this,"YES"+lIndex , Toast.LENGTH_LONG).show();
    	    	  Intent myIntent = new Intent(SearchActivity.this, NGOActivity.class);
    	    	  myIntent.putExtra("intVariableName", lIndex);
    	    	  startActivity(myIntent);
    	      }
    	        
    	        return false;

    	        }

				public boolean onTouch1(View arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					return false;
				}
    	   });
        
    }
}

    }



