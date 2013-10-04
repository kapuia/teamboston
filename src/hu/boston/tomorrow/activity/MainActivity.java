package hu.boston.tomorrow.activity;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.DrawerAdapter;
import hu.boston.tomorrow.fragment.Profile_Fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ArrayList<String> mMenuTitles = new ArrayList<String>();

	private int mCurrentPage;
	private Fragment mCurrentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Menu elemek hozzaadasa
		mMenuTitles.add("Profile");
//		mMenuTitles.add("Social Feed");
//		mMenuTitles.add("Spotlight");
//		mMenuTitles.add("Leaderboard");
//		mMenuTitles.add("Profile");
//		mMenuTitles.add("About");

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// set a custom shadow that overlays the main content when the
		// drawer
		// opens
//		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
//				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_list_item, mMenuTitles));
		DrawerAdapter adapter = new DrawerAdapter(getApplicationContext(),
				mMenuTitles);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav
		// drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu(); // creates call to
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu(); // creates call to
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(1);
		} else {
			setTitle(mMenuTitles.get(savedInstanceState.getInt("currentPage")));
		}

	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	private void selectItem(int position) {
		mCurrentPage = position;

		switch (position) {
		
		// "PROFILE"
		case 0:
			mCurrentFragment = new Profile_Fragment();
			break;		

		default:
			return;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, mCurrentFragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuTitles.get(position));
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu
				.findItem(R.id.action_search));

		mSearchView.setQueryHint("Search");
		mSearchView.setIconifiedByDefault(true);
		
//		mSearchView.setOnQueryTextListener(this);
//		mSearchView
//				.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {
//
//					@Override
//					public void onFocusChange(View v, boolean hasFocus) {
//						if (!hasFocus) {
//							mSearchView.setIconified(true);
//						}
//					}
//				});
		
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		Intent intent;

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;
//		case R.id.action_settings:
//			intent = new Intent(this, SettingsActivity.class);
//			startActivity(intent);
//			return true;
//		case R.id.action_more_apps:
//			intent = new Intent(this, OtherAppsActivity.class);
//			startActivity(intent);
//			return true;
		}

		return false;
	}
}
