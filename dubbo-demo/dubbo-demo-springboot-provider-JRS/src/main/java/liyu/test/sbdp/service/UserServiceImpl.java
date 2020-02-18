package liyu.test.sbdp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.dubbo.demo.User;
import org.apache.dubbo.demo.UserService;
import javax.ws.rs.core.MediaType;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

@Path("users")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class UserServiceImpl implements UserService{
	@GET
    @Path("{id : \\d+}")
	public User getUser(Long id) {
		return new User(id);
	}
	@POST
    @Path("register")
	public User registUser(User user) {
		System.out.println(user.getId());
		return user;
	}

}
