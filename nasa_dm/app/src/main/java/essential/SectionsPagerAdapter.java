package essential;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astudios.disastermanagement.MeetUpActivity;

import fragments.fragment1;
import fragments.fragment2;
import fragments.fragment3;
import fragments.fragment4;
import fragments.fragment5;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                fragment1 fragment1 = new fragment1();
                return fragment1;
            case 1:
                fragment2 fragment2 = new fragment2();
                return fragment2;
            case 2:
                fragment3 fragment3 = new fragment3();
                return fragment3;
            case 3:
                fragment4 fragment4 = new fragment4();
                return fragment4;
            case 4:
                fragment5 fragment5 = new fragment5();
                return fragment5;


        }
        return null;
    }


    @Override
    public int getCount() {
        return MeetUpActivity.MAX_FRAGS+1;
    }
}
