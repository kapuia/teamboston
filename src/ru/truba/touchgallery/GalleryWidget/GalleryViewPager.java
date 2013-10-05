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
package ru.truba.touchgallery.GalleryWidget;

import ru.truba.touchgallery.TouchView.TouchImageView;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * This class implements method to help <b>TouchImageView</b> fling, draggin and
 * scaling.
 */
public class GalleryViewPager extends ViewPager {

	static final long TAP_INTERVAL = 100;
	// TODO - UiUtil-ossal
	static final int CLICK = 20;

	private PointF start = new PointF();
	private PointF last = new PointF();
	
	private long mPressTime;

	public TouchImageView currentView;
	public TextView mDescription;
	
	public GalleryViewPager(Context context) {
		super(context);
	}

	public GalleryViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private float[] handleMotionEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mPressTime = System.currentTimeMillis();
			last = new PointF(event.getX(0), event.getY(0));
			start = last;
			break;
		case MotionEvent.ACTION_MOVE:
			PointF curr = new PointF(event.getX(0), event.getY(0));
			return new float[] { curr.x - last.x, curr.y - last.y };
		case MotionEvent.ACTION_UP:

			// Perform scale on double click
			long upTime = System.currentTimeMillis();
			
			int xDiff = (int) Math.abs(event.getX() - start.x);
            int yDiff = (int) Math.abs(event.getY() - start.y);
			
            if (xDiff < CLICK && yDiff < CLICK && upTime - mPressTime <= TAP_INTERVAL) {

				if(mDescription.getVisibility() == View.VISIBLE) {
					mDescription.setVisibility(View.GONE);
				} else {
					mDescription.setVisibility(View.VISIBLE);
				}
			}

			PointF curr2 = new PointF(event.getX(0), event.getY(0));
			return new float[] { curr2.x - last.x, curr2.y - last.y };

		}
		return null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
			super.onTouchEvent(event);
		}

		float[] difference = handleMotionEvent(event);

		if (currentView.pagerCanScroll()) {
			return super.onTouchEvent(event);
		} else {
			if (difference != null && currentView.onRightSide
					&& difference[0] < 0) // move right
			{
				return super.onTouchEvent(event);
			}
			if (difference != null && currentView.onLeftSide
					&& difference[0] > 0) // move left
			{
				return super.onTouchEvent(event);
			}
			if (difference == null
					&& (currentView.onLeftSide || currentView.onRightSide)) {
				return super.onTouchEvent(event);
			}
		}

		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
			super.onInterceptTouchEvent(event);
		}

		float[] difference = handleMotionEvent(event);

		if (currentView.pagerCanScroll()) {
			return super.onInterceptTouchEvent(event);
		} else {
			if (difference != null && currentView.onRightSide
					&& difference[0] < 0) // move right
			{
				return super.onInterceptTouchEvent(event);
			}
			if (difference != null && currentView.onLeftSide
					&& difference[0] > 0) // move left
			{
				return super.onInterceptTouchEvent(event);
			}
			if (difference == null
					&& (currentView.onLeftSide || currentView.onRightSide)) {
				return super.onInterceptTouchEvent(event);
			}
		}
		return false;
	}
	
	public void setDescriptionText(TextView description) {
		mDescription = description;
	}
}
