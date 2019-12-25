package com.sean.springboot.config;

import com.sean.springboot.prework.interceptor.SeanInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: 注册拦截器
 * @Author: Sean, CSII
 * @Date: 2019-12-11 16:47
 */
@EnableWebMvc
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /* 注入已经写好的拦截器类 */
    @Autowired
    private SeanInterceptor seanInterceptor;

    /**
     * @Description:
     *  这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     *  若存在N个拦截器，则按照需求顺序进行 addInterceptor... ...等操作，将会组成了一个拦截器链
     *  excludePathPatterns是取消拦截的路径
     * @param registry
     * @return void
     * @Author: Sean, CSII
     * @Date:   2019/12/11 0011 17:10
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(seanInterceptor).addPathPatterns("/**");
        registry.addInterceptor(seanInterceptor).addPathPatterns("/mng/**").excludePathPatterns("/login");
    }

    /**
     * @Description:
     *  这个方法是用来配置静态资源的，比如html，js，css，等等
     * @param  registry
     * @return  void
     * @Author: Sean, CSII
     * @Date:   2019/12/11 0011 17:13
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
