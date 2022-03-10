package com.steven.solomon.config;//package com.steven.solomon.config;
//
//import com.steven.solomon.handler.SecurityAuthenticationFailureHandler;
//import com.steven.solomon.handler.SecurityFailureHandler;
//import com.steven.solomon.handler.SecuritySuccessHandler;
//import com.steven.solomonGatewayController.handler.SecurityUnauthorizedHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig{
//
//    /**
//     * 登陆成功处理器
//     */
//    @Autowired
//    private SecuritySuccessHandler securitySuccessHandler;
//    /**
//     * 登陆失败处理器
//     */
//    @Autowired
//    private SecurityFailureHandler securityFailureHandler;
//    /**
//     * 未认证处理器
//     */
//    @Autowired
//    private SecurityUnauthorizedHandler securityUnauthorizedHandler;
//    /**
//     * 鉴权失败处理器
//     */
//    @Autowired
//    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;
//
//    private static final String[] excludedAuthPages = {
//        "/auth/login",
//        "/auth/logout",
//        "/health",
//        "/api/socket/**"
//    };
//
//    @Bean
//    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
////        http
////         .cors().disable()//关闭cors跨域
////         .csrf().disable()//关闭csrf防护
////        .authorizeExchange()
////         .pathMatchers("/login","/logout").permitAll()//登陆登出接口无需权限访问
////         .pathMatchers(HttpMethod.OPTIONS).permitAll()//OPTIONS请求访问无需权限访问
////         .anyExchange().authenticated()
////         .and()
////         .httpBasic()
////         .and()
////         .exceptionHandling()
////         .authenticationEntryPoint(securityUnauthorizedHandler)//未认证处理器
////         .accessDeniedHandler(securityAuthenticationFailureHandler)//处理鉴权失败处理器
////         .and()
////         .formLogin().loginPage("/login")
////         .authenticationSuccessHandler(securitySuccessHandler) //认证成功
////         .authenticationFailureHandler(securityFailureHandler)//登陆验证失败
////        .and().logout().logoutUrl("/logout");
//        http
//            .authorizeExchange()
//            .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
//            .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
//            .anyExchange().authenticated()
//            .and()
//            .httpBasic()
//            .and()
//            .formLogin() //启动页面表单登陆,spring security 内置了一个登陆页面/login
//            .and().csrf().disable()//必须支持跨域
//            .logout().disable();
//        return http.build();
//    }
//
//    /**
//     * 密码加密工具
//     *
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
