package jp.peo3.android.intentspooler;

import jp.peo3.android.intentspooler.database.IntentDatabaseAdapter;
import jp.peo3.android.intentspooler.database.IntentTables;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

public class IntentSpoolerList extends ListActivity implements OnItemLongClickListener {
	private IntentDatabaseAdapter dbHelper;
	SimpleCursorAdapter adapter;
	
	private void debugPopupText(String text) {
		//Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		debugPopupText("onCreate");
        setContentView(R.layout.intent_list);
        
		dbHelper = new IntentDatabaseAdapter(this);
		dbHelper.open();
		
	    ListView list = (ListView) findViewById(android.R.id.list);
	    list.setOnItemLongClickListener(this);

        populateList();
    }

    @Override
    protected void onListItemClick(ListView l, View v,
                int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	Cursor cur = (Cursor) getListView().getItemAtPosition(position);
    	Integer rowid = cur.getInt(cur.getColumnIndex(IntentTables.KEY_ROWID));
    	String timestamp = cur.getString(cur.getColumnIndex(IntentTables.KEY_TIMESTAMP));

    	Intent intent = dbHelper.getIntent(rowid, timestamp);
    	startActivity(intent);
    }
    
    
	@Override
	protected void onPause() {
		super.onPause();
		debugPopupText("onPause");
		// What should I do?
	}

	@Override
	protected void onResume() {
		super.onResume();
		debugPopupText("onResume");
	}

	private void populateList() {
		Cursor cur = dbHelper.fetchAllIntents();
		startManagingCursor(cur);
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.intent_list_row,
                cur,
                new String[] {IntentTables.KEY_TIMESTAMP,
                		IntentTables.KEY_SUMMARY},
                new int[] {R.id.timestamp, R.id.summary});

		setListAdapter((ListAdapter)adapter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		debugPopupText("onStop");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		debugPopupText("onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		debugPopupText("onDestroy");
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	debugPopupText("onSaveInstanceState");
    	// What should I do?
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  
    	super.onRestoreInstanceState(savedInstanceState);
    	debugPopupText("onRestoreInstanceState");
    	// What should I do?
    }

	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {

    	Cursor cur = (Cursor) getListView().getItemAtPosition(position);
    	final Integer rowId = cur.getInt(0);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.ok_to_delete)
		       .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dbHelper.deleteIntent(rowId);
		                // Refresh the list
		                adapter.getCursor().requery();
		           }
		       })
		       .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();

		return true;
	}
}