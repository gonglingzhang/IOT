<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-4-22
  Time: 下午12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>找回密码</title>
</head>
<body style="background-color: #e0e0e0;margin: 0px;padding: 0px;">
<%@include file="/header.jsp" %>

<div style="padding-top: 30px;">
    <form action="/resetpasswords" method="post">
        <center>
            <table style="text-align: left; border-collapse: separate;border-spacing: 10px;" >
                <tr>
                    <th colspan="2">
                        <h4><span class="glyphicon glyphicon-triangle-right" aria-hidden="true">重置密码</span></h4></th>
                </tr>
                <tr>
                    <td><label class="help-block">用户名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="username"></td>
                </tr>
                <tr>
                    <td><label class="help-block">新密码</label></td>
                    <td><input type="password" class="form-control" aria-describedby="helpBlock" name="password"></td>
                </tr>
                <tr>
                    <td><label class="help-block">确认密码</label></td>
                    <td><input type="password" class="form-control" aria-describedby="helpBlock" name="password2"></td>
                </tr>
                <tr>
                    <td><label class="help-block">预留的手机号</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="phone"></td>
                </tr>
                <tr>
                    <td><label class="help-block">预留的邮箱</label></td>
                    <td><input type="email" class="form-control" aria-describedby="helpBlock" name="mail"></td>
                </tr>
                <tr style="text-align: center;">
                    <td colspan="2">
                        <button type="submit" class="btn btn-default">提交</button>
                    </td>
                </tr>
                <tr style="text-align: center;">
                    <td colspan="2"><font color="red">${Tip}</font></td>
                </tr>
            </table>
        </center>
    </form>
</div>
</body>
</html>
