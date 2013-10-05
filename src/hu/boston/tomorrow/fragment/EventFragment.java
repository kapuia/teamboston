package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;

import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class EventFragment extends Fragment {

	private ImageView mTopImage;
	private ImageView mMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_event, container, false);
		
		mTopImage = (ImageView) v.findViewById(R.id.top_image);
		
		if(!MainModel.getInstance().isHackathonEvent) {
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
		
		return v;
	}
}