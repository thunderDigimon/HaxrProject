package com.haxr.officetracker;

import android.util.Log;

import java.util.Timer;

/**
 * Created by yathish on 19/08/15.
 */
public class TimerClass
{
    private long mStartTime=0;
    private long mStopTime=0;
    private  long mTimeElapsed=0;
    private long mPausedTime=0;

    public void setStartTime()
    {
        mStartTime=System.currentTimeMillis();
    }

    public long getStartTime()
    {
        return mStartTime;
    }

    public void setStopTime()
    {
        mStopTime=System.currentTimeMillis();
    }


    public long getElapsedTimeSecs()
    {
        mTimeElapsed = ((System.currentTimeMillis() - mStartTime-mPausedTime) / 1000);//seconds
        Log.i("Timer", "Time elspased: " + String.valueOf(mTimeElapsed));
        return mTimeElapsed;
    }

    public void incrementPausedTime()
    {
        mPausedTime++;
    }


}
