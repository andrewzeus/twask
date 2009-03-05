package com.doublesunflower.core.ui.calendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CalendarActivity extends Activity {
	
	static final int DAY_VIEW_MODE = 0;
    static final int WEEK_VIEW_MODE = 1;
    
    private SharedPreferences mPrefs;
    private int mCurViewMode;

    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        /*SharedPreferences mPrefs = getSharedPreferences();
        mCurViewMode = mPrefs.getInt("view_mode" ,DAY_VIEW_MODE);*/
    }

    protected void onPause()
    {
        super.onPause();

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("view_mode", mCurViewMode);
        ed.commit();
    }

}
