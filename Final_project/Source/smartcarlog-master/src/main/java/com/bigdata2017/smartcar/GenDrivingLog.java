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
	private static int no = 1;
	public static void main(String[] args) {
		
		PrintWriter printWriter = null;
		
		try 
		{			
			String date =  new SimpleDateFormat( "yyyyMMdd" ).format( new Date( System.currentTimeMillis() ) );	
		
	
			ExecutorService exc = Executors.newFixedThreadPool(100); 		
	
			//printWriter = new PrintWriter( System.out, true );
			String logFile = "D:/Bigdata_Project/Final_project/data/RealTime" + date + ".log";
			printWriter = new PrintWriter( new FileWriter( logFile ), true );
			
				exc.submit( new GenDrivingLogThread( date,  genCustomberNum(),  getMacaddr() , printWriter));
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
	}

	public static int genCustomberNum() {
	
		return  no++  ;
	}

	public static int randomRange(int n1, int n2) {
		return (int)((Math.random() * (n2 - n1 + 1)) + n1);
	}
}