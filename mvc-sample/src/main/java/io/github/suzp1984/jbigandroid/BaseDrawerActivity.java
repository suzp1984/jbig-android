package io.github.suzp1984.jbigandroid;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.display.AndroidDisplay;
import io.github.suzp1984.jbigandroid.display.IDisplay;

public abstract class BaseDrawerActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private Unbinder mButterUnbinder;

    @Inject
    MainController mMainController;
    private IDisplay mAndroidDisplay;

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(R.layout.activity_base_drawer);
        FrameLayout parent = (FrameLayout) findViewById(R.id.content);
        LayoutInflater.from(this).inflate(layoutId, parent, true);

        injectView();

        mAndroidDisplay = new AndroidDisplay(this, mDrawerLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((JbigApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        mMainController.attachDisplay(mAndroidDisplay);
        mMainController.init();
    }

    @Override
    public void onPause() {
        mMainController.suspend();
        mMainController.detachDisplay(mAndroidDisplay);

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mButterUnbinder.unbind();

        super.onDestroy();

        mAndroidDisplay = null;
    }

    public MainController getMainController() {
        return mMainController;
    }

    private void injectView() {
        mButterUnbinder = ButterKnife.bind(this);
    }
}
