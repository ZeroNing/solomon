package com.steven.solomon.config.i18n;

import com.steven.solomon.base.code.BaseCode;
import com.steven.solomon.i18n.I18nControl;
import com.steven.solomon.utils.logger.LoggerUtils;
import com.steven.solomon.utils.verification.ValidateUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class I18nConfig {

  private Logger logger = LoggerUtils.logger(getClass());

  @Value("${i18n.language}")
  public Locale DEFAULT_LOCALE;
  @Value("${i18n.messages.baseNames}")
  private String BASE_NAMES;

  /**
   * 初始化I18N国际化文件
   */
  @Bean("resourceBundleMessageSource")
  public ResourceBundleMessageSource init() {
    ResourceBundle resources = ResourceBundle.getBundle("classpath*:i18n/messages", new I18nControl());
    ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
    bundleMessageSource.setDefaultEncoding(BaseCode.UTF8);
    if(ValidateUtils.isEmpty(BASE_NAMES)){
      logger.info("初始化I18N国际化文件缺少I18N文件路径，忽略初始化国际化配置");
      return bundleMessageSource;
    }
    String beanNames = BASE_NAMES;
    beanNames = beanNames + "," + resources.getBaseBundleName();
    List<String> baseNameList = Arrays.asList(beanNames.split(","));

    bundleMessageSource.setBasenames(baseNameList.toArray(new String[0]));
    bundleMessageSource.setDefaultLocale(ValidateUtils.isEmpty(DEFAULT_LOCALE) ? Locale.CHINESE : DEFAULT_LOCALE);
    bundleMessageSource.setDefaultEncoding("UTF-8");
    logger.info("BaseI18nConfig初始化I18N国际化文件成功,国际化默认语言为:{},国际化文件路径为:{}",DEFAULT_LOCALE.toString(), baseNameList.toString());
    return bundleMessageSource;
  }

}
