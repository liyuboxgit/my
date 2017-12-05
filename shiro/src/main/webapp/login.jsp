<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<script src="<%=request.getContextPath()%>/static/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('#loginbtn').click(function() {
	    var param = {
	        username : $("#username").val()||'admin',
	        password : $("#password").val()||'123456'
	    };
	    $.ajax({ 
	        type: "post", 
	        url: "<%=request.getContextPath()%>/login", 
	        data: param, 
	        dataType: "json", 
	        success: function(data) { 
	            if(data.success == false){
	                alert(data.msg);
	            }else{
	                //登录成功
	                window.location.href = "<%=request.getContextPath()%>/loginSuccess";
	            }
	        },
	        error: function(data) { 
	        	console.log(data);
	        	alert("调用失败...." + data.status); 
	        }
	    });
	});
});
</script>
username: <input type="text" id="username"><br><br>  
password: <input type="password" id="password"><br><br>
<button id="loginbtn">登录</button>
</body>
</html>
