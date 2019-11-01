package storm;

import org.apache.storm.redis.bolt.AbstractRedisBolt;
import org.apache.storm.redis.common.config.JedisClusterConfig;
import org.apache.storm.redis.common.config.JedisPoolConfig;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public  class RedisBolt extends AbstractRedisBolt {

	private static final long serialVersionUID = 1L;

	public RedisBolt(JedisPoolConfig config) {
		super(config);
	}

	public RedisBolt(JedisClusterConfig  config) {
		super(config);
	}


	@Override
	public void execute(Tuple input) {

		String date = input.getStringByField("date");
		String user_id = input.getStringByField("user_id");

		JedisCommands jedisCommands = null;

		try {

			jedisCommands = getInstance();
			jedisCommands.sadd(date, user_id);
			
			jedisCommands.expire(date, 604800);

		} catch (JedisConnectionException e) {
			throw new RuntimeException("Exception occurred to JedisConnection", e);
		} catch (JedisException e) {
			System.out.println("Exception occurred from Jedis/Redis" + e);
		} finally {

			if (jedisCommands != null) {
				returnInstance(jedisCommands);
			}
			this.collector.ack(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
}