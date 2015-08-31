package lib.jacob.org.jbigandroid.display;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by moses on 8/31/15.
 */
public class AndroidDisplay implements IDisplay {

    private final AppCompatActivity mCompatActivity;
    private final DrawerLayout mDrawerLayout;

    public AndroidDisplay(AppCompatActivity activity,
            DrawerLayout drawerLayout) {
        mCompatActivity = activity;
        mDrawerLayout = drawerLayout;
    }

    @Override
    public void showPaintUi() {

    }

    @Override
    public void showDecoderUi() {

    }
}
