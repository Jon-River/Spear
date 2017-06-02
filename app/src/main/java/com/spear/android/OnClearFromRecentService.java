package com.spear.android;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.spear.android.managers.SQLliteManager;

/**
 * Created by Pablo on 3/6/17.
 */

public class OnClearFromRecentService extends Service {

    SQLliteManager dataManager;
    SQLiteDatabase db;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        dataManager = new SQLliteManager(this, "spear", null, 1);
        db = dataManager.getWritableDatabase();
        dataManager.onUpgrade(db, 1, 1);
        Log.e("ClearFromRecentService", "END");
        //Code here
        stopSelf();
    }

}
