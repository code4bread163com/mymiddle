package redispool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangliang
 * @date 2020/2/8
 */
//@Component
@Slf4j
public class LettuceConnectionValidTask {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Scheduled(cron = "0/2 * * * * ?")
    public void task() {
        if (redisConnectionFactory instanceof LettuceConnectionFactory) {
            LettuceConnectionFactory c = (LettuceConnectionFactory) redisConnectionFactory;
            c.validateConnection();
        }
    }
}

