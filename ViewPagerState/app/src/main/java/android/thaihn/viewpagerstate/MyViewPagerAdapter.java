package android.thaihn.viewpagerstate;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int NUM_PAGER = 3;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(0, "HOME");

            case 1:
                return ContactFragment.newInstance(1, "CONTACT");

            case 2:
                return SettingFragment.newInstance(2, "SETTINGS");

            default:
                return HomeFragment.newInstance(0, "HOME");

        }
    }

    @Override
    public int getCount() {
        return NUM_PAGER;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "HOME";
            case 1:
                return "CONTACT";
            case 2:
                return "SETTING";
            default:
                return "HOME";
        }
    }
}
