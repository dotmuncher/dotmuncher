package org.streetpacman;

import android.widget.AbsoluteLayout;

public class DMSpriteFactory {
	private static DMSprite[] arrDMSprite = new DMSprite[8];

	public DMSprite getSprite(int animIndex) {
		if (arrDMSprite[animIndex] != null) {
			return arrDMSprite[animIndex];
		} else {
			arrDMSprite[animIndex] = new DMSprite(DMBoard.getInstance(),animIndex);
			DMBoard.getInstance().addSprite(arrDMSprite[animIndex]);
			DMBoard.getInstance().mapView.post(arrDMSprite[animIndex].rAnimate);
			return arrDMSprite[animIndex];
		}
	}
}
