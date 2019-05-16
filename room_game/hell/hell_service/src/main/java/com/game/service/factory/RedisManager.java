package com.game.service.factory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;
import java.util.Map.Entry;

@Service("redisManager")
public class RedisManager {

    @Value("${spring.redis.timeout}")
    private int redisTimeOut;

    /**
     * 非切片链接池
     */
    @Autowired
    public JedisPool jedisPool;

    public void close(Jedis jedis) {
        if (null != jedis)
            jedis.close();
    }

    public String bRPopValue(String name) {
        Jedis jedis = null;
        String data = null;
        try {
            jedis = jedisPool.getResource();
            List<String> list = jedis.brpop(redisTimeOut, name);
            if (list != null && !list.isEmpty()) {
                data = list.get(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return data;
    }

    public boolean lpush(String name, Object object) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(name, JSONObject.toJSONString(object));
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public String hMGet(String key, String name) {

        Jedis jedis = null;
        String value = "";
        try {
            jedis = jedisPool.getResource();
            List<String> list = jedis.hmget(key, name);
            if (list != null) {
                value = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e);
        } finally {
            close(jedis);
        }
        return value;
    }

    public boolean hDel(String key, String name) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, name);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public boolean hMSet(String key, Map<String, Object> map) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Map<String, String> m = new HashMap<String, String>();
            for (Entry<String, Object> entry : map.entrySet()) {
                String mk = entry.getKey();
                String mv = entry.getValue().toString();
                m.put(mk, mv);
            }
            jedis.hmset(key, m);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public boolean hMSetTtl(String key, Map<String, Object> map, int seconds) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Map<String, String> m = new HashMap<String, String>();
            for (Entry<String, Object> entry : map.entrySet()) {
                String mk = entry.getKey();
                String mv = entry.getValue().toString();
                m.put(mk, mv);
            }
            jedis.hmset(key, m);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public long llen(String key) {
        Jedis jedis = null;
        long len = 0L;
        try {
            jedis = jedisPool.getResource();
            len = jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return len;
    }

    public boolean expire(String key, int seconds) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, seconds);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public Long ttl(String key) {
        Jedis jedis = null;
        Long timeout = null;
        try {
            jedis = jedisPool.getResource();
            timeout = jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
            timeout = null;
        } finally {
            close(jedis);
        }
        return timeout;
    }

    public Map<String, String> hGetAll(String key) {
        Jedis jedis = null;
        Map<String, String> map = null;
        try {
            jedis = jedisPool.getResource();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return map;
    }

    public boolean delete(String[] keys) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public boolean delete(String keys) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public boolean batchDel(String value) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();
            Response<Set<String>> set = pp.keys(value);
            pp.sync();
            Iterator<String> it = set.get().iterator();
            pp = jedis.pipelined();
            while (it.hasNext()) {
                String key = it.next();
                pp.del(key);
            }
            pp.sync();
            pp.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public boolean batchDelByList(String name, List<String> values) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();
            pp = jedis.pipelined();
            for (String v : values) {
                pp.hdel(name, v);
            }
            pp.sync();
            pp.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public JSONArray batchHgetAll(List<String> keys) {
        Jedis jedis = null;
        JSONArray array = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < keys.size(); i++) {
                pp.hgetAll(keys.get(i));
            }
            List<Object> obj = pp.syncAndReturnAll();
            pp.close();

            array = JSONArray.parseArray(JSONObject.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return array;
    }

    public JSONArray batchHget(String name, List<String> keys) {
        Jedis jedis = null;
        JSONArray array = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < keys.size(); i++) {
                pp.hget(name, keys.get(i));
            }
            List<Object> obj = pp.syncAndReturnAll();
            pp.close();

            array = JSONArray.parseArray(JSONObject.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return array;
    }

    public boolean batchHset(String name, List<String> keys, List<String> datas) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < keys.size(); i++) {
                pp.hset(name, keys.get(i), datas.get(i));
            }
            pp.sync();
            pp.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public <T> boolean batchLpush(String name, List<T> objects) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < objects.size(); i++) {
                pp.lpush(name, JSONObject.toJSONString(objects.get(i)));
            }
            pp.sync();
            pp.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return flag;
    }

    public long sadd(String key, String sn) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = jedisPool.getResource();
            result = jedis.sadd(key, sn);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public long srem(String key, String sn) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = jedisPool.getResource();
            result = jedis.srem(key, sn);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public String spop(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.spop(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public String set(String key, String value, int seconds) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            } else {
                jedis.expire(key, 600);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger("error").error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return result;
    }

    public JSONArray batchHget(String hashKeyPrefix, List<String> keys, String key) {
        Jedis jedis = null;
        JSONArray array = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < keys.size(); i++) {
                pp.hget(hashKeyPrefix + keys.get(i), key);
                //pp.hgetAll(keys.get(i));
            }
            List<Object> obj = pp.syncAndReturnAll();
            pp.close();
            array = JSONArray.parseArray(JSONObject.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return array;
    }


    public <T> Map<String, Object> batchHgetHash(String key, List<T> names) {
        Jedis jedis = null;
        Map<String, Object> map = new HashMap<>();
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();

            for (int i = 0; i < names.size(); i++) {
                pp.hget(key, names.get(i).toString());
                //pp.hgetAll(keys.get(i));
            }
            List<Object> obj = pp.syncAndReturnAll();
            pp.close();
            for (int i = 0; i < names.size(); i++) {
                if (obj.get(i) != null) {
                    map.put(names.get(i).toString(), obj.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return map;
    }


    public List<Object> batchBRPopValue(String name, long size) {
        Jedis jedis = null;
        List<Object> item = new ArrayList<>();
        try {
            jedis = jedisPool.getResource();
            Pipeline pp = jedis.pipelined();
//			long total = jedis.llen(name);
//			if(total < size) size= total;
            for (int i = 0; i < size; i++) {
//				pp.brpop(redisTimeOut,name);
                pp.rpop(name);
            }

            List<Object> list = pp.syncAndReturnAll();
            list.forEach(i -> {
                try {
                    if (i != null) {
                        item.add(i);
						/*List<String> ilist = (List<String>) i;
						if (ilist != null&&!ilist.isEmpty()) {

						}*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pp.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return item;
    }

}
