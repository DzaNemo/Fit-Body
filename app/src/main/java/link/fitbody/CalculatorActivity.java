package link.fitbody;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class CalculatorActivity extends MotherActivity {

    CalculatorPagerAdapter adapter;
    ViewPager vPager;
    TabLayout tabLayout;

    private int[] tabIcons = { R.drawable.weight, R.drawable.calorie, R.drawable.body };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        vPager = (ViewPager) findViewById(R.id.vpPager);
        adapter = new CalculatorPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);

        int calculator_num = this.getIntent().getIntExtra("CALCULATOR_NUM", 0);
        vPager.setCurrentItem(calculator_num);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vPager);

        getSupportActionBar().setElevation(0);
        /*setTabIcons();*/
    }

    /*private void setTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }*/

    public static class CalculatorPagerAdapter extends FragmentPagerAdapter {

        static final int NUM_ITEMS = 3;
        static final String TAB_TITLES[] = new String[]{"Ideal Weight", "Calorie", "Body Type"};

        public CalculatorPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IdealWeightFragment();
                case 1:
                    return new CalorieFragment();
                case 2:
                    return new BodyTypeFragment();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }

    }
}
