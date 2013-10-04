package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.widget.FontableTextView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class DrawerAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private ArrayList<String> mObjects;

	// TODO - Ne itt legyen beegetve
	private ArrayList<Integer> mIconsList = new ArrayList<Integer>();

	public DrawerAdapter(Context context, ArrayList<String> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;

		mIconsList.add(R.drawable.ic_launcher);
		mIconsList.add(R.drawable.ic_launcher);
		mIconsList.add(R.drawable.ic_launcher);
		mIconsList.add(R.drawable.ic_launcher);
		mIconsList.add(R.drawable.ic_launcher);
		
//		mIconsList.add(R.drawable.icon_social_feed);
//		mIconsList.add(R.drawable.icon_social_feed);
//		mIconsList.add(R.drawable.icon_spotlight);
//		mIconsList.add(R.drawable.icon_leaderboard);
//		mIconsList.add(R.drawable.icon_profile);
//		mIconsList.add(R.drawable.icon_about);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DrawerItemViewHolder viewHolder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.drawer_list_item, parent, false);

			viewHolder = new DrawerItemViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.menu_icon);
			viewHolder.title = (FontableTextView) convertView
					.findViewById(R.id.menu_name);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (DrawerItemViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(mObjects.get(position));
		viewHolder.icon.setImageDrawable(getContext().getResources()
				.getDrawable(mIconsList.get(position)));

		return convertView;
	}

	static class DrawerItemViewHolder {
		ImageView icon;
		FontableTextView title;
		ImageView firstImage;
	}
}