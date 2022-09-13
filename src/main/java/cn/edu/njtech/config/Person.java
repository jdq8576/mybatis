package cn.edu.njtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tim
 * @date 2022/9/13 1:45 下午
 */
@Configuration
public class Person {

    @Bean(name = "p")
    public Person person() {
        return new Person();
    }

}
