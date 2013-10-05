package hu.boston.tomorrow.activity;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.DrawerAdapter;
import hu.boston.tomorrow.events.EventsDownloadedEvent;
import hu.boston.tomorrow.fragment.ContentFragment;
import hu.boston.tomorrow.fragment.EventChooserFragment;
import hu.boston.tomorrow.fragment.FeedFragment;
import hu.boston.tomorrow.fragment.Profile_Fragment;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.task.GetEventContentsTask;
import hu.boston.tomorrow.task.GetEventsTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.urbanairship.push.PushManager;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerAdapter adapter;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ArrayList<String> mMenuTitles = new ArrayList<String>();

	private int mCurrentPage;
	private Fragment mCurrentFragment;

	private EventBus eventBus;

	public static int RESULT_CAMERA_IMAGE = 222;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		eventBus = EventBusManager.getInstance();
		eventBus.register(this);

		// Menu elemek hozzaadasa
		mMenuTitles.add("All Events");
		mMenuTitles.add("Profile");
		mMenuTitles.add("Current Event");
		mMenuTitles.add("Wall");
		mMenuTitles.add("Schedule");
		mMenuTitles.add("Speakers");
		mMenuTitles.add("Attendees");

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// set a custom shadow that overlays the main content when the
		// drawer
		// opens
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		// set up the drawer's list view with items and click listener
		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_list_item, mMenuTitles));
		adapter = new DrawerAdapter(getApplicationContext(),
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

		GetEventsTask task = new GetEventsTask(this);
		task.execute();

		Set<String> pushTags = new HashSet<String>();
		pushTags.add("all");
		PushManager.shared().setTags(pushTags);
	}

	@Subscribe
	public void eventsDownloaded(EventsDownloadedEvent event) {
		MainModel.getInstance().selectedEvent = MainModel.getInstance().events
				.get(0);
		GetEventContentsTask task2 = new GetEventContentsTask(this,
				"8c65add0-6564-4430-98ec-62a8dfeffe5a");
		task2.execute();
		
		adapter.notifyDataSetChanged();
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

		// "ALL EVENTS"
		case 0:
			mCurrentFragment = new EventChooserFragment();
			break;

		// "PROFILE"
		case 1:
			mCurrentFragment = new Profile_Fragment();
			break;

		// "CURRENT EVENT"
		case 2:
			mCurrentFragment = new FeedFragment();
			break;

		// "WALL"
		case 3:
			mCurrentFragment = new FeedFragment();
			
//			MainModel.getInstance().selectedContent = MainModel.getInstance().events
//					.get(0).getEventContentList().get(0);
//			mCurrentFragment = new ContentFragment();
			break;

		
		case 4:
//			MainModel.getInstance().selectedContent = MainModel.getInstance().events
//					.get(0).getEventContentList().get(1);
//			mCurrentFragment = new ContentFragment();
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

		// mSearchView.setOnQueryTextListener(this);
		// mSearchView
		// .setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {
		//
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// if (!hasFocus) {
		// mSearchView.setIconified(true);
		// }
		// }
		// });

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

		case R.id.action_refresh:
			startCamera();
			return true;

			// case R.id.action_settings:
			// intent = new Intent(this, SettingsActivity.class);
			// startActivity(intent);
			// return true;
			// case R.id.action_more_apps:
			// intent = new Intent(this, OtherAppsActivity.class);
			// startActivity(intent);
			// return true;
		}

		return false;
	}

	private void startCamera() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		try {
			// place where to store camera taken picture
			MainModel.getInstance().photo = this.createTemporaryFile("picture",
					".jpg");
			MainModel.getInstance().photo.delete();
		} catch (Exception e) {
			Log.d("DEBUG", "Can't create file to take picture!");
			Toast.makeText(this, "Fénykép készítése jelenleg nem lehetséges!",
					Toast.LENGTH_SHORT).show();
		}
		try {
			MainModel.getInstance().imageUri = Uri.fromFile(MainModel
					.getInstance().photo);

			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					MainModel.getInstance().imageUri);

			startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
		} catch (Exception e) {
			Toast.makeText(this, "Fénykép készítése jelenleg nem lehetséges!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private File createTemporaryFile(String part, String ext) throws Exception {
		File tempDir = Environment.getExternalStorageDirectory();
		tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		return File.createTempFile(part, ext, tempDir);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_CAMERA_IMAGE && resultCode != 0) {
			Intent intent = new Intent(this, PhotoResultActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent);
		}
	}
}
