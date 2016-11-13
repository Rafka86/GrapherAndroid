package com.rafka.grapherandroid.core;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GrapherCore extends Observable implements Observer {
	private final float centerPlusLim = 500.0f;
	private final float centerMinusLim = -500.0f;
	private final float sizeMaxLim = 100.0f;
	private final float sizeMinLim = 1e-4f;

	private boolean viewSizeFlag;

	private ArrayList<Function> fs;

	private float xSize, ySize;
	private float xMax, xMin, yMax, yMin;
	private float deltaX, deltaY;
	private float centerX, centerY;
	private float vWidth, vHeight, vRate;

	public GrapherCore() {
		centerX = centerY = 0.0f;
		xSize = 10.0f;
		xMax = 5.0f;
		xMin = -5.0f;
		viewSizeFlag = false;

		fs = new ArrayList<Function>();
		fs.clear();

		fs.add(new Function(this));
		fs.get(0).setFunction("x^2");
	}

	public ArrayList<Function> getFunctionList() {
		return fs;
	}

	public void addFunction() {
		fs.add(new Function(this));
		Changed();
	}

	public void removeFunction(int index) {
		fs.remove(index);
		Changed();
	}

	public Function getFunction(int index) {
		return fs.get(index);
	}

	public void addCenter(float xVal, float yVal) {
		centerX += xVal;
		centerY += yVal;
		if (Math.abs(centerX) > centerPlusLim)
			if (centerX > centerPlusLim)
				centerX = centerPlusLim;
			else
				centerX = centerMinusLim;
		if (Math.abs(centerY) > centerPlusLim)
			if (centerY > centerPlusLim)
				centerY = centerPlusLim;
			else
				centerY = centerMinusLim;
		CalcArgs();

		Changed();
	}

	public void mulSizeScale(float val) {
		xSize *= val;
		if (xSize > sizeMaxLim)
			xSize = sizeMaxLim;
		if (xSize < sizeMinLim)
			xSize = sizeMinLim;
		CalcArgs();

		Changed();
	}

	public float getCenterX() {
		return centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public float getXMax() {
		return xMax;
	}

	public float getXMin() {
		return xMin;
	}

	public float getYMax() {
		return yMax;
	}

	public float getYMin() {
		return yMin;
	}

	public float getDeltaX() {
		return deltaX;
	}

	public float getDeltaY() {
		return deltaY;
	}

	public float getViewWidth() {
		return vWidth;
	}

	public float getViewHeight() {
		return vHeight;
	}

	public float getXSize() {
		return xSize;
	}

	public float getYSize() {
		return ySize;
	}

	private void CalcArgs() {
		ySize = xSize * vRate;

		xMax = centerX + xSize / 2.0f;
		xMin = centerX - xSize / 2.0f;
		yMax = centerY + ySize / 2.0f;
		yMin = centerY - ySize / 2.0f;

		deltaX = xSize / vWidth;
		deltaY = ySize / vHeight;
	}

	public void setViewSize(int width, int height) {
		vWidth = (float) width;
		vHeight = (float) height;
		vRate = vHeight / vWidth;

		CalcArgs();
		viewSizeFlag = true;

		Changed();
	}

	public boolean hasViewSize() {
		return viewSizeFlag;
	}

	private void Changed() {
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		Changed();
	}
}
