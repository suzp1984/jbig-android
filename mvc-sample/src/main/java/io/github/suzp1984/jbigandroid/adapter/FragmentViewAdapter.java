package io.github.suzp1984.jbigandroid.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import io.github.suzp1984.jbigandroid.fragment.DecoderFragment;
import io.github.suzp1984.jbigandroid.fragment.PaintViewFragment;

/**
 * Created by moses on 8/27/15.
 */
public class FragmentViewAdapter extends FragmentPagerAdapter {

    public FragmentViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new PaintViewFragment();
                break;
            case 1:
                fragment = new DecoderFragment();
                break;
            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position) {
            case 0:
                title = "Paint";
                break;
            case 1:
                title = "Decoder";
                break;
            default:
                title = null;
                break;
        }

        return title;
    }

}
