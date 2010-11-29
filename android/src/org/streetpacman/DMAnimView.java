package org.streetpacman;

import org.streetpacman.core.DMConstants;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DMAnimView extends TextView {

	public DMAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
/*
	protected void onDraw (Canvas canvas){
		
		super.onDraw(canvas);
		Log.i(DMConstants.TAG,"DMAnimView.onDraw");
	}
	*/
	/*
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
	*/
}
