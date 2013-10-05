package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.model.Message;
import hu.boston.tomorrow.widget.FontableTextView;
import hu.boston.tomorrow.widget.RoundedImageTransformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

public class FeedAdapter extends ArrayAdapter<Message> {

	private Context mContext;
	private ArrayList<Message> mObjects;
	private SimpleDateFormat mDateFormat;

	private RoundedImageTransformation iconTransformation = new RoundedImageTransformation();

	public FeedAdapter(Context context, ArrayList<Message> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;

		mDateFormat = new SimpleDateFormat("EEEE, MMMM, d");
	}

	public void setObjects(ArrayList<Message> items) {
		mObjects = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EventItemViewHolder viewHolder = null;

		Message message = mObjects.get(position);
		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(R.layout.feed_item, parent, false);

			viewHolder = new EventItemViewHolder();
			viewHolder.author = (FontableTextView) convertView.findViewById(R.id.author);
			viewHolder.description = (FontableTextView) convertView.findViewById(R.id.description);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			viewHolder.fader = (ImageView) convertView.findViewById(R.id.fader);
			viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			viewHolder.root = (RelativeLayout) convertView.findViewById(R.id.rootlayout);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (EventItemViewHolder) convertView.getTag();
		}

		if (message.getSender() != null) {
			viewHolder.author.setText(message.getSender().getDisplayName());

			if (!mObjects.get(position).getSender().getUserName().equals("kapuia")) {
				Picasso.with(mContext).load(R.drawable.icon_laszlo).noFade().placeholder(null).transform(iconTransformation).into(viewHolder.avatar);

			} else
				Picasso.with(mContext).load(R.drawable.icon_akos).noFade().placeholder(null).transform(iconTransformation).into(viewHolder.avatar);

		}

		if (message.getImage() == null || message.getImage().getUrl() == null) {
			viewHolder.image.setVisibility(View.GONE);
			viewHolder.fader.setVisibility(View.GONE);
		} else {
			viewHolder.image.setVisibility(View.VISIBLE);
			viewHolder.fader.setVisibility(View.VISIBLE);

			Picasso.with(mContext).load(mObjects.get(position).getImage().getUrl()).placeholder(null).into(viewHolder.image);
		}

		if (!MainModel.getInstance().isHackathonEvent) {
			viewHolder.root.setBackground(mContext.getResources().getDrawable(R.drawable.card_bg_blue));
			viewHolder.description.setTextColor(mContext.getResources().getColor(R.color.white));
			viewHolder.author.setTextColor((mContext.getResources().getColor(R.color.white)));
		}

		viewHolder.description.setText(mObjects.get(position).getContent());

		return convertView;
	}

	static class EventItemViewHolder {
		FontableTextView author;
		FontableTextView description;
		ImageView image;
		ImageView fader;
		ImageView avatar;
		RelativeLayout root;
	}
}