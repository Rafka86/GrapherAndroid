package com.rafka.grapherandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.EditText;

public class GraphSheet extends View implements OnScaleGestureListener {
	MainActivity activity;

	ScaleGestureDetector sDetector;
	EditText et1;

	public GraphSheet(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
		sDetector = new ScaleGestureDetector(context, this);
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

}
