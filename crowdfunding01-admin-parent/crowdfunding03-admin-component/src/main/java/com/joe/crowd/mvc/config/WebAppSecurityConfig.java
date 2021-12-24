package com.joe.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 当前类为配置类
@Configuration
// 启用web环境下权限控制功能
@EnableWebSecurity
// 启用全局方法权限控制功能， 并且设置 prePostEnabled = true
// 保证@PreAuthority、@PostAuthority、 @PreFilter、 @PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                // 对请求进行授权
                .authorizeRequests()
                // 针对登录页进行设置
                .antMatchers("/admin/to/login/page.html")
                .permitAll()
                // 针对静态资源进行设置， 无条件访问
                .antMatchers("/bootstrap/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/js/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                // 访问 Admin分页功能时具备“经理” 角色
                .antMatchers("/admin/get/page.html")
                .hasRole("manager")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception", new Exception("Access Denied"));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest, httpServletResponse);
                    }
                })
                .and()
                .csrf()
                // 关闭防跨站请求伪造
                .disable()
                // 开启表单登录功能
                .formLogin()
                // 指定登录页面
                .loginPage("/admin/to/login/page.html")
                // 指定处理登录请求的地址
                .loginProcessingUrl("/security/do/login.html")
                //指定登录成功后前往的地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 账号的请求参数名称
                .usernameParameter("loginAcct")
                // 密码的请求参数名称
                .passwordParameter("userPswd")
                .and()
                // 退出登录功能
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
}
