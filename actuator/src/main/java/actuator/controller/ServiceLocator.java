package actuator.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 服务定位器
 *
 * @author xuhaikun
 * @date 2019-04-01
 */
@Service
public class ServiceLocator implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static void setCtx(ApplicationContext ctx) {
        ServiceLocator.ctx = ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setCtx(applicationContext);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return ctx.getBean(beanName, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
