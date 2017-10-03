package brewer.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		//servletContext.getSessionCookieConfig().setMaxAge(20); //Quanto tempo esa sessão vai viver em segundos independetemente de ação do usuário
		
		/* 
		 * Força o gerenciamento da sessão através de cookie para o JSessionID não aparecer na URL
		 */
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE)); 
		
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		
	}
	
}
