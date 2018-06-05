package com.example.korz.cookbook.fragmentLifecycle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.example.korz.cookbook.Fragments.Fragment2;

import java.lang.reflect.Field;
import java.util.List;

public class AdapterState extends FragmentStatePagerAdapter {

    List<String> data;
    int prevSelection = -1;

    public AdapterState(FragmentManager fm, ViewPager viewPager) {
        super(fm);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevSelection != -1)
                    onUnSelectFragment(getFragment(prevSelection));
                onSelectFragment(getFragment(position));
                prevSelection = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void onSelectFragment(Fragment fragment) {
        Log.d("LOG", "SELECTED:" + ((Fragment2)fragment).text);
    }

    void onUnSelectFragment(Fragment fragment) {
        Log.d("LOG", "UNSELECTED:" + ((Fragment2)fragment).text);
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment2.getInstance(data.get(position));
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    private Fragment getFragment(int position) {
        try {
            Field f = getClass().getSuperclass().getDeclaredField("mFragments");
            f.setAccessible(true);
            List<Fragment> fragments = (List<Fragment>) f.get(this);
            return fragments.get(position);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;

    }
}
