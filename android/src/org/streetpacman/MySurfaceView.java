package org.streetpacman;

import org.streetpacman.core.DMConstants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MySurfaceView extends SurfaceView implements
SurfaceHolder.Callback {

	public AnimationSprite testSprite1;
	// public AnimationSprite testSprite2;
	// public AnimationSprite testSprite3;
	public long GameTime;

	private MySurfaceThread thread;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawColor(Color.BLACK);
		testSprite1.Update(GameTime);
		// testSprite2.Update(GameTime);
		// testSprite3.Update(GameTime);
		testSprite1.draw(canvas);
		// testSprite2.draw(canvas);
		// testSprite3.draw(canvas);
	}

	private void init() {
		getHolder().addCallback(this);
		thread = new MySurfaceThread(getHolder(), this);
		// create a graphic
		testSprite1 = new AnimationSprite();
		// testSprite2 = new AnimationSprite();
		// testSprite3 = new AnimationSprite();
		testSprite1.init(BitmapFactory.decodeResource(getResources(),
				R.drawable.arrow), 100, 60, 1, 6);
		// testSprite2.init(BitmapFactory.decodeResource(getResources(),
		// R.drawable.arrow), 100, 60, 8, 6);
		// testSprite3.init(BitmapFactory.decodeResource(getResources(),
		// R.drawable.arrow), 100, 60, 8, 6);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	

	public class MySurfaceThread extends Thread {
		private SurfaceHolder myThreadSurfaceHolder;
		private MySurfaceView myThreadSurfaceView;
		private boolean myThreadRun = false;

		public MySurfaceThread(SurfaceHolder surfaceHolder,
				MySurfaceView surfaceView) {
			myThreadSurfaceHolder = surfaceHolder;
			myThreadSurfaceView = surfaceView;
		}

		public void setRunning(boolean b) {
			myThreadRun = b;
		}

		@Override
		public void run() {
			while (myThreadRun) {
				Canvas c = null;
				try {
					GameTime = System.currentTimeMillis();
					c = myThreadSurfaceHolder.lockCanvas(null);
					synchronized (myThreadSurfaceHolder) {
						myThreadSurfaceView.onDraw(c);
					}
				} finally {
					if (c != null) {
						myThreadSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	public class AnimationSprite {

		private Bitmap mAnimation;
		private int mXPos;
		private int mYPos;
		private Rect mSRectangle;
		private int mFPS;
		private int mNoOfFrames;
		private int mCurrentFrame;
		private long mFrameTimer;
		private int mSpriteHeight;
		private int mSpriteWidth;

		public AnimationSprite() {
			mSRectangle = new Rect(0, 0, 0, 0);
			mFrameTimer = 0;
			mCurrentFrame = 0;
			mXPos = 150;
			mYPos = 80;
		}

		public void init(Bitmap theBitmap, int Height, int Width, int theFPS,
				int theFrameCount) {
			mAnimation = theBitmap;
			mSpriteHeight = Height;
			mSpriteWidth = Width;
			mSRectangle.top = 0;
			mSRectangle.bottom = mSpriteHeight;
			mSRectangle.left = 0;
			mSRectangle.right = mSpriteWidth;
			mFPS = 1000 / theFPS;
			mNoOfFrames = theFrameCount;
		}

		public void Update(long GameTime) {
			if (GameTime > mFrameTimer + mFPS) {
				mFrameTimer = GameTime;
				mCurrentFrame += 1;

				if (mCurrentFrame >= mNoOfFrames) {
					mCurrentFrame = 0;
				}
			}

			mSRectangle.left = mCurrentFrame * mSpriteWidth;
			mSRectangle.right = mSRectangle.left + mSpriteWidth;
		}

		public void draw(Canvas canvas) {
			Rect dest = new Rect(getXPos(), getYPos(),
					getXPos() + mSpriteWidth, getYPos() + mSpriteHeight);

			canvas.drawBitmap(mAnimation, mSRectangle, dest, null);
		}

		public int getYPos() {
			return mYPos;
		}

		public int getXPos() {
			return mXPos;
		}
	}
}
