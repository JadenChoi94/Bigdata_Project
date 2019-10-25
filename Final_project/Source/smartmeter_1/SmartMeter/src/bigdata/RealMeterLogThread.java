package bigdata;
import java.io.PrintWriter;

public class RealMeterLogThread extends Thread{
	String date;
	PrintWriter printWriter;
	RealMeterStatus realmeterStatus;

	public RealMeterLogThread( String date, String meterNum, String Macaddr, int Family, PrintWriter printWriter ) {
		this.date = date;
		this.printWriter = printWriter;
		realmeterStatus  = new RealMeterStatus( meterNum, Macaddr, Family );
	}

	@Override    
	public void run() {
		synchronized(printWriter){
			int count = (24 * 60 * 60); //시, 분, 초
			
		//	printWriter.println("Time"+",MeterNum"+",FamCount"+",MacAdd"+",Kwh");
	
			for(int i = 0; i <= count; i += 1) { // 3600초 간격 1시간
				
				printWriter.println(
					date +
					getSecToTime(i)	          + "," + 
					realmeterStatus.getMeterNum() + "," + 
					//realmeterStatus.getFamily()   + "," + 
					realmeterStatus.getMacaddr()  + "," + 
					realmeterStatus.getKw(date)
					);
			}
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
}
