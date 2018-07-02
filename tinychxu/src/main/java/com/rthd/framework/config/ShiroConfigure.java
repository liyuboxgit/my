package com.rthd.framework.config;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedHashMap;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rthd.framework.config.shiro.AuthRealm;
import com.rthd.framework.config.shiro.JedisPoolManager;
import com.rthd.framework.config.shiro.Matcher;
import com.rthd.framework.config.shiro.RedisCache;
import com.rthd.framework.config.shiro.SessionControlFilter;
import com.rthd.framework.config.shiro.ShiroSessionDao;
import com.rthd.framework.config.shiro.ShiroSessionRedisRepostory;
import com.rthd.framework.config.shiro.ShiroSessionRepository;
import com.rthd.framework.util.Conf;


@Configuration
public class ShiroConfigure {
	
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher() {
		return new Matcher();
	}
	
	@Bean(name = "authRealm")
	public AuthRealm authRealm(
			@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}
	
	@Bean(name = "jedisPoolManager")
	public JedisPoolManager jedisPoolManager() {
		return new JedisPoolManager(Conf.get().getRedis_ip(), Conf.get().getRedis_port());
	}
	
	@Bean(name = "shiroSessionRepository")
	public ShiroSessionRepository shiroSessionRepository(
			@Qualifier("jedisPoolManager") JedisPoolManager jedisPoolManager) {
		ShiroSessionRedisRepostory redisRepostory = new ShiroSessionRedisRepostory();
		redisRepostory.setJedisPoolManager(jedisPoolManager);
		return redisRepostory;
	}
	
	@Bean(name = "shiroSessionDao")
	public ShiroSessionDao shiroSessionDao(
			@Qualifier("shiroSessionRepository") ShiroSessionRepository shiroSessionRepository) {
		ShiroSessionDao shiroSessionDao = new ShiroSessionDao();
		shiroSessionDao.setShiroSessionRepository(shiroSessionRepository);
		return shiroSessionDao;
	}
	
	@Bean(name = "webSessionManager")
	public WebSessionManager webSessionManager(
			@Qualifier("shiroSessionDao") ShiroSessionDao shiroSessionDao) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(Long.valueOf(Conf.get().getSession_timeout()));
		sessionManager.setSessionDAO(shiroSessionDao);
		return sessionManager;
	}
	
	@Bean(name = "securityManager")
	public SecurityManager securityManager(
			@Qualifier("authRealm") AuthRealm authRealm,
			@Qualifier("webSessionManager") WebSessionManager webSessionManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		manager.setSessionManager(webSessionManager);
		return manager;
	}
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(
			@Qualifier("securityManager") SecurityManager manager,
			@Qualifier("webSessionManager") WebSessionManager webSessionManager,
			@Qualifier("jedisPoolManager") JedisPoolManager jedisPoolManager) {
		
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录，权限拦截路径
		bean.setLoginUrl(Conf.get().getLogin_url());
		bean.setUnauthorizedUrl(Conf.get().getUnauthorized_url());
		
		SessionControlFilter sessionControlFilter = new SessionControlFilter();
		sessionControlFilter.setSessionManager(webSessionManager);
		RedisCache<String, Deque<Serializable>> cache = new RedisCache<String, Deque<Serializable>>();
		cache.setJedisPoolManager(jedisPoolManager);
		sessionControlFilter.setCache(cache);
		sessionControlFilter.setMaxSession(Integer.valueOf(Conf.get().getMax_session()));
		
	    bean.getFilters().put("kickout", sessionControlFilter);
	    
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		for(String el:Conf.get().getAnon().split(",")) 	filterChainDefinitionMap.put(el, "anon");
		for(String el:Conf.get().getAuthc().split(",")) filterChainDefinitionMap.put(el, "authc,kickout");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return bean;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

}
