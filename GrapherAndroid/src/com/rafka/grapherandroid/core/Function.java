package com.rafka.grapherandroid.core;

import java.util.HashMap;
import java.util.Observable;

import android.graphics.Color;

public class Function extends Observable {
	private Expression exp;
	private HashMap<String, Float> cs;

	private String rexp;
	private String vname;
	private int color;
	private boolean visible;

	public Function(GrapherCore gc) {
		addObserver(gc);
		exp = new Expression("");
		rexp = "";
		cs = new HashMap<String, Float>();
		cs.clear();
		vname = "x";
		cs.put(vname, 0.0f);
		color = Color.BLUE;
		visible = false;
	}

	public float[] getValues(float[] xs, int length) {
		float[] res = new float[length];

		for (int i = 0; i < length; i++) {
			cs.put(vname, xs[i]);
			res[i] = exp.Eval(cs);
		}

		return res;
	}

	public String getFunction() {
		return rexp;
	}

	public void setFunction(String nexp) {
		rexp = nexp;
		exp.Remake(nexp);
		Changed();
	}

	public String getVarianceName() {
		return vname;
	}

	public void addConstName(String cname) {
		if (!cs.containsKey(cname)) {
			cs.put(cname, 0.0f);
			Changed();
		}
	}

	public void removeConstName(String cname) {
		if (cs.containsKey(cname)) {
			cs.remove(cname);
			Changed();
		}
	}

	public void setConstValue(String cname, float val) {
		if (cs.containsKey(cname)) {
			cs.put(cname, val);
			Changed();
		}
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int new_color) {
		color = new_color;
		Changed();
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean new_state) {
		visible = new_state;
		Changed();
	}

	private void Changed() {
		this.setChanged();
		this.notifyObservers();
	}
}
