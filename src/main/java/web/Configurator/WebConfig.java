package web.Configurator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.filter.AuthorizationInterceptor;

/**
 * version: 1.0.0
 * desc   : mvc配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，配置拦截地址 其中/**表示当前目录以及所有子目录（递归），/*表示当前目录，不包括子目录。
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
