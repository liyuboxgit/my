/**
 * @Title: Organization.java
 * @Package: OThinker.H3.Portal.webservices
 * @Description: Organization
 * @Author: linjh
 * @Date: 2017年9月5日 上午10:48:35
 * @Version: V1.0
 */
package liyu.test.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @ClassName: Organization
 * @Description: 定义客户端请求Organization所需要的接口
 * @Author: linjh
 * @Date: 2017年9月5日 上午10:48:35
 */
@WebService(targetNamespace="http://aozhe.com/",serviceName="Organization")
public interface Organization {

	/**
	 * @Title: AddGroup
	 * @Description: 添加组
	 * @Param: @param modifier 请求变更的用户ID
	 * @Param: @param group 需要添加的群组
	 * @Param: @return
	 * @Return: HandleResult
	 * @throws Exception
	 */

	/**
	 * @Title: AddOrgUnit
	 * @Description: 增加组织机构单元
	 * @Param: @param modifier 请求变更的ID
	 * @Param: @param orgUnit 需要添加的组织机构单元
	 * @Param: @return
	 * @Return: HandleResult
	 * @throws Exception
	 */
	/**
	 * @Title: AddUser
	 * @Description: 添加用户
	 * @Param: @param modifier 请求变更的ID
	 * @Param: @param user 需要添加的用户
	 * @Param: @return
	 * @Return: HandleResult
	 * @throws Exception
	 */
	@WebMethod
	public String addUser(@WebParam(name="modifier") String modifier, @WebParam(name="user") User user) throws Exception;

	/**
	 * @Title: GetFullName
	 * @Description: 获得组织机构命名
	 * @Param: @param unitID 组织机构唯一标识符
	 * @Param: @return
	 * @Return: String
	 * @throws Exception
	 */
	@WebMethod
	public String getFullName(@WebParam(name="unitID") String unitID) throws Exception;

	/**
	 * @Title: GetManager
	 * @Description: 获取用户经理
	 * @Param: @param ID 组织机构ID
	 * @Param: @return
	 * @Return: String
	 * @throws Exception
	 */
	@WebMethod
	public String getManager(@WebParam(name="userID") String userID) throws Exception;

	/**
	 * @Title: GetName
	 * @Description: 获取组织的显示名称
	 * @Param: @param userID 用户ID
	 * @Param: @return
	 * @Return: String
	 * @throws Exception
	 */
	@WebMethod
	public String getName(@WebParam(name="userID") String userID) throws Exception;

	/**
	 * @Title: GetParent
	 * @Description: 获取父节点
	 * @Param: @param userID 组织ID
	 * @Param: @return
	 * @Return: String
	 * @throws Exception
	 */
	@WebMethod
	public String getParent(@WebParam(name="userID") String userID) throws Exception;

	/**
	 * @Title: IsAncestor
	 * @Description: 是否具有父子关系
	 * @Param: @param childID 子组织机构ID
	 * @Param: @param ancestorID 父节点ID
	 * @Param: @return
	 * @Return: boolean
	 * @throws Exception
	 */
	@WebMethod
	public boolean isAncestor(@WebParam(name="childID") String childID, @WebParam(name="ancestorID") String ancestorID) throws Exception;

	/**
	 * 添加角色
	 * @param orgPost 角色信息
	 * @return true if success else false
	 */

	@WebMethod
	public WebServiceResult updateUser(@WebParam(name="user") User user);

}
