package eu.bidtrans.popularmovies;

import android.app.Application;
import android.content.Context;

public class PopularMoviesApp extends Application {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }
}
