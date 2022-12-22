package redispool;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redispool.lock.RedLockUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 测试redisson
 * Created by apple on 2018/5/29.
 */
//@RestController
@Slf4j
public class TestController2 {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedLockUtils redLockUtils;

    @GetMapping("/3")
    @Cacheable(cacheNames = "methodCache", key = "'key'", unless = "'200' ne #result")
    public String redissionSet() {
        log.info("set reids");

        RBucket<String> bucket = redissonClient.getBucket("test", new JsonJacksonCodec());
        bucket.set("123");

        return "200";
    }

    @GetMapping("/4")
    public String redissionGet() {
        log.info("get redis");

        RBucket<String> bucket = redissonClient.getBucket("test", new JsonJacksonCodec());
        String test = bucket.get();
        System.out.println(test);

        return "helloworld";
    }


    @GetMapping("/5")
    public String redissonLock() {
        try {
            log.info("lock redis");
            boolean result = redLockUtils.tryLock("test1", 1000, 30000, TimeUnit.MILLISECONDS);
            log.info("lock " + result);

            Thread.sleep(20000);

            if (redLockUtils.isLocked("test1")) {
                redLockUtils.unlock("test1");
                log.info("unlock succ");
            }

            log.info("end");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "helloworld";
    }

}
