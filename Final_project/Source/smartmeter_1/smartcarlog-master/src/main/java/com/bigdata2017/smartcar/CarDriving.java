package com.bigdata2017.smartcar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CarDriving {
	
	private String time;
	private String meterNum;
	private String MacAdd;
	private String UserId;
	private boolean isAdd = false;
	private int areaTypeNum;


	public CarDriving(String meterNum, boolean isAdd, int areaTypeNum ){
		this.meterNum = meterNum;
		this.isAdd = isAdd;
		this.areaTypeNum = areaTypeNum;

	}
	
	public static <E> E getWeightedRandom(Map<E, Double> weights, Random random) { 
		E result = null;  
		double bestValue = Double.MAX_VALUE;    
		for (E element : weights.keySet()) {     
			double value = -Math.log(random.nextDouble()) / weights.get(element);     
			if (value < bestValue) {      
				bestValue = value;       result = element; 
			}  
		}   return result; 
	}

	public int randomRange(int n1, int n2) {  
		return  (int)(Math.random() * (n2 - n1 + 1)) + n1;
	} 
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMeterNum() {
		return meterNum;
	}
	public void setMeterNum(String meterNum) {
		this.meterNum = meterNum;
	}
	
	public String getMacAdd(){
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
	    return MacAdd;
	}  
	public void setMacAdd(String MacAdd){
		this.MacAdd = MacAdd;
	}
	
	 
	public String getUserId(){
	    char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
	    StringBuffer sb = new StringBuffer();
	    Random rn = new Random();
	        
	    for( int i = 0 ; i < 6 ; i++ ){
	        sb.append( charaters[ rn.nextInt( charaters.length ) ] );
	      }
	      return UserId;
	 }
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	public boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	
	public void setAreaTypeNum(int areaTypeNum) {
		this.areaTypeNum = areaTypeNum;
	}
	
	public int getAreaTypeNum() {
		return areaTypeNum;
	}
}
