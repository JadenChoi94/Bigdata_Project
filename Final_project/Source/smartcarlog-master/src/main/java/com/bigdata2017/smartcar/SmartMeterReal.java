package com.bigdata2017.smartcar;

import java.util.Map;
import java.util.Random;

public class SmartMeterReal
{
	private  String smartmeterno;
	private  int customno; 
	private  double kw;
	private  String macaddr;
	private  Random r =  new Random();
	public SmartMeterReal(){}
	
	public SmartMeterReal(String smartmeterno, int customno, double kw, String macaddr) {
		super();
		this.smartmeterno = smartmeterno;
		this.customno = customno;
		this.kw = kw;
		this.macaddr = macaddr;
	}
	
	public String getSmartmeterno()
	{
		return smartmeterno;
	}
	public void setSmartmeterno(String smartmeterno) {
		this.smartmeterno = smartmeterno;
	}
	public int getCustomno() {
		return customno;
	}
	public void setCustomno(int customno) {
		this.customno = customno;
	}
	
	
	
	public double getKw() { //가중치 추가
		// 계절 월 6,7,8
//		String month =date.substring(4,6);	
//		if(month>= 06 && month < 09) {
//			return  Math.abs( r.nextDouble()%1.28+ 0.4 * 0.55);
//		}				
//		else if( familyno ){
//			
//		}
//		else
			
			// 가구원 수
			// 기기수
			return  Math.abs( r.nextDouble()%1.28+ 0.4 * 0.15);
		
	}
	public void setKw(double kw) {
		this.kw = kw;
	}
	
	
	public String getMacaddr() {
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
		return macaddr;
	}
	
	
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
	
//	public String genMacAdd(){
//	    Random rand = new Random();
//	    byte[] macAddr = new byte[6];
//	    rand.nextBytes(macAddr);
//
//	    macAddr[0] = (byte)(macAddr[0] & (byte)254);  
//
//	    StringBuilder sb = new StringBuilder(18);
//	    for(byte b : macAddr){
//	        if(sb.length() > 0)
//	            sb.append(":");
//	        sb.append(String.format("%02x", b));
//	    }
//
//	    return sb.toString();
//	}
	

	

}
