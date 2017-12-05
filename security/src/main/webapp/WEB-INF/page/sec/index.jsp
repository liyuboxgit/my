<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  String ctx=request.getContextPath(); %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	index 重要提示：没有权限也能访问，只有受控的url，才能拦截<br/>
	是否有/order/query:C的权限：
	<shiro:hasRole name="/order/query">
		<shiro:hasPermission name="/order/query:C">
			<a href="<%=ctx %>/order/query/C">有</a>
		</shiro:hasPermission>
	</shiro:hasRole>
	<br/>
	是否有/order/query:D的权限：
	<shiro:hasRole name="/order/query">
		<shiro:hasPermission name="/order/query:D">
			<a href="<%=ctx %>/order/query/D">有</a>
		</shiro:hasPermission>
	</shiro:hasRole>
	<br/>
	是否有/order/query:U的权限：
	<shiro:hasRole name="/order/query">
		<shiro:hasPermission name="/order/query:U">
			<a href="<%=ctx %>/order/query/U">有</a>
		</shiro:hasPermission>
	</shiro:hasRole>
	<br/>
	是否有/order/query:R的权限：
	<shiro:hasRole name="/order/query">
		<shiro:hasPermission name="/order/query:R">
			<a href="<%=ctx %>/order/query/R">有</a>
		</shiro:hasPermission>
	</shiro:hasRole>
	<br/>
	是否有/order/query:T的权限：
	<shiro:hasRole name="/order/query">
		<shiro:hasPermission name="/order/query:T">
			<a href="<%=ctx %>/order/query/T">有</a>
		</shiro:hasPermission>
	</shiro:hasRole>
</body>
</html>