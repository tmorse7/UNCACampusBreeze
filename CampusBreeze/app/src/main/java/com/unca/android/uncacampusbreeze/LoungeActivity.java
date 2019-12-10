package com.unca.android.uncacampusbreeze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

public class LoungeActivity extends AppCompatActivity {

    private static String TAG = "LoungeActivity";
    private boolean mLocationGranted = false;
    private Intent mGatekeepingStatusIntent;
    private boolean mLoggedIntoServer = false;
    private boolean mDeviceInValidLocation = false;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge);
        mContext = getApplicationContext();

        registerReceiver(onLoggedInBroadcast, new IntentFilter("logged_in_status"));
        registerReceiver(onCampusBroadcast, new IntentFilter("on_campus_status"));

        LoginService.startActionLogin(getApplicationContext());

        // ask for location permission if not granted
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) { //Check for ACCESS FINE LOCATION permission
            mLocationGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    9002);
            mLocationGranted = false;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onRestart() {
        super.onRestart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onLoggedInBroadcast);
        unregisterReceiver(onCampusBroadcast);
    }

    private BroadcastReceiver onLoggedInBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            boolean isLoggedIn = i.getBooleanExtra("Status", false);
            if (isLoggedIn) {
                mLoggedIntoServer = true;
                LocationService.startActionStartLocationService(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Logged into server.", Toast.LENGTH_SHORT).show();
            } else {
                mLoggedIntoServer = false;
                Toast.makeText(getApplicationContext(), "Could not log into server.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private BroadcastReceiver onCampusBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            boolean isOnCampus = i.getBooleanExtra("Status", false);
            if (isOnCampus) {
                Toast.makeText(getApplicationContext(), "You are on campus.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Not on campus.", Toast.LENGTH_SHORT).show();
            }
        }
    };

}