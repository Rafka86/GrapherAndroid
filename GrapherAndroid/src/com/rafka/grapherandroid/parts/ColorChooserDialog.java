package com.rafka.grapherandroid.parts;

import com.rafka.grapherandroid.MainActivity;
import com.rafka.grapherandroid.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class ColorChooserDialog extends DialogFragment implements OnClickListener {
	MainActivity activity;
	MyColorChooser mChooser;

	public ColorChooserDialog() {
	}

	//Creation Method
	public static ColorChooserDialog CreateDialog(int index, int nowColor) {
		ColorChooserDialog dialog = new ColorChooserDialog();

		Bundle args = new Bundle();
		args.putInt("nowColor", nowColor);
		args.putInt("index", index);

		dialog.setArguments(args);

		return dialog;
	}

	@Override
	public void onActivityCreated(Bundle b) {
		super.onActivityCreated(b);

		Dialog dialog = this.getDialog();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int dialogWidth = (int) (metrics.widthPixels * 0.8f);
		int dialogHeight = (int) (metrics.heightPixels * 0.6f);

		lp.width = dialogWidth;
		lp.height = dialogHeight;
		dialog.getWindow().setAttributes(lp);

		activity = (MainActivity) getActivity();
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
		Bundle bundle = this.getArguments();

		MyColorChooser chooser = (MyColorChooser) content.findViewById(R.id.myColorChooser1);
		chooser.setNowColor(bundle.getInt("nowColor"));
		mChooser = chooser;

		Button negative = (Button) content.findViewById(R.id.dialog_negative);
		Button positive = (Button) content.findViewById(R.id.dialog_positive);
		negative.setOnClickListener(this);
		positive.setOnClickListener(this);

		return content;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_negative:
			this.dismiss();
			break;
		case R.id.dialog_positive:
			int index = getArguments().getInt("index");
			activity.getGrapherCore().getFunction(index).setColor(mChooser.getColor());
			this.dismiss();
			break;
		}
	}
}
