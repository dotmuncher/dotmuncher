package org.streetpacman;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class DMAnim extends Activity {
	AnimationDrawable frameAnimation;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		ImageView pacman_chomp_Image = (ImageView) findViewById(R.id.img_anim);
		pacman_chomp_Image.setBackgroundResource(R.drawable.pacman_dead);
		//pacman_chomp_Image.setBackgroundResource(R.drawable.pacman_chomp);
		frameAnimation = (AnimationDrawable) pacman_chomp_Image.getBackground();
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			frameAnimation.start();
			return true;
		}
		return super.onTouchEvent(event);
	}
}
