package com.rafka.grapherandroid;

import com.rafka.grapherandroid.core.GrapherCore;
import com.rafka.grapherandroid.parts.FunctionAdapter;
import com.rafka.grapherandroid.parts.FunctionList;
import com.rafka.grapherandroid.parts.GraphSheet;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity {
	private boolean nowEditScene = false;

	GrapherCore gc;
	GraphSheet gs;
	EditText debug;

	FunctionList flv;
	FunctionAdapter adapter;

	private void setMainActivity() {
		setContentView(R.layout.activity_main);

		debug = (EditText) findViewById(R.id.debug_text);
		gs = (GraphSheet) findViewById(R.id.graphSheet1);

		gs.setEditText(debug);
		gs.setGraphCore(gc);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(false);
		nowEditScene = false;
		invalidateOptionsMenu();
	}

	private void setGraphEditActivity() {
		setContentView(R.layout.activity_sub);

		flv = (FunctionList) findViewById(R.id.functionList1);

		flv.setGrapherCore(gc);
		flv.setAdapter(adapter);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		nowEditScene = true;
		invalidateOptionsMenu();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gc = new GrapherCore();
		adapter = new FunctionAdapter(this, R.layout.list_item, gc.getFunctionList());

		setMainActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (nowEditScene)
			getMenuInflater().inflate(R.menu.option_menu_sub, menu);
		else
			getMenuInflater().inflate(R.menu.option_menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!gc.hasViewSize())
			gc.setViewSize(gs.getWidth(), gs.getHeight());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setMainActivity();
			return true;
		case R.id.menu_edit:
			setGraphEditActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
