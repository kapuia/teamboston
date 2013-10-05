package hu.boston.tomorrow.activity;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.DrawerAdapter;
import hu.boston.tomorrow.events.EventChangedEvent;
import hu.boston.tomorrow.events.EventContentDownloadedEvent;
import hu.boston.tomorrow.events.EventsDownloadedEvent;
import hu.boston.tomorrow.fragment.AllEventsFragment;
import hu.boston.tomorrow.fragment.ContentFragment;
import hu.boston.tomorrow.fragment.EventFragment;
import hu.boston.tomorrow.fragment.ProfileFragment;
import hu.boston.tomorrow.fragment.SendMessageDialogFragment;
import hu.boston.tomorrow.fragment.WallFragment;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.EventContent;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.task.GetEventContentsTask;
import hu.boston.tomorrow.task.GetEventsTask;
import hu.boston.tomorrow.util.UiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

	private Menu mMenu;

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
		adapter = new DrawerAdapter(getApplicationContext(), mMenuTitles);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav
		// drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		
		final Activity a = this;
		
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
				//supportInvalidateOptionsMenu(); // creates call to
				updateStyle();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				//supportInvalidateOptionsMenu(); // creates call to
				updateStyle();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		} else {
			setTitle(mMenuTitles.get(savedInstanceState.getInt("currentPage")));
		}

		GetEventsTask task = new GetEventsTask(this);
		task.execute();

		Set<String> pushTags = new HashSet<String>();
		pushTags.add("all");
		PushManager.shared().setTags(pushTags);

		String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

		if (android_id.equals("4f44a5f3e48e4dc9"))
			hu.boston.tomorrow.Constants.USER_ID = "1DC79F8D-83D3-42DC-9DBA-7820DA91C8C2";
	}

	@Subscribe
	public void eventSelected(EventChangedEvent event) {
		selectItem(3);
		updateStyle();
		updateMenu();
	}

	private void updateStyle() {
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);

		if(MainModel.getInstance().selectedEvent == null) {
			return;
		}
		
		if (MainModel.getInstance().selectedEvent.getEventId().equals("8c65add0-6564-4430-98ec-62a8dfeffe5a")) {
			MainModel.getInstance().isHackathonEvent = false;

			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
			getSupportActionBar().setLogo(this.getResources().getDrawable(R.drawable.logo_microsoft));

			yourTextView.setTextColor(Color.WHITE);

			mMenu.getItem(0).setIcon(this.getResources().getDrawable(R.drawable.icon_message_2));
			mMenu.getItem(1).setIcon(this.getResources().getDrawable(R.drawable.icon_camera_2));
			
			mDrawerLayout.setBackgroundColor(Color.BLACK);
			mDrawerList.setBackgroundColor(Color.parseColor("#444444"));
			mDrawerList.setDivider(new ColorDrawable(Color.parseColor("#2d2d2d")));
			mDrawerList.setDividerHeight(UiUtil.pXToDp(1, this));
		} else {

			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
			getSupportActionBar().setLogo(this.getResources().getDrawable(R.drawable.app_icon2));

			mMenu.getItem(0).setIcon(this.getResources().getDrawable(R.drawable.icon_message));
			mMenu.getItem(1).setIcon(this.getResources().getDrawable(R.drawable.icon_camera));

			yourTextView.setTextColor(Color.BLACK);

			MainModel.getInstance().isHackathonEvent = true;
			
			mDrawerLayout.setBackgroundColor(Color.LTGRAY);
			mDrawerList.setBackgroundColor(Color.parseColor("#f3f3f3"));
			mDrawerList.setDivider(new ColorDrawable(Color.parseColor("#dcdcdc")));
			mDrawerList.setDividerHeight(UiUtil.pXToDp(1, this));
		}
	}

	private void updateMenu() {
		int size = mMenuTitles.size() - 4;

		for (int i = 0; i < size; i++) {
			mMenuTitles.remove(4);
		}

		for (EventContent eventContent : MainModel.getInstance().selectedEvent.getEventContentList()) {
			mMenuTitles.add(eventContent.getTitle());
		}

		adapter.notifyDataSetChanged();
	}

	@Subscribe
	public void eventsDownloaded(EventsDownloadedEvent event) {
		MainModel.getInstance().selectedEvent = MainModel.getInstance().events.get(0);

		for (Event eeee : MainModel.getInstance().events) {
			GetEventContentsTask task2 = new GetEventContentsTask(this, eeee.getEventId());
			task2.execute();
		}

		adapter.notifyDataSetChanged();
	}

	@Subscribe
	public void eventContentDownloadedEvent(EventContentDownloadedEvent event) {
		if (event.getEvent() == MainModel.getInstance().selectedEvent)
			updateMenu();
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
		
		int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView yourTextView = (TextView) findViewById(titleId);
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
			mCurrentFragment = new AllEventsFragment();
			break;

		// "PROFILE"
		case 1:
			mCurrentFragment = new ProfileFragment();
			break;

		// "CURRENT EVENT"
		case 2:
			mCurrentFragment = new EventFragment();
			break;

		// "WALL"
		case 3:
			mCurrentFragment = new WallFragment();

			// MainModel.getInstance().selectedContent =
			// MainModel.getInstance().events
			// .get(0).getEventContentList().get(0);
			// mCurrentFragment = new ContentFragment();
			break;

		default:

			if (position > 3) {

				Event event = MainModel.getInstance().selectedEvent;
				ArrayList<EventContent> contentList = event.getEventContentList();
				MainModel.getInstance().selectedContent = contentList.get(position - 4);
				mCurrentFragment = new ContentFragment();

			} else
				return;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, mCurrentFragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuTitles.get(position));
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		mMenu = menu;

		// SearchView mSearchView = (SearchView)
		// MenuItemCompat.getActionView(menu
		// .findItem(R.id.action_search));
		//
		// mSearchView.setQueryHint("Search");
		// mSearchView.setIconifiedByDefault(true);

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

		case R.id.action_message:
			FragmentManager fm = this.getSupportFragmentManager();
			SendMessageDialogFragment dialog = new SendMessageDialogFragment();
			dialog.show(fm, "calendar_dialog");
			break;

		case R.id.action_photo:
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
			MainModel.getInstance().photo = this.createTemporaryFile("picture", ".jpg");
			MainModel.getInstance().photo.delete();
		} catch (Exception e) {
			Log.d("DEBUG", "Can't create file to take picture!");
			Toast.makeText(this, "Fénykép készítése jelenleg nem lehetséges!", Toast.LENGTH_SHORT).show();
		}
		try {
			MainModel.getInstance().imageUri = Uri.fromFile(MainModel.getInstance().photo);

			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MainModel.getInstance().imageUri);

			startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
		} catch (Exception e) {
			Toast.makeText(this, "Fénykép készítése jelenleg nem lehetséges!", Toast.LENGTH_SHORT).show();
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
