package configuration;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TestApplicationContext implements ApplicationContextAware{

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        TestApplicationContext.context = applicationContext;
    }

    public static Object getBean(final String beanName) {
        return context.getBean(beanName);
    }
}
