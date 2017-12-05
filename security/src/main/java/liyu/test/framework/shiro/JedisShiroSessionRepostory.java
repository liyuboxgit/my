package liyu.test.framework.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class JedisShiroSessionRepostory extends JedisPoolManager implements ShiroSessionRepository{

	/** 
     * redis session key前缀 
     */  
    private final String REDIS_SHIRO_SESSION = "shiro-session:";  
  
   
  
    public void saveSession(Session session) {  
        if (session == null || session.getId() == null) {  
            return;  
        }  
        byte[] key = SerializeUtil  
                .serialize(getRedisSessionKey(session.getId()));  
        byte[] value = SerializeUtil.serialize(session);  
        Jedis jedis = this.getJedis(0);  
        try {  
            Long timeOut = session.getTimeout() / 1000;  
            jedis.set(key, value);  
            jedis.expire(key, Integer.parseInt(timeOut.toString()));  
        } catch (JedisException e) {  
            e.printStackTrace();
        } finally {  
            this.releaseJedis(jedis);  
        }  
    }  
  
    public void deleteSession(Serializable id) {  
        if (id == null) {  
           
            return;  
        }  
        Jedis jedis = this.getJedis(0);  
        try {  
            jedis.del(SerializeUtil.serialize(getRedisSessionKey(id)));  
        } catch (JedisException e) {  
            e.printStackTrace();
        } finally {  
            this.releaseJedis(jedis);  
        }  
    }  
  
    public Session getSession(Serializable id) {  
        if (id == null) {  
           
            return null;  
        }  
        Session session = null;  
        Jedis jedis = this.getJedis(0);  
        try {  
            byte[] value = jedis.get(SerializeUtil  
                    .serialize(getRedisSessionKey(id)));  
            session = SerializeUtil.deserialize(value, Session.class);  
        } catch (JedisException e) {  
           e.printStackTrace();
        } finally {  
            this.releaseJedis(jedis);  
        }  
        return session;  
    }  
  
    public Collection<Session> getAllSessions() {  
        Jedis jedis = this.getJedis(0);  
        Set<Session> sessions = new HashSet<Session>();  
        try {  
            Set<byte[]> byteKeys = jedis.keys(SerializeUtil  
                    .serialize(this.REDIS_SHIRO_SESSION + "*"));  
            if (byteKeys != null && byteKeys.size() > 0) {  
                for (byte[] bs : byteKeys) {  
                    Session s = SerializeUtil.deserialize(jedis.get(bs),  
                            Session.class);  
                    sessions.add(s);  
                }  
            }  
        } catch (JedisException e) {  
           e.printStackTrace();
        } finally {  
            this.releaseJedis(jedis);  
        }  
        return sessions;  
    }  
  
    /** 
     * 获取redis中的session key 
     *  
     * @param sessionId 
     * @return 
     */  
    private String getRedisSessionKey(Serializable sessionId) {  
        return this.REDIS_SHIRO_SESSION + sessionId;  
    }  

}
