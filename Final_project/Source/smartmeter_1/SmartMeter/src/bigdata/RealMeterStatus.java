package bigdata;
import java.util.Random;

public class RealMeterStatus {
	private String time;
	private String meterNum;
	private double kw;
	private String Macaddr;
	private int Family;
	
	public RealMeterStatus( String meterNum, String Macaddr, int Family ){
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
		if(monthnum == 1 ){
			month_value = 1.11065;
		}
		else if(monthnum == 2){
			month_value = 1.05666;
		}
		else if(monthnum == 3){
			month_value = 1.01700;
		}
		else if(monthnum == 4){
			month_value = 0.98300;
		}
		else if(monthnum == 5){
			month_value = 0.97167;
		}
		else if(monthnum == 6){
			month_value = 0.94051;
		}
		else if(monthnum == 7){
			month_value = 0.95184;
		}
		else if(monthnum == 8){
			month_value = 1.01700;
		}
		else if(monthnum == 9){
			month_value = 1.05666;
		}
		else if(monthnum == 10){
			month_value = 0.96034;
		}
		else if(monthnum == 11){
			month_value = 0.94901;
		}
		else {
			month_value = 1;
		}		
		
//		(표준편차 * r.nextGaussian()) + 평균	
		
		double family_value = 0;
//		double famNo = (double)((Math.abs(rand.nextDouble()*100+1)));
		
		int famNo    = Family;
		
		//1명일때 
        if( famNo == 1 ) {
        	family_value = ((10.16406573*rand.nextGaussian())+206)/(720*3600);
        }
        //2명일때
        else if (famNo == 2){
        	family_value = ((16.39197882*rand.nextGaussian())+289)/(720*3600);
        }
        //3명일때
        else if (famNo == 3){
        	family_value = ((16.88710284*rand.nextGaussian())+321)/(720*3600);
        }
        //4명 일때
        else if (famNo == 4){
        	family_value = ((17.90949469*rand.nextGaussian())+338)/(720*3600);
        }
        //5명 일때
        else if (famNo == 5){
        	family_value = ((19.48348174*rand.nextGaussian())+355)/(720*3600);
        }
        //6명 일때
        else if (famNo == 6){
        	family_value = ((18.54887075*rand.nextGaussian())+392)/(720*3600);
        }
        //7명 일때
        else if (famNo == 7){
        	family_value = ((19.36022164*rand.nextGaussian())+388)/(720*3600);
        }
        //8명 일때
        else if (famNo == 8) {
        	family_value = ((25.99111153*rand.nextGaussian())+464)/(720*3600);
        }
        //9명 일때
        else {
        	family_value = ((74.01822053*rand.nextGaussian())+427)/(720*3600);
        }	
        double KW = Double.parseDouble(String.format("%.7f",(month_value * family_value)));
        return KW;
        
		
//      시간당임
//		return Math.abs( rand.nextDouble()%0.496090121 + 0.271299909 ) * ( month_value + family_value );
	
	}
	

	public void setKw(double kw) {
		this.kw = kw;
	}
}
