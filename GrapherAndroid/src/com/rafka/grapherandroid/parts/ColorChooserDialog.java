package com.rafka.grapherandroid.parts;

import com.rafka.grapherandroid.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ColorChooserDialog extends DialogFragment {
	
	//Creation Method
	public static ColorChooserDialog CreateDialog(int nowColor) {
		ColorChooserDialog dialog = new ColorChooserDialog();
		
		Bundle args = new Bundle();
		args.putInt("nowColor", nowColor);
		
		dialog.setArguments(args);
		
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle b) {
		Dialog dialog = super.onCreateDialog(b);
		
		dialog.setTitle("ColorChooserDialog");
		dialog.setCanceledOnTouchOutside(false);
		
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b) {
		View content = inflater.inflate(R.layout.dialog_cc, null);
		return content;
	}
}
