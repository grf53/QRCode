package software.experiment.qrcode.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	final static String TABLE_NAME = "losts";
	final static String ITEM_NAME = "name";
	final static String LOST = "lost";
	final static String PRICE = "price";
	final static String PATH_QRCODE = "qrcode";
	final static String _ID = "_id";
	final static String[] columns = { _ID, ITEM_NAME };

	// Complete the code below with columns name
	final private static String CREATE_CMD
			= "CREATE TABLE tvshow ("
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ITEM_NAME	+ " TEXT NOT NULL, "
			+ LOST + " INTEGER "
			+ PRICE + " INTEGER"
			+ PATH_QRCODE + " TEXT NOT NULL"
			+ ")";

	final private static String DB_NAME = "losts.sqlite";
	final private static Integer VERSION = 1;
	final private Context mContext;

	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
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
