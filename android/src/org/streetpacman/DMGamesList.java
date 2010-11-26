package org.streetpacman;

import org.streetpacman.store.DMConstants;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class DMGamesList extends ListActivity {
	private ListView listView = null;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent result = new Intent();
		result.putExtra("trackid", id);
		setResult(DMConstants.SHOW_GAME, result);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dmmaps_list);
		listView = getListView();

	}

}
