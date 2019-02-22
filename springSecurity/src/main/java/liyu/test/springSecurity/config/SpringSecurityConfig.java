package liyu.test.springSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    private UserDetailsService getUserDetailService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
        return manager;
    }
	
	
    private DaoAuthenticationProvider getDaoAuthenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;
    }
	
	protected void configure(HttpSecurity http) throws Exception {
		UserDetailsService userDetailService = getUserDetailService();
		DaoAuthenticationProvider authenticationProvider = getDaoAuthenticationProvider(userDetailService);
		
		http
	    	.authenticationProvider(authenticationProvider)
	        .authorizeRequests()
	        	.antMatchers("/static/**").permitAll()    
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .and()
	        .logout()
	        	.and()
        	.csrf().disable()
	        .httpBasic();
	}
	

}
