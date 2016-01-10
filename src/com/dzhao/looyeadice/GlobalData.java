package com.dzhao.looyeadice;


public class GlobalData {
	
	
	public static int mNumOfDice = 1;
	public static boolean mNumChanged = false;
	public static boolean mCheatEnabled = false;
	public static int mCurrentDiceID = 0;
	
	public static Dice[] mDiceArray={
		new Dice(0),
		new Dice(1),
		new Dice(2),
		new Dice(3),
		new Dice(4),
		new Dice(5)
	};
	
	public static final int[][] mMappingArray = {
		{R.id.image_dice4},
		{R.id.image_dice3,R.id.image_dice5},
		{R.id.image_dice2,R.id.image_dice4,R.id.image_dice6},
		{R.id.image_dice0,R.id.image_dice2,R.id.image_dice6,R.id.image_dice8},
		{R.id.image_dice0,R.id.image_dice2,R.id.image_dice4,R.id.image_dice6,R.id.image_dice8},
		{R.id.image_dice0,R.id.image_dice2,R.id.image_dice3,R.id.image_dice5,R.id.image_dice6,R.id.image_dice8}
	};
	
	

}

class Dice{
	private int mID;
	private int[] mWeight={0,0,0,0,0,0};
	
	public Dice(int ID){
		mID = ID;		
	}
	
	public int getWeight(int index){
		return mWeight[index];
	}
	
	public int getID() {
		return mID;
		
	}
	
	public void setWeight(int index, int value){
		mWeight[index]=value;
	}
}
