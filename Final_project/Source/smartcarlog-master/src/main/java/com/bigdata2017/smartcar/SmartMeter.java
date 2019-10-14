package com.bigdata2017.smartcar;


import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

public class SmartMeter {
	
	private Timestamp time;
	private double ee;

	public SmartMeter() {
		Random r =  new  Random();
		time = Timestamp.from( Calendar.getInstance().toInstant() );
		ee = Math.abs( r.nextDouble()%50 + 10); 
	}
	
	public void show() {
		int count = 24 * 60 * 60; //하루
		for(int i = 0; i <= count; i += 1) { //1초씩 오류
			System.out.println(time.toLocaleString() + "," + 
								getSecToTime(i) + "," +
								genHouseId(100) + ","+
								ee + ","+ 
								genSerialNum(4) + "," + 
								genMacAdd()	);
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
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public static String genHouseId(int num) {
		String[] meterNumPrefix = {"A", "B" , "C" , "D" , "E" , "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; 
		String prefixNum = meterNumPrefix[randomRange(0, 25)] ;
		
		DecimalFormat format = new DecimalFormat("0000");
		String meterNum = format.format(num);

		return prefixNum + meterNum;
	}
	
	public static int randomRange(int n1, int n2) {  
		return (int)((Math.random() * (n2 - n1 + 1)) + n1);
	}
	
	public String genSerialNum(int length){
        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        
        for( int i = 0 ; i < length ; i++ ){
            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
        }
        return sb.toString();
    }
	
	public String genMacAdd(){
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



	
