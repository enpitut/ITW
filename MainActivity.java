package com.example.aa.locationsample;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements LocationListener, View.OnClickListener{

    private LocationManager mLocationManager;
    private TextView mWifiLatitudeTextView;
    private TextView mWifiLongitudeTextView;
    private TextView mWifiAccuracyTextView;
    private TextView mWifiAltitudeTextView;
    private TextView mGpsLatitudeTextView;
    private TextView mGpsLongitudeTextView;
    private TextView mGpsAccuracyTextView;
    private TextView mGpsAltitudeTextView;
    private static int WIFI = 0;
    private static int GPS = 1;
    private int mLocationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("PlaceSample", "plog onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiLatitudeTextView = (TextView) findViewById(R.id.text_view_wifi_latitude_value);
        mWifiLongitudeTextView = (TextView) findViewById(R.id.text_view_wifi_longitude_value);
        mWifiAccuracyTextView = (TextView) findViewById(R.id.text_view_wifi_accuracy_value);
        mWifiAltitudeTextView = (TextView) findViewById(R.id.text_view_wifi_altitude_value);
        mGpsLatitudeTextView = (TextView) findViewById(R.id.text_view_gps_latitude_value);
        mGpsLongitudeTextView = (TextView) findViewById(R.id.text_view_gps_longitude_value);
        mGpsAccuracyTextView = (TextView) findViewById(R.id.text_view_gps_accuracy_value);
        mGpsAltitudeTextView = (TextView) findViewById(R.id.text_view_gps_altitude_value);

        Button gpsButton = (Button)findViewById(R.id.button_gps);
        gpsButton.setOnClickListener(this);

        Button wifiButton = (Button) findViewById(R.id.button_wifi);
        wifiButton.setOnClickListener(this);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    @Override
    protected void onResume() {
        Log.d("PlaceSample", "plog onResume()");
        super.onResume();
    }

    protected void onPause() {
        Log.d("PlaceSample", "plog onPause");
        if(mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("PlaceSample", "plog onLocationChanged");
        if(mLocationType == GPS) {
            mGpsLatitudeTextView.setText(String.valueOf(location.getLatitude()));
            mGpsLongitudeTextView.setText(String.valueOf(location.getLongitude()));
            mGpsAccuracyTextView.setText(String.valueOf(location.getAccuracy()));
            mGpsAltitudeTextView.setText(String.valueOf(location.getAltitude()));
        } else if(mLocationType == WIFI) {
            mWifiLatitudeTextView.setText(String.valueOf(location.getLatitude()));
            mWifiLongitudeTextView.setText(String.valueOf(location.getLongitude()));
            mWifiAccuracyTextView.setText(String.valueOf(location.getAccuracy()));
            mWifiAltitudeTextView.setText(String.valueOf(location.getAltitude()));
        }
        Log.v("PlaceSample", String.valueOf(location.getLatitude()));
        Log.v("PlaceSample", String.valueOf(location.getLongitude()));
        Log.v("PlaceSample", String.valueOf(location.getAccuracy()));
        Log.v("PlaceSample", String.valueOf(location.getAltitude()));

        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch(status) {
            case LocationProvider.AVAILABLE:
                Log.v("PlaceSample", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("PlaceSample", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("PlaceSample", "TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_gps) {
            Log.d("PlaceSample", "GPS selected");
            mLocationType = GPS;
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, this);

        } else if(v.getId() == R.id.button_wifi) {
            Log.d("PlaceSample", "WiFi selected");
            mLocationType = WIFI;
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }
}
