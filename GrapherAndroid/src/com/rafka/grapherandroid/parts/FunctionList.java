package com.rafka.grapherandroid.parts;

import java.util.Observable;
import java.util.Observer;

import com.rafka.grapherandroid.MainActivity;
import com.rafka.grapherandroid.R;
import com.rafka.grapherandroid.core.GrapherCore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FunctionList extends ListView implements OnClickListener, Observer, OnEditorActionListener {
	private MainActivity activity;
	private GrapherCore gc;
	private FunctionAdapter adapter;

	public FunctionList(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
	}

	public void setGrapherCore(GrapherCore gc) {
		this.gc = gc;
		this.gc.addObserver(this);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = (FunctionAdapter) adapter;
	}

	public void setAddButton(Button b) {
		b.setOnClickListener(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.litem_delbutton:
			gc.removeFunction((Integer) v.getTag());
			break;
		case R.id.add_button:
			gc.addFunction();
			break;
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (event == null) {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				gc.getFunction((Integer) v.getTag()).setFunction(v.getText().toString());
				((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(v.getWindowToken(), 0);
				Log.v("onEditorAction",
						"edittext at " + v.getTag() + " : " + gc.getFunction((Integer) v.getTag()).getFunction());
			}
		} else {
			if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
				gc.getFunction((Integer) v.getTag()).setFunction(v.getText().toString());
				((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(v.getWindowToken(), 0);
				Log.v("onEditorAction",
						"edittext at " + v.getTag() + " : " + gc.getFunction((Integer) v.getTag()).getFunction());
			}
		}
		return false;
	}

}
