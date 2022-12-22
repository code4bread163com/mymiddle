package springShutDown;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 申请人信息表数据库服务
 *
 * @author zhangliang
 * @date 2022/1/5
 */
@Slf4j
@Service
public class DbService4ApplApptImpl implements InitializingBean, DisposableBean {

    //@Resource(name= "abcd")
    private ThreadPoolTaskExecutor executor;

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            executor = new ThreadPoolTaskExecutor();
            executor.setBeanName("1233");
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
            executor.setWaitForTasksToCompleteOnShutdown(false);
            //等待 超过这个时间，就自动销毁
            executor.setAwaitTerminationSeconds(60);

            //线程池对拒绝任务的处理策略
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.setDaemon(true);
            executor.initialize();

            long a = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
    }

    @Autowired
    private RedissonClient client;

    public static Random random = new Random();

    public void doTaskOne() throws Exception {
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int start = random.nextInt();

                        log.info("开始做任务一" + start);
                        RBucket<String> bucket = client.getBucket("asset:out:configuration:moneySpli:DXM001", new SerializationCodec());
                        if (bucket == null || !bucket.isExists()) {
                            throw new RuntimeException("资产拆分配置异常! ");
                        }

                        log.info("完成任务，" + start);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void doTaskTwo() throws Exception {
        this.doTaskOne();
    }

    public void doTaskThree() throws Exception {
        this.doTaskOne();
    }


}
