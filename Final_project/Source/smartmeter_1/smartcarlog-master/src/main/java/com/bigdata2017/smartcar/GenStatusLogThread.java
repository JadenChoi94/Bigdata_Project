package com.bigdata2017.smartcar;

import java.io.PrintWriter;

public class GenStatusLogThread extends Thread{
	
	String date;
	PrintWriter printWriter;
	CarStatus carStatus;
	String Macaddr;
	

	public GenStatusLogThread( String date, String meterNum, String Macaddr, PrintWriter printWriter ) {
		this.date = date;
		this.printWriter = printWriter;
		carStatus= new CarStatus( meterNum );
		carStatus= new CarStatus( Macaddr );
	}

	@Override    
	public void run() {
		synchronized(printWriter){
			int count = 24 * 60 * 60;
			
			printWriter.println("SmartMeter Information");
	
			for(int i = 0; i <= count; i += 3600) { // 3600초 간격 1시간
				
				printWriter.println(
					date +
					getSecToTime(i)	  + "," + 
					carStatus.getMeterNum() + "," + 
					carStatus.getMacaddr() + "," +
					carStatus.getKw(date)
					);
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
