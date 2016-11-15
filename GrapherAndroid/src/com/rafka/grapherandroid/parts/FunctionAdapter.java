package com.rafka.grapherandroid.parts;

import java.util.List;

import com.rafka.grapherandroid.R;
import com.rafka.grapherandroid.core.Function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class FunctionAdapter extends ArrayAdapter<Function> {
	private LayoutInflater inflater;

	private int viewResourceId;
	private List<Function> functions;

	public FunctionAdapter(Context context, int resource, List<Function> objects) {
		super(context, resource, objects);
		viewResourceId = resource;
		functions = objects;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;

		if (convertView != null)
			v = convertView;
		else
			v = inflater.inflate(viewResourceId, null);

		Function f = functions.get(position);
		CheckBox visCBox = (CheckBox) v.findViewById(R.id.litem_checkBox);
		EditText fText = (EditText) v.findViewById(R.id.litem_editText);
		Button delButton = (Button) v.findViewById(R.id.litem_delbutton);

		visCBox.setChecked(f.isVisible());
		visCBox.setOnCheckedChangeListener((FunctionList) parent);
		visCBox.setTag(position);
		fText.setTag(position);
		fText.setOnEditorActionListener((FunctionList) parent);
		fText.setText(f.getFunction());
		delButton.setTag(position);
		delButton.setOnClickListener((FunctionList) parent);

		return v;
	}

}
