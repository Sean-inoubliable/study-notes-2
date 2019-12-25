# 项目小记

## 过滤器 Filter：实现 Filter (import javax.servlet.Filter)
> 它依赖于 servlet容器。在实现上，基于函数回调，它可以对几乎所有请求进行过滤，但缺点是一个过滤器实例只能在容器初始化是调用一次。

> 使用过滤器的目的，是用来做一些过滤操作，获取我们想要获取的数据，比如：在 JavaWeb中，对传入的 request/response提前过滤掉一些信息，或者提前设置一些参数，然后再传入 servlet或者 controller进行业务逻辑操作。

> 通常用的场景：在过滤器中修改字符编码（CharaterEncodingFilter）、在过滤器中修改 HttpServletRequest的一些参数（XSSFilter(自定义过滤器)），如：过滤低俗文字、危险字符等。

> 过滤器的运行是依赖于servlet容器，跟springmvc等框架并没有关系。并且，多个过滤器的执行顺序跟xml文件中定义的先后关系有关。

### SpringBoot项目中添加过滤器Filter
- 方式1
    - 通过实现Filter接口，创建一个过滤器类
    - 通过@WebFilter注解，注册过滤器。urlPatterns属性代表需要被过滤的请求地址。filterName属性代表过滤器名称。
    - 在SpringBoot应用启动类中，添加@ServletComponentScan注解，表示项目启动自动扫描Servlet组件。Filter属于Servlet组件。
    
- 方式2
    - 创建一个过滤器，实现javax.servlet.Filter接口，并重写其中的init、doFilter、destory方法。

```java
/* Java代码示例： */
@Component
@WebFilter(filterName="SeanFilter", urlPatterns = "/*")
public class SeanFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        System.out.println(" ------- seanFilter doFilter executed ... ... ");

        HttpServletRequest request = (HttpServletRequest)req;

        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();

        System.out.println("URI: " + requestURI);
        System.out.println("URL: " + requestURL);

        filterChain.doFilter(req, resp);

        System.out.println(" ------- seanFilter doFilter after ... ... ");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        /* 可以加载 Filter启动之前需要的资源 */

        System.out.println(" ------- seanFilter init ... ... ");

        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {

    }
}
```
```xml
<!-- XML文件示例： -->

<!-- 字符编码 -->
<filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>encoding</filter-name>
    <servlet-name>/*</servlet-name>
</filter-mapping>

<!-- 自定义过滤器 -->
<filter>  
    <filter-name>seanFilter</filter-name>  
    <filter-class>com.sean.springboot.prework.filter.SeanFilter</filter-class>  
</filter>  
<filter-mapping>  
    <filter-name>seanFilter</filter-name>  
    <url-pattern>/*</url-pattern>  
</filter-mapping>  

```



## 拦截器 Interceptor: implements HandlerInterceptor
>它依赖于 web框架，在 SpringMVC中就是依赖于 SpringMVC框架。

>在实现上，基于 Java的反射机制，属于面向切面编程（AOP）的一种运用，就是在 service或者一个方法前，调用一个方法，或者在方法后，调用一个方法

>比如动态代理就是拦截器的简单实现

> 一个拦截器示例在一个controller生命周期内可以多次调用，缺点是只能对controller请求进行拦截，对其他的一些比如直接访问静态资源的请求则么办法进行拦截处理

> Interceptor 在AOP（Aspect-Oriented Programming）中用于在某个方法或字段被访问之前，进行拦截然后在之前或之后加入某些操作。

### SpringBoot项目中添加拦截器 Interceptor
    - 实现HandlerInterceptor接口，然后定义我们的方法（preHandler、postHandler、afterCompletion）
    - 步骤：
      1、创建自己的拦截器实现HandlerInterceptor接口
      2、创建自己的拦截器链，继承WebMvcConfigurerAdapter类，重写addInterceptors方法。
      3、实例化自己的拦截器，并加入到拦截器链中。

```java
/* Java代码示例 */
@Service
public class SeanInterceptor implements HandlerInterceptor {

    /**
     * 在DispatcherServlet之前执行
     * 在请求处理之前进行调用 
     * 只有返回true才会继续向下执行，返回false取消当前请求 
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(" ------- seanInterceptor preHandler executed ... ... ");
        return true;
    }

    /**
     *请求处理之后进行调用，但是在视图被渲染之前 
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println(" ------- seanInterceptor postHandle executed ... ... ");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作） 
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println(" ------- seanInterceptor afterCompletion executed ... ... ");
    }
}

```

```java
/* 在SpringBoot中采用Java类的形式配置拦截器配置 */
@EnableWebMvc
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /* 注入已经写好的拦截器类 */
    @Autowired
    private SeanInterceptor seanInterceptor;

    /**
     * @Description:
     *  这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
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
```

```xml

<!-- 在SpringMVC的配置文件中，加上拦截器的配置： -->
<!-- 拦截器 -->  
<mvc:interceptors>  
    <!-- 对所有请求都拦截，公共拦截器可以有多个 -->  
    <bean name="baseInterceptor" class="com.scorpios.interceptor.BaseInterceptor" />  
    
    <mvc:interceptor> 
        <!-- 对/test.html进行拦截 -->       
        <mvc:mapping path="/test.html"/>  
        <!-- 特定请求的拦截器只能有一个 -->  
        <bean class="com.scorpios.interceptor.TestInterceptor" />  
    </mvc:interceptor>  
</mvc:interceptors>  
```

## 总结
```text
1.Filter需要在Web.xml中配置，依赖于servlet

2.Interceptor需要在SpringMVC中配置，依赖于框架

3.Filter的执行顺序在Interceptor之前，具体流程：... ...

4.二者的本质区别：
    a.拦截器（Interceptor）基于Java的反射机制
    b.过滤器（Filter）是基于函数回调
    
  从灵活性上来讲：
    拦截器：功能更强大，Filter能做的事情，Interceptor都能做，而且可以在请求前、请求后执行，比较灵活
    过滤器：Filter主要是针对URL地址做一个编码的事情、过滤掉没用的参数、安全校验（比较泛用的，比如登陆不登陆之类）
    
  根据情况不同选择合适的
```


## 监听器 Listener
> listener是servlet规范中定义的一种特殊类。用于监听servletContext、HttpSession和servletRequest等域对象的创建和销毁事件。监听域对象的属性发生修改的事件。用于在事件发生前、发生后做一些必要的处理。其主要可用于以下方面：1、统计在线人数和在线用户2、系统启动时加载初始化信息3、统计网站访问量4、记录用户访问路径。


