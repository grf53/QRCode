package software.experiment.qrcode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

public class DatabaseHelper {

	private DatabaseOpenHelper helper;
	
	public DatabaseHelper(Context context) {
		helper = new DatabaseOpenHelper(context);
	}
	
	// Returns all artist records in the database
	public Cursor getDBCursor() {
		return helper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
				DatabaseOpenHelper.columns, null, new String[] {}, null, null, null);
	}

	// Modify the contents of the database
	public void updateByName(String targetName, Item item) {
		ContentValues cv = new ContentValues();
		cv.put(DatabaseOpenHelper.ITEM_NAME, item.name);
		cv.put(DatabaseOpenHelper.LOST, item.lost? 1:0);
		cv.put(DatabaseOpenHelper.PRICE, item.price);
		cv.put(DatabaseOpenHelper.PATH_QRCODE, item.pathQRCode);
		helper.getWritableDatabase().update(DatabaseOpenHelper.TABLE_NAME, cv, DatabaseOpenHelper._ID+" = ?", new String[]{targetName});
	}
	
	// Modify the contents of the database
	public void deleteByName(String name) {
		helper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, DatabaseOpenHelper.ITEM_NAME+" = ?", new String[]{name});
	}
	
	public void deleteById(int _id) {
		helper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, DatabaseOpenHelper._ID+" = ?", new String[]{String.valueOf(_id)});
	}
	
	public void insert(String name, boolean lost, int price, String pathQRCode) {
		ContentValues cv = new ContentValues();
		cv.put(DatabaseOpenHelper.ITEM_NAME, name);
		cv.put(DatabaseOpenHelper.LOST, lost? 1:0);
		cv.put(DatabaseOpenHelper.PRICE, price);
		cv.put(DatabaseOpenHelper.PATH_QRCODE, pathQRCode);
		helper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, cv);
	}

	public void insert(Item item) {
		ContentValues cv = new ContentValues();
		cv.put(DatabaseOpenHelper.ITEM_NAME, item.name);
		cv.put(DatabaseOpenHelper.LOST, item.lost? 1:0);
		cv.put(DatabaseOpenHelper.PRICE, item.price);
		cv.put(DatabaseOpenHelper.PATH_QRCODE, item.pathQRCode);
		helper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, cv);
	}
	
	public void renewCursorAdapter(CursorAdapter adapter){
		adapter.swapCursor(getDBCursor());
		adapter.notifyDataSetChanged();
	}

	// Delete all records
	public void clearAll() {
		helper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);
	}

	public void closeDB() {
		helper.getWritableDatabase().close();
		helper.deleteDatabase();
		helper = null;
	}
}