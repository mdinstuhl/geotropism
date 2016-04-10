package org.dinstuhl.geotropism.ui;

import org.dinstuhl.geotropism.R;
import org.dinstuhl.geotropism.domain.*;
import org.dinstuhl.geotropism.dao.*;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Criteria;

import java.util.ArrayList;

public class Main extends Activity {
	
	private LocationManager locManager;
	private Criteria locCriteria;
	private String provider;
	private EditText txtBox;
	private GeoLocationListener listener;
	

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		//LinearLayout view = new LinearLayout(this);
		
		setContentView(R.layout.main);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locCriteria = new Criteria();
		locCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		locCriteria.setCostAllowed(false);
		provider = locManager.getBestProvider(locCriteria, false);
		provider = LocationManager.NETWORK_PROVIDER;
		System.out.println("Provider is " + provider);
		listener = new GeoLocationListener();
		locManager.requestLocationUpdates(provider,  200,  1,  listener);
		Location location = locManager.getLastKnownLocation(provider);
		
		txtBox = (EditText) findViewById(R.id.edit_message);
		//txtBox.setText(String.valueOf(location.getLatitude() + ", " + String.valueOf(location.getLongitude())));
		
		
		/*
		while(location == null){
			System.out.println("Location is still null");
			if (locManager.isProviderEnabled(provider) == false){
				System.out.println("Provider is not enabled - sending user to config screen.");
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
				
			} 
			
			location = locManager.getLastKnownLocation(provider);
		}
		*/
		ArrayList<GeoMessage> messages = new ArrayList<GeoMessage>();
		listener.onLocationChanged(location);
		String lat = String.valueOf(location.getLatitude());
		
		new GetMessagesTask().execute(lat);
		
		
		
	
		
		
	}
	
	private class GeoLocationListener implements LocationListener {
		
		@Override
		public void onLocationChanged(Location location){

			//txtBox.setText(String.valueOf(location.getLatitude() + ", " + String.valueOf(location.getLongitude())));

			
		}
		
		@Override
		public void onProviderEnabled(String provider){
			
			
			
		}
		
		@Override
		public void onProviderDisabled(String provider){
			
			
			
		}		
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
			
		}
		
	}
	
	
	private class GetMessagesTask extends AsyncTask<String, Void , ArrayList<GeoMessage>>{
		
		
		protected ArrayList<GeoMessage> doInBackground(String... ids){
			ArrayList<GeoMessage> messages = new ArrayList<GeoMessage>();
			String id = ids[0];
			messages = MessageDAO.getMessages(id);
			System.out.println("number of messages returned = " + messages.size());
			txtBox.setText(String.valueOf(messages.size()));
			return messages;
		}
		
		
		
	}

}
