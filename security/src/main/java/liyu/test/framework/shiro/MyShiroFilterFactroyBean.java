package liyu.test.framework.shiro;

import java.util.List;
import java.util.Map;

import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import liyu.test.security.entity.Button;
import liyu.test.security.entity.Module;
import liyu.test.security.service.ButtonService;
import liyu.test.security.service.ModuleService;

/**
 * 
 * @author Administrator
 *
 */
public class MyShiroFilterFactroyBean extends ShiroFilterFactoryBean{
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ButtonService buttonService;
	
	private String loginSubmitUrl;
	@Override
	public void setFilterChainDefinitions(String definitions) {
		super.setFilterChainDefinitions(definitions);
       
		Map<String, String> definitionMap = super.getFilterChainDefinitionMap();
        Ini db = new Ini();
        String content = getFilterContent();
        if(content.length()>0){        	
        	db.load(content);
        }
        
        definitionMap.putAll(db.getSection(""));   
    }
	
	private String getFilterContent(){
		List<Module> list = moduleService.query();
		StringBuffer sb = new StringBuffer();
		for (Module module : list) {
			if(module.getPid()>0){
				String code = module.getUrl();
				if(!StringUtils.isEmpty(code)){
					String trim = code.trim();
					List<Button> buttons = buttonService.query();
					if(!buttons.isEmpty()){
						for(Button btn:buttons){
							sb.append("/"+trim+"/"+btn.getCode()+"=perms["+trim+":"+btn.getCode()+"]"+"\n");
						}
					}
					
				}
			}
		}
		sb.append(loginSubmitUrl+"=anon"+"\n");
		sb.append("/**=authc");
		return sb.toString();
		
	}

	public String getLoginSubmitUrl() {
		return loginSubmitUrl;
	}

	public void setLoginSubmitUrl(String loginSubmitUrl) {
		this.loginSubmitUrl = loginSubmitUrl;
	}
}
