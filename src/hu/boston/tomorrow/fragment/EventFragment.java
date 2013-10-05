package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;

import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventFragment extends Fragment {

	private ImageView mTopImage;
	private ImageView mMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_event, container, false);

		mTopImage = (ImageView) v.findViewById(R.id.top_image);

		if (!MainModel.getInstance().isHackathonEvent) {
			mTopImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.dummy_image_microsoft));
		}

		mMap = (ImageView) v.findViewById(R.id.map_image);
		mMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String uri = String.format(Locale.ENGLISH, "geo:0,0?q=Budapest Saletrom utca 30");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				getActivity().startActivity(intent);
			}
		});

		if (!MainModel.getInstance().isHackathonEvent) {

			View rootlayout = v.findViewById(R.id.rootlayout);
			rootlayout.setBackgroundColor(getResources().getColor(R.color.black));
		}

		View inforoot = v.findViewById(R.id.inforoot);
		View maproot = v.findViewById(R.id.maproot);
		TextView to = (TextView) v.findViewById(R.id.to);
		TextView from = (TextView) v.findViewById(R.id.from);
		TextView information = (TextView) v.findViewById(R.id.information);
		TextView fromdate = (TextView) v.findViewById(R.id.from_date);
		TextView todate = (TextView) v.findViewById(R.id.to_date);
		TextView description = (TextView) v.findViewById(R.id.description);
		TextView maptitle = (TextView) v.findViewById(R.id.map_title);
		TextView descriptiontext = (TextView) v.findViewById(R.id.description_text);
		TextView locationdescription = (TextView) v.findViewById(R.id.location_description);

		if (!MainModel.getInstance().isHackathonEvent) {
			inforoot.setBackground(getResources().getDrawable(R.drawable.card_bg_blue));
			maproot.setBackground(getResources().getDrawable(R.drawable.card_bg_blue));

			to.setTextColor(Color.WHITE);
			information.setTextColor(Color.WHITE);
			from.setTextColor(Color.WHITE);
			fromdate.setTextColor(Color.WHITE);
			todate.setTextColor(Color.WHITE);
			description.setTextColor(Color.WHITE);
			maptitle.setTextColor(Color.WHITE);
			descriptiontext.setTextColor(Color.WHITE);
			locationdescription.setTextColor(Color.WHITE);

			from.setAlpha(0.8F);
			to.setAlpha(0.8F);
			// viewHolder.description.setTextColor(mContext.getResources().getColor(R.color.white));
			// viewHolder.author.setTextColor((mContext.getResources().getColor(R.color.white)));
		}

		return v;
	}
}