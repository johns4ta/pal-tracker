package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private Boolean disableHttps;

    SecurityConfiguration(@Value("${https.disabled}") Boolean disableHttps){
        this.disableHttps = disableHttps;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        if (!disableHttps)
            httpSecurity.requiresChannel().anyRequest().requiresSecure();

        httpSecurity.authorizeRequests().antMatchers("/**").hasRole("USER").and().httpBasic().and().csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");

    }
}
