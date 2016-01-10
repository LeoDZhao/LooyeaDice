package com.dzhao.looyeadice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private static int[] mDiceViewIDs = {
		R.id.image_dice0,
		R.id.image_dice1,
		R.id.image_dice2,
		R.id.image_dice3,
		R.id.image_dice4,
		R.id.image_dice5,
		R.id.image_dice6,
		R.id.image_dice7,
		R.id.image_dice8
	};

	public ImageView[] mDiceViews;
	
	private static MyOnTouchListener mOnTouchListener=null;
	
	private SensorManager mSensorManager;
	private Vibrator mVibrator;
	private static final int SENSOR_SHAKE = 10;
	
	private  GestureDetector gd;
	
	public ArrayList<Bitmap> mBitmaps;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		mDiceViews = new ImageView[9];
		for (int i = 0; i < mDiceViewIDs.length; i++) {
			mDiceViews[i]= (ImageView) findViewById(mDiceViewIDs[i]);
		}
		
		//Store bitmaps
		mBitmaps = new ArrayList<Bitmap>();
		AssetManager assetManager = getAssets();
		for (int i = 1; i <= 6; i++) {
			InputStream isDice=null;
			try {
				isDice = assetManager.open("dice" + i + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}      
		        
			Bitmap bitmapDice=BitmapFactory.decodeStream(isDice);
			
			mBitmaps.add(bitmapDice);
		}
		
		init();

	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		if(GlobalData.mNumChanged){
			GlobalData.mNumChanged=false;
			initDices();
			
		}
	}



	@Override
	protected void onResume() {
        if (mSensorManager != null) {
        	mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
		super.onResume();
	}



	@Override
	protected void onPause() {
        if (mSensorManager != null) {
        	mSensorManager.unregisterListener(sensorEventListener);
        }
		super.onPause();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_settings){
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void init(){
		AssetManager assetManager = getAssets();
		
		//Background
		InputStream isBack=null;		
		try {
			isBack = assetManager.open("background.png");
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		ImageView backGround = (ImageView) findViewById(R.id.image_background);
        Bitmap bitmapBackGround=BitmapFactory.decodeStream(isBack);        
        backGround.setImageBitmap(bitmapBackGround);        
        backGround.setScaleType(ScaleType.CENTER_CROP); 
		
        //Dices
        initDices();
			
        //TouchListener
        mOnTouchListener=new MyOnTouchListener(this);
//        backGround.setOnTouchListener(mOnTouchListener);
        
        gd = new GestureDetector(this, new OnGesture());
        
        //Vibrator
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (mSensorManager != null) {
        	mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
        
	}
	
	private SensorEventListener sensorEventListener = new SensorEventListener() {
		 
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            int medumValue = 19;
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                mVibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }
 
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
 
        }
    };
    
    //Handler for shaking
    private static Handler handler = new Handler() {
 
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case SENSOR_SHAKE:
            	if (mOnTouchListener!=null) {
					mOnTouchListener.changeLogic();
				}
                break;
            }
        }
 
    };
	
	private void initDices(){
		
		//Set all invisible first
		for (int i = 0; i < mDiceViewIDs.length; i++) {
			mDiceViews[i].setVisibility(View.INVISIBLE);
		}
		
		ImageView dice=null;
		Random random = new Random();
		for (int i = 0; i < GlobalData.mNumOfDice; i++) {
			
			int num = (int) (random.nextFloat() * 6) + 1;
		
	        dice = (ImageView) findViewById(GlobalData.mMappingArray[GlobalData.mNumOfDice-1][i]);
	        dice.setImageBitmap(mBitmaps.get(num-1));        
	        dice.setScaleType(ScaleType.CENTER_INSIDE); 
	        dice.setVisibility(View.VISIBLE);
		}
	}

	private class OnGesture extends GestureDetector.SimpleOnGestureListener{

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			GlobalData.mCheatEnabled=!GlobalData.mCheatEnabled;
			Toast toast = null;
			if (GlobalData.mCheatEnabled) {
				toast = Toast.makeText(MainActivity.this, R.string.text_cheat_on, Toast.LENGTH_SHORT);
				
			}
			else {
				toast = Toast.makeText(MainActivity.this, R.string.text_cheat_off, Toast.LENGTH_SHORT);
			}
			
			toast.setGravity(Gravity.BOTTOM, 0, 0);
			
			toast.show();
			
			return super.onDoubleTap(e);
		}


		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			mOnTouchListener.changeLogic();
			return super.onSingleTapConfirmed(e);
		}
		
		
		
	}
	
	 @Override
    public boolean onTouchEvent(MotionEvent event) {
         return gd.onTouchEvent(event);
     }
	 
	

}
