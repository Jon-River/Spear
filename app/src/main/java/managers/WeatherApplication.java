package managers;

import com.activeandroid.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class WeatherApplication extends Application {

    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue= Volley.newRequestQueue(this);
    }
}
