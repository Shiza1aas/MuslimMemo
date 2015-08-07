package com.example.shiza.muslimmemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;

    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private  ActionBarDrawerToggle drawerToggle;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        toolbar.setTitle("Muslim Memo");

        setSupportActionBar(toolbar);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        mDrawer = (NavigationView)findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        viewPager = (ViewPager)findViewById(R.id.pager);

        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.setDrawerListener(drawerToggle);
        viewPager.setAdapter(adapter);

        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        drawerToggle.syncState();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        Intent intent;

        switch (menuItem.getItemId())
        {
            case R.id.home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.contact:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://muslimmemo.com/contact/"));
                startActivity(intent);
            case R.id.contribute:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://muslimmemo.com/contribute/"));
                startActivity(intent);
            case R.id.discuss:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://muslimmemo.com/discuss/"));
                startActivity(intent);
            case R.id.ask:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://muslimmemo.com/ask/"));
                startActivity(intent);
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

        }

        return false;
    }

}

class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Home myFragment = new Home();
        return myFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab number is: " + (position + 1);
    }
}

