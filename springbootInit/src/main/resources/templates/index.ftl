<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>how are you?</h1>
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
    <input type="submit" value="logout">
</form>
</body>
</html>