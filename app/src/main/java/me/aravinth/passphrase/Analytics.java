package me.aravinth.passphrase;

import android.app.Application;
import android.os.Trace;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by aravinth on 24-May-15.
 */
public class Analytics extends Application {
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        analytics=GoogleAnalytics.getInstance(getApplicationContext());
        tracker = analytics.newTracker("UA-54066327-4");
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
    }
}
