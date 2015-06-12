package software.experiment.qrcode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.CursorAdapter;

public class DatabaseHelper {
	
	private DatabaseOpenHelper helper;
	
	private int index_id;
	private int indexItemName;
	private int indexLost;
	private int indexPrice;
	private int indexPathQRCode;
	
	public DatabaseHelper(Context context) {
		helper = new DatabaseOpenHelper(context);
		Cursor cursor = getDBCursor();
		index_id = cursor.getColumnIndex(DatabaseOpenHelper._ID);
		indexItemName = cursor.getColumnIndex(DatabaseOpenHelper.ITEM_NAME);
		indexLost = cursor.getColumnIndex(DatabaseOpenHelper.LOST);
		indexPrice = cursor.getColumnIndex(DatabaseOpenHelper.PRICE);
		indexPathQRCode = cursor.getColumnIndex(DatabaseOpenHelper.QRCODE_PATH);
	}
	
	public Cursor getDBCursor() {
		return helper.getWritableDatabase().query(DatabaseOpenHelper.TABLE_NAME,
				DatabaseOpenHelper.columns, null, new String[] {}, null, null, null);
	}
	
	public int getLast_id(){
		Cursor cursor = getDBCursor();
		cursor.moveToLast();
		return cursor.getInt(index_id);
	}
	
	public Item getItemById(int _id){
		Cursor cursor = getDBCursor();
		for(;cursor.moveToNext();){
			if(cursor.getInt(index_id) == _id){
				Item item = new Item(cursor.getString(indexItemName), 
						cursor.getInt(indexLost) == 1? true : false,
						cursor.getInt(indexPrice),
						cursor.getString(indexPathQRCode));
				return item;
			}
		}
		return null;
	}
	
	public Item getItemByIndex(int rowIndex){
		Cursor cursor = getDBCursor();
		cursor.move(rowIndex+1);
		Item item = new Item(cursor.getString(indexItemName), 
				cursor.getInt(indexLost) == 1? true : false,
				cursor.getInt(indexPrice),
				cursor.getString(indexPathQRCode));
		return item;
	}

	public void updateByName(String targetName, Item item) {
		ContentValues cv = new ContentValues();
		cv.put(DatabaseOpenHelper.ITEM_NAME, item.itemName);
		cv.put(DatabaseOpenHelper.LOST, item.lost? 1:0);
		cv.put(DatabaseOpenHelper.PRICE, item.price);
		cv.put(DatabaseOpenHelper.QRCODE_PATH, item.QRCodeFileName);
		helper.getWritableDatabase().update(DatabaseOpenHelper.TABLE_NAME, cv, DatabaseOpenHelper._ID+" = ?", new String[]{targetName});
	}
	
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
		cv.put(DatabaseOpenHelper.QRCODE_PATH, pathQRCode);
		helper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, cv);
	}

	public void insert(Item item) {
		ContentValues cv = new ContentValues();
		cv.put(DatabaseOpenHelper.ITEM_NAME, item.itemName);
		cv.put(DatabaseOpenHelper.LOST, item.lost? 1:0);
		cv.put(DatabaseOpenHelper.PRICE, item.price);
		cv.put(DatabaseOpenHelper.QRCODE_PATH, item.QRCodeFileName);
		helper.getWritableDatabase().insert(DatabaseOpenHelper.TABLE_NAME, null, cv);
	}
	
	public void renewCursorAdapter(CursorAdapter adapter){
		adapter.swapCursor(getDBCursor());
		adapter.notifyDataSetChanged();
	}

	public void clearAll() {
		helper.getWritableDatabase().delete(DatabaseOpenHelper.TABLE_NAME, null, null);
	}

	public void closeDB() {
		helper.getWritableDatabase().close();
		helper.deleteDatabase();
		helper = null;
	}
}