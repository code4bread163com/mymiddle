package springShutDown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

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

    //@Bean("abcd")
    public ThreadPoolTaskExecutor taskExecutor() {

        //ThreadPool Task Executor 线程池 任务 执行器
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //缓冲队列200
        executor.setQueueCapacity(200);
        //允许线程的空闲时间60秒
        executor.setKeepAliveSeconds(60);
        //线程池名的前缀
        executor.setThreadNamePrefix("abcdef-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待 超过这个时间，就自动销毁
        executor.setAwaitTerminationSeconds(60);

        //线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
