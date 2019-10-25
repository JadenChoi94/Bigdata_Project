package bigdata;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import bigdata.GenMeterLogThread;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenMeterLog {

	public static void main(String[] args) {
		
		PrintWriter printWriter = null;
		
		try {
			int meterCount = 100;
			String date =  new SimpleDateFormat( "yyyyMMdd" ).format( new Date( System.currentTimeMillis() ) );
	
//			if(args != null  && args.length > 1) {
//				date = args[0];
//			}
//			if(args != null && args.length > 1) {
//				meterCount = Integer.parseInt(args[1]);
//			}
			//D:\Bigdata_Project\Final_project\data
			//"/home/workspace/smartmeter/working/logs/hour/MeterStatus_" + date + ".log";
			
			String logFile = "/home/workspace/smartmeter/working/logs/hour/MeterStatus_" + date + ".log";
			printWriter = new PrintWriter( new FileWriter( logFile ), true );
			
			ArrayList<Thread> threads = new ArrayList<Thread>();			
			
			for(int i = 1; i <= meterCount; i++) {				
				Thread t = new GenMeterLogThread( date, genMeterId( i ), genMacAdd(), genFamily(i), printWriter );
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
	
	
	
	public static int genFamily(int koo) {
		int[][] indat = new int[100][2];     
		int famNum = 0;
        try {
        	
            //jar로 만들기 전에 경로변경!!!
        	
            File csv = new File("/home/workspace/smartmeter/working/domain.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            int row =0 ,i; 
            while ((line = br.readLine()) != null) {
            	
                String[] token = line.split(",", -1);
                for(i=1;i<2;i++)    indat[row][i] = Integer.parseInt(token[i]);                               
                row++;
            }
            br.close(); 
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        famNum = indat[koo-1][1];  
        
	    return famNum;       
	}
        
	
	public static String genMeterId(int num) { 
		String prefixNum = ("H");
		DecimalFormat format = new DecimalFormat("000");
		String meterNum = format.format(num);

		return prefixNum + meterNum;
	}
	
	public static String genMacAdd() {
	    Random rand = new Random();
	    
	    byte[] macAddr = new byte[6];
	    rand.nextBytes(macAddr);

	    macAddr[0] = (byte)(macAddr[0] & (byte)254);  

	    StringBuilder sb = new StringBuilder(18);
	    for(byte b : macAddr){
	        if(sb.length() > 0)
	            sb.append(":");
	        sb.append(String.format("%02x", b));
	    }
	   
	    return sb.toString();
	}

}
