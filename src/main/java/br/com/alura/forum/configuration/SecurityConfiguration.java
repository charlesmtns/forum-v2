package br.com.alura.forum.configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.com.alura.forum.security.JwtAuthenticationFilter;
import br.com.alura.forum.security.jwt.TokenManager;
import br.com.alura.forum.security.service.UsersService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsersService usersService;

	@Autowired
	private TokenManager tokenManager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/api/topics/**").permitAll().antMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated().and().cors().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new JwtAuthenticationFilter(tokenManager, usersService),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(usersService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/confguration/**",
				"/swagger-resources/**");
	}

	private static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

		private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {

			logger.error("Um acesso não autorizado foi verificado. Mensagem: {}", authException.getMessage());

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Você não está autorizado a acessar esse recurso.");

		}
	}
}
