/**
 * Copyright (c) 2016-2019 http://sinways.com.cn/ All rights reserved.
 * <p>
 * https://www.studycompass.jp
 * <p>
 * システム名 :
 */

package jp.learningpark.framework.config;

import jp.learningpark.framework.freemaker.method.ResourceUrlMethod;
import jp.learningpark.modules.sys.shiro.ShiroTag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Freemarker配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class FreemarkerConfig {
    @Value("${ans-url.token}")
    String token;
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(ShiroTag shiroTag) throws UnsupportedEncodingException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates");
        Map<String, Object> variables = new HashMap<>(1);
        variables.put("shiro", shiroTag);
        variables.put("gurl", new ResourceUrlMethod());
        //tokenトランスコード modify yang 2020/11/26 start--
        String tokenEncode = URLEncoder.encode(token,"utf-8");
        variables.put("token", tokenEncode);
        //tokenトランスコード modify yang 2020/11/26 end--
        configurer.setFreemarkerVariables(variables);

        Properties settings = new Properties();
        settings.setProperty("default_encoding", "utf-8");
        settings.setProperty("number_format", "0.##");
        settings.setProperty("localized_lookup", "false");
        configurer.setFreemarkerSettings(settings);
        return configurer;
    }

}
