package io.github.suzp1984.jbigandroid;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import io.github.suzp1984.jbigandroid.adapter.FragmentViewAdapter;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.widget.SwipeControlViewPager;

public class MainActivity extends BaseDrawerActivity
        implements MainController.MainControllerUi {

    @Bind(R.id.viewpager)
    SwipeControlViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private MainController.MainControllerUiCallback mUiCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage_sample);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.paint_item:
                                Log.e("TAG", "menu item paint item");
                                mUiCallback.onTabItemSelected(MainController.TabItem.PAINT_TAB);
                                break;
                            case R.id.encoder:
                                Log.e("TAG", "menu item encoder");
                                mUiCallback.onTabItemSelected(MainController.TabItem.DECODER_TAB);
                                break;
                            case R.id.about:
                                Log.e("TAG", "menu item about");
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
                Log.e("TAG", "Page seleted = " + position);

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

        if (mTabLayout.getSelectedTabPosition() == 0) {
            mViewPager.setSwipeable(false);
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
        if (id == R.id.action_settings) {
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
}
