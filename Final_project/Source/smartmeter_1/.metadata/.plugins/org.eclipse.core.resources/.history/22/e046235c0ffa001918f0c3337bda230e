package storm;


import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.apache.storm.guava.collect.Maps;
import org.apache.storm.redis.common.config.JedisPoolConfig;

import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.Scheme;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;


public class SmartCarDriverTopology {


	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException, IOException {  

		StormTopology topology = makeTopology();

		Map<String, String> HBaseConfig = Maps.newHashMap();
		HBaseConfig.put("hbase.rootdir","hdfs://server01.hadoop.com:8020/hbase");

		Config config = new Config();
		config.setDebug(true);
		config.put("HBASE_CONFIG",HBaseConfig);

		config.put(Config.NIMBUS_HOST, "server02.hadoop.com");
		config.put(Config.NIMBUS_THRIFT_PORT, 6627);
		config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
		config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList("server02.hadoop.com"));

		StormSubmitter.submitTopology(args[0], config, topology);

	}  



	private static StormTopology makeTopology() {

		String zkHost = "server02.hadoop.com:2181";
		TopologyBuilder driverCarTopologyBuilder = new TopologyBuilder();
		
		// Spout Bolt
		BrokerHosts brkBost = new ZkHosts(zkHost);
		String topicName = "SmartMeter-Topic";
		String zkPathName = "/SmartMeter-Topic";
		
		SpoutConfig spoutConf = new SpoutConfig(brkBost, topicName, zkPathName, UUID.randomUUID().toString());
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConf);
		
		RealtimeMeterTopologyBuilder.setSpout("kafkaSpout", kafkaSpout, 1);
		
		
		// Grouping - SplitBolt & EsperBolt
		RealtimeMeterTopologyBuilder.setBolt("splitBolt", new SplitBolt(),1).allGrouping("kafkaSpout");
		RealtimeMeterTopologyBuilder.setBolt("esperBolt", new EsperBolt(),1).allGrouping("kafkaSpout");
		
		
		// HBase Bolt
		TupleTableConfig hTableConfig = new TupleTableConfig("SmartMeterInfo", "r_key");
		hTableConfig.setZkQuorum("server02.hadoop.com");
		hTableConfig.setZkClientPort("2181");
		hTableConfig.setBatch(false);
		
		hTableConfig.addcolumn("cf1", "date");
		hTableConfig.addcolumn("cf1", "user_id");
		hTableConfig.addcolumn("cf1", "mac");
		hTableConfig.addcolumn("cf1", "family");
		hTableConfig.addcolumn("cf1", "elec");
		
		HBaseBolt hbaseBolt = new HBaseBolt(hTableConfig);
		RealtimeMeterTopologyBuilder.setBolt("HBASE", hbaseBolt, 1).shuffleGrouping("splitBolt");
		
		
		// Redis Bolt
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig.Builder().setHost("server02.hadoop.com").setPort(6379).build();
		RedisBolt redisBolt = new RedisBolt(jedisPoolConfig);
		
		driverCarTopologyBuilder.setBolt("REDIS", redisBolt, 1).shuffleGrouping("esperBolt");

		return driverCarTopologyBuilder.createTopology();
	}

}  
