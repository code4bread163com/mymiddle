package redispool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * spring-cache配置
 *
 * @author changzhenyu
 */
//@EnableCaching
//@Configuration
@Slf4j
public class SpringCacheConfiguration extends CachingConfigurerSupport {
    public static final String SEPARATOR_VERTICAL_SPOT = ".";
    public static final String SEPARATOR_VERTICAL_DOUBLE_COLON = "::";
    public static final String SEPARATOR_VERTICAL_COLON = ":";

    /**
     * 缓存名称
     */
    private String defaultCacheName = "methodCache";
    /**
     * 默认缓存过期时间
     */
    private int defaultTtl = 1 * 24;
    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private RedisConnectionFactory redissonConnectionFactory;

    /**
     * 自定义KeyGenerator,key为：类名.接口名.参数Hash
     * 当参数为空时，key为：类名.接口名
     * 注意hash冲突问题
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            //获取代理对象的最终目标对象
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
            StringBuilder builder = new StringBuilder();
            builder.append(targetClass.getSimpleName()).append(SEPARATOR_VERTICAL_SPOT);
            builder.append(method.getName()).append(SEPARATOR_VERTICAL_SPOT);
            //参数hash
            if (params != null || params.length > 0) {
                int hashCode = SimpleKeyGenerator.generateKey(params).hashCode();
                builder.append(hashCode);
            }

            return builder;
        };
    }

    /**
     * CacheManager配置
     *
     * @return
     */
    @Bean
    @Override
    public CacheManager cacheManager() {

        // 设置jdk序列化方式
        RedisSerializer serializer = new JdkSerializationRedisSerializer();
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                // 设置缓存前缀
                .computePrefixWith(cacheName -> applicationName + SEPARATOR_VERTICAL_DOUBLE_COLON + cacheName + SEPARATOR_VERTICAL_COLON)
                //若返回值为null，则不允许存储到Cache中
                //.disableCachingNullValues()
                // 默认超时时间
                .entryTtl(Duration.ofHours(defaultTtl));
        // 设置一个初始化的cacheNameset集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(defaultCacheName);

        // 构造RedisCacheManager
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redissonConnectionFactory)
                // 模板配置必须先执行
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                //.withInitialCacheConfigurations()
                .build();
        return redisCacheManager;
    }

    //@Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache,
                                            Object key, Object value) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache,
                                            Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                              Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                RedisErrorException(exception, null);
            }
        };
        return cacheErrorHandler;
    }

    protected void RedisErrorException(Exception exception, Object key) {
        log.error("redis异常：key=[{}]", key, exception);
    }

}
