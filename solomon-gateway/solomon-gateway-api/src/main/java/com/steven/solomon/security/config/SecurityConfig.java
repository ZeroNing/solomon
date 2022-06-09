package com.steven.solomon.security.config;//package com.steven.solomon.config;

import com.steven.solomon.security.handler.SecurityAuthenticationFailureHandler;
import com.steven.solomon.security.handler.SecurityFailureHandler;
import com.steven.solomon.security.handler.SecurityLogoutHandler;
import com.steven.solomon.security.handler.SecurityLogoutSuccessHandler;
import com.steven.solomon.security.handler.SecuritySuccessHandler;
import com.steven.solomon.security.handler.SecurityUnauthorizedHandler;
import com.steven.solomon.security.service.impl.UserDetailsServiceImpl;
import java.util.LinkedList;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  /**
   * 登陆成功处理器
   */
  @Resource
  private SecuritySuccessHandler               securitySuccessHandler;
  /**
   * 登陆失败处理器
   */
  @Resource
  private SecurityFailureHandler               securityFailureHandler;
  /**
   * 未认证处理器
   */
  @Resource
  private SecurityUnauthorizedHandler          securityUnauthorizedHandler;
  /**
   * 鉴权失败处理器
   */
  @Resource
  private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;
  /**
   * 登出处理器
   */
  @Resource
  private SecurityLogoutHandler                securityLogoutHandler;

  /**
   * 登出成功处理器
   */
  @Resource
  private SecurityLogoutSuccessHandler securityLogoutSuccessHandler;

  @Resource
  private UserDetailsServiceImpl userDetailsService;

  private static final String[] excludedAuthPages = {
      "/authenticate/login",
      "/authenticate/logout"
  };

  @Bean
  public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//         .cors().disable()//关闭cors跨域
//         .csrf().disable()//关闭csrf防护
//        .authorizeExchange()
//         .pathMatchers("/login","/logout").permitAll()//登陆登出接口无需权限访问
//         .pathMatchers(HttpMethod.OPTIONS).permitAll()//OPTIONS请求访问无需权限访问
//         .anyExchange().authenticated()
//         .and()
//         .httpBasic()
//         .and()
//         .exceptionHandling()z
//         .authenticationEntryPoint(securityUnauthorizedHandler)//未认证处理器
//         .accessDeniedHandler(securityAuthenticationFailureHandler)//处理鉴权失败处理器
//         .and()
//         .formLogin().loginPage("/login")
//         .authenticationSuccessHandler(securitySuccessHandler) //认证成功
//         .authenticationFailureHandler(securityFailureHandler)//登陆验证失败
//        .and().logout().logoutUrl("/logout");
//    http.cors().disable();
//    http.csrf().disable();
    http.logout().logoutUrl("/authenticate/logout").logoutHandler(securityLogoutHandler).logoutSuccessHandler(securityLogoutSuccessHandler);
    //配置权限
    http.authorizeExchange().pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
        .pathMatchers(HttpMethod.OPTIONS).permitAll(); //option 请求默认放行

    http
        .authorizeExchange()
        .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
        .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
        .anyExchange().authenticated()
        .and()
        .httpBasic()
        .and()
        .formLogin()//启动页面表单登陆,spring security 内置了一个登陆页面/login
        .loginPage("/authenticate/login")//配置登录接口
        .authenticationSuccessHandler(securitySuccessHandler)//配置登录成功后的处理器
        .authenticationFailureHandler(securityFailureHandler)//配置登陆失败后的处理器
        .authenticationEntryPoint(securityUnauthorizedHandler)//配置未认证的处理器
        .and()
        .exceptionHandling().accessDeniedHandler(securityAuthenticationFailureHandler)//配置鉴权失败的处理器

        .and().csrf().disable();//必须支持跨域
    return http.build();
  }

  /**
   * 密码加密工具
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 注册用户信息验证管理器，可按需求添加多个按顺序执行
   */
  @Bean
  public ReactiveAuthenticationManager reactiveAuthenticationManager() {
    LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
    managers.add(authentication -> {
      // 其他登陆方式 (比如手机号验证码登陆) 可在此设置不得抛出异常或者 Mono.error
      return Mono.empty();
    });
    // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
    managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService));
    return new DelegatingReactiveAuthenticationManager(managers);
  }

}
