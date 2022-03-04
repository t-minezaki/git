package jp.learningpark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * GakkenAppApplication
 *
 * @author huangyong
 * @date 2018/10/10
 */
@SpringBootApplication
@MapperScan(basePackages = {"jp.learningpark.**.dao"})
@ComponentScan("jp.learningpark")
@ServletComponentScan
public class GakkenAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(GakkenAppApplication.class, args);
    }

    /**
     * WAR
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GakkenAppApplication.class);
    }

}