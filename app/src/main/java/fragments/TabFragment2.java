package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.spear.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    View mapView;



    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mapView != null) {
            ViewGroup parent = (ViewGroup) mapView.getParent();
            if (parent != null)
                parent.removeView(mapView);
        }
        try {
            mapView = inflater.inflate(R.layout.fragment_tab2, container, false);
        } catch (InflateException e) {
    /* map is already there, just return view as it is */
        }
        return mapView;

    }



}
