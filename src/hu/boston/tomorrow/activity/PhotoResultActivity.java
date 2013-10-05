package hu.boston.tomorrow.activity;

import hu.boston.tomorrow.Constants;
import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.widget.FontableEditText;
import hu.boston.tomorrow.widget.FontableTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoResultActivity extends ActionBarActivity {

	private Context mContext;
	private ImageView mPhotoPreview;
	private ImageButton mButton;

	private FontableEditText mCommentText;

	private boolean mIsSended = false;

	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_result);

		mContext = this;

		mPhotoPreview = (ImageView) findViewById(R.id.photo_preview);
		mButton = (ImageButton) findViewById(R.id.send_button);
		mCommentText = (FontableEditText) findViewById(R.id.comment_text);

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendClicked();
			}
		});

		grabImage();
	}

	public void grabImage() {
		try {
			this.getContentResolver().notifyChange(
					MainModel.getInstance().imageUri, null);
		} catch (Exception e) {

		}

		ContentResolver cr = this.getContentResolver();

		try {
			mPhotoPreview.setImageBitmap(decodeSampledBitmapFromUri(
					MainModel.getInstance().imageUri, 1000, 1000));
		} catch (Exception e) {
			Toast.makeText(this, "Nem sikerült a kép betöltése!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			} else {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
		}
		return inSampleSize;
	}

	private Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth,
			int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Rect rect = new Rect(0, 0, reqWidth, reqHeight);

		try {
			BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri), rect, options);
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			// return
			// BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),
			// null, options);

			Bitmap bm = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri), null, options);

			Matrix matrix = new Matrix();

			try {
				mContext.getContentResolver().notifyChange(
						MainModel.getInstance().imageUri, null);
				ExifInterface exif = new ExifInterface(
						MainModel.getInstance().photo.getAbsolutePath());
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);

				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					matrix.postRotate(270);
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					matrix.postRotate(180);
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					matrix.postRotate(90);
					break;
				}

				Log.d("DEBUG", "Exif orientation: " + orientation);
			} catch (IOException e) {
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					matrix.postRotate(90);
				}
			}

			// RECREATE THE NEW BITMAP
			Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, false);

			try {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

				FileOutputStream fo = new FileOutputStream(
						MainModel.getInstance().photo);
				fo.write(bytes.toByteArray());
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bm = null;

			return resizedBitmap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void sendClicked() {
		if (mIsSended) {
			return;
		}

		if (mCommentText.getText().toString().length() == 0) {
			Toast.makeText(this, "Couldn't updload the image without co",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!isConnectedToTheInternet(getApplicationContext())) {
			Toast.makeText(this, "No internet connection, please try later!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		mIsSended = true;

		mDialog = ProgressDialog.show(PhotoResultActivity.this, "",
				"Image uploading...", true);

		UploadService us = new UploadService();

		String url = Constants.WEB_SERVICE_URL + "Events/SendMessage" + "?eventId=" 
				+ Constants.DUMMY_EVENT_ID
				+ "&subject="
				+ "TestSubject"
				+ "&content="
				+ mCommentText.getText().toString()
				+ "&userId="
				+ Constants.DUMMY_USER_ID;

		url = url.replace(" ", "%20");

		NameValuePair[] list2 = {
				new BasicNameValuePair("url", url),
				new BasicNameValuePair("image",
						MainModel.getInstance().photo.getAbsolutePath()) };

		us.execute(list2);
	}

	class UploadService extends AsyncTask<NameValuePair, Void, HttpResponse> {

		@Override
		protected HttpResponse doInBackground(NameValuePair... nameValuePairs) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpPost httpPost = null;

			try {
				MultipartEntity entity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);

				for (int index = 0; index < nameValuePairs.length; index++) {
					if (nameValuePairs[index].getName().equalsIgnoreCase(
							"image")) {
						// If the key equals to "image", we use FileBody to
						// transfer
						// the data
						entity.addPart(
								nameValuePairs[index].getName(),
								new FileBody(new File(nameValuePairs[index]
										.getValue())));
					} else if (nameValuePairs[index].getName()
							.equalsIgnoreCase("url")) {
						httpPost = new HttpPost(
								nameValuePairs[index].getValue());
					} else {
						// Normal string data
						entity.addPart(
								nameValuePairs[index].getName(),
								new StringBody(nameValuePairs[index].getValue()));
					}
				}

				httpPost.setEntity(entity);

				return httpClient.execute(httpPost, localContext);
			} catch (IOException e) {
				
			} catch (Exception e) {
				
			}

			Log.d("DEBUG", "EXCEPTION IN SENDING REQUEST");
			return null;
		}

		protected void onPostExecute(HttpResponse response) {
			mDialog.dismiss();

			String responseBody;
			try {
				responseBody = EntityUtils.toString(response.getEntity());
				Log.d("DEBUG", "CONTENT " + responseBody);
				JSONObject respObject = null;

				finish();
				
//				try {
//					CinemaResult cr = null;
//					respObject = new JSONObject(responseBody);
//
//					cr = new CinemaResult(respObject.getBoolean("IsSuccess"),
//							respObject.getJSONArray("Items").toString(),
//							respObject.getString("Code"));
//
//					MainModel.getInstance().currentCinemaResult = cr;
//
//					MainModel.getInstance().pastCinemaResults.add(cr);
//					SerializatorHandler.saveArrayList(getApplicationContext(),
//							"history.bin", MainModel.getInstance().kryo,
//							MainModel.getInstance().pastCinemaResults,
//							CinemaResult.class, new CinemaResultSerializator());
//
//					Intent intent = new Intent(mContext, ResultActivity.class);
//					intent.putExtra("isSuccess", cr.isSuccess);
//					intent.putExtra("response", cr.response);
//					startActivity(intent);
//
//					previewImage.setImageBitmap(null);
//				} catch (JSONException e) {
//					Toast.makeText(getApplicationContext(),
//							"Sikertelen feltöltés kérem próbálkozzon újból!",
//							Toast.LENGTH_SHORT).show();
//					mIsSended = false;
//				}
			} catch (ParseException e) {
				mIsSended = false;
				Toast.makeText(getApplicationContext(),
						"Sikertelen feltöltés kérem próbálkozzon újból!",
						Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				mIsSended = false;
				Toast.makeText(getApplicationContext(),
						"Sikertelen feltöltés kérem próbálkozzon újból!",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				mIsSended = false;
				Toast.makeText(getApplicationContext(),
						"Sikertelen feltöltés kérem próbálkozzon újból!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public static boolean isConnectedToTheInternet(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
}