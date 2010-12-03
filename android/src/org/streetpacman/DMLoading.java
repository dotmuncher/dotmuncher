package org.streetpacman;

import org.streetpacman.core.DMConstants;
import org.streetpacman.core.DMCore;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DMLoading extends Activity {
	LinearLayout mLinearLayout;
	AnimationDrawable frameAnimation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Create a LinearLayout in which to add the ImageView
		mLinearLayout = new LinearLayout(this);

		// Instantiate an ImageView and define its properties
		ImageView i = new ImageView(this);
		i.setImageResource(R.drawable.mock_loading);
		i.setAdjustViewBounds(true); // set the ImageView bounds to match the
										// Drawable's dimensions
		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		// Add the ImageView to the layout and set the layout as the content
		// view
		mLinearLayout.addView(i);
		setContentView(mLinearLayout);

		DMCore.self().net(DMConstants.update_phone_settings, rTrue, rFalse);

	}

	private void callFinish(int resultCode) {
		this.setResult(resultCode);
		this.finish();
	}

	private Runnable rTrue = new Runnable() {
		public void run() {
			Log.i(DMConstants.TAG, "rTrue");
			callFinish(RESULT_OK);
		}
	};

	private int retryLimit = 5;
	private Runnable rFalse = new Runnable() {
		public void run() {
			Log.i(DMConstants.TAG, "rFalse");
			// retry
			if (retryLimit > 0) {
				retryLimit--;
				DMCore.self().net(DMConstants.update_phone_settings, rTrue,
						rFalse);
			} else {
				callFinish(DMConstants.LOADING_TIMEOUT);
			}
		}
	};
}
