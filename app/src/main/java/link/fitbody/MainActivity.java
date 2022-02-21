package link.fitbody;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends MotherActivity {

    View fragmentContainer;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_container);

        if (fragmentContainer != null) {

            if (savedInstanceState == null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new IdealWeightFragment();
                ft.add(fragmentContainer.getId(), fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        } else {

            //handle click on Navigation Drawer items
            drawerList = (ListView) findViewById(R.id.left_drawer);
            drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);

                    switch (position) {
                        case 0:
                            intent.putExtra("CALCULATOR_NUM", 0);
                            break;
                        case 1:
                            intent.putExtra("CALCULATOR_NUM", 1);
                            break;
                        case 2:
                            intent.putExtra("CALCULATOR_NUM", 2);
                            break;
                    }

                    startActivity(intent);
                }
            });

            //add App icon for handling opening and showing of Navigation Drawer
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(
                    this,                  /* host Activity */
                    drawerLayout,         /* DrawerLayout object */
                    R.string.drawer_open,  /* "open drawer" description */
                    R.string.drawer_close  /* "close drawer" description */
            );

            // Set the drawer toggle as the DrawerListener
            drawerLayout.addDrawerListener(drawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           //getSupportActionBar().setHomeButtonEnabled(true);

            drawerToggle.syncState();

        }
    }


    public void onCalcButtonClick(View view) {

        fragmentContainer = findViewById(R.id.fragment_container);

        LinearLayout source = (LinearLayout) view;
        int sourceId = source.getId();

        if (fragmentContainer != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;

            String tag = "";

            if (sourceId == R.id.idealWeightButton) {
                fragment = new IdealWeightFragment();
                tag = "Ideal Weight";
            } else if (sourceId == R.id.calorieButton) {
                fragment = new CalorieFragment();
                tag = "Calorie";
            } else if (sourceId == R.id.bodyTypeButton) {
                fragment = new BodyTypeFragment();
                tag = "Body Type";
            }

            if (fragment == null)
                return;

            if (getLastFragmentTagFromBackStack().equals(tag))
                return;

            ft.replace(fragmentContainer.getId(), fragment);
            ft.addToBackStack(tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

            //changeActionBarText("Fit Body - " + tag);

        } else {
            Intent intent = new Intent(this, CalculatorActivity.class);

            if (sourceId == R.id.idealWeightButton) {
                intent.putExtra("CALCULATOR_NUM", 0);
            } else if (sourceId == R.id.calorieButton) {
                intent.putExtra("CALCULATOR_NUM", 1);
            } else if (sourceId == R.id.bodyTypeButton) {
                intent.putExtra("CALCULATOR_NUM", 2);
            }

            startActivity(intent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(drawerToggle != null){
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        switch (item.getItemId()) {
            case R.id.info_item:

                int spannableColor = ContextCompat.getColor(this, R.color.primary_text);
                String infoText = "FIT BODY INFO APP\nAuthor:\nFor:\nCompany:\n2020";

                Spannable spannable = new SpannableString(infoText);
                spannable.setSpan(new ForegroundColorSpan(spannableColor), 0, infoText.indexOf("\n"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new RelativeSizeSpan(0.7f), infoText.indexOf("\n"), infoText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                showInfoToast(spannable);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String getLastFragmentTagFromBackStack() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            return "";

        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();
        return tag;
    }

}
