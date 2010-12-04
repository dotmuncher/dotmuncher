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
	private static DMSpriteFactory factory = new DMSpriteFactory();
	private AnimationDrawable frameAnimation;
	private int height;
	private int width;
	private int heightHalf;
	private int widthHalf;
	private static float scaleFactor = 0.2f;
	public Runnable rAnimate = new Runnable() {
		@Override
		public void run() {
			frameAnimation.start();

		}
	};
	private int _x;
	private int _y;
	private float _heading = 0;
	private boolean isPacman;

	public DMSprite(Context context, int animIndex) {
		super(context);
		setSprite(DMConstants.SPRITES[animIndex]);
		if (animIndex == 0 || animIndex == 9) {
			isPacman = true;
		} else {
			isPacman = false;
		}
	}

	public void setSprite(int resId) {
		setImageResource(resId);
		frameAnimation = (AnimationDrawable) this.getDrawable();
		height = frameAnimation.getIntrinsicHeight();
		width = frameAnimation.getIntrinsicWidth();
		heightHalf = height / 2;
		widthHalf = width / 2 + 3;
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		setScaleType(ScaleType.MATRIX);
		applyMatrix();
	}

	private void applyMatrix() {
		Matrix matrix = new Matrix();
		matrix.preTranslate(-widthHalf, -heightHalf);
		if (isPacman) {
			matrix.postRotate(-90);
		}
		matrix.postRotate(_heading);
		matrix.postScale(scaleFactor, scaleFactor);
		matrix.postTranslate(widthHalf, heightHalf);
		setImageMatrix(matrix);
	}

	public void setXY(int x, int y) {
		if (x != _x || y != _y) {
			_x = x;
			_y = y;
			LayoutParams lp = new AbsoluteLayout.LayoutParams(-2, -2, x
					- widthHalf, y - heightHalf);
			setLayoutParams(lp);
		}
	}

	public void setHeading(float heading) {
		if (_heading != heading) {
			_heading = heading;
			applyMatrix();
		}
	}

	public static DMSprite get(int ctx){
		return get(factory.getAnimIndex(ctx), ctx);
	}
	
	public static DMSprite get(int animIndex, int ctx) {
		return factory.getSprite(animIndex, ctx);
	}

	public static void setXY(int x, int y, int ctx) {
		get(factory.getAnimIndex(ctx), ctx).setXY(x, y);
	}
}
