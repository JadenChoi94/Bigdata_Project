package com.bigdata2017.smartcar;

import java.io.PrintWriter;

public class GenDrivingLogThread extends Thread{

	private String date;
	PrintWriter printWriter;
	CarDriving carDriving;

	public GenDrivingLogThread( String date, String carNum, boolean isAdd, PrintWriter printWriter) {
		this.date = date;
		carDriving = new CarDriving(carNum, isAdd, randomRange(0,5));
		this.printWriter = printWriter;
	}
	

	@Override    
	public void run() {
			int count = 24 * 60 * 60;
	
//			printWriter.println( "Driver Status Infomation,CarNum,AccStep,BrkStep,WheelStep,DirLightStep,Speed,AreaNum" );
			
			try {
				for(int i = 0; i <= count; i += 1) { // 1초 간격
					
					synchronized( printWriter ){
						printWriter.println(
							date +
							getSecToTime(i)				 + "," +
							carDriving.getCarNum()		 + "," +
							carDriving.getAccStep()		 + "," +
							carDriving.getBrkStep()		 + "," +
							carDriving.getWheelStep()	 + "," +
							carDriving.getDirLightStep() + "," +
							carDriving.getSpeed()		 + "," +
							carDriving.getAreaNum() );
					}
					
					sleep(1 * 100);
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public String getSecToTime(int inSec) {
		String time = String.valueOf(inSec/3600);
		if(time.length() == 1) time = "0" + time;
		String min = String.valueOf(inSec%3600/60);
		if(min.length() == 1) min = "0" + min;
		String sec = String.valueOf(inSec%3600%60%60);
		if(sec.length() == 1) sec = "0" + sec;
		return time+min+sec;
	}
	
	public int randomRange(int n1, int n2) {  
		return  (int)(Math.random() * (n2 - n1 + 1)) + n1;
	} 
}
