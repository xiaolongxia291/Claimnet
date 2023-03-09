package tracy.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tracy.manage.entity.Admin;
import tracy.manage.service.AdminService;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminService adminService;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //只有super管理员才能访问此模块
                .antMatchers("/admin/**")
                .hasRole("super");
        http.authorizeRequests()
                //只有data、super管理员才能访问此模块
                .antMatchers("/crud/**")
                .hasAnyRole("data","super");

        //没有权限默认会跳到登录页面
        http.formLogin();

        //开启了注销功能
        http.logout();//需要访问localhost:8080/logout才能注销
    }

    //认证
    //密码需要编码，否则会报错
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<Admin> admins=adminService.list();

        for(Admin admin:admins){
            auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser(admin.getUsername()).password(new BCryptPasswordEncoder().encode(admin.getPassword())).roles(admin.getRole());
        }
    }
}
