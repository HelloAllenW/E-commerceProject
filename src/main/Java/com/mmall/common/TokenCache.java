package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class TokenCache {

    // 打印
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    /** 设置本地缓存，通过调用链的模式进行构建
     * LoadingCache : 官网中的本地缓存
     * initialCapacity(1000) : 设置缓存的初始化容量
     * maximumSize(10000) : 设置缓存的最大容量，当超过这个容量，会采用 LRU 算法（最小使用算法进行清除）
     * expireAfterAccess(12, TimeUnit.HOURS) ： 表示有效期为 12 小时
     */
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载。
                @Override
                public String load(String key) throws Exception {
                    return "null"; // 防止异常（报空指针），将此处返回为null字符串
                }
            });
    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache get error", e);
        }
        return null;
    }


}
