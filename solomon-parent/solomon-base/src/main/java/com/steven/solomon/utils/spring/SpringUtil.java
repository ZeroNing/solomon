package com.steven.solomon.utils.spring;

import com.steven.solomon.base.excetion.BaseException;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.lang.annotation.Annotation;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

/**
 * @Description Spring上下文工具类
 * @Author Sans
 * @CreateTime 2019/6/17 13:40
 */
@Configuration("springUtil")
@Lazy(false)
@Order(1)
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	/**
	 * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
	 * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.context == null) {
			SpringUtil.context = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * 通过Name返回指定的Bean
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	public static <T>T getBean(String name,Class<T> beanClass) {
		return context.getBean(name,beanClass);
	}

	public static <T> T getBean(String name,Class<T> beanClass,String errorCode) throws BaseException {
		return ValidateUtils.isEmpty(context.getBean(name,beanClass),errorCode);
	}

	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return context.getBeansWithAnnotation(annotationType);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return context.getBeansOfType(type);
	}
}
