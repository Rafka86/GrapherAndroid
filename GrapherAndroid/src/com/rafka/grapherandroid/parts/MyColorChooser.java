package com.rafka.grapherandroid.parts;

import com.rafka.grapherandroid.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class MyColorChooser extends View {
	private MainActivity activity;
	private Paint hPaint, svPaint;
	
	private final int[] sg_colors = {0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
	private final SweepGradient sg;
	private int[] lg_colors;
	
	private float nowHue = 0.0f;

	public MyColorChooser(Context context, AttributeSet attrs) {
		super(context, attrs);

		activity = (MainActivity)context;
		
		sg = new SweepGradient(0.0f, 0.0f, sg_colors, null);
		
		hPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		hPaint.setStyle(Paint.Style.STROKE);
		hPaint.setShader(sg);
		
		svPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		svPaint.setStyle(Paint.Style.FILL);
		svPaint.setStrokeWidth(2.0f);
	}

	private void drawHueCircle(Canvas canvas) {
		
	}

	private void drawSVSquare(Canvas canvas) {
		final float squareSize = this.getWidth() / 3.0f;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(this.getWidth()/2.0f, this.getHeight()/2.0f);
		
		drawHueCircle(canvas);
		drawSVSquare(canvas);
	}
	
	private int genHSVColor(float hue, float sat, float val) {
		float[] hsv = new float[3];
		
		hsv[0] = (hue >= 360) ? 359f : ((hue < 0)    ? 0.0f : hue);
		hsv[1] = (sat > 1.0f) ? 1.0f : ((sat < 0.0f) ? 0.0f : sat);
		hsv[2] = (val > 1.0f) ? 1.0f : ((val < 0.0f) ? 0.0f : val);
		
		return Color.HSVToColor(hsv);
	}
}
