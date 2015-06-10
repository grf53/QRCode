package software.experiment.qrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

	private LayoutInflater inflater;
	private int layout;

	public CustomAdapter(Context context, int layout, String[] items) {
		super(context, layout, items);
		inflater = LayoutInflater.from(context);
		this.layout = layout;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemViewHolder viewHolder;

		if(convertView == null){
			convertView = inflater.inflate(layout, parent, false);

			viewHolder = new ListItemViewHolder();
			viewHolder.bulletView = (ImageView) convertView.findViewById(R.id.image_bullet);
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.text_title);

			convertView.setTag(viewHolder);
		}
		else	{
			viewHolder = (ListItemViewHolder) convertView.getTag();
		}
		
		viewHolder.titleView.setText(getItem(position));

		return convertView;
	}
}
