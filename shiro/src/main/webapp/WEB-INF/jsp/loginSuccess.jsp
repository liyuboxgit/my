<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/jquery.js"></script>
</head>
<body>
login success.
<script type="text/javascript">
	var $http = function(url,config){
		this.setting = $.extend(
			{
				url:url,
				success:function(ret){console.log(ret);},
				error:function(xhr){console.log(xhr.responseText);},
				dataType:this.dataType,
				type:'GET'
			}, config || {}
		);
	};
	
	$http.prototype = {	
		send:function(callback){
			if(callback && typeof callback=='function')
				this.setting.success = callback;
			$.ajax(this.setting);
		}
	}

new $http('/shiro/query',{type:'post'}).send(function(ret){
	alert(ret.success);
});
</script>
</body>
</html>