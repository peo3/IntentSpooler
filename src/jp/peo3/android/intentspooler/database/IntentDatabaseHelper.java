// Inspired by http://www.vogella.de/articles/AndroidSQLite/article.html

package jp.peo3.android.intentspooler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IntentDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "intent.db";

	private static final int DATABASE_VERSION = 5;

	public IntentDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		IntentTables.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		IntentTables.onUpgrade(database, oldVersion, newVersion);
	}

}
