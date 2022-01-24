package com.test.backend.embedapi.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {

    /*
        - EhCache 팩터리 빈 등록
    */
    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean(){
        return new EhCacheManagerFactoryBean();
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(){

        /*
                ehCache 매니저 등록
                - 캐싱 생명주기 10분
        */
        CacheConfiguration oEmbedCacheConfig = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(600)
                .timeToLiveSeconds(600)
                .maxEntriesLocalHeap(0)
                .memoryStoreEvictionPolicy("LRU")
                .name("oEmbedCaching");

        Cache oEmbedCache = new net.sf.ehcache.Cache(oEmbedCacheConfig);
        CacheManager cacheManager
                = Objects.requireNonNull(cacheManagerFactoryBean().getObject());

        cacheManager.addCache(oEmbedCache);
        return new EhCacheCacheManager(cacheManager);
    }
}
