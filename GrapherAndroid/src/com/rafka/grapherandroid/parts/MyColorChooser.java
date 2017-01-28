package com.rafka.grapherandroid.parts;

//import com.rafka.grapherandroid.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class MyColorChooser extends View {
	//private MainActivity activity;
	private Paint hPaint, svPaint;

	private final int[] sg_colors = { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00,
			0xFFFF0000 };
	private final SweepGradient sg;
	private int[] lgColors;

	private float nowHue = 0.0f;

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
		final float circleRadius = this.getWidth() * 0.2f * 1.4142136f + lineWidth * 0.2f;

		hPaint.setStrokeWidth(lineWidth);
		canvas.drawCircle(0.0f, 0.0f, circleRadius, hPaint);
	}

	private void drawSVSquare(Canvas canvas) {
		final float squareSize = this.getWidth() * 0.32f;
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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(this.getWidth() / 2.0f, this.getHeight() / 2.0f);

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
}
