package io.github.suzp1984.jbigandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.util.Colors;

import io.github.suzp1984.jbigandroid.adapter.FragmentViewAdapter;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.widget.SwipeControlViewPager;

public class MainActivity extends BaseDrawerActivity
        implements MainController.MainControllerUi {

    private final String SELECTED_TAB_INT = "SELECTED_TAB";

    SwipeControlViewPager mViewPager;

    TabLayout mTabLayout;

    Toolbar mToolbar;

    private MainController.MainControllerUiCallback mUiCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage_sample);

        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mToolbar = findViewById(R.id.toolbar);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.paint_item:
                                Log.d("TAG", "menu item paint item");
                                mUiCallback.onTabItemSelected(MainController.TabItem.PAINT_TAB);
                                break;
                            case R.id.encoder:
                                Log.d("TAG", "menu item encoder");
                                mUiCallback.onTabItemSelected(MainController.TabItem.DECODER_TAB);
                                break;
                            case R.id.about:
                                Log.d("TAG", "menu item about");
                                startAboutLibrary();
                                break;
                            default:
                                return false;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.ic_menu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }

        mViewPager.setAdapter(new FragmentViewAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "Page seleted = " + position);

                if (position == 0) {
                    mViewPager.setSwipeable(false);
                } else {
                    mViewPager.setSwipeable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState != null) {
            if (mTabLayout.getTabAt(savedInstanceState.getInt(SELECTED_TAB_INT, 0)) != null) {
                mTabLayout.getTabAt(savedInstanceState.getInt(SELECTED_TAB_INT, 0)).select();
            }
        }

        if (mTabLayout.getSelectedTabPosition() == 0) {
            mViewPager.setSwipeable(false);
        }
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mTabLayout.getTabAt(savedInstanceState.getInt(SELECTED_TAB_INT, 0)).select();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startAboutLibrary();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        getMainController().attachUi(this);
    }

    @Override
    public void onPause() {
        getMainController().detachUi(this);

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_TAB_INT, mTabLayout.getSelectedTabPosition());
    }

    public void selectPaintTab() {
        mTabLayout.getTabAt(0).select();
    }

    public void selectDecodeTab() {
        mTabLayout.getTabAt(1).select();
    }

    // implement MainControllerUi interface
    @Override
    public void setCallback(MainController.MainControllerUiCallback callback) {
        mUiCallback = callback;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    private void startAboutLibrary() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LibsBuilder().withActivityStyle(Libs.ActivityStyle.DARK)
                        .withActivityColor(new Colors(R.color.light_black, R.color.black))
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withAboutDescription("Good people does good things.")
                        .start(MainActivity.this);
            }
        });
    }
}
