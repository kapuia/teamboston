package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.Message;
import hu.boston.tomorrow.widget.FontableTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class FeedAdapter extends ArrayAdapter<Message> {

	private Context mContext;
	private ArrayList<Message> mObjects;
	private SimpleDateFormat mDateFormat;

	public FeedAdapter(Context context, ArrayList<Message> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;

		mDateFormat = new SimpleDateFormat("EEEE, MMMM, d");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EventItemViewHolder viewHolder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);

			viewHolder = new EventItemViewHolder();
			viewHolder.title = (FontableTextView) convertView.findViewById(R.id.title);
			viewHolder.place = (FontableTextView) convertView.findViewById(R.id.place);
			viewHolder.date = (FontableTextView) convertView.findViewById(R.id.date);
			viewHolder.attendeesCount = (FontableTextView) convertView.findViewById(R.id.attendes);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (EventItemViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(mObjects.get(position).getSubject());
		// viewHolder.place.setText(mObjects.get(position).get)

		// viewHolder.date.setText(mDateFormat.format(mObjects.get(position).getStartTime())
		// + "\n"
		// + mDateFormat.format(mObjects.get(position).getEndTime()));

		// Picasso.with(mContext).load(mObjects.get(position)).placeholder(null)
		// .into(viewHolder.dummyItem);

		return convertView;
	}

	static class EventItemViewHolder {
		FontableTextView title;
		FontableTextView place;
		FontableTextView date;
		FontableTextView attendeesCount;
		ImageView image;
	}
}