package org.dev.framework.config;

import org.dev.framework.modules.sys.service.impl.SysUserServiceImpl;
import org.dev.framework.security.MyAccessDecisionManager;
import org.dev.framework.security.MySecurityMetadataSource;
import org.dev.framework.security.filter.JWTAuthorizationFilter;
import org.dev.framework.security.filter.LoginAuthenticationFilter;
import org.dev.framework.security.handler.*;
import org.dev.framework.modules.sys.service.impl.SysUserServiceImpl;
import org.dev.framework.security.*;
import org.dev.framework.security.filter.JWTAuthorizationFilter;
import org.dev.framework.security.filter.LoginAuthenticationFilter;
import org.dev.framework.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    protected UserDetailsService userDetailsService() {

        return new SysUserServiceImpl();
    }

    /***
     * 密码加密方式
     * @return
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(10);
    }

    /***
     * 登录成功处理类
     * @return
     */
    @Bean
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    /***
     * 认证失败处理类
     * @return
     */
    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }


    /***
     * 认证异常处理类
     * @return
     */
    @Bean
    protected AuthenticationEntryPoint authenticationEntryPoint() {

        return new MyAuthenticationEntryPointHandler();
    }

    @Bean
    LogoutHandler logoutHandler() {

        return new MyLogoutHandler();
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }


    @Bean
    FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new MySecurityMetadataSource();
    }

    @Bean
    AccessDecisionManager accessDecisionManager() {

        return new MyAccessDecisionManager();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }


    /***
     * 配置用户验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN", "USER")
//                .and()
//                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER");
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 设置忽略验证的URL
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/wflowDefine/view","/wflowChart/traceprocess","/wflowChart/showImg")
                .antMatchers("/favicon.ico");
    }

    /**
     * 配置如何通过拦截器保护请求
     * 指定哪些请求需要认证，哪些请求不需要认证，以及所需要的权限
     * 通过调用authorizeRequests()和anyRequest().authenticated()就会要求所有进入应用的HTTP请求都要进行认证
     * <p>
     * 方法描述
     * anonymous()                                        允许匿名用户访问
     * authenticated()                                    允许经过认证的用户访问
     * denyAll()                                          无条件拒绝所有访问
     * fullyAuthenticated()                如果用户是完整的话（不是通过Remember-me功能认证的），就允许访问
     * hasAnyAuthority(String...)                 如果用户具备给定权限中的某一个的话，就允许访问
     * hasAnyRole(String...)                    如果用户具备给定角色中的某一个的话，就允许访问
     * hasAuthority(String)                     如果用户具备给定权限的话，就允许访问
     * hasIpAddress(String)                    如果请求来自给定IP地址的话，就允许访问
     * hasRole(String)                        如果用户具备给定角色的话，就允许访问
     * not()                               对其他访问方法的结果求反
     * permitAll()                           无条件允许访问
     * rememberMe()                          如果用户是通过Remember-me功能认证的，就允许访问
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login").permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
                        o.setAccessDecisionManager(accessDecisionManager());
                        return o;
                    }
                }).and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .and()   // 不需要session
                .addFilter(new LoginAuthenticationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())//登录失败
                .failureHandler(authenticationFailureHandler())//登录成功
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(logoutHandler())
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll()
                .and()
                .csrf()
                .disable();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
