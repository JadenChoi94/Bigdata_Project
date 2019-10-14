package com.bigdata2017.smartcar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenDrivingLog {

	public static void main(String[] args) {
		
		PrintWriter printWriter = null;

		try {
			int carCount = 100;
			String date =  new SimpleDateFormat( "yyyyMMdd" ).format( new Date( System.currentTimeMillis() ) );
			
			if(args != null  && args.length > 1) {
				date = args[0];
			}
			
			if(args != null && args.length > 1) {
				carCount = Integer.parseInt(args[1]);
			}
	
			ExecutorService exc = Executors.newFixedThreadPool(carCount); 
			int wildDrivercnt = (int)(carCount * 0.1);
	
			HashSet<Integer> wildCarSet = new HashSet<Integer>();
			for(int i=0 ; i < wildDrivercnt; i++) {
				wildCarSet.add(randomRange(1,carCount));
			}
	
			Iterator<Integer> itr = wildCarSet.iterator();
	
			boolean isWild = false;
			int tmpWildCarNum;
	
			//printWriter = new PrintWriter( System.out, true );
			String logFile = "./logs/driving/driving.log";
			printWriter = new PrintWriter( new FileWriter( logFile ), true );
			
			for(int i = 1; i <= carCount; i++) {
				
				while(itr.hasNext()) {
					tmpWildCarNum = itr.next();
					
					if( tmpWildCarNum == i ) {
						isWild = true;
						break;
					} else {
						isWild = false;
					}
				}
	
				itr = wildCarSet.iterator();
				exc.submit( new GenDrivingLogThread( date, genCarId( i ), isWild, printWriter ) );
			}
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	public static String genCarId(int num) {
		String[] carNumPrefix = {"A", "B" , "C" , "D" , "E" , "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; 
		String prefixNum = carNumPrefix[randomRange(0, 25)] ;

		DecimalFormat format = new DecimalFormat("0000");
		String carNum = format.format(num);

		return prefixNum + carNum;
	}

	public static int randomRange(int n1, int n2) {
		return (int)((Math.random() * (n2 - n1 + 1)) + n1);
	}
}