package android.thaihn.viewpagerstate.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.thaihn.viewpagerstate.DepthPageTransformer;
import android.thaihn.viewpagerstate.MyViewPagerAdapter;
import android.thaihn.viewpagerstate.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.viewpager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("TabLayout ViewPager");

        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Setup ViewPager
     */
    private void setupViewPager() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
