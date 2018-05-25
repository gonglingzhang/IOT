<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-3-23
  Time: 下午4:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico" />
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>欢迎登陆</title>
    <style type="text/css">
        body {
            background-image:url("/images/background.jpg");
            background-repeat: no-repeat;
            background-size: 100% 100%;
            background-attachment: fixed;
        }
    </style>
</head>
<body>
<%
  String name = "";
  if(!session.isNew()) {
      name = (String)session.getAttribute("username");
      if (name == null)
          name = "";
  }
%>
<i style="float: right;padding: 10px 10px;">联系我们:&nbsp;18360578368</i>

<div style="width: 32%;margin: auto; padding-top: 20%;">
        <form class="form-horizontal" action="/logins" method="post">
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputEmail3" placeholder="USERNAME" name="username">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">密&nbsp;&nbsp;&nbsp;码</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword3" placeholder="PASSWORD" name="password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">登录</button>
                    <a href="/returntopage" class="btn btn-default" onclick="">忘记密码？</a>
                </div>
            </div>
            <font color="red">${Tip}</font>
        </form>
</div>
</body>
</html>