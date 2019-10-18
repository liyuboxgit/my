package liyu.test.wscli;

public class Main {
	public static void main(String[] args) {
		OrganizationImplService service = new OrganizationImplService();
		OrganizationImpl impl = service.getOrganizationImplPort();
		try {
			String name = impl.getName("impl");
			System.out.println(name);
		} catch (Exception_Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(impl.addUser("name", new User()));
		} catch (Exception_Exception e) {
			e.printStackTrace();
		}
	}
}
