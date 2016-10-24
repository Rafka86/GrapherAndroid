package com.rafka.grapherandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
	GraphSheet gs;
	EditText et1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et1 = (EditText) findViewById(R.id.editText1);
		gs = (GraphSheet) findViewById(R.id.graphSheet1);
		gs.setEditText(et1);
	}
}
