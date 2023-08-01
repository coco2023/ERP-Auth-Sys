package com.prac.springkafka.repository.cache;

public interface CacheRepository {

    String getUserIdByAccessToken(String token);
}
