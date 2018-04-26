package liyu.test.sb2_0.configure.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

public class ShiroSessionDao extends AbstractSessionDAO{

	private ShiroSessionRepository shiroSessionRepository;

	public void update(Session session) throws UnknownSessionException {
		 getShiroSessionRepository().saveSession(session);  
	}

	public void delete(Session session) {
		if (session == null) {
			return;
		}
		Serializable id = session.getId();
		if (id != null)
			getShiroSessionRepository().deleteSession(id);
	}

	public Collection<Session> getActiveSessions() {
		 return getShiroSessionRepository().getAllSessions();  
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
        getShiroSessionRepository().saveSession(session);  
        return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		 return getShiroSessionRepository().getSession(sessionId);  
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	} 
	
	
}
