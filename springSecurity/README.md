springsecurity defined a filter named FilterChainProxy.
this filter as normal filter,but it can invoke some springsecurity's filter which implication Filter interface looped.
after all the filters invoke finished.than execute the normal Filter. additional the servlet filter like a stack data strut.  

FilterChainProxy[Filter Chains: [[ 
org.springframework.security.web.util.matcher.AnyRequestMatcher@1, [
	 org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@571a01f9,
	 org.springframework.security.web.context.SecurityContextPersistenceFilter@6f1a16fe, 
	 org.springframework.security.web.header.HeaderWriterFilter@70730db, 
	 org.springframework.security.web.authentication.logout.LogoutFilter@77865933, 
	 org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@5c723f2d, 
	 org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@57562473, 
	 org.springframework.security.web.authentication.www.BasicAuthenticationFilter@2d7a9786, 
	 org.springframework.security.web.savedrequest.RequestCacheAwareFilter@33634f04, 
	 org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@765ffb14, 
	 org.springframework.security.web.authentication.AnonymousAuthenticationFilter@d7109be, 
	 org.springframework.security.web.session.SessionManagementFilter@12704e15, 
	 org.springframework.security.web.access.ExceptionTranslationFilter@15405bd6, 
	 org.springframework.security.web.access.intercept.FilterSecurityInterceptor@61bfc9bf]
]]]

org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler