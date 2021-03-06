package hu.boston.tomorrow.widget;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.util.UiUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontableTextView extends TextView {

	public FontableTextView(Context context) {
		super(context);
	}

	public FontableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		UiUtil.setCustomFont(this, context, attrs,
				R.styleable.hu_boston_tomorrow_widget_FontableTextView,
				R.styleable.hu_boston_tomorrow_widget_FontableTextView_font);
	}

	public FontableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		UiUtil.setCustomFont(this, context, attrs,
				R.styleable.hu_boston_tomorrow_widget_FontableTextView,
				R.styleable.hu_boston_tomorrow_widget_FontableTextView_font);
	}
}
