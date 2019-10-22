package com.example.bus;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.google.android.gms.location.LocationServices;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import broadcast_receiver.Main_Broadcast;
import services.MyGcmListenerService;
import services.Server_calss;
@SuppressLint("NewApi")
public class Bank_Profile extends ActionBarActivity implements LocationListener 
{
	private static final String[] INITIAL_PERMS={
			android.Manifest.permission.ACCESS_FINE_LOCATION,
			android.Manifest.permission.READ_CONTACTS
		  };
	private LocationManager location_manager;
	private Button bus_no;
	private Button source;
	private Button destination;
	private String String_bus_no;
	private EditText text_message;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company__list);
		bus_no=(Button)findViewById(R.id.bharate_gas);
		source=(Button)findViewById(R.id.hp_gas);
		destination=(Button)findViewById(R.id.indan_gas);	
		 String_bus_no=MainActivity.shared_preference.getString(Server_calss.busno, "");
		bus_no.setText(String_bus_no);
		source.setText(MainActivity.shared_preference.getString(Server_calss.source_route, ""));
	destination.setText(MainActivity.shared_preference.getString(Server_calss.destination_route, ""));
			text_message=(EditText)findViewById(R.id.message);
		if(Build.VERSION.SDK_INT>22)
		{
		requestPermissions(INITIAL_PERMS, 12);
		}
		else
	location_manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0,this);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company__list, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);		
	}
	
	public void button(View v)
	{
		int id=v.getId();
		switch (id) {
		case R.id.bharate_gas:
		{
		
			break;
		}
		case R.id.hp_gas:
		{
			
			break;
		}
		case R.id.indan_gas:
		{
			
			break;
		}
		case R.id.message_done:
		{
			Log.e("Bank_Profile", "message ::");
			String message=text_message.getText().toString();
			Intent intent=new Intent("com.example.gasbooking.BraadCast");
			intent.putExtra("action",Server_calss.action_driver_message);
			String busno=MainActivity.shared_preference.getString(Server_calss.busno,"111");
			String driver_user=MainActivity.shared_preference.getString(Server_calss.username,"kumar");
			intent.putExtra(Server_calss.busno,busno);
			intent.putExtra(Server_calss.username,driver_user);
			intent.putExtra(MyGcmListenerService.student_msg,message);
	
			sendBroadcast(intent);
			Log.e("Bank_Profile", "Driver message Sent");
			break;
		}

		default:
			break;
		}
		
	}
	
	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop();
		location_manager.removeUpdates(this);
		Log.e("bank_profile", "location updated Removed Successfully");
}

	@Override
	public void onLocationChanged(Location location) 
	{
		Log.e("Bank_Profile", "Latitude,Longitude::"+location.getLatitude()+"  :"+location.getLongitude());
		Intent intent=new Intent("com.example.gasbooking.BraadCast");
		intent.putExtra("action",Server_calss.action_location_update);
		intent.putExtra(Server_calss.latitude,""+location.getLatitude());
		intent.putExtra(Server_calss.longitude,""+location.getLongitude());
		intent.putExtra(Server_calss.busno,""+String_bus_no);
		sendBroadcast(intent);
		Log.e("Bank_Profile", "LOcation updated Broadcast Sent");
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Toast.makeText(getApplicationContext(), "provider_enabled::"+provider, Toast.LENGTH_LONG).show();	
	}
	@Override
	public void onProviderDisabled(String provider) 
	{
		Toast.makeText(getApplicationContext(), "provider_disabled::"+provider, Toast.LENGTH_LONG).show();	}	
	@Override
	public void onRequestPermissionsResult(int arg0, String[] arg1, int[] arg2) 
	{
		Log.e("bank_Profile","resullt::"+arg0);
		if(arg0==12)
		{
			Log.e("bank_profile", "Permission grated");
			location_manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

			location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0,this);		
		}
}
