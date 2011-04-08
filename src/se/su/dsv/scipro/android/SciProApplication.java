package se.su.dsv.scipro.android;

import se.su.dsv.scipro.android.dummydata.DummyData;
import android.app.Application;

public class SciProApplication extends Application {

    private static SciProApplication instance;
    
    public static SciProApplication getInstance() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
