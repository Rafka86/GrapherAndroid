package com.rafka.grapherandroid;

import java.util.Observable;

public class GrapherCore extends Observable {
	private boolean viewSizeFlag;
	
	private float xMax, xMin, yMax, yMin;
	private float deltaX, deltaY;
	private float centerY;
	private float vWidth, vHeight, vRate;
	
	public GrapherCore() {
		centerY = 0.0f;
		xMax = 5.0f;
		xMin = -5.0f;
		viewSizeFlag = false;
	}
	
	public void setXMax(float value) {
		xMax = value;
		CalcArgs();
		
		Changed();
	}
	
	public float getXMax() {
		return xMax;
	}
	
	public void setXMin(float value) {
		xMin = value;
		CalcArgs();
		
		Changed();
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
	
	private void CalcArgs() {
		float ySize = (xMax - xMin) * vRate;
		yMax = centerY + ySize / 2.0f;
		yMin = centerY - ySize / 2.0f;
		
		deltaX = (xMax - xMin) / vWidth;
		deltaY = ySize / vHeight;
	}
	
	public void setViewSize(int width, int height) {
		vWidth = (float)width;
		vHeight = (float)height;
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
}
