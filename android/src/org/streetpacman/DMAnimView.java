package org.streetpacman;

import org.streetpacman.core.DMConstants;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class DMAnimView extends ImageView {
	public AnimationDrawable frameAnimation;

	public DMAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.pacman_chomp);
		frameAnimation = (AnimationDrawable) this.getBackground();
	}

	public void setXY(int x, int y) {
		LayoutParams lp = new AbsoluteLayout.LayoutParams(-2, -2, x, y);
		this.setLayoutParams(lp);
	}
}
