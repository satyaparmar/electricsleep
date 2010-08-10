package com.androsz.electricsleep.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarPreference extends DialogPreference {

	private final Context context;
	private SeekBar seekBar;
	private TextView textView;

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			persistInt(seekBar.getProgress());
		}
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return (int) a.getInt(index, 0);
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		int temp = restoreValue ? getPersistedInt(0) : (Integer) defaultValue;
		if (!restoreValue)
			persistInt(temp);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {

		final LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setMinimumWidth(400);
		layout.setPadding(20, 20, 20, 20);

		textView = new TextView(context);
		textView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		syncTextViewText(getPersistedInt(0));
		textView.setPadding(5, 5, 5, 5);
		layout.addView(textView);

		seekBar = new SeekBar(context);
		seekBar.setMax(100);
		seekBar.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		seekBar.setProgress(getPersistedInt(0));
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				syncTextViewText(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		layout.addView(seekBar);

		builder.setView(layout);
		builder.setTitle(getTitle());

		super.onPrepareDialogBuilder(builder);
	}

	private void syncTextViewText(int progress) {
		textView.setText("" + progress);
	}
}