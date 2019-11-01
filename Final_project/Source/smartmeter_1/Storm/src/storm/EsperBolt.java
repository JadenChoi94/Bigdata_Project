package storm;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;


public class EsperBolt extends BaseBasicBolt{

	private static final long serialVersionUID = 1L;

	private EPServiceProvider espService;

	private boolean isOverSpeedEvent = false;

	public void prepare(Map stormConf, TopologyContext context) {

		Configuration configuration = new Configuration();
		configuration.addEventType("DriverCarInfoBean", DriverCarInfoBean.class.getName());

		espService = EPServiceProviderManager.getDefaultProvider(configuration);
		espService.initialize();
		
		int avgOverWatt = 0.2  # 일단 임의로 정한거임
		int windowTime = 1800  # 30분
		
		String overWattEpl = "SELECT date, user_id, mac, family, elec"
		               + "FROM SmartMeterInfoBean.win:time_batch("+windowTime+" sec)"
		               + "GROUP BY user_id HAVING AVG(elec) >" + avgOverWatt;

		EPStatement smartMeterinfoStmt = espService.getEPAdministrator().createEPL(overWattEpl);

		smartMeterinfoStmt.addListener((UpdateListener) new OverWattEventListener());
	}



	public void execute(Tuple tuple, BasicOutputCollector collector) {

		// TODO Auto-generated method stub
		String tValue = tuple.getString(0); 

		//諛쒖깮�씪�떆(14�옄由�), 李⑤웾踰덊샇, 媛��냽�럹�떖, 釉뚮젅�씠�겕�럹�떖, �슫�쟾���쉶�쟻媛�, 諛⑺뼢吏��떆�벑, 二쇳뻾�냽�룄, 裕ㅼ쭅踰덊샇
		String[] receiveData = tValue.split("\\,");

		DriverCarInfoBean driverCarInfoBean =new DriverCarInfoBean();

		driverCarInfoBean.setDate(receiveData[0]);
		driverCarInfoBean.setCarNumber(receiveData[1]);
		driverCarInfoBean.setSpeedPedal(receiveData[2]);
		driverCarInfoBean.setBreakPedal(receiveData[3]);
		driverCarInfoBean.setSteerAngle(receiveData[4]);
		driverCarInfoBean.setDirectLight(receiveData[5]);
		driverCarInfoBean.setSpeed(Integer.parseInt(receiveData[6]));
		driverCarInfoBean.setAreaNumber(receiveData[7]);

		espService.getEPRuntime().sendEvent(driverCarInfoBean); 


		if(isOverSpeedEvent) {
			//諛쒖깮�씪�떆(14�옄由�), 李⑤웾踰덊샇
			collector.emit(new Values(	driverCarInfoBean.getDate().substring(0,8), 
										driverCarInfoBean.getCarNumber()+"-"+driverCarInfoBean.getDate()));
			isOverSpeedEvent = false;
		}

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("date", "car_number"));
	}

	// 에스퍼 Bolt 소스2 - EsperBolt.java의 에스퍼 이벤트 함수
	private class OverWattEventListener implements UpdateListener
	{
		@Override
		public void update(EventBean[] newEvents, EventBean[] oldEvents) {
			if (newEvents != null) {
				try {
					isOverWattEvent = true;
				} catch (Exception e) {
					System.out.println("Failed to Listener Update" + e);
				} 
			}
		}
	}

}