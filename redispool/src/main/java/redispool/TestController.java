package redispool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by apple on 2018/5/29.
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;



    @GetMapping("/1")
    @Cacheable(cacheNames = "methodCache", key = "'key'", unless = "'200' ne #result")
    public String helloworld1() {
        try {
            log.info("set reids");
            ValueOperations<String, String> ops = redisTemplate.opsForValue();

            Thread.sleep(20000);

            ops.set("test", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "200";
    }

    @GetMapping("/2")
    public String helloworld2() {
        log.info("get redis");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String aa = ops.get("test");
        System.out.println(aa);

        return "helloworld";
    }

    @GetMapping("/6")
    public String test() {
        try {
            log.info("begin");

            Thread.sleep(20000);

            log.info("end");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "helloworld";
    }

}
