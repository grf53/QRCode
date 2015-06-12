package software.experiment.qrcode.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public final static String TABLE_NAME = "losts";
	
	public final static String ITEM_NAME = "name";
	public final static String LOST = "lost";
	public final static String PRICE = "price";
	public final static String QRCODE_PATH = "qrcode";
	public final static String _ID = "_id";
	public final static String[] columns = { _ID, ITEM_NAME, LOST, PRICE, QRCODE_PATH };

	// Complete the code below with columns name
	final private static String CREATE_CMD
			= "CREATE TABLE " + TABLE_NAME + " ("
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ITEM_NAME	+ " TEXT NOT NULL, "
			+ LOST + " INTEGER, "
			+ PRICE + " INTEGER, "
			+ QRCODE_PATH + " TEXT NOT NULL"
			+ ")";

	final private static String DB_NAME = "losts.sqlite";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.mContext = context;
		Log.d("asdf", CREATE_CMD);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("asdf", CREATE_CMD);
		db.execSQL(CREATE_CMD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// N/A
	}

	void deleteDatabase() {
		mContext.deleteDatabase(DB_NAME);
	}
}
