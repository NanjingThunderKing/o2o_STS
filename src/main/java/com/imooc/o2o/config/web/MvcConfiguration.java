package com.imooc.o2o.config.web;

import javax.servlet.ServletException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.imooc.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.imooc.o2o.interceptor.shopadmin.ShopPermissionInterceptor;


/**
 * 开启Mvc，自动注入spring容器。WebMvcConfigurerAdapter：配置视图解析器。
 * 当一个类实现了ApplicationContextAware这个接口，这个类就可以方便获得ApplicationContext中的所有bean。
 * @author yangtengyu
 *
 */
@Configuration
//等价于<mvc:annotation-driven />
@EnableWebMvc  
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	// Spring容器
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 静态资源配置
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/yangtengyu/desktop/ssmforwork/upload/");
	}
	
	/**
	 * 定义默认的请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * 定义视图解析器
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// 设置Spring 容器
		viewResolver.setApplicationContext(this.applicationContext);
		// 取消缓存
		viewResolver.setCache(false);
		// 设置解析的前缀
		viewResolver.setPrefix("/WEB-INF/html/");
		// 设置视图解析的后缀
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
	
	/**
	 * 文件上传解析器
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		// 1024 * 1024 * 20 = 20M
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}
	
	@Value("${kaptcha.border}")
	private String border;
	
	@Value("${kaptcha.textproducer.font.color}")
	private String fcolor;
	
	@Value("${kaptcha.image.width}")
	private String width;
	
	@Value("${kaptcha.textproducer.char.string}")
	private String cString;
	
	@Value("${kaptcha.image.height}")
	private String height;
	
	@Value("${kaptcha.textproducer.font.size}")
	private String fsize;
	
	@Value("${kaptcha.noise.color}")
	private String nColor;
	
	@Value("${kaptcha.textproducer.char.length}")
	private String clength;
	
	@Value("${kaptcha.textproducer.font.names}")
	private String fnames;
	
	/**
	 * 由于web.xml不生效了，在这儿配置Kaptcha验证码Servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() throws ServletException {
		ServletRegistrationBean servlet =
				new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
		servlet.addInitParameter("kaptcha.border", border);
		servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor);
		servlet.addInitParameter("kaptcha.image.width", width);
		servlet.addInitParameter("kaptcha.textproducer.char.string", cString);
		servlet.addInitParameter("kaptcha.image.height", height);
		servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);
		servlet.addInitParameter("kaptcha.noise.color", nColor);
		servlet.addInitParameter("kaptcha.textproducer.char.length", clength);
		servlet.addInitParameter("kaptcha.textproducer.font.names", fnames);
		return servlet;
	}
	
	/**
	 * 添加拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String interceptPath = "/shopadmin/**";
		// 注册拦截器1号
		InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
		// 配置拦截器1号拦截的路径
		loginIR.addPathPatterns(interceptPath);
		// 配置拦截器1号放行的路径
		/** shopauthmanagement page  **/
		loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
		// 注册拦截器2号
		InterceptorRegistration permissionIR =
				registry.addInterceptor(new ShopPermissionInterceptor());
		// 配置拦截器2号拦截的路径
		permissionIR.addPathPatterns(interceptPath);
		// 配置拦截器2号不拦截的路径
		/** shoplist page **/
		permissionIR.excludePathPatterns("/shopadmin/shoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshoplist");
		/** shopregister page **/
		permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
		permissionIR.excludePathPatterns("/shopadmin/registershop");
		permissionIR.excludePathPatterns("/shopadmin/shopoperation");
		/** shopmanage page **/ 
		permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
		permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
		/** shopauthmanagement page  **/
		permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");
		
	}

	
}
