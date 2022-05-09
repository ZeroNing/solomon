package com.steven.solomon.config;

import com.steven.solomon.constant.code.BaseCode;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nConfig {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${i18n.language}")
  public Locale DEFAULT_LOCALE;
  @Value("${i18n.all-locale}")
  private String ALL_LOCALE;

  /**
   * 初始化I18N国际化文件
   */
  @Bean("resourceBundleMessageSource")
  public ResourceBundleMessageSource init() {
    List<String> allLocale = Arrays.asList(ALL_LOCALE.split(","));
    ResourceBundle resourceBundle = initResources(allLocale,0,null);

    ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
    bundleMessageSource.setDefaultEncoding(BaseCode.UTF8);

    bundleMessageSource.setBasenames(resourceBundle.getBaseBundleName());
    bundleMessageSource.setDefaultLocale(DEFAULT_LOCALE == null ? Locale.CHINESE : DEFAULT_LOCALE);
    bundleMessageSource.setDefaultEncoding("UTF-8");
    logger.info("BaseI18nConfig初始化I18N国际化文件成功,国际化默认语言为:{},国际化文件路径为:{}",DEFAULT_LOCALE.toString(), resourceBundle.getBaseBundleName());
    return bundleMessageSource;
  }

  private ResourceBundle initResources(List<String> locales,int index,ResourceBundle resourceBundle){
    if(index >= locales.size()){
      return resourceBundle;
    }
    String language = locales.get(index);
    if(language.isEmpty() || "".equals(language)){
      return resourceBundle;
    }
    resourceBundle = ResourceBundle.getBundle("classpath*:i18n/messages", new Locale(language), new I18nControl());
    return initResources(locales,index+1,resourceBundle);
  }

}