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
package ru.truba.touchgallery.TouchView;

import hu.boston.tomorrow.BostonApplication;
import hu.boston.tomorrow.services.imagestream.ImageStreamDataPreserver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;

public class UrlTouchImageView extends RelativeLayout {
	protected TouchImageView mImageView;

	protected Context mContext;

	protected int mCurrentPos;
	private String mCurrentImageUrl;

	private ImageRequest mImageRequest;
	private static final Object TAG = new Object();

	public UrlTouchImageView(Context ctx) {
		super(ctx);
		mContext = ctx;
		init();
	}

	public UrlTouchImageView(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		mContext = ctx;
		init();
	}

	public TouchImageView getImageView() {
		return mImageView;
	}

	protected void init() {
		mImageView = new TouchImageView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		mImageView.setLayoutParams(params);
		// Backward compatiblity
//		mImageView.setBackgroundDrawable(mContext.getResources().getDrawable(
//				R.drawable.bglogo_black));
//		mImageView.setImageBitmap(BitmapFactory.decodeResource(
//				mContext.getResources(), R.drawable.transparent_bg));

		mImageView.setDrawingCacheEnabled(true);
		this.addView(mImageView);
	}

	public void setUrl(String imageUrl, int pos) {

		mCurrentPos = pos;

		if (ImageStreamDataPreserver.mImageDictionary.containsKey(imageUrl)) {
			int[] dimensions = ImageStreamDataPreserver.mImageDictionary
					.get(imageUrl);
			mImageView.setBitmapDimensions(dimensions[0], dimensions[1]);
		}

		mCurrentImageUrl = imageUrl;

		mImageRequest = new ImageRequest(mCurrentImageUrl,
				new Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bm) {
						ImageStreamDataPreserver.mImageDictionary.put(
								mCurrentImageUrl,
								new int[] { bm.getWidth(), bm.getHeight() });
						mImageView.setBitmapDimensions(bm.getWidth(),
								bm.getHeight());

						mImageView.setImageBitmap(bm);
					}
				}, 0, 0, Config.RGB_565, null);

		mImageRequest.setTag(TAG);
		
		
		// TODO - Lehet vissza kell rakni
//		BostonApplication.mRequestQueue.add(mImageRequest);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
		}
	}
}