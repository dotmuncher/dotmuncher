package org.streetpacman;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class DMAnim extends Activity{
	AnimationDrawable pacman_chomp_Animation;

	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);

	  ImageView pacman_chomp_Image = (ImageView) findViewById(R.id.pacman_chomp);
	  pacman_chomp_Image.setBackgroundResource(R.drawable.pacman_chomp);
	  pacman_chomp_Animation = (AnimationDrawable) pacman_chomp_Image.getBackground();
	}

	public boolean onTouchEvent(MotionEvent event) {
	  if (event.getAction() == MotionEvent.ACTION_DOWN) {
	    pacman_chomp_Animation.start();
	    return true;
	  }
	  return super.onTouchEvent(event);
	}
}
