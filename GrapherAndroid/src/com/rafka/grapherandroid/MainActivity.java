package com.rafka.grapherandroid;

import com.rafka.grapherandroid.core.GrapherCore;
import com.rafka.grapherandroid.parts.GraphSheet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {
	GrapherCore gc;
	GraphSheet gs;
	EditText et1;

	private void setMainActivity() {
		setContentView(R.layout.activity_main);

		et1 = (EditText) findViewById(R.id.editText1);
		gs = (GraphSheet) findViewById(R.id.graphSheet1);
		gs.setEditText(et1);
		gs.setGraphCore(gc);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gc = new GrapherCore();
		setMainActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!gc.hasViewSize())
			gc.setViewSize(gs.getWidth(), gs.getHeight());
	}
}
