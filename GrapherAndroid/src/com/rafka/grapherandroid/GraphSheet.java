package com.rafka.grapherandroid;

import java.nio.FloatBuffer;
import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.EditText;

public class GraphSheet extends View implements Observer, OnScaleGestureListener {
	private MainActivity activity;
	private GrapherCore gc;
	private Paint paint;

	private ScaleGestureDetector sDetector;
	private EditText et1;
	
	private float gridSpan;

	public GraphSheet(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
		sDetector = new ScaleGestureDetector(activity, this);
		
		paint = new Paint();
		gridSpan = 1.0f;
	}
	
	public void setGraphCore(GrapherCore gc) {
		this.gc = gc;
		this.gc.addObserver(this);
	}

	public void setEditText(EditText et) {
		et1 = et;
	}

	private void clear() {
		et1.setText("");
	}

	private void message(String s) {
		et1.append(s);
		et1.append("\n");
	}
	
	private void drawGrid(Canvas canvas) {
		int width = this.getWidth();
		int height = this.getHeight();
		FloatBuffer fb = FloatBuffer.allocate(400);
		fb.clear();
		
		float x_min = gc.getXMin(), x_max = gc.getXMax(), y_max = gc.getYMax(), y_min = gc.getYMin();
		float delta_x = gc.getDeltaX(), delta_y = gc.getDeltaY();
		float gridSpanPix = gridSpan / delta_x;
		
		//軸の描画
		fb.put(-x_min / delta_x);	fb.put(0.0f);
		fb.put(-x_min / delta_x);	fb.put((float)height);
		fb.put(0.0f);					fb.put(y_max / delta_y);
		fb.put((float)width);		fb.put(y_max / delta_y);
		paint.setStrokeWidth(3.0f);
		canvas.drawLines(fb.array(), paint);
		fb.clear();
		
		for(float i = 0.0f; i < width; i += gridSpanPix) {
			fb.put(i); fb.put(0.0f);
			fb.put(i); fb.put((float)height);
		}
		for(float i = 0.0f; i < height; i += gridSpanPix) {
			fb.put(0.0f);			fb.put(i);
			fb.put((float)width);	fb.put(i);
		}
		paint.setStrokeWidth(1.0f);
		canvas.drawLines(fb.array(), paint);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawGrid(canvas);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		sDetector.onTouchEvent(event);

		invalidate();
		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		clear();
		if (detector.getScaleFactor() < 1.0)
			message("ピンチイン");
		else
			message("ピンチアウト");

		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void update(Observable observable, Object data) {
		invalidate();
	}

}
