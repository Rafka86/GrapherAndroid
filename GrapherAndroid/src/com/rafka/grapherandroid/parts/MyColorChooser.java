package com.rafka.grapherandroid.parts;

import android.annotation.SuppressLint;

//import com.rafka.grapherandroid.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyColorChooser extends View {
	//private MainActivity activity;
	private Paint hPaint, svPaint;

	private final int[] sg_colors = { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00,
			0xFFFF0000 };
	private final SweepGradient sg;
	private int[] lgColors;

	private float nowHue = 0.0f;
	private int nowColor;

	private float centerX, centerY;
	private float svXs, svYs, svXe, svYe, hRs, hRe;

	public MyColorChooser(Context context, AttributeSet attrs) {
		super(context, attrs);

		//activity = (MainActivity) context;

		sg = new SweepGradient(0.0f, 0.0f, sg_colors, null);

		hPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hPaint.setStyle(Paint.Style.STROKE);
		hPaint.setShader(sg);

		svPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		svPaint.setStyle(Paint.Style.FILL);
		svPaint.setStrokeWidth(2.0f);
	}

	public void setNowColor(int c) {
		float[] hsv = new float[3];
		Color.colorToHSV(c, hsv);
		nowHue = hsv[0];
	}

	private void drawHueCircle(Canvas canvas) {
		final float lineWidth = this.getWidth() * 0.1f;
		final float circleRadius = this.getWidth() * 0.2f * 1.4142136f + lineWidth * 0.5f;

		hPaint.setStrokeWidth(lineWidth);
		canvas.drawCircle(0.0f, 0.0f, circleRadius, hPaint);

		hRs = circleRadius - lineWidth * 0.5f;
		hRe = circleRadius + lineWidth * 0.5f;
		Log.v("H", "Rs:" + hRs + " Re:" + hRe);
	}

	private void drawSVSquare(Canvas canvas) {
		final float squareSize = this.getWidth() * 0.38f;
		final float xStart = -squareSize * 0.5f;
		final float xEnd = -xStart;
		final float yStart = xEnd;
		final float resolution = 0.01f;
		lgColors = new int[10];

		for (float y = 0; y < 1.0f; y += resolution) {
			float x;
			int i;
			for (x = 0.0f, i = 0; i < 10; x += 0.1f, i++) {
				lgColors[i] = genHSVColor(nowHue, x, y);
			}
			LinearGradient lg = new LinearGradient(xStart, yStart - squareSize * y,
					xEnd, yStart - squareSize * y,
					lgColors, null, Shader.TileMode.CLAMP);
			svPaint.setShader(lg);
			canvas.drawLine(xStart, yStart - squareSize * y, xEnd, yStart - squareSize * y, svPaint);
		}

		svXs = xStart;
		svYs = yStart;
		svXe = xEnd;
		svYe = xStart;
		Log.v("SV", "Xs:" + svXs + " Xe:" + svXe + " Ys:" + svYs + " Ye:" + svYe);
	}

	private boolean isInHCircle(float x, float y) {
		float distance = (float) Math.sqrt(x * x + y * y);
		return hRs <= distance && distance <= hRe;
	}

	private boolean isInSVSquare(float x, float y) {
		return (svXs <= x && x <= svXe) && (svYe <= y && y <= svYs);
	}

	private boolean touchH = false;
	private boolean touchSV = false;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		if (event.getPointerCount() == 1) {
			float tchX = event.getX() - centerX;
			float tchY = -(event.getY() - centerY);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (isInHCircle(tchX, tchY))
					touchH = true;
				else if (isInSVSquare(tchX, tchY))
					touchSV = true;
				else
					touchH = touchSV = false;
				break;
			case MotionEvent.ACTION_MOVE: {
				if (touchH) {
					if (isInHCircle(tchX, tchY)) {
						float angle = (float) Math.atan2(tchY, tchX);
						angle *= 180.0f / Math.PI;
						nowHue = (angle < 0.0f) ? angle + 360.0f : angle;
						Log.v("DialogTouchHue", "X:" + tchX + " Y:" + tchY + " H:" + nowHue);
						invalidate();
					}
				}
				if (touchSV) {
					if (isInSVSquare(tchX, tchY)) {
						float sat = (tchX + svXe) / (svXe * 2.0f);
						float val = (tchY + svYs) / (svYs * 2.0f);
						Log.v("DialogTouchSV", "sat:" + sat + " val:" + val);
						nowColor = genHSVColor(nowHue, sat, val);
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP:
				if (touchH) {
					if (isInHCircle(tchX, tchY)) {
						float angle = (float) Math.atan2(tchY, tchX);
						angle *= 180.0f / Math.PI;
						nowHue = (angle < 0.0f) ? angle + 360.0f : angle;
						Log.v("DialogTouchHue", "X:" + tchX + " Y:" + tchY + " H:" + nowHue);
						invalidate();
					}
					touchH = false;
				}
				if (touchSV) {
					if (isInSVSquare(tchX, tchY)) {
						float sat = (tchX + svXe) / (svXe * 2.0f);
						float val = (tchY + svYs) / (svYs * 2.0f);
						nowColor = genHSVColor(nowHue, sat, val);
					}
					touchSV = false;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				touchH = touchSV = false;
				break;
			}
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
		centerX = this.getWidth() * 0.5f;
		centerY = this.getHeight() * 0.5f;

		drawSVSquare(canvas);
		drawHueCircle(canvas);
	}

	private int genHSVColor(float hue, float sat, float val) {
		float[] hsv = new float[3];

		hsv[0] = (hue >= 360) ? 359f : ((hue < 0) ? 0.0f : hue);
		hsv[1] = (sat > 1.0f) ? 1.0f : ((sat < 0.0f) ? 0.0f : sat);
		hsv[2] = (val > 1.0f) ? 1.0f : ((val < 0.0f) ? 0.0f : val);

		return Color.HSVToColor(hsv);
	}

	public int getColor() {
		return nowColor;
	}
}
