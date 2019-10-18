
package liyu.test.wscli;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the liyu.test.wscli package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetFullNameResponse_QNAME = new QName("http://aozhe.com/", "getFullNameResponse");
    private final static QName _GetManagerResponse_QNAME = new QName("http://aozhe.com/", "getManagerResponse");
    private final static QName _GetNameResponse_QNAME = new QName("http://aozhe.com/", "getNameResponse");
    private final static QName _AddUser_QNAME = new QName("http://aozhe.com/", "addUser");
    private final static QName _GetManager_QNAME = new QName("http://aozhe.com/", "getManager");
    private final static QName _GetName_QNAME = new QName("http://aozhe.com/", "getName");
    private final static QName _IsAncestorResponse_QNAME = new QName("http://aozhe.com/", "isAncestorResponse");
    private final static QName _GetParent_QNAME = new QName("http://aozhe.com/", "getParent");
    private final static QName _Exception_QNAME = new QName("http://aozhe.com/", "Exception");
    private final static QName _UpdateUser_QNAME = new QName("http://aozhe.com/", "updateUser");
    private final static QName _IsAncestor_QNAME = new QName("http://aozhe.com/", "isAncestor");
    private final static QName _GetFullName_QNAME = new QName("http://aozhe.com/", "getFullName");
    private final static QName _AddUserResponse_QNAME = new QName("http://aozhe.com/", "addUserResponse");
    private final static QName _UpdateUserResponse_QNAME = new QName("http://aozhe.com/", "updateUserResponse");
    private final static QName _GetParentResponse_QNAME = new QName("http://aozhe.com/", "getParentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: liyu.test.wscli
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetManager }
     * 
     */
    public GetManager createGetManager() {
        return new GetManager();
    }

    /**
     * Create an instance of {@link GetParent }
     * 
     */
    public GetParent createGetParent() {
        return new GetParent();
    }

    /**
     * Create an instance of {@link GetName }
     * 
     */
    public GetName createGetName() {
        return new GetName();
    }

    /**
     * Create an instance of {@link IsAncestorResponse }
     * 
     */
    public IsAncestorResponse createIsAncestorResponse() {
        return new IsAncestorResponse();
    }

    /**
     * Create an instance of {@link GetManagerResponse }
     * 
     */
    public GetManagerResponse createGetManagerResponse() {
        return new GetManagerResponse();
    }

    /**
     * Create an instance of {@link GetFullNameResponse }
     * 
     */
    public GetFullNameResponse createGetFullNameResponse() {
        return new GetFullNameResponse();
    }

    /**
     * Create an instance of {@link AddUser }
     * 
     */
    public AddUser createAddUser() {
        return new AddUser();
    }

    /**
     * Create an instance of {@link GetNameResponse }
     * 
     */
    public GetNameResponse createGetNameResponse() {
        return new GetNameResponse();
    }

    /**
     * Create an instance of {@link UpdateUser }
     * 
     */
    public UpdateUser createUpdateUser() {
        return new UpdateUser();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link IsAncestor }
     * 
     */
    public IsAncestor createIsAncestor() {
        return new IsAncestor();
    }

    /**
     * Create an instance of {@link AddUserResponse }
     * 
     */
    public AddUserResponse createAddUserResponse() {
        return new AddUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link GetFullName }
     * 
     */
    public GetFullName createGetFullName() {
        return new GetFullName();
    }

    /**
     * Create an instance of {@link GetParentResponse }
     * 
     */
    public GetParentResponse createGetParentResponse() {
        return new GetParentResponse();
    }

    /**
     * Create an instance of {@link WebServiceResult }
     * 
     */
    public WebServiceResult createWebServiceResult() {
        return new WebServiceResult();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFullNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getFullNameResponse")
    public JAXBElement<GetFullNameResponse> createGetFullNameResponse(GetFullNameResponse value) {
        return new JAXBElement<GetFullNameResponse>(_GetFullNameResponse_QNAME, GetFullNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetManagerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getManagerResponse")
    public JAXBElement<GetManagerResponse> createGetManagerResponse(GetManagerResponse value) {
        return new JAXBElement<GetManagerResponse>(_GetManagerResponse_QNAME, GetManagerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getNameResponse")
    public JAXBElement<GetNameResponse> createGetNameResponse(GetNameResponse value) {
        return new JAXBElement<GetNameResponse>(_GetNameResponse_QNAME, GetNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "addUser")
    public JAXBElement<AddUser> createAddUser(AddUser value) {
        return new JAXBElement<AddUser>(_AddUser_QNAME, AddUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetManager }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getManager")
    public JAXBElement<GetManager> createGetManager(GetManager value) {
        return new JAXBElement<GetManager>(_GetManager_QNAME, GetManager.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getName")
    public JAXBElement<GetName> createGetName(GetName value) {
        return new JAXBElement<GetName>(_GetName_QNAME, GetName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAncestorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "isAncestorResponse")
    public JAXBElement<IsAncestorResponse> createIsAncestorResponse(IsAncestorResponse value) {
        return new JAXBElement<IsAncestorResponse>(_IsAncestorResponse_QNAME, IsAncestorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getParent")
    public JAXBElement<GetParent> createGetParent(GetParent value) {
        return new JAXBElement<GetParent>(_GetParent_QNAME, GetParent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "updateUser")
    public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
        return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAncestor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "isAncestor")
    public JAXBElement<IsAncestor> createIsAncestor(IsAncestor value) {
        return new JAXBElement<IsAncestor>(_IsAncestor_QNAME, IsAncestor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFullName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getFullName")
    public JAXBElement<GetFullName> createGetFullName(GetFullName value) {
        return new JAXBElement<GetFullName>(_GetFullName_QNAME, GetFullName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "addUserResponse")
    public JAXBElement<AddUserResponse> createAddUserResponse(AddUserResponse value) {
        return new JAXBElement<AddUserResponse>(_AddUserResponse_QNAME, AddUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "updateUserResponse")
    public JAXBElement<UpdateUserResponse> createUpdateUserResponse(UpdateUserResponse value) {
        return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME, UpdateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://aozhe.com/", name = "getParentResponse")
    public JAXBElement<GetParentResponse> createGetParentResponse(GetParentResponse value) {
        return new JAXBElement<GetParentResponse>(_GetParentResponse_QNAME, GetParentResponse.class, null, value);
    }

}
