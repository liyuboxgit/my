ShiroFilterFactoryBean
1 construct
1.1 
2 postProcessBeforeInit 
2.1 set loginUrl to AccessControlFilter
2.2 set successUrl to AuthenticationFilter
2.3 set unauthorizedUrl AuthorizationFilter
3 getObject 
3.1 create instance of AbstractShiroFilter(AbstractBean can't instanted,the fact is SpringShiroFileter)
	PathMatchingFilterChainResolver chainResolver <-- FilterChainManager
	SpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
	
{	
	anon=anon, 
	authc=authc, 
	authcBasic=authcBasic, 
	logout=logout, 
	noSessionCreation=noSessionCreation, 
	perms=perms, 
	port=port, 
	rest=rest, 
	roles=roles, 
	ssl=ssl, 
	user=user
}

OncePerRequestFilter:
public final void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
        if ( request.getAttribute(alreadyFilteredAttributeName) != null ) {
            filterChain.doFilter(request, response);
        } else if (!isEnabled(request, response) || shouldNotFilter(request) ) {
            filterChain.doFilter(request, response);
        } else {
            try {
                doFilterInternal(request, response, filterChain);
            } finally {
                request.removeAttribute(alreadyFilteredAttributeName);
            }
        }
    }
}

AbstractShiroFilter:
protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, final FilterChain chain)
            throws ServletException, IOException {
   	final Subject subject = createSubject(request, response);
   	subject.execute(new Callable() {
        public Object call() throws Exception {
            updateSessionLastAccessTime(request, response);
            executeChain(request, response, chain);
            return null;
        }
    }); 
}
