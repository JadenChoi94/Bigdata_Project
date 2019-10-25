package bigdata;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.io.*;


public class RealMeterLog {

	public static void main(String[] args) {
		PrintWriter printWriter = null;

			try {
					int MeterCount = 100;
					String date =  new SimpleDateFormat( "yyyyMMdd" ).format( new Date( System.currentTimeMillis() ) );
					
					if(args != null  && args.length > 1) {
						date = args[0];
					}
					
					if(args != null && args.length > 1) {
						MeterCount = Integer.parseInt(args[1]);
					}
			
					ExecutorService exc = Executors.newFixedThreadPool(MeterCount); 
					int wildDrivercnt = (int)(MeterCount * 0.1);
			
					HashSet<Integer> wildMeterSet = new HashSet<Integer>();
					for(int i=0 ; i < wildDrivercnt; i++) {
						wildMeterSet.add(randomRange(1,MeterCount));
					}
			
					Iterator<Integer> itr = wildMeterSet.iterator();
			
					boolean isWild = false;
					int tmpWildCarNum;
			
					//printWriter = new PrintWriter( System.out, true );	
					
					String logFile = "/home/workspace/smartmeter/working/logs/hour/RealTimeMeter_" + date + ".log";
					printWriter = new PrintWriter( new FileWriter( logFile ), true );
					
					for(int i = 1; i <= MeterCount; i++) {
						
						while(itr.hasNext()) {
							tmpWildCarNum = itr.next();
							
							if( tmpWildCarNum == i ) {
								isWild = true;
								break;
							} else {
								isWild = false;
							}
						}
			
						itr = wildMeterSet.iterator();
						exc.submit( new RealMeterLogThread( date, genMeterId( i ), genMacAdd(), genFamily(i), printWriter ) );
					}
				} catch( IOException e ) {
					e.printStackTrace();
				}
			}

	
	public static int randomRange(int n1, int n2) {
		return (int)((Math.random() * (n2 - n1 + 1)) + n1);
	}

	
	public static int genFamily(int koo) {
		int[][] indat = new int[100][2];     
		int famNum = 0;
        try {
            
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