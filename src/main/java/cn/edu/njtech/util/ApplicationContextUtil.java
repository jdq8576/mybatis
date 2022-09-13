package cn.edu.njtech.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tim
 * @date 2022/9/13 2:21 下午
 */
public class ApplicationContextUtil {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    private ApplicationContextUtil() {

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
