package com.rafka.grapherandroid.parts;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.rafka.grapherandroid.MainActivity;
import com.rafka.grapherandroid.core.Function;
import com.rafka.grapherandroid.core.GrapherCore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
	private float tchStrtX, tchStrtY;
	private int graphReso = 100;
	private float[] xs;

	public GraphSheet(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
		sDetector = new ScaleGestureDetector(activity, this);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		gridSpan = 1.0f;
		tchStrtX = tchStrtY = 0.0f;
		xs = new float[graphReso];
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
		float width = gc.getViewWidth();
		float height = gc.getViewHeight();
		FloatBuffer fb = FloatBuffer.allocate(400);
		fb.clear();

		float x_min = gc.getXMin(), y_max = gc.getYMax();
		float delta_x = gc.getDeltaX(), delta_y = gc.getDeltaY();
		if (gridSpan * 2.0f > gc.getXSize())
			gridSpan /= 10.0f;
		else if (gridSpan * 20.0f < gc.getXSize())
			gridSpan *= 10.0f;
		float gridSpanPix = gridSpan / delta_x;

		float xAyP = y_max / delta_y; //X軸のY座標 x axis y pos
		float yAxP = -x_min / delta_x; //Y軸のX座標 y axis x pos

		//軸の描画
		fb.put(yAxP);
		fb.put(0.0f);
		fb.put(yAxP);
		fb.put(height);
		fb.put(0.0f);
		fb.put(xAyP);
		fb.put(width);
		fb.put(xAyP);
		paint.setStrokeWidth(3.0f);
		paint.setColor(Color.GRAY);
		canvas.drawLines(fb.array(), paint);
		fb.clear();

		//グリッドの描画
		for (float i = yAxP; i > 0.0f; i -= gridSpanPix) {
			if (i <= 0.0f)
				break;
			else if (i > width)
				i -= gridSpanPix * (int) ((i - width) / gridSpanPix);
			fb.put(i);
			fb.put(0.0f);
			fb.put(i);
			fb.put(height);
		}

		for (float i = yAxP; i < width; i += gridSpanPix) {
			if (i >= width)
				break;
			else if (i < 0.0f)
				i += gridSpanPix * (int) (-i / gridSpanPix);
			fb.put(i);
			fb.put(0.0f);
			fb.put(i);
			fb.put(height);
		}

		for (float i = xAyP; i < height; i += gridSpanPix) {
			if (i >= height)
				break;
			else if (i < 0.0f)
				i += gridSpanPix * (int) (-i / gridSpanPix);
			fb.put(0.0f);
			fb.put(i);
			fb.put(width);
			fb.put(i);
		}

		for (float i = xAyP; i > 0.0f; i -= gridSpanPix) {
			if (i <= 0.0f)
				break;
			else if (i > height)
				i -= gridSpanPix * (int) ((i - height) / gridSpanPix);
			fb.put(0.0f);
			fb.put(i);
			fb.put(width);
			fb.put(i);
		}

		paint.setStrokeWidth(1.0f);
		canvas.drawLines(fb.array(), paint);
	}

	private void drawGraphs(Canvas canvas) {
		ArrayList<Function> list = gc.getFunctionList();
		Function tmp;
		float step = gc.getXSize() / (float) graphReso;
		FloatBuffer fb = FloatBuffer.allocate(4000);

		xs[0] = gc.getXMin();
		for (int i = 1; i < graphReso; i++)
			xs[i] = xs[i - 1] + step;

		float[] ys;
		float x_base = gc.getXMin(), y_base = gc.getYMin() + gc.getYSize();
		float delta_x = gc.getDeltaX(), delta_y = gc.getDeltaY();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isVisible()) {
				fb.clear();
				tmp = list.get(i);
				ys = tmp.getValues(xs, graphReso);
				for (int j = 1; j < graphReso; j++) {
					fb.put((xs[j - 1] - x_base) / delta_x);
					fb.put((y_base - ys[j - 1]) / delta_y);
					fb.put((xs[j] - x_base) / delta_x);
					fb.put((y_base - ys[j]) / delta_y);
				}
				paint.setStrokeWidth(2.0f);
				paint.setColor(tmp.getColor());
				canvas.drawLines(fb.array(), paint);
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawGrid(canvas);
		drawGraphs(canvas);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		sDetector.onTouchEvent(event);

		if (event.getPointerCount() == 1) {
			//float touchX = gc.getXMin() + event.getX() * gc.getDeltaX();
			//float touchY = gc.getYMin() + gc.getYSize() - event.getY() * gc.getDeltaY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				tchStrtX = event.getX();
				tchStrtY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE: {
				float moveX = (tchStrtX - event.getX()) / (gc.getViewWidth() / 2.0f);
				float moveY = (event.getY() - tchStrtY) / (gc.getViewWidth() / 2.0f);
				gc.addCenter(moveX * gridSpan, moveY * gridSpan);
				break;
			}
			case MotionEvent.ACTION_UP:
				tchStrtX = tchStrtY = 0.0f;
				break;
			case MotionEvent.ACTION_CANCEL:
				tchStrtX = tchStrtY = 0.0f;
				break;
			}
		}

		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		clear();
		message(String.valueOf(1.0f + (1.0f - detector.getScaleFactor())));
		gc.mulSizeScale(1.0f + (1.0f - detector.getScaleFactor()));

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
