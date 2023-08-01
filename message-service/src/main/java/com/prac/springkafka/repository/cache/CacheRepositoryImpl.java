package com.prac.springkafka.repository.cache;

import com.prac.springkafka.config.JedisFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class CacheRepositoryImpl implements CacheRepository{
    @Override
    public String getUserIdByAccessToken(String token) {
        try (Jedis jedis = JedisFactory.getConnection()) {

            return jedis.get(token);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
