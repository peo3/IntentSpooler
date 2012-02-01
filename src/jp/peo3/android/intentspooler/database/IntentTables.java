// Inspired by http://www.vogella.de/articles/AndroidSQLite/article.html

package jp.peo3.android.intentspooler.database;

import android.database.sqlite.SQLiteDatabase;

public class IntentTables {
	public static final String DB_TABLE_INTENT = "intent";
	public static final String DB_TABLE_CATEGORY = "category";
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_TIMESTAMP = "timestamp";
	public static final String KEY_ACTION = "action";
	public static final String KEY_FLAGS = "flags";
	public static final String KEY_NB_CATEGORIES = "nbCategories";
	public static final String KEY_DATA = "data";
	public static final String KEY_EXTRAS = "extras";
	public static final String KEY_TYPE = "type";
	public static final String KEY_NB_EXTRAS = "nbExtras";
	public static final String KEY_SCHEME = "scheme";
	public static final String KEY_CATEGORY = "category";

	public static final String[] DATABASE_KEYS = {
		KEY_ROWID,
		KEY_SUMMARY,
		KEY_TIMESTAMP,
		KEY_ACTION,
		KEY_FLAGS,
		KEY_NB_CATEGORIES,
		KEY_NB_EXTRAS,
		KEY_DATA,
		KEY_TYPE,
		KEY_SCHEME,
		KEY_EXTRAS,
	};
	
	private static final String DATABASE_CREATE_INTENT = "create table "
			+ DB_TABLE_INTENT + " ("
			+ "_id integer primary key autoincrement, "
			+ "timestamp datetime not null, "
			+ "summary text not null, "
			+ "action text not null, "
			+ "flags integer not null, "
			+ "nbCategories not null, "
			+ "nbExtras not null, "
			+ "data text, "
			+ "type text, "
			+ "scheme text, "
			+ "extras blob);";

	public static final String[] DATABASE_CATEGORY_KEYS = {
		KEY_ROWID,
		KEY_TIMESTAMP,
		KEY_CATEGORY,
	};

	private static final String DATABASE_CREATE_CATEGORY = "create table "
			+ DB_TABLE_CATEGORY + " ("
			+ "_id integer, "
			+ "timestamp datetime not null, "
			+ "category text not null);";
	
	private static void createTables(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_INTENT);
		database.execSQL(DATABASE_CREATE_CATEGORY);
	}
	
	private static void dropTables(SQLiteDatabase database) {
		database.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_INTENT);
		database.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CATEGORY);
	}

	public static void onCreate(SQLiteDatabase database) {
		createTables(database);
	}
	
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// FIXME: Keep old data if possible
		dropTables(database);
		createTables(database);
	}
}
