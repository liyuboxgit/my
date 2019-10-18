/**
 * @Title: OrganizationImpl.java
 * @Package: OThinker.H3.Portal.webservices.impl
 * @Description: OrganizationImpl
 * @Author: linjh
 * @Date: 2017年9月5日 上午11:56:06
 * @Version: V1.0
 */
package liyu.test.ws.impl;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import liyu.test.ws.Organization;
import liyu.test.ws.User;
import liyu.test.ws.WebServiceResult;
import liyu.test.ws.util.PortalPropertiesUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: OrganizationImpl
 * @Description: 定义客户端请求Organization所需要的接口实现类
 * @Author: linjh
 * @Date: 2017年9月5日 上午11:56:06
 */
@WebService(targetNamespace="http://aozhe.com/")
public class OrganizationImpl implements Organization {

	

	/**
	 * <p>Title: AddGroup</p>
	 * <p>Description: 添加组</p>
	 * @param modifier 请求变更的用户ID
	 * @param group 需要添加的群组
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#AddGroup(java.lang.String, OThinker.Common.Organization.Models.Group)
	 */
	
	/**
	 * <p>Title: AddOrgUnit</p>
	 * <p>Description: 增加组织机构单元</p>
	 * @param modifier 请求变更的ID
	 * @param orgUnit 需要添加的组织机构单元
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#AddOrgUnit(java.lang.String, OThinker.Common.Organization.Models.OrganizationUnit)
	 */
	
	/**
	 * <p>Title: AddUser</p>
	 * <p>Description: 添加用户</p>
	 * @param modifier 请求变更的ID
	 * @param user 需要添加的用户
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#AddUser(java.lang.String, OThinker.Common.Organization.Models.User)
	 */
	@Override
	public String addUser(String modifier, User user) throws Exception {
		System.out.println("3-OrganizationImpl.AddUser()");
		System.out.println("Modifier:" + modifier + ",User:" + user.toString());

		return "ok";
	}

	/**
	 * <p>Title: GetFullName</p>
	 * <p>Description: 获得组织机构命名</p>
	 * @param unitID 组织机构唯一标识符
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#GetFullName(java.lang.String)
	 */
	@Override
	public String getFullName(String unitID) throws Exception {
		System.out.println("4-OrganizationImpl.GetFullName()");
		System.out.println("UnitID:" + unitID);
		//获取组织机构完整的名称
		return "ok";
	}

	/**
	 * <p>Title: GetManager</p>
	 * <p>Description: 获取用户经理</p>
	 * @param userID 组织机构ID
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#GetManager(java.lang.String)
	 */
	@Override
	public String getManager(String userID) throws Exception {
		System.out.println("5-OrganizationImpl.GetManager()");
		System.out.println("userID:" + userID);
		//返回组织对象的经理
		return "ok";
	}

	/**
	 * <p>Title: GetName</p>
	 * <p>Description: 获取组织机构名称</p>
	 * @param userID 用户ID
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#GetName(java.lang.String)
	 */
	@Override
	public String getName(String userID) throws Exception {
		System.out.println("6-OrganizationImpl.GetName()");
		System.out.println("UserID:" + userID);
		//获取组织机构名称
		return "ok";
	}

	/**
	 * <p>Title: GetParent</p>
	 * <p>Description: 获取上级OU</p>
	 * @param userID 组织ID
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#GetParent(java.lang.String)
	 */
	@Override
	public String getParent(String userID) throws Exception {
		System.out.println("7-OrganizationImpl.GetParent()");
		System.out.println("ID:" + userID);
		//获取上级OU
		return "ok";
	}

	/**
	 * <p>Title: IsAncestor</p>
	 * <p>Description: 获取一个组织是否另一个组织的父级元素</p>
	 * @param childID 子组织机构ID
	 * @param ancestorID 父节点ID
	 * @return
	 * @throws Exception
	 * @see OThinker.H3.Portal.webservices.Organization#IsAncestor(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isAncestor(String childID, String ancestorID) throws Exception {
		System.out.println("8-OrganizationImpl.IsAncestor()");
		System.out.println("ChildID:" + childID + ",AncestorID:" + ancestorID);
		//获取一个组织是否另一个组织的父级元素
		return true;
	}

	@Override
	public WebServiceResult updateUser(User user) {
		String objectID = user.getObjectID();
		return WebServiceResult.success("update user success");
	}

	
	public static Endpoint startService(){
		OrganizationImpl organizationImpl = new OrganizationImpl();
		String address= PortalPropertiesUtil.getProperty("OrganizationAddress");

		try {
			InetAddress ia = InetAddress.getLocalHost();
			address = address.replace("127.0.0.1", ia.getHostAddress());
			System.out.println(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return Endpoint.publish(address, organizationImpl);
	}

	public static void main(String[] args) {
		startService();
	}
}