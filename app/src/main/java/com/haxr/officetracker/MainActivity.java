package com.haxr.officetracker;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//TODO should contain only UI related functions ,Have not  implemeted properly now could be used for testing the development
public class MainActivity extends ActionBarActivity
{
    static  MainActivity uiReference=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        OfficeLocationManager.SharedInstance().checkCurrentLocation(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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


    void displayInToast(String message,Context context)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();

    }

    public static MainActivity sharedInstance()
    {
        if(uiReference==null)
            uiReference=new MainActivity();
        return uiReference;
    }

//function assigned to "Set Location" button
    public void setLocation(View view)
    {
        boolean isLocationSet=OfficeLocationManager.SharedInstance().setLocation(this);
        if(isLocationSet)
        {
            displayInToast("Location set Successfully",getApplicationContext());
        } else
        {
            displayInToast("Failed to set the Location",getApplicationContext());
        }
        Toast.makeText(getApplicationContext(), "on click called", Toast.LENGTH_LONG).show();
    }

    //function assigned to "Get Distance" button

    public void getLocationDistance(View view)
    {
        float distance=OfficeLocationManager.SharedInstance().checkDistance();
        displayInToast("Distance is " + distance, getApplicationContext());

    }

    public void startTimer(View view)
    {
        OfficeLocationManager.SharedInstance().startTimer();
    }

    public void updateTimer(long value)
    {
        //TODO logic for timer display
    }


}
