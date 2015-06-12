package software.experiment.qrcode;

import software.experiment.qrcode.database.DatabaseOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {
	private int indexItemName;
	private int indexLost;
	private LayoutInflater inflater;

	public CustomCursorAdapter(Context context, Cursor cursor, boolean autoRequery) {
		super(context, cursor, autoRequery);
		indexItemName = cursor.getColumnIndex(DatabaseOpenHelper.ITEM_NAME);
		indexLost = cursor.getColumnIndex(DatabaseOpenHelper.LOST);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String itemName = cursor.getString(indexItemName);
		TextView textItemName = (TextView)view.findViewById(R.id.text_list_item_name);
		textItemName.setText(itemName);
		
		boolean lost = (cursor.getInt(indexLost) == 1);
		ImageView imageIconLost = (ImageView)view.findViewById(R.id.image_list_item_lost);
		imageIconLost.setImageResource(lost? R.drawable.lost:R.drawable.magnifier);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.row_layout, parent, false);
	}
}
