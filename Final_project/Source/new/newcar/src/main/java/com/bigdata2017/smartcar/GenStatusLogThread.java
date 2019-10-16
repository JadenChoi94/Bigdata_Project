package com.bigdata2017.smartcar;

import java.io.PrintWriter;

public class GenStatusLogThread extends Thread{
	
	String date;
	PrintWriter printWriter;
	CarStatus carStatus;

	public GenStatusLogThread( String date, String carNum, PrintWriter printWriter ) {
		this.date = date;
		this.printWriter = printWriter;
		carStatus= new CarStatus( carNum );
	}

	@Override    
	public void run() {
		synchronized(printWriter){
			int count = 24 * 60 * 60;
			
			printWriter.println("SmartCar Status Information" + ",CarNum" + ",TireFL" + ",TireFR" + ",TireBL" + ",TireBR" + ",LightFL" + ",LightFR" + ",LightBL" + ",LightBR" + ",EngineInfo" + ",BreakInfo" + ",BatteryInfo");
	
			for(int i = 0; i <= count; i += 3) { // 3초 간격
				
				printWriter.println(
					date +
					getSecToTime(i)	  + "," + 
					carStatus.getCarNum() + "," +
					carStatus.getTireFL() + "," + 
					carStatus.getTireFR() + "," + 
					carStatus.getTireBL() + "," +
					carStatus.getTireBR() + "," +
					carStatus.getLightFL() + "," +
					carStatus.getLightFR() + "," +
					carStatus.getLightBL() + "," +
					carStatus.getLightBR() + "," +
					carStatus.getEngineInfo() + "," +
					carStatus.getBreakInfo() + ","+
					carStatus.getBatteryInfo());
			}
		}
	}

	public String getSecToTime(int inSec) {

		String time = String.valueOf(inSec/3600);
	
		if(time.length() == 1){
			time = "0" + time;
		}
		String min = String.valueOf(inSec%3600/60);
		
		if(min.length() == 1){
			min = "0" + min;
		}
		
		String sec = String.valueOf(inSec%3600%60%60);
		if(sec.length() == 1){
			sec = "0" + sec;
		}

		return time + min + sec;
	}
}
