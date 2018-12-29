package liyu.test.springbootMybatis.mybatis.jdbc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import liyu.test.springbootMybatis.mybatis.Conf;
import liyu.test.springbootMybatis.mybatis.JdkSerializeUtil;
import liyu.test.springbootMybatis.mybatis.JedisPoolManager;
import redis.clients.jedis.Jedis;

@Service
public class RedisCacheImpl implements RedisCache,InitializingBean  {
	private JedisPoolManager pm = new JedisPoolManager(Conf.get().getProperty("redis.host"), Conf.get().getProperty("redis.port"));
	public Long putObject(String uuid, Object key, Object value) {
		Jedis redisClient = pm.getJedis(0);
		try {
			return redisClient.hset(uuid.getBytes(), JdkSerializeUtil.serialize(key), JdkSerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.releaseJedis(redisClient);
		}
	}

	public Object getObject(String uuid, Object key) {
		Jedis redisClient = pm.getJedis(0);
		try {
			return JdkSerializeUtil.deserialize(redisClient.hget(uuid.getBytes(), JdkSerializeUtil.serialize(key)),
					Object.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.releaseJedis(redisClient);
		}
	}
	
	public void clear(Class<?> type) {
		Jedis redisClient = pm.getJedis(0);
		try {
			for(String elem : JdbdJoinCachedAspect.link.get(type)) {				
				redisClient.del(elem.getBytes());
				redisClient.hset(elem.getBytes(), "".getBytes(), "".getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(redisClient);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
