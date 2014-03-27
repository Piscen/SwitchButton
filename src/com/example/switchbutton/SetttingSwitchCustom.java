package com.example.switchbutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class SetttingSwitchCustom extends View implements OnTouchListener{
	private Paint mPaint;
	private Bitmap mBitmap,mBitmapRight,mBitmapLeft,mSwitch;
	private boolean left = true, right = true;
	//圆的横坐标
	private int switchX;
	//间隔
	private int distance;
	//幅度
	private int extent = 2;
	//频率
	private int minu = 3;
	public SetttingSwitchCustom(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
	}

	public SetttingSwitchCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initImage();
		mBitmap = mBitmapRight;
		distance = (mBitmapRight.getHeight() - mSwitch.getHeight()) / 2;
		switchX = mBitmapRight.getWidth()-distance-mSwitch.getWidth();
	}

	public SetttingSwitchCustom(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
	}

	//设置大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mBitmapRight.getWidth(), mBitmapRight.getHeight());
	}


	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);

		canvas.drawBitmap(mSwitch,switchX, distance, mPaint);
		super.onDraw(canvas);
	}
	//初始化图片
	private void initImage()
	{
		mPaint = new Paint();
		mBitmapRight = BitmapFactory.decodeResource(getResources(), R.drawable.switch_bg_right);      
		mBitmapLeft = BitmapFactory.decodeResource(getResources(),R.drawable.switch_bg_left);
		mSwitch = BitmapFactory.decodeResource(getResources(),R.drawable.switch_button);
		setOnTouchListener(this);
	}
	//点击
	public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	{
		switch (paramMotionEvent.getAction())
		{
		default:
		case 0:
			
		case 2:
			if(paramMotionEvent.getX()<mBitmapRight.getWidth()&&paramMotionEvent.getX()>distance&&paramMotionEvent.getY()<mBitmapRight.getHeight()&&paramMotionEvent.getY()>distance){

				if(paramMotionEvent.getX()>mBitmapRight.getWidth()/2){
					mBitmap = mBitmapRight;
					
				}
				if(paramMotionEvent.getX()<mBitmapRight.getWidth()/2){
					mBitmap = mBitmapLeft;
					
				}
				if(paramMotionEvent.getX()-switchX<mSwitch.getWidth()/2){
					if(switchX>=distance){
						switchX = (int) (paramMotionEvent.getX()-mSwitch.getWidth()/2);
						if(paramMotionEvent.getX()<=distance+mSwitch.getWidth()/2){
							switchX = distance;
						}
						new Thread(new DrawSwitch()).start();
					}
				}else if (paramMotionEvent.getX()-mSwitch.getWidth()/2>switchX){
					if(switchX<=(mBitmapRight.getWidth()-distance-mSwitch.getWidth())){
						switchX = (int) (paramMotionEvent.getX()-mSwitch.getWidth()/2);
						if(paramMotionEvent.getX()>=(mBitmapRight.getWidth()-distance-mSwitch.getWidth()/2)){
							switchX = (mBitmapRight.getWidth()-distance-mSwitch.getWidth());
						}
						new Thread(new DrawSwitch()).start();
					}
				}
			}
			
		case 1:
		case 3:
			
		}
		if(paramMotionEvent.getX()<mBitmapRight.getWidth()/2){
			handler.postDelayed(leftRun, 0);
			onCharChange(false);
		}
		if(paramMotionEvent.getX()>=mBitmapRight.getWidth()/2){
			onCharChange(true);
			handler.postDelayed(rightRun, 0);
		}
		
		return true;
	}

	class DrawSwitch implements Runnable{

		@Override
		public void run() {
			Looper.prepare();

			postInvalidate();

			Looper.loop();

		}
	}
	Handler handler = new Handler();

	Runnable leftRun = new  Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			left();
			if(left){
				handler.postDelayed(leftRun,minu);
			}
			left = true;
		}};
		Runnable rightRun = new  Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				right();
				if(right){
					handler.postDelayed(rightRun, minu);
				}
				right = true;
			}};
			//左边回滚
			private void left(){
				if(switchX > distance&&switchX < mBitmapRight.getWidth()/2-mSwitch.getWidth()/2){
					switchX-=extent;
					postInvalidate();
				}
				if(switchX == distance){
//					onCharChange(false);
					left = false;
				}
			}
			//右边回滚
			private void right(){
				if(switchX >= mBitmapRight.getWidth()/2-mSwitch.getWidth()/2&&switchX < mBitmapRight.getWidth()-distance-mSwitch.getWidth()){
					switchX+=extent;
					postInvalidate();
				}
				if(switchX == mBitmapRight.getWidth()-distance-mSwitch.getWidth()){
//					onCharChange(true);
					right = false;
				}
			}

			protected void onCharChange(boolean c) {
				
				if (mOnSwitchChangeListener != null)
					if(switchX>0&&switchX<mBitmapRight.getWidth()/2){
						onCharChange(false);
					}
					if(switchX>mBitmapRight.getWidth()/2){
						onCharChange(true);
					}
					mOnSwitchChangeListener.onSwitchChange(c);
			}

			private OnSwitchChangeListener mOnSwitchChangeListener;

			public void setOnSwitchChangeListener(OnSwitchChangeListener listener) {
				mOnSwitchChangeListener = listener;
			}

			public interface OnSwitchChangeListener {
				void onSwitchChange(boolean c);
			}
}
