package redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisClient extends Thread{

	private String key;
	private Jedis jedis;


	public RedisClient(String k) {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool jPool = new JedisPool(jedisPoolConfig, "server02.hadoop.com", 6379);
		jedis = jPool.getResource();

		this.key = k;
		

	}

	@Override    
	public void run() {
		

		Set<String> overWattMeterList = null;
		
		int cnt = 1;


		try {
			while(true) {

				overWattMeterList = jedis.smembers(key);

				System.out.println("################################################");
				System.out.println("#####   Start of The OverSpeed SmartCar    #####");
				System.out.println("################################################");
				
				
				System.out.println("\n[ Try No." + cnt++ + "]");

				if(overWattMeterList.size() > 0) {
					for (String list : overWattMeterList) {
						System.out.println(list);
					}
					System.out.println("");
					
					jedis.del(key);
				}else{
					System.out.println("\nEmpty Meter List...\n");
				}

				System.out.println("################################################");
				System.out.println("######   End of The OverWatt SmartMeter    ######");
				System.out.println("################################################");
				System.out.println("\n\n");

				Thread.sleep(10 * 1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( jedis != null ) jedis.close();
		}
	}
}
