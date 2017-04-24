package dallanosm.icopendataapp.roots;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import dallanosm.icopendataapp.R;


public abstract class ViewActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected TabLayout tabLayout;

    protected ViewPager viewPager;

    private CustomSwipeToRefresh swipeRefreshContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViews();
        setViews();
        setItems();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        swipeRefreshContainer = (CustomSwipeToRefresh) findViewById(R.id.swipe_container);
    }

    public void setupViews() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Query", getQueryFragment());
        viewPagerAdapter.addFragment("List", getListFragment());
        viewPagerAdapter.addFragment("Map", getMapFragment());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (swipeRefreshContainer != null) {
            swipeRefreshContainer.setEnabled(enable);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ViewActivity.this, "Testing", Toast.LENGTH_SHORT).show();
                    swipeRefreshContainer.setRefreshing(false);
                }
            }, 3000);
        }
    }

    public abstract void setViews();

    public abstract void setItems();

    public abstract QueryFragment getQueryFragment();

    public abstract MapFragment getMapFragment();

    public abstract ListFragment getListFragment();

}
