package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.widget.FontableTextView;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
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
	private ArrayList<Integer> mMsIconsList = new ArrayList<Integer>();

	public DrawerAdapter(Context context, ArrayList<String> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;

		mIconsList.add(R.drawable.event);
		mIconsList.add(R.drawable.profil);
		mIconsList.add(R.drawable.ic_launcher);
		mIconsList.add(R.drawable.wall);
		mIconsList.add(R.drawable.schedule);
		mIconsList.add(R.drawable.speakers);
		mIconsList.add(R.drawable.ic_launcher);

		mMsIconsList.add(R.drawable.ms_event);
		mMsIconsList.add(R.drawable.ms_profile);
		mMsIconsList.add(R.drawable.ic_launcher);
		mMsIconsList.add(R.drawable.ms_wall);
		mMsIconsList.add(R.drawable.ms_schedule);
		mMsIconsList.add(R.drawable.ms_speakers);
		mMsIconsList.add(R.drawable.ic_launcher);

		// mIconsList.add(R.drawable.icon_social_feed);
		// mIconsList.add(R.drawable.icon_social_feed);
		// mIconsList.add(R.drawable.icon_spotlight);
		// mIconsList.add(R.drawable.icon_leaderboard);
		// mIconsList.add(R.drawable.icon_profile);
		// mIconsList.add(R.drawable.icon_about);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DrawerItemViewHolder viewHolder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_list_item, parent, false);

			viewHolder = new DrawerItemViewHolder();
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.menu_icon);
			viewHolder.title = (FontableTextView) convertView.findViewById(R.id.menu_name);
			viewHolder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
			viewHolder.eventTitle = (FontableTextView) convertView.findViewById(R.id.event_title);
			viewHolder.dateAgo = (FontableTextView) convertView.findViewById(R.id.date_ago);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (DrawerItemViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(mObjects.get(position));

		if (MainModel.getInstance().isHackathonEvent) {
			viewHolder.icon.setImageDrawable(getContext().getResources().getDrawable(mIconsList.get(position)));
			viewHolder.title.setTextColor(Color.parseColor("#525252"));
		}
		else {
			viewHolder.icon.setImageDrawable(getContext().getResources().getDrawable(mMsIconsList.get(position)));
			viewHolder.title.setTextColor(Color.parseColor("#ffffff"));
		}

		if (position == 2) {
			viewHolder.eventImage.setVisibility(View.VISIBLE);
			viewHolder.eventTitle.setVisibility(View.VISIBLE);
			viewHolder.dateAgo.setVisibility(View.VISIBLE);

			// TODO - event image
			if (MainModel.getInstance().selectedEvent != null && MainModel.getInstance().selectedEvent.getImageModel() != null
					&& MainModel.getInstance().selectedEvent.getImageModel().getUrl() != null)
				Picasso.with(mContext).load(MainModel.getInstance().selectedEvent.getImageModel().getUrl()).into(viewHolder.eventImage);

			if (MainModel.getInstance().selectedEvent != null) {
				viewHolder.eventTitle.setText(MainModel.getInstance().selectedEvent.getTitle());
			}
		} else {
			viewHolder.eventImage.setVisibility(View.GONE);
			viewHolder.eventTitle.setVisibility(View.GONE);
			viewHolder.dateAgo.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class DrawerItemViewHolder {
		ImageView icon;
		FontableTextView title;
		ImageView eventImage;
		FontableTextView eventTitle;
		FontableTextView dateAgo;
	}
}