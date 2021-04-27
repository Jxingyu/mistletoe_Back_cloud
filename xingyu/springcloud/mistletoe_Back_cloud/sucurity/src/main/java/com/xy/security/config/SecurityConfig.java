package com.xy.security.config;

//import com.xy.security.common.JwtAuthenticationTokenFilter;
import com.xy.security.common.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 跨域
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// 允许方法上使用一些注解 /*访问方法前进行权限校验*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;// 注入方式

//    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()// 禁用跨域攻击-- ring Security 3默认关闭csrf，Spring Security 4默认启动了csrf。如果不采用csrf，可禁用security的csrf ,加上 .csrf().disable()即可。
//                .cors()
//                .and()
                .sessionManagement()// 基于token，所以不需要 securityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login/check", "/code", "/register/check", "/user/registerIconUpdate"
                        ,"/login/checkAccessToUri", "/utr/select/user", "/utr/select/userByTeamId", "/utr/selectUtr", "/utr/insertUserTeam", "/utr/deleteUtrIdInUtr"
                        , "/utr/select/captain", "/utr/select/NowCaptain", "/utr/selectUcr", "/team/findTeamById"
                        , "/sign/findSignById", "/daily/selectDailyId", "/daily/selectDailyEditMesByUserId/**"
                        , "/daily/selectDailyById/**", "/daily/updateDraftDaily", "/daily/updateDailyStatus/**","/LoginRecodes/SelectAll","/LoginRecodes/SelectAll/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/**").permitAll() //都可以访问
                // .antMatchers("/users/**").hasRole("ADMIN") //需要相应的角色才能访问(某些路径（接口）需要什么权限或者什么角色才可以访问)
                .anyRequest().authenticated() // 任何请求都需要认证
                .and()
//                .formLogin() //基于Form表单登录验证
//                .and()
//                .userDetailsService(userDetailsServiceBean()); // 掉函数方式
                .userDetailsService(userDetailsService);// 注入方式 // 提供一个通过登录名查询用户实体的函数
        // 放到账号密码认证前的一个过滤器里 让自定义的过滤器生效 通过权限对比用
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "http://localhost:8081/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security"

            // other public endpoints of your API may be appended to this array
    };
//    @Autowired
//    //   这是将用户存入内存的第一种方式（== 放在代码中）
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication() //认证信息存储到内存中
//                .passwordEncoder(passwordEncoder())
//                .withUser("chenlei").password(passwordEncoder()
//                .encode("chenlei"))
//                .roles("ADMIN")
//                .authorities("wx:product:read","wx:product:delete");
//    }

    //   这是将用户存入内存的第二种方式
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(new User("chenlei",passwordEncoder().encode("chenlei"),     AuthorityUtils.createAuthorityList("wx:product:read","wx:product:delete")));
//        return manager;
//    }

    // 这是将用户存入数据库的方式

    //   这是密码加密解密器 可以用于加密密码也可以用户对比原始密码与加密密码，你只需暴露，security会将你暴露的passwordEncoder 作为默认的
    //   passwordEncoder.encode()  用于加密密码
    //   passwordEncoder.matchs(原始密码，加密密码)  对比原始密码与加密密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
