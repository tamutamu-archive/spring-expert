package brewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import brewer.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/css/**")
		.antMatchers("/js/**")
		.antMatchers("/images/**")
		.antMatchers("/layout/**");		
	}
	
	/*
	 * hasRole => é necessário concatenar ROLE_ ou a permissão deve estar gravada com ROLE_CADASTRAR_CIDADE 
	 * no banco de dados 
	 * 
	 * hasAuthority => suprime a necessidade de concatenar ROLE_ ou estar gravada de forma completa ROLE_CADASTAR_CIDADE
	 * no banco de dados
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")	
				.antMatchers("/usuarios/novo").hasRole("CADASTRAR_USUARIO")
				.anyRequest().authenticated()	//Está deve ser a última regra antes do formLogin()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.exceptionHandling()
					.accessDeniedPage("/403")
				.and()
				.sessionManagement()
					.invalidSessionUrl("/login");	//Sessao inválida ... faça o login novamente (pode ser customizada outra página informando que a sessão foi expirada
								
				/* Gerenciamento de Sessão 
				.sessionManagement()
					.maximumSessions(1)
					.expiredUrl("/login");	//É possível customizar uma página para informar que a sessão expirou
				
				*/
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
