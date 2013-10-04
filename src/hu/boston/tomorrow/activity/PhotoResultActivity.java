package hu.boston.tomorrow.activity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoResultActivity extends ActionBarActivity {

	private Context mContext;
	private ImageView mPhotoPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_result);

		mContext = this;

		mPhotoPreview = (ImageView) findViewById(R.id.photo_preview);

		grabImage();
	}

	public void grabImage() {
		try {
			this.getContentResolver().notifyChange(MainModel.getInstance().imageUri, null);
		} catch (Exception e) {

		}

		ContentResolver cr = this.getContentResolver();

		try {
			// http://stackoverflow.com/questions/13353839/outofmemeryerror-from-take-photo-android-in-some-devices

			// mBitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,
			// MainModel.getInstance().imageUri);
			//
			// mResizedBitmap = getResizedBitmap(mBitmap, 1000);

			mPhotoPreview.setImageBitmap(decodeSampledBitmapFromUri(MainModel.getInstance().imageUri, 1000, 1000));
		} catch (Exception e) {
			Toast.makeText(this, "Nem sikerült a kép betöltése!", Toast.LENGTH_SHORT).show();
		}
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

	private Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Rect rect = new Rect(0, 0, reqWidth, reqHeight);

		try {
			BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), rect, options);
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			// return
			// BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),
			// null, options);

			Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);

			Matrix matrix = new Matrix();

			try {
				mContext.getContentResolver().notifyChange(MainModel.getInstance().imageUri, null);
				ExifInterface exif = new ExifInterface(MainModel.getInstance().photo.getAbsolutePath());
				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

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
			Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);

			try {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

				FileOutputStream fo = new FileOutputStream(MainModel.getInstance().photo);
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
}