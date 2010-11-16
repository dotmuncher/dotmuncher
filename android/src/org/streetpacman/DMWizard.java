package org.streetpacman;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DMWizard extends Activity{
	LinearLayout mLinearLayout;

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Create a LinearLayout in which to add the ImageView
	    mLinearLayout = new LinearLayout(this);

	    // Instantiate an ImageView and define its properties
	    ImageView i = new ImageView(this);
	    i.setImageResource(R.drawable.mock_loading);
	    i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
	    i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

	    // Add the ImageView to the layout and set the layout as the content view
	    mLinearLayout.addView(i);
	    setContentView(mLinearLayout);
	}
}
