/**
 * @Title: User.java
 * @Package: OThinker.H3.Portal.webservices.Entity
 * @Description: User
 * @Author: linjh
 * @Date: 2017年9月6日 下午8:20:36
 * @Version: V1.0
 */
package liyu.test.ws;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: 用于Organization接口的参数
 * @Author: linjh
 * @Date: 2017年9月6日 下午8:20:36
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 8540161105264546864L;
	
	private String appellation;
	private String bankAccount;
	private String bankCity;
	private String bankName;
	private String bankProvince;
	private Date birthday;
	private String calendarID;
	private String code;
	private String costCenter;
	private Date createdTime;
	private String defaultLanguage;
	private Date departureDate;
	private String description;
	private String dingTalkAccount;
	private Boolean doLock;
	private String email;
	private String employeeNumber;
	private Integer employeeRank;
	private Date entryDate;
	private String extend1;
	private String extend2;
	private String facsimileTelephoneNumber;
	private Integer gender;
	private String idNumber;
	private String imageID;
	private String imageUrl;
	private Boolean isIsAdministrator;
	private Boolean isIsConsoleUser;
	private Boolean isIsSystemUser;
	private Boolean isIsVirtualUser;
	private String jpushID;
	private String managerID;
	private Date modifiedTime;
	private String mobile;
	private String mobileToken;
	private Integer mobileType;
	private String name;
	private Integer notifyType;
	private String objectID;
	private String officePhone;
	private String parentID;
	private Integer parentIndex;
	private String parentObjectID;
	private String parentPropertyName;
	private String password;
	private String postalCode;
	private String postOfficeBox;
	private Integer privacyLevel;
	private String qq;
	private String relationUserID;
	private String rtx;
	//private String secretaryID = null;
	private Integer serviceState;
	private Integer sortKey;
	private String sourceID;
	private String sid;
	private String skype;
	private Integer state;
	private Integer visibility;
	private String weChatAccount;
	private String modifier;
	
	/**
	 * @Title: getAppellation
	 * @Description: getAppellation
	 * @Return: appellation
	 */
	public String getAppellation() {
		return appellation;
	}
	/**
	 * @Title: setAppellation
	 * @Description: setAppellation
	 * @Param appellation the appellation to set
	 */
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	/**
	 * @Title: getBankAccount
	 * @Description: getBankAccount
	 * @Return: bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}
	/**
	 * @Title: setBankAccount
	 * @Description: setBankAccount
	 * @Param bankAccount the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	/**
	 * @Title: getBankCity
	 * @Description: getBankCity
	 * @Return: bankCity
	 */
	public String getBankCity() {
		return bankCity;
	}
	/**
	 * @Title: setBankCity
	 * @Description: setBankCity
	 * @Param bankCity the bankCity to set
	 */
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	/**
	 * @Title: getBankName
	 * @Description: getBankName
	 * @Return: bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @Title: setBankName
	 * @Description: setBankName
	 * @Param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @Title: getBankProvince
	 * @Description: getBankProvince
	 * @Return: bankProvince
	 */
	public String getBankProvince() {
		return bankProvince;
	}
	/**
	 * @Title: setBankProvince
	 * @Description: setBankProvince
	 * @Param bankProvince the bankProvince to set
	 */
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	/**
	 * @Title: getBirthday
	 * @Description: getBirthday
	 * @Return: birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @Title: setBirthday
	 * @Description: setBirthday
	 * @Param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @Title: getCalendarID
	 * @Description: getCalendarID
	 * @Return: calendarID
	 */
	public String getCalendarID() {
		return calendarID;
	}
	/**
	 * @Title: setCalendarID
	 * @Description: setCalendarID
	 * @Param calendarID the calendarID to set
	 */
	public void setCalendarID(String calendarID) {
		this.calendarID = calendarID;
	}
	/**
	 * @Title: getCode
	 * @Description: getCode
	 * @Return: code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @Title: setCode
	 * @Description: setCode
	 * @Param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @Title: getCostCenter
	 * @Description: getCostCenter
	 * @Return: costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}
	/**
	 * @Title: setCostCenter
	 * @Description: setCostCenter
	 * @Param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	/**
	 * @Title: getCreatedTime
	 * @Description: getCreatedTime
	 * @Return: createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @Title: setCreatedTime
	 * @Description: setCreatedTime
	 * @Param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @Title: getDefaultLanguage
	 * @Description: getDefaultLanguage
	 * @Return: defaultLanguage
	 */
	public String getDefaultLanguage() {
		return defaultLanguage;
	}
	/**
	 * @Title: setDefaultLanguage
	 * @Description: setDefaultLanguage
	 * @Param defaultLanguage the defaultLanguage to set
	 */
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	/**
	 * @Title: getDepartmentName
	 * @Description: getDepartmentName
	 * @Return: departmentName
	 */
	/*public String getDepartmentName() {
		return departmentName;
	}*/
	/**
	 * @Title: setDepartmentName
	 * @Description: setDepartmentName
	 * @Param departmentName the departmentName to set
	 */
	/*public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}*/
	/**
	 * @Title: getDepartureDate
	 * @Description: getDepartureDate
	 * @Return: departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	/**
	 * @Title: setDepartureDate
	 * @Description: setDepartureDate
	 * @Param departureDate the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	
	/**
	 * @Title: getDescription
	 * @Description: getDescription
	 * @Return: description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @Title: setDescription
	 * @Description: setDescription
	 * @Param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @Title: getDingTalkAccount
	 * @Description: getDingTalkAccount
	 * @Return: dingTalkAccount
	 */
	public String getDingTalkAccount() {
		return dingTalkAccount;
	}
	public Boolean getDoLock() {
		return doLock;
	}
	public void setDoLock(Boolean doLock) {
		this.doLock = doLock;
	}
	/**
	 * @Title: setDingTalkAccount
	 * @Description: setDingTalkAccount
	 * @Param dingTalkAccount the dingTalkAccount to set
	 */
	public void setDingTalkAccount(String dingTalkAccount) {
		this.dingTalkAccount = dingTalkAccount;
	}
	/**
	 * @Title: isDoLock
	 * @Description: isDoLock
	 * @Return: doLock
	 */
	public Boolean isDoLock() {
		return doLock;
	}
	
	/**
	 * @Title: setDoLock
	 * @Description: setDoLock
	 * @Param doLock the doLock to set
	 */
	public void setDoLock(boolean doLock) {
		this.doLock = doLock;
	}
	/**
	 * @Title: getEmail
	 * @Description: getEmail
	 * @Return: email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @Title: setEmail
	 * @Description: setEmail
	 * @Param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @Title: getEmployeeNumber
	 * @Description: getEmployeeNumber
	 * @Return: employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * @Title: setEmployeeNumber
	 * @Description: setEmployeeNumber
	 * @Param employeeNumber the employeeNumber to set
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * @Title: getEmployeeRank
	 * @Description: getEmployeeRank
	 * @Return: employeeRank
	 */
	public Integer getEmployeeRank() {
		return employeeRank;
	}
	/**
	 * @Title: setEmployeeRank
	 * @Description: setEmployeeRank
	 * @Param employeeRank the employeeRank to set
	 */
	public void setEmployeeRank(Integer employeeRank) {
		this.employeeRank = employeeRank;
	}
	/**
	 * @Title: getEntryDate
	 * @Description: getEntryDate
	 * @Return: entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}
	/**
	 * @Title: setEntryDate
	 * @Description: setEntryDate
	 * @Param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * @Title: getExtend1
	 * @Description: getExtend1
	 * @Return: extend1
	 */
	public String getExtend1() {
		return extend1;
	}
	/**
	 * @Title: setExtend1
	 * @Description: setExtend1
	 * @Param extend1 the extend1 to set
	 */
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	/**
	 * @Title: getExtend2
	 * @Description: getExtend2
	 * @Return: extend2
	 */
	public String getExtend2() {
		return extend2;
	}
	/**
	 * @Title: setExtend2
	 * @Description: setExtend2
	 * @Param extend2 the extend2 to set
	 */
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	/**
	 * @Title: getFacsimileTelephoneNumber
	 * @Description: getFacsimileTelephoneNumber
	 * @Return: facsimileTelephoneNumber
	 */
	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}
	/**
	 * @Title: setFacsimileTelephoneNumber
	 * @Description: setFacsimileTelephoneNumber
	 * @Param facsimileTelephoneNumber the facsimileTelephoneNumber to set
	 */
	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}
	/**
	 * @Title: getFavoriteAppCodes
	 * @Description: getFavoriteAppCodes
	 * @Return: favoriteAppCodes
	 */
	/*public String getFavoriteAppCodes() {
		return favoriteAppCodes;
	}*/
	/**
	 * @Title: setFavoriteAppCodes
	 * @Description: setFavoriteAppCodes
	 * @Param favoriteAppCodes the favoriteAppCodes to set
	 */
	/*public void setFavoriteAppCodes(String favoriteAppCodes) {
		this.favoriteAppCodes = favoriteAppCodes;
	}*/
	/**
	 * @Title: getGender
	 * @Description: getGender
	 * @Return: int
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * @Title: setGender
	 * @Description: setGender
	 * @Param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	/**
	 * @Title: getIdNumber
	 * @Description: getIdNumber
	 * @Return: iDNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}
	/**
	 * @Title: setIdNumber
	 * @Description: setIdNumber
	 * @Param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	/**
	 * @Title: getImageID
	 * @Description: getImageID
	 * @Return: imageID
	 */
	public String getImageID() {
		return imageID;
	}
	/**
	 * @Title: setImageID
	 * @Description: setImageID
	 * @Param imageID the imageID to set
	 */
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	/**
	 * @Title: getImageUrl
	 * @Description: getImageUrl
	 * @Return: imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @Title: setImageUrl
	 * @Description: setImageUrl
	 * @Param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @Title: isAdministrator
	 * @Description: isAdministrator
	 * @Return: isAdministrator
	 */
	public Boolean isIsAdministrator() {
		return isIsAdministrator;
	}
	/**
	 * @Title: setAdministrator
	 * @Description: setAdministrator
	 * @Param isAdministrator the isAdministrator to set
	 */
	public void setIsAdministrator(boolean isIsAdministrator) {
		this.isIsAdministrator = isIsAdministrator;
	}
	/**
	 * @Title: isConsoleUser
	 * @Description: isConsoleUser
	 * @Return: isConsoleUser
	 */
	public Boolean isIsConsoleUser() {
		return isIsConsoleUser;
	}
	/**
	 * @Title: setConsoleUser
	 * @Description: setConsoleUser
	 * @Param isConsoleUser the isConsoleUser to set
	 */
	public void setIsConsoleUser(boolean isIsConsoleUser) {
		this.isIsConsoleUser = isIsConsoleUser;
	}
	/**
	 * @Title: isSystemUser
	 * @Description: isSystemUser
	 * @Return: isSystemUser
	 */
	public Boolean isIsSystemUser() {
		return isIsSystemUser;
	}
	/**
	 * @Title: setSystemUser
	 * @Description: setSystemUser
	 * @Param isSystemUser the isSystemUser to set
	 */
	public void setIsSystemUser(boolean isIsSystemUser) {
		this.isIsSystemUser = isIsSystemUser;
	}
	/**
	 * @Title: getState
	 * @Description: getState
	 * @Return: state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @Title: setState
	 * @Description: setState
	 * @Param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @Title: isVirtualUser
	 * @Description: isVirtualUser
	 * @Return: isVirtualUser
	 */
	public Boolean isIsVirtualUser() {
		return isIsVirtualUser;
	}
	/**
	 * @Title: setVirtualUser
	 * @Description: setVirtualUser
	 * @Param isVirtualUser the isVirtualUser to set
	 */
	public void setIsVirtualUser(boolean isIsVirtualUser) {
		this.isIsVirtualUser = isIsVirtualUser;
	}
	/**
	 * @Title: getVisibility
	 * @Description: getVisibility
	 * @Return: visibility
	 */
	public Integer getVisibility() {
		return visibility;
	}
	/**
	 * @Title: setVisibility
	 * @Description: setVisibility
	 * @Param visibility the visibility to set
	 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	/**
	 * @Title: getJpushID
	 * @Description: getJpushID
	 * @Return: jpushID
	 */
	public String getJpushID() {
		return jpushID;
	}
	/**
	 * @Title: setJPushID
	 * @Description: setJPushID
	 * @Param jpushID the jpushID to set
	 */
	public void setJpushID(String jpushID) {
		this.jpushID = jpushID;
	}
	/**
	 * @Title: getManagerID
	 * @Description: getManagerID
	 * @Return: managerID
	 */
	public String getManagerID() {
		return managerID;
	}
	/**
	 * @Title: setManagerID
	 * @Description: setManagerID
	 * @Param managerID the managerID to set
	 */
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	/**
	 * @Title: getModifiedTime
	 * @Description: getModifiedTime
	 * @Return: modifiedTime
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * @Title: setModifiedTime
	 * @Description: setModifiedTime
	 * @Param modifiedTime the modifiedTime to set
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * @Title: getMobile
	 * @Description: getMobile
	 * @Return: mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @Title: setMobile
	 * @Description: setMobile
	 * @Param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @Title: getMobileToken
	 * @Description: getMobileToken
	 * @Return: mobileToken
	 */
	public String getMobileToken() {
		return mobileToken;
	}
	/**
	 * @Title: setMobileToken
	 * @Description: setMobileToken
	 * @Param mobileToken the mobileToken to set
	 */
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	/**
	 * @Title: getMobileType
	 * @Description: getMobileType
	 * @Return: int
	 */
	public Integer getMobileType() {
		return mobileType;
	}
	/**
	 * @Title: setMobileType
	 * @Description: setMobileType
	 * @Param mobileType the mobileType to set
	 */
	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}
	/**
	 * @Title: getName
	 * @Description: getName
	 * @Return: name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @Title: setName
	 * @Description: setName
	 * @Param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @Title: getNotifyType
	 * @Description: getNotifyType
	 * @Return: notifyType
	 */
	public Integer getNotifyType() {
		return notifyType;
	}
	/**
	 * @Title: setNotifyType
	 * @Description: setNotifyType
	 * @Param notifyType the notifyType to set
	 */
	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}
	/**
	 * @Title: getObjectID
	 * @Description: getObjectID
	 * @Return: objectID
	 */
	public String getObjectID() {
		return objectID;
	}
	/**
	 * @Title: setObjectID
	 * @Description: setObjectID
	 * @Param objectID the objectID to set
	 */
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	/**
	 * @Title: getOfficePhone
	 * @Description: getOfficePhone
	 * @Return: officePhone
	 */
	public String getOfficePhone() {
		return officePhone;
	}
	/**
	 * @Title: setOfficePhone
	 * @Description: setOfficePhone
	 * @Param officePhone the officePhone to set
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	/**
	 * @Title: getParentID
	 * @Description: getParentID
	 * @Return: parentID
	 */
	public String getParentID() {
		return parentID;
	}
	/**
	 * @Title: setParentID
	 * @Description: setParentID
	 * @Param parentID the parentID to set
	 */
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	/**
	 * @Title: getParentIndex
	 * @Description: getParentIndex
	 * @Return: parentIndex
	 */
	public Integer getParentIndex() {
		return parentIndex;
	}
	/**
	 * @Title: setParentIndex
	 * @Description: setParentIndex
	 * @Param parentIndex the parentIndex to set
	 */
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}
	/**
	 * @Title: getParentObjectID
	 * @Description: getParentObjectID
	 * @Return: parentObjectID
	 */
	public String getParentObjectID() {
		return parentObjectID;
	}
	/**
	 * @Title: setParentObjectID
	 * @Description: setParentObjectID
	 * @Param parentObjectID the parentObjectID to set
	 */
	public void setParentObjectID(String parentObjectID) {
		this.parentObjectID = parentObjectID;
	}
	/**
	 * @Title: getParentPropertyName
	 * @Description: getParentPropertyName
	 * @Return: parentPropertyName
	 */
	public String getParentPropertyName() {
		return parentPropertyName;
	}
	/**
	 * @Title: setParentPropertyName
	 * @Description: setParentPropertyName
	 * @Param parentPropertyName the parentPropertyName to set
	 */
	public void setParentPropertyName(String parentPropertyName) {
		this.parentPropertyName = parentPropertyName;
	}
	/**
	 * @Title: getPassword
	 * @Description: getPassword
	 * @Return: password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @Title: setPassword
	 * @Description: setPassword
	 * @Param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @Title: getPostalCode
	 * @Description: getPostalCode
	 * @Return: postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @Title: setPostalCode
	 * @Description: setPostalCode
	 * @Param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	/**
	 * @Title: getPostOfficeBox
	 * @Description: getPostOfficeBox
	 * @Return: postOfficeBox
	 */
	public String getPostOfficeBox() {
		return postOfficeBox;
	}
	/**
	 * @Title: setPostOfficeBox
	 * @Description: setPostOfficeBox
	 * @Param postOfficeBox the postOfficeBox to set
	 */
	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}
	/**
	 * @Title: getPrivacyLevel
	 * @Description: getPrivacyLevel
	 * @Return: int
	 */
	public Integer getPrivacyLevel() {
		return privacyLevel;
	}
	/**
	 * @Title: setPrivacyLevel
	 * @Description: setPrivacyLevel
	 * @Param privacyLevel the privacyLevel to set
	 */
	public void setPrivacyLevel(int privacyLevel) {
		this.privacyLevel = privacyLevel;
	}
	/**
	 * @Title: getQq
	 * @Description: getQq
	 * @Return: qq
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * @Title: setQQ
	 * @Description: setQq
	 * @Param qQ the qQ to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * @Title: getRelationUserID
	 * @Description: getRelationUserID
	 * @Return: relationUserID
	 */
	public String getRelationUserID() {
		return relationUserID;
	}
	/**
	 * @Title: setRelationUserID
	 * @Description: setRelationUserID
	 * @Param relationUserID the relationUserID to set
	 */
	public void setRelationUserID(String relationUserID) {
		this.relationUserID = relationUserID;
	}
	/**
	 * @Title: getRtx
	 * @Description: getRtx
	 * @Return: rtx
	 */
	public String getRtx() {
		return rtx;
	}
	/**
	 * @Title: setRtx
	 * @Description: setRtx
	 * @Param rtx the rtx to set
	 */
	public void setRtx(String rtx) {
		this.rtx = rtx;
	}
	/**
	 * @Title: getSecretaryID
	 * @Description: getSecretaryID
	 * @Return: secretaryID
	 */
	/*public String getSecretaryID() {
		return secretaryID;
	}*/
	/**
	 * @Title: setSecretaryID
	 * @Description: setSecretaryID
	 * @Param secretaryID the secretaryID to set
	 */
	/*public void setSecretaryID(String secretaryID) {
		this.secretaryID = secretaryID;
	}*/
	/**
	 * @Title: getServiceState
	 * @Description: getServiceState
	 * @Return: serviceState
	 */
	public Integer getServiceState() {
		return serviceState;
	}
	/**
	 * @Title: setServiceState
	 * @Description: setServiceState
	 * @Param serviceState the serviceState to set
	 */
	public void setServiceState(int serviceState) {
		this.serviceState = serviceState;
	}
	/**
	 * @Title: getSid
	 * @Description: getSid
	 * @Return: sid
	 */
	public String getSid() {
		return sid;
	}
	/**
	 * @Title: getSortKey
	 * @Description: getSortKey
	 * @Return: sortKey
	 */
	public Integer getSortKey() {
		return sortKey;
	}
	/**
	 * @Title: setSortKey
	 * @Description: setSortKey
	 * @Param sortKey the sortKey to set
	 */
	public void setSortKey(int sortKey) {
		this.sortKey = sortKey;
	}
	/**
	 * @Title: getSourceID
	 * @Description: getSourceID
	 * @Return: sourceID
	 */
	public String getSourceID() {
		return sourceID;
	}
	/**
	 * @Title: setSourceID
	 * @Description: setSourceID
	 * @Param sourceID the sourceID to set
	 */
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	/**
	 * @Title: setSid
	 * @Description: setSid
	 * @Param sID the sID to set
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
	/**
	 * @Title: getSkype
	 * @Description: getSkype
	 * @Return: skype
	 */
	public String getSkype() {
		return skype;
	}
	/**
	 * @Title: setSkype
	 * @Description: setSkype
	 * @Param skype the skype to set
	 */
	public void setSkype(String skype) {
		this.skype = skype;
	}
	/**
	 * @Title: getWeChatAccount
	 * @Description: getWeChatAccount
	 * @Return: weChatAccount
	 */
	public String getWeChatAccount() {
		return weChatAccount;
	}
	/**
	 * @Title: setWeChatAccount
	 * @Description: setWeChatAccount
	 * @Param weChatAccount the weChatAccount to set
	 */
	public void setWeChatAccount(String weChatAccount) {
		this.weChatAccount = weChatAccount;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [appellation=" + appellation + ", bankAccount="
				+ bankAccount + ", bankCity=" + bankCity + ", bankName="
				+ bankName + ", bankProvince=" + bankProvince + ", birthday="
				+ birthday + ", calendarID=" + calendarID + ", code=" + code
				+ ", costCenter=" + costCenter + ", createdTime=" + createdTime
				+ ", defaultLanguage=" + defaultLanguage
				+ ", departureDate=" + departureDate
				+ ", description=" + description + ", dingTalkAccount="
				+ dingTalkAccount + ", doLock=" + doLock + ", email=" + email
				+ ", employeeNumber=" + employeeNumber + ", employeeRank="
				+ employeeRank + ", entryDate=" + entryDate + ", extend1="
				+ extend1 + ", extend2=" + extend2
				+ ", facsimileTelephoneNumber=" + facsimileTelephoneNumber
				+ ", gender="
				+ gender + ", idNumber=" + idNumber + ", imageID=" + imageID
				+ ", imageUrl=" + imageUrl + ", isAdministrator="
				+ isIsAdministrator + ", isConsoleUser=" + isIsConsoleUser
				+ ", isSystemUser=" + isIsSystemUser + ", isVirtualUser="
				+ isIsVirtualUser + ", jpushID=" + jpushID + ", managerID="
				+ managerID + ", modifiedTime=" + modifiedTime + ", mobile="
				+ mobile + ", mobileToken=" + mobileToken + ", mobileType="
				+ mobileType + ", name=" + name + ", notifyType=" + notifyType
				+ ", officePhone=" + officePhone + ", parentID=" + parentID
				+ ", parentIndex=" + parentIndex + ", parentObjectID="
				+ parentObjectID + ", parentPropertyName=" + parentPropertyName
				+ ", password=" + password + ", postalCode=" + postalCode
				+ ", postOfficeBox=" + postOfficeBox + ", privacyLevel="
				+ privacyLevel + ", qq=" + qq + ", relationUserID="
				+ relationUserID + ", rtx=" + rtx 
				+ ", serviceState=" + serviceState + ", sortKey="
				+ sortKey + ", sourceID=" + sourceID + ", sid=" + sid
				+ ", skype=" + skype + ", state=" + state + ", visibility="
				+ visibility + ", weChatAccount=" + weChatAccount + "]";
	}
}
