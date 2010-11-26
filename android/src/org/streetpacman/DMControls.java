package org.streetpacman;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class DMControls {
	private boolean isVisible = false;
	private static final Animation SHOW_MENU_ANIMATION = new AlphaAnimation(0F,
			1F);
	//private final TouchLayout menuImage;
	public void show() {
		if (!isVisible) {
			SHOW_MENU_ANIMATION.setDuration(300);
			SHOW_MENU_ANIMATION.startNow();
			//menuImage.setAnimation(SHOW_MENU_ANIMATION);
		}
	}

}
