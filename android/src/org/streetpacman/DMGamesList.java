package org.streetpacman;

import org.streetpacman.core.DMConstants;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class DMGamesList extends ListActivity {
	private ListView listView = null;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent result = new Intent();
		result.putExtra("gameid", id);
		setResult(RESULT_OK, result);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(DMConstants.TAG,"DMGamesList.onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dmmaps_list);
		listView = getListView();
		
		setResult(RESULT_OK);
		//finish();
	}

}
