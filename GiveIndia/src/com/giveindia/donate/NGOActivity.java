package com.giveindia.donate;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NGOActivity extends Activity {
	int ngo;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ngo);
		Intent mIntent = getIntent();
		 ngo = mIntent.getIntExtra("intVariableName", 0);
		 
		 TextView ngoname = (TextView) findViewById(R.id.ngoname);
		 ngoname.setText("Name :"+ ngos[ngo]);
		 
		 TextView ngoaddr = (TextView) findViewById(R.id.ngoaddr);
		 ngoaddr.setText("Address :"+locations[ngo]);
		 
		 TextView ngocause = (TextView) findViewById(R.id.ngocause);
		 ngocause.setText("Primary Cause :"+primarycause[ngo]);
		 
		 Button btn = (Button) findViewById(R.id.btn);
		 btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);         
				sendIntent.setData(Uri.parse("sms:"));
				sendIntent.putExtra("sms_body", "Hi, I need you to have a look at this GiveIndia App! I just loved it!");
				startActivity(sendIntent);
				
			}
		});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ngo, menu);
		return true;
	}

}
