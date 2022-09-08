package com.study.liu.config.Interceptor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    //字符编码过滤器
    @Bean
    public FilterRegistrationBean registerCharacterEncodingFilterBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        filterRegistrationBean.setFilter(encodingFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    //静态资源处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") //访问路径
                .addResourceLocations("classpath:/static/","classpath:/templates/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    //解决跨越问题
   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") //允许任何跨越
				.allowedHeaders("*") //允许的请求头（这里允许任何请求头）
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE") //允许的方法
                .allowCredentials(true);
    }

    //拦截配置器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(getloginInterceptor());
        //添加拦截映射规则，哪些拦截，哪些不拦截
        //先拦截所有
        interceptorRegistration.addPathPatterns("/**");
        //排除不拦截的
        interceptorRegistration.excludePathPatterns("/login","/login.html","/index","/index.html","*.js","*.css","*.html");
    }
    @Bean
    public LoginInterceptor getloginInterceptor(){
        return new LoginInterceptor();
    }
}
