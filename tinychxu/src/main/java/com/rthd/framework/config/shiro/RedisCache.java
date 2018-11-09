package com.rthd.framework.config.shiro;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.CacheException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class RedisCache<K, V>{
	
	private JedisPoolManager pm;
	
	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		if (key == null) {
			return null;
		}
		V v = null;
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			if(value!=null) {				
				v = (V)SerializeUtil.deserialize(value).readObject();
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return v;
	}
	@Deprecated
	public V put(K key, V value) throws CacheException {
		if (key == null) {
			return null;
		}
		
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] keys = SerializeUtil.serialize(key);
			byte[] values = SerializeUtil.serialize(value);
			jedis.set(keys, values);
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return null;
	}
	
	public V put(K key, V value, int expt) throws CacheException {
		if (key == null) {
			return null;
		}
		
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] keys = SerializeUtil.serialize(key);
			byte[] values = SerializeUtil.serialize(value);
			jedis.setex(keys, expt, values);
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public V remove(K key) throws CacheException {
		if (key == null) {
			return null;
		}
		V v = null;
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			v = (V)SerializeUtil.deserialize(value).readObject();
			if(v != null) {
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return v;
	}

	public void clear() throws CacheException {
		
	}

	public int size() {
		return 0;
	}

	public Set<K> keys() {
		return null;
	}

	public Collection<V> values() {
		return null;
	}
	
	public void setJedisPoolManager(JedisPoolManager jedisPoolManager) {
		this.pm = jedisPoolManager;
	}
	
}

