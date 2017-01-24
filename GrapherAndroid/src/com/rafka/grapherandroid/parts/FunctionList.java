package com.rafka.grapherandroid.parts;

import java.util.Observable;
import java.util.Observer;

import com.rafka.grapherandroid.MainActivity;
import com.rafka.grapherandroid.R;
import com.rafka.grapherandroid.core.Function;
import com.rafka.grapherandroid.core.GrapherCore;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FunctionList extends ListView implements OnClickListener, Observer, OnEditorActionListener, OnCheckedChangeListener {
	private MainActivity activity;
	private GrapherCore gc;
	private FunctionAdapter adapter;
	
	private Button addButton;
	private final int addB_id = 0x00000001;

	public FunctionList(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (MainActivity) context;
		addButton = new Button(context, attrs);
		addButton.setId(addB_id);
		addButton.setText("Add");
		addButton.setOnClickListener(this);
	}

	public void setGrapherCore(GrapherCore gc) {
		this.gc = gc;
		this.gc.addObserver(this);
		if (this.gc.hasSpaceOfNewFunction())
			addFooterView(addButton);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = (FunctionAdapter) adapter;
	}

	@Override
	public void update(Observable observable, Object data) {
		removeFooterView(addButton);
		if (gc.hasSpaceOfNewFunction())
			addFooterView(addButton);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.litem_delbutton:
			//gc.removeFunction((Integer) v.getTag());
			ColorChooserDialog.CreateDialog(Color.RED).show(activity.getFragmentManager(), "def");
			break;
		case addB_id:
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Function f = gc.getFunction((Integer)buttonView.getTag());
		if (!f.getFunction().equals(""))
			f.setVisible(isChecked);
		else
			buttonView.setChecked(false);
	}

}
