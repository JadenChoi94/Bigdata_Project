package bigdata;

<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.Map;
>>>>>>> 9e0b151a376803217fc285800e3cf968ba377cc7
import java.util.Random;

public class MeterStatus {
	private String time;
	private String meterNum;
	private double kw;
	private String Macaddr;
	private int Family;
	
	public MeterStatus( String meterNum, String Macaddr, int Family ){
		this.meterNum = meterNum;
		this.Macaddr  = Macaddr;
		this.Family   = Family;
	}	

	public String randomRange(int n1, int n2) {  
		return String.valueOf( (int)(Math.random() * (n2 - n1 + 1)) + n1);
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
	
	
	
	public String getMacaddr() {
		return Macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.Macaddr = macaddr;
	}

	
	public int getFamily() {
		return Family;
	}
	public void setFamily(int family) {
		this.Family = family;
	}
	
	public double getKw( String date ) { //가중치 추가
		Random rand   = new Random();		
		String month  = date.substring(4,5);
		int monthnum  = Integer.parseInt(month);
		double month_value = 0;
		
		//(배율)
		if(monthnum >= 1 && monthnum <= 3){
			month_value = 1.1;
		}
		else if(monthnum >= 4 && monthnum <= 7){
			month_value = 0.96;
		}
		else if(monthnum >= 8 && monthnum <= 9){
			month_value = 1.1;
		}
		else {
			month_value = 0.97;
		}		
//		(표준편차 * r.nextGaussian()) + 평균	
		
		double family_value = 0;
//		double famNo = (double)((Math.abs(rand.nextDouble()*100+1)));
		
		int famNo    = Family;
		
		//1명일때 
        if( famNo == 1 ) {
        	family_value = ((10.16406573*rand.nextGaussian())+206)/720;
        }
        //2명일때
        else if (famNo == 2){
        	family_value = ((16.39197882*rand.nextGaussian())+289)/720;
        }
        //3명일때
        else if (famNo == 3){
        	family_value = ((16.88710284*rand.nextGaussian())+321)/720;
        }
        //4명 일때
        else if (famNo == 4){
        	family_value = ((17.90949469*rand.nextGaussian())+338)/720;
        }
        //5명 일때
        else if (famNo == 5){
        	family_value = ((19.48348174*rand.nextGaussian())+355)/720;
        }
        //6명 일때
        else if (famNo == 6){
        	family_value = ((18.54887075*rand.nextGaussian())+392)/720;
        }
        //7명 일때
        else if (famNo == 7){
        	family_value = ((19.36022164*rand.nextGaussian())+388)/720;
        }
        //8명 일때
        else if (famNo == 8) {
        	family_value = ((25.99111153*rand.nextGaussian())+464)/720;
        }
        //9명 일때
        else {
        	family_value = ((74.01822053*rand.nextGaussian())+427)/720;
        }	
        return month_value * family_value; 
        
		
//      시간당임
//		return Math.abs( rand.nextDouble()%0.496090121 + 0.271299909 ) * ( month_value + family_value );
	
	}
	

	public void setKw(double kw) {
		this.kw = kw;
	}
}
