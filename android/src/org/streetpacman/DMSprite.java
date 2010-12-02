package org.streetpacman;

import org.streetpacman.core.DMConstants;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class DMSprite extends ImageView {
	AnimationDrawable frameAnimation;
	private int height;
	private int width;
	private int heightHalf;
	private int widthHalf;
	private static float scaleFactor = 0.2f;
	private int X;
	private int Y;

	public DMSprite(Context context) {
		super(context);		
		setSprite(DMConstants.SPRITE_FRAME_ANIMS[8]);
	}

	public void setSprite(int resId) {
		setImageResource(resId);
		frameAnimation = (AnimationDrawable) this.getDrawable();
		height = frameAnimation.getIntrinsicHeight();
		width = frameAnimation.getIntrinsicWidth();
		heightHalf = height / 2;
		widthHalf = width / 2 + 3;
		Matrix matrix = getImageMatrix();
		matrix.preTranslate(-widthHalf, -heightHalf);
		matrix.postScale(scaleFactor, scaleFactor);
		matrix.postTranslate(widthHalf, heightHalf);
		setImageMatrix(matrix);
		setScaleType(ScaleType.MATRIX);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}

	public void setXY(int x, int y) {
		if (x != X || y != Y) {
			X = x;
			Y = y;
			LayoutParams lp = new AbsoluteLayout.LayoutParams(-2, -2, x
					- widthHalf, y - heightHalf);
			setLayoutParams(lp);
		}
	}

}
