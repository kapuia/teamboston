package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.widget.FontableTextView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class EventsAdapter extends ArrayAdapter<Event> {

	private Context mContext;
	private ArrayList<Event> mObjects;

	public EventsAdapter(Context context, ArrayList<Event> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EventItemViewHolder viewHolder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.event_item, parent, false);

			viewHolder = new EventItemViewHolder();
			viewHolder.title = (FontableTextView) convertView
					.findViewById(R.id.title);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (EventItemViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(mObjects.get(position).getTitle());

		// Picasso.with(mContext).load(mObjects.get(position)).placeholder(null)
		// .into(viewHolder.dummyItem);

		return convertView;
	}

	static class EventItemViewHolder {
		FontableTextView title;
		FontableTextView place;
		FontableTextView startDate;
		FontableTextView endDate;
		FontableTextView attendeesCount;
		ImageView image;
	}
}