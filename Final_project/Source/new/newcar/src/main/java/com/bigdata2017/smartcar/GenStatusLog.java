package com.bigdata2017.smartcar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GenStatusLog {

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
			
			String logFile = "./logs/status/status_" + date + ".log";
			printWriter = new PrintWriter( new FileWriter( logFile ), true );
			
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for(int i = 1; i <= carCount; i++) {
				Thread t = new GenStatusLogThread( date, genCarId( i ), printWriter );
		        t.start();
		        threads.add(t);
			}
	
			for(Thread thread : threads){
				thread.join();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( printWriter != null ) {
				printWriter.close();
				System.out.println( "finished" );
			}
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
