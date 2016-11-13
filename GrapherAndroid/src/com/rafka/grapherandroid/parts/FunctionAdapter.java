package com.rafka.grapherandroid.parts;

import java.util.List;

import com.rafka.grapherandroid.R;
import com.rafka.grapherandroid.core.Function;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		EditText ftext = (EditText) v.findViewById(R.id.litem_editText);

		ftext.setText(f.getFunction());

		return v;
	}

}
