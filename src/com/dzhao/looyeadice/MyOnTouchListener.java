package com.dzhao.looyeadice;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MyOnTouchListener implements OnTouchListener {
	
	private final static int DURATION = 300;	
	
	private Activity mActivity;
	private ImageView[] mDiceImageViews;
	private SoundPool mSoundPool;
	private int mEffectSoundID;
	private boolean mIsLastAnimation;
	
	public MyOnTouchListener(Context context) {
		mActivity = (Activity)context;
		mDiceImageViews = ((MainActivity)mActivity).mDiceViews;
		
		mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mEffectSoundID = mSoundPool.load(context, R.raw.dice, 1);

	}
	
	 
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		changeLogic();
		
		return false;		

		
	}
	
	public void changeLogic(){
		Animation disappAnimation1 = AnimationUtils.loadAnimation(mActivity, R.anim.scale2small);
		Animation disappAnimation2 = AnimationUtils.loadAnimation(mActivity, R.anim.rotate2small);
		Animation appAnimation1 = AnimationUtils.loadAnimation(mActivity, R.anim.scale2big);
		Animation appAnimation2 = AnimationUtils.loadAnimation(mActivity, R.anim.rotate2big);
		
		disappAnimation1.setDuration(DURATION);
		disappAnimation2.setDuration(DURATION);
		appAnimation1.setDuration(DURATION);
		appAnimation2.setDuration(DURATION);
		
		appAnimation1.setStartOffset(DURATION);
		appAnimation2.setStartOffset(DURATION);				
		
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
		
		animationSet.addAnimation(disappAnimation1);
		animationSet.addAnimation(disappAnimation2);
		animationSet.addAnimation(appAnimation1);
		animationSet.addAnimation(appAnimation2);
		
		disappAnimation1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if (mIsLastAnimation) {
					changeDice(GlobalData.mNumOfDice);
				}
				
			}
		});
		
		playAnimation(GlobalData.mNumOfDice, animationSet);
		
		//Voice effect
		AudioManager audioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
		float currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = currentVolume/maxVolume;
		mSoundPool.play(mEffectSoundID, volume, volume, 0, 0, 1);
	}

	
	private void playAnimation(int diceNum, AnimationSet animationSet) {
		
		int[][] positionMapping = {
				{4},
				{3,5},
				{2,4,6},
				{0,2,6,8},
				{0,2,4,6,8},
				{0,2,3,5,6,8}				
		};
		
		int[] tempArray = positionMapping[diceNum-1];
		
		mIsLastAnimation = false;
		for (int i = 0; i < tempArray.length; i++) {
			if (i==tempArray.length-1) {
				mIsLastAnimation = true;
			}
			mDiceImageViews[tempArray[i]].startAnimation(animationSet);
		}
		
	}

	private void changeDice(int diceNum) {
		
		int[] viewIDs = GlobalData.mMappingArray[diceNum-1];
		
		ImageView dice = null;
		Random random = new Random();
		for (int k = 0; k < viewIDs.length; k++) {

			
			int num = (int) (random.nextFloat() * 6) + 1;
			
			if (GlobalData.mCheatEnabled) {
				int totalWeight = 0;
				ArrayList<Integer> numArray = new ArrayList<Integer>();
				for (int i = 0; i < 6; i++) {
					int weight = GlobalData.mDiceArray[k].getWeight(i);
					
					for (int j = 0; j < weight; j++) {
						numArray.add(i+1);
					}
					
					totalWeight += weight;
					
				}
				
				if (totalWeight>0 && totalWeight!=60) {
					
					num = numArray.get((int) (random.nextFloat() * numArray.size()));
				}
				
			}

	        
	        dice = (ImageView) mActivity.findViewById(viewIDs[k]);
	        dice.setImageBitmap(((MainActivity)mActivity).mBitmaps.get(num-1));  
        
		}
		
	}



}
