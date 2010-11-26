package org.streetpacman;

import org.streetpacman.store.DMConstants;

import android.app.TabActivity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DMStreetPacman extends TabActivity implements OnTouchListener {
	private static DMStreetPacman instance;
	private DMControls dMControls;

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			final Intent results) {
		switch (requestCode) {
		case DMConstants.SHOW_GAME:

			break;
		}
	}

	// @VisibleForTesting
	static void clearInstance() {
		instance = null;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			dMControls.show();
		}
		return false;
	}
}
