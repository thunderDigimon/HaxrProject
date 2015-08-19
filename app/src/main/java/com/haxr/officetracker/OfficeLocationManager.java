package com.haxr.officetracker;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yathish on 18/08/15.
 * Its the main Data handler class holding all the location related data of the user
 */

public class OfficeLocationManager
{
    public static OfficeLocationManager s_globalRef=null;
    private List<Location> mSavedLocations=null;
    private Location mCurrentLocation=null;
    private Handler mHandler =null;
    private boolean mIsPaused=false;
    private TimerClass mTimer=null;
    private boolean mShouldStartTrackingTime=false;//could be used to start the timer when a button start timer is clicked

    //Limits
    private static  final long UPDATE_INTERVAL=1000;//millisecond

    private static final long UPDATE_START_DELAY=1000;//

    private static final long AREA_RADIUS=50;//in meters

//Singleton object created for each user
    public static OfficeLocationManager SharedInstance()
    {
        if(s_globalRef==null)
        {
            s_globalRef=new OfficeLocationManager();
        }
        return s_globalRef;
    }

    OfficeLocationManager()
    {
        mSavedLocations=new ArrayList<Location>();
        mHandler = new Handler();
        mTimer=new TimerClass();
    }

//function to add the user location
    //TODO its a list now would require a UI to display these location
    public boolean setLocation(Context content)
    {
        boolean retVal=false;
        printTheLogOnScreen("Please wait while setting the Location", content);

        GPSUpdater gpsInfo=new GPSUpdater(content);
        if(gpsInfo.canGetLocation())
        {
            Location currentLocation=gpsInfo.getLocation();
            if(currentLocation!=null)
            {
                retVal = true;
                mSavedLocations.add(currentLocation);
                printTheLogOnScreen(String.valueOf(currentLocation), content);
            }
            else
            {
                retVal=false;
            }

        }
        else
        {
            gpsInfo.showSettingsAlert();
        }

        return retVal;
    }

    public boolean isUserWithinTheArea()
    {
        boolean retVal=false;
        float[] results=new float[1];
        float distance=0;

        //TODO we should be taking the user specified location from a list show to user in UI now takes the forst elemet of the list


        if(mCurrentLocation!=null && mSavedLocations.size()!=0 && mSavedLocations!=null)
            Location.distanceBetween(mSavedLocations.get(0).getLatitude(), mSavedLocations.get(0).getLongitude(), mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), results);

        distance=results[0];

        if(distance>AREA_RADIUS)
        {
            retVal=false;
            mIsPaused=true;
            //TODO here the timer should be paused
            if(mShouldStartTrackingTime)
                mTimer.incrementPausedTime();
        }
        else
        {
            retVal=true;
            mIsPaused=false;
            //TODO here the timer should be unpaused
        }

        return retVal;
    }

    //update function that call on defined interval for checking the user location and finding the distance from the set location
    public void checkCurrentLocation(final Context context)
    {
        final GPSUpdater gpsInfo=new GPSUpdater(context);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mCurrentLocation = gpsInfo.getLocation();
                if (mSavedLocations.size() != 0 || mSavedLocations != null) {
                    boolean isWithinArea = isUserWithinTheArea();
                    if (isWithinArea)
                    {
                        Log.i("a", "user is inside area specified");
                    }
                    else
                    {
                        Log.i("a", "user is outside area specified");
                    }
                    if(mShouldStartTrackingTime)
                        updateTimer();

                }
                mHandler.postDelayed(this, UPDATE_INTERVAL);
            }
        }, UPDATE_START_DELAY);
    }

    public void startTimer()
    {
        mTimer.setStartTime();
        mShouldStartTrackingTime=true;
    }

    void updateTimer()
    {
        long timeElapsed=mTimer.getElapsedTimeSecs();
        MainActivity.sharedInstance().updateTimer(timeElapsed);
        Log.i("timer","time elapsed is: "+timeElapsed);
    }

    //Function for checking implementation
    void printTheLogOnScreen(String message,Context context)
    {
        MainActivity.sharedInstance().displayInToast(message, context);

    }

    //debug function
    public float checkDistance()
    {
        float[] results=new float[1];
        float distance=0;

        if(mCurrentLocation!=null && mSavedLocations.size()!=0 && mSavedLocations!=null)
            Location.distanceBetween(mSavedLocations.get(0).getLatitude(), mSavedLocations.get(0).getLongitude(), mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), results);

       return results[0];
    }


}



