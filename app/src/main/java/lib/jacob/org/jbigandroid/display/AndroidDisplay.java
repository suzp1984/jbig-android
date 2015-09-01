package lib.jacob.org.jbigandroid.display;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import lib.jacob.org.jbigandroid.MainActivity;

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
        MainActivity activity = (MainActivity) mCompatActivity;
        activity.selectPaintTab();
    }

    @Override
    public void showDecoderUi() {
        MainActivity activity = (MainActivity) mCompatActivity;
        activity.selectDecodeTab();
    }

    @Override
    public void showDrawerLayout() {
        if (mDrawerLayout != null &&
                !mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void closeDrawerLayout() {
        if (mDrawerLayout != null &&
                mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        }
    }
}
