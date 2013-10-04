package hu.boston.tomorrow.widget;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.util.UiUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class FontableEditText extends EditText {

	public FontableEditText(Context context) {
		super(context);
	}

	public FontableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		UiUtil.setCustomFont(this, context, attrs,
				R.styleable.hu_boston_tomorrow_widget_FontableEditText,
				R.styleable.hu_boston_tomorrow_widget_FontableEditText_font);
	}

	public FontableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		UiUtil.setCustomFont(this, context, attrs,
				R.styleable.hu_boston_tomorrow_widget_FontableEditText,
				R.styleable.hu_boston_tomorrow_widget_FontableEditText_font);
	}

}
