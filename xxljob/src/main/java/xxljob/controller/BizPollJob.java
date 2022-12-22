package xxljob.controller;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.springframework.stereotype.Component;

/**
 * 回调补偿定时任务，进行结果查询
 *
 * @author changzhenyu
 */
@Slf4j
@Component
public class BizPollJob {

    /**
     * 风险决策结果查询-授信
     */
    @XxlJob("grantBizPollJob")
    public ReturnT<String> grantBizPollJob(String param) {

        log.info("定时任务处理-授信风险决策结果查询-结束");

        Thread th = new Thread(new RunnableWrapper(new Runnable() {
            @Override
            public void run() {
                log.info("定时任务处理-new thread start");
            }
        }));

        th.start();

        return ReturnT.SUCCESS;
    }

}
