package com.dzhao.looyeadice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	private View mCheatLayout;
	private View mAboutView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_settings);
		
		
		//Set dice number
		Spinner spinnerNum = (Spinner) findViewById(R.id.spinner_number);
		spinnerNum.setSelection(GlobalData.mNumOfDice-1);
		spinnerNum.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int originalNum = GlobalData.mNumOfDice;
				GlobalData.mNumOfDice = arg2 + 1;
				if (originalNum!=GlobalData.mNumOfDice) {
					GlobalData.mNumChanged = true;	
					
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Switch switchCheat = (Switch) findViewById(R.id.switch_cheat);
		
		//Tricky text
		mAboutView = findViewById(R.id.layout_about);
		mAboutView.setVisibility(View.VISIBLE);
		mAboutView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				mAboutView.setVisibility(View.GONE);
				mCheatLayout.setVisibility(View.VISIBLE);
				switchCheat.setChecked(GlobalData.mCheatEnabled);
				return false;
			}
		});
				
		
		//Set cheat mode
		mCheatLayout = findViewById(R.id.layout_probability);
		mCheatLayout.setVisibility(View.INVISIBLE);
		
		switchCheat.setChecked(GlobalData.mCheatEnabled);		
		switchCheat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if (isChecked) {
					GlobalData.mCheatEnabled = true;
				}
				else {
					GlobalData.mCheatEnabled = false;
				}
				
			}
		});
		
		
		SeekBar seekBar0 = (SeekBar) findViewById(R.id.seekBar0);
		SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		SeekBar seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
		SeekBar seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
		
		final SeekBar[] seekBars = {seekBar0, seekBar1, seekBar2, seekBar3, seekBar4, seekBar5};
		
		//Set current dice ID
		Spinner spinnerDice = (Spinner) findViewById(R.id.spinner_dice);
		spinnerDice.setSelection(GlobalData.mCurrentDiceID);
		
		spinnerDice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				GlobalData.mCurrentDiceID = arg2;
				
				for (int i = 0; i < seekBars.length; i++) {
					
					seekBars[i].setProgress(GlobalData.mDiceArray[arg2].getWeight(i));
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		
		//Set number weight for each dice
		seekBar0.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight0);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(0, progress);
				
			}
		});
		
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight1);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(1, progress);
				
			}
		});
		
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight2);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(2, progress);
				
			}
		});
		
		seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight3);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(3, progress);
				
			}
		});
		
		seekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight4);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(4, progress);
				
			}
		});
		
		seekBar5.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView textWeight = (TextView) findViewById(R.id.text_weight5);
				String progressText = Integer.toString(progress);
				textWeight.setText(progressText);		
				
				
				GlobalData.mDiceArray[GlobalData.mCurrentDiceID].setWeight(5, progress);
				
			}
		});
		
		
			
		
	}
	
	@Override
	protected void onStart() {
		mAboutView.setVisibility(View.VISIBLE);
		mCheatLayout.setVisibility(View.INVISIBLE);
		super.onStart();
		

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	



}
