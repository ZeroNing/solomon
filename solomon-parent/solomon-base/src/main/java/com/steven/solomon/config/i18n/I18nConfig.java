package com.steven.solomon.config.i18n;

import com.steven.solomon.base.code.BaseCode;
import com.steven.solomon.i18n.I18nControl;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.poi.util.LocaleUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties.LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nConfig {

  private Logger logger = LoggerUtils.logger(getClass());

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
    bundleMessageSource.setDefaultLocale(ValidateUtils.isEmpty(DEFAULT_LOCALE) ? Locale.CHINESE : DEFAULT_LOCALE);
    bundleMessageSource.setDefaultEncoding("UTF-8");
    logger.info("BaseI18nConfig初始化I18N国际化文件成功,国际化默认语言为:{},国际化文件路径为:{}",DEFAULT_LOCALE.toString(), resourceBundle.getBaseBundleName());
    return bundleMessageSource;
  }

  private ResourceBundle initResources(List<String> locales,int index,ResourceBundle resourceBundle){
    if(index >= locales.size()){
      return resourceBundle;
    }
    String language = locales.get(index);
    if(ValidateUtils.isEmpty(language)){
      return resourceBundle;
    }
    resourceBundle = ResourceBundle.getBundle("classpath*:i18n/messages", new Locale(language), new I18nControl());
    return initResources(locales,index+1,resourceBundle);
  }

}
