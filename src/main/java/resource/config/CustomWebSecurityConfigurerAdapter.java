package resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import resource.filter.AddHeaderFilter;

@Configuration
public class CustomWebSecurityConfigurerAdapter
        extends WebSecurityConfigurerAdapter {
    @Bean
    public AddHeaderFilter authenticationTokenFilterBean() throws Exception {
        return new AddHeaderFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不起作用
        http.addFilterBefore(authenticationTokenFilterBean(), AbstractPreAuthenticatedProcessingFilter.class);
        //http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}