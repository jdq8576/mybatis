package cn.edu.njtech.cache;

import cn.edu.njtech.util.ApplicationContextUtil;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author tim
 * @date 2022/9/13 12:46 下午
 */
@Slf4j
public class MyBatisRedisCache implements Cache, ApplicationContextAware {

    public MyBatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.info("Redis Cache id " + id);
        this.id = id;
    }

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    private String id;

    private ApplicationContext applicationContext;


    private FastJsonRedisSerializer redisSerializer = new FastJsonRedisSerializer(Object.class);

    private RedisTemplate<Serializable, Serializable> redisTemplate = (RedisTemplate<Serializable, Serializable>) ApplicationContextUtil.getApplicationContext().getBean("redisTemplate");


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            if (redisTemplate == null) {
                System.out.println("Hello");
            }
            redisTemplate.opsForValue().set(key.toString(), redisSerializer.serialize(value), 2, TimeUnit.DAYS);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            if (key != null) {
                System.out.println("MyBatisRedisCache::getObject");
                Object obj = redisTemplate.opsForValue().get(key.toString());
                return redisSerializer.deserialize((byte[]) obj);
            }
        } catch (Exception e) {
            log.error("redis ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try {
            if (key != null) {
                redisTemplate.expire(key.toString(), 1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        Long size = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
