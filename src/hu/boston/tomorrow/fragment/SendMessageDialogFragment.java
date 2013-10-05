package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.activity.MainActivity;
import hu.boston.tomorrow.util.UiUtil;
import hu.boston.tomorrow.widget.FontableTextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SendMessageDialogFragment extends DialogFragment {

	private FontableTextView mButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_send_message_dialog,
				container);

		mButton = (FontableTextView) view.findViewById(R.id.button);

		if (android.os.Build.VERSION.SDK_INT < 14) {
			mButton.setText("Add to calendar");
		}

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		return view;
	}
}