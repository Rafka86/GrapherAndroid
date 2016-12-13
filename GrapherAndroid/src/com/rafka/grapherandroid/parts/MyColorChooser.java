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
	private Paint paint;
	
	private final int[] sg_colors = {0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
	private final SweepGradient sg;
	private int[] lg_colors;

	public MyColorChooser(Context context, AttributeSet attrs) {
		super(context, attrs);

		activity = (MainActivity)context;
		paint = new Paint();
		
		sg = new SweepGradient();
	}

	private void drawHueCircle(Canvas canvas) {
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawHueCircle(canvas);
	}
}
