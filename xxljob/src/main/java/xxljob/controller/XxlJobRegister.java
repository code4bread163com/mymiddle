package xxljob.controller;

import com.xxl.job.core.biz.model.ReturnT;

/**
 * xxlj-job定时任务注册器
 *
 * @author linjun
 * @date 2020年11月17日
 */
public interface XxlJobRegister {

    /**
     * 获取xxljob上的定时任务名称
     *
     * @return the job name
     */
    String getJobName();

    /**
     * 执行定时任务
     *
     * @param param 定时任务入参
     * @return 返回结果
     */
    ReturnT<String> execute(String param);

}
