package com.rafka.grapherandroid;

import com.rafka.grapherandroid.core.GrapherCore;
import com.rafka.grapherandroid.parts.GraphSheet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
	GrapherCore gc;
	GraphSheet gs;
	EditText et1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gc = new GrapherCore();
		
		et1 = (EditText) findViewById(R.id.editText1);
		gs = (GraphSheet) findViewById(R.id.graphSheet1);
		gs.setEditText(et1);
		gs.setGraphCore(gc);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(!gc.hasViewSize()) gc.setViewSize(gs.getWidth(), gs.getHeight());
	}
}
