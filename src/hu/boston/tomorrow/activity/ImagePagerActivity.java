/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package hu.boston.tomorrow.activity;

import hu.boston.tomorrow.R;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagePagerActivity extends ActionBarActivity {

	private String imageDescription = "";

	private ShareActionProvider mShareActionProvider;
	private Intent mShareIntent;

	private TextView description;

	private MediaScannerConnection msConn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_pager);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		Bundle bundle = getIntent().getExtras();
		

		String url = bundle.getString("image");
		imageDescription = bundle.getString("description");

		ImageView image = (ImageView) findViewById(R.id.image);
		
		Picasso.with(this).load(url).into(image);
		
		description = (TextView) findViewById(R.id.description);
		description.setText(imageDescription);
		
	}

	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_with_share, menu);
//
//		MenuItem shareItem = menu.findItem(R.id.menu_share);
//		mShareActionProvider = (ShareActionProvider) MenuItemCompat
//				.getActionProvider(shareItem);
//
//		mShareIntent = new Intent(android.content.Intent.ACTION_SEND);
//		mShareIntent.setType("text/plain");
//		mShareActionProvider.setShareIntent(mShareIntent);
//		getShareContent(allUrl.get(0), description.getText().toString());
//
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	private void getShareContent(final String url, final String description) {
//		new AsyncTask<Void, String, String>() {
//
//			@Override
//			protected void onPostExecute(String result) {
//				mShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//						description + " " + result);
//				super.onPostExecute(result);
//			}
//
//			@Override
//			protected String doInBackground(Void... params) {
//				return SpyApplication.shortenURL(url);
//			}
//
//		}.execute();
//	}
//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		Intent intent;

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
//		case R.id.action_settings:
//			intent = new Intent(this, SettingsActivity.class);
//			startActivity(intent);
//			return true;
//		case R.id.action_more_apps:
//			intent = new Intent(this, OtherAppsActivity.class);
//			startActivity(intent);
//			return true;
//		case R.id.menu_save:
//			Bitmap bitmap = mViewPager.currentView.getDrawingCache();
//			savePhoto(bitmap);
//			break;
		}
		return super.onOptionsItemSelected(item);
	}
//
//	public void savePhoto(Bitmap bmp) {
//		try {
//			File imageFileFolder = new File(
//					Environment.getExternalStorageDirectory(), "SPY");
//			if (imageFileFolder == null) {
//				Toast.makeText(
//						this,
//						"Can't save image because SD Card is missing. Please insert one.",
//						Toast.LENGTH_SHORT).show();
//				return;
//			}
//			imageFileFolder.mkdir();
//			FileOutputStream out = null;
//			Calendar c = Calendar.getInstance();
//			String date = fromInt(c.get(Calendar.MONTH))
//					+ fromInt(c.get(Calendar.DAY_OF_MONTH))
//					+ fromInt(c.get(Calendar.YEAR))
//					+ fromInt(c.get(Calendar.HOUR_OF_DAY))
//					+ fromInt(c.get(Calendar.MINUTE))
//					+ fromInt(c.get(Calendar.SECOND));
//			File imageFileName = new File(imageFileFolder, "RedBullF1Spy"
//					+ date.toString() + ".jpg");
//
//			out = new FileOutputStream(imageFileName);
//			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
//			out.flush();
//			out.close();
//			scanPhoto(imageFileName.toString());
//			out = null;
//
//			Toast.makeText(this, "Picture saved successfully.",
//					Toast.LENGTH_SHORT).show();
//		} catch (Exception e) {
//			Toast.makeText(
//					this,
//					"Can't save image because SD Card is missing. Please insert one.",
//					Toast.LENGTH_SHORT).show();
//			// e.printStackTrace();
//		}
//	}
//
//	public String fromInt(int val) {
//		return String.valueOf(val);
//	}
//
//	public void scanPhoto(final String imageFileName) {
//		msConn = new MediaScannerConnection(this,
//				new MediaScannerConnectionClient() {
//					public void onMediaScannerConnected() {
//						msConn.scanFile(imageFileName, null);
//					}
//
//					public void onScanCompleted(String path, Uri uri) {
//						msConn.disconnect();
//					}
//				});
//		msConn.connect();
//	}
}