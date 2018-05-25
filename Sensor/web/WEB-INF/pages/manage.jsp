<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-3-23
  Time: 下午9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>管理员</title>
    <style type="text/css">
        #part1 {
            width: 50%;
            height: 380px;
            float: left;
        }

        #part2 {
            width: 50%;
            height: 320px;
            float: left;
        }

        #part3 {
            width: 50%;
            height: 200px;
            float: left;
            clear: left;
        }

        #part4 {
            width: 50%;
            height: 220px;
            float: left;
        }
    </style>
</head>
<body style="background-color: #e0e0e0;">
<%@include file="/header.jsp"%>
<div id="part1">
    <div style="padding-left: 150px;">
        <form action="/manages_add" method="post">
            <table border="0" style="border-collapse: separate;border-spacing: 0px;">
                <tr>
                    <th colspan="2">
                        <h4><span class="glyphicon glyphicon-triangle-right" aria-hidden="true">增加用户</span></h4>
                    </th>
                </tr>
                <tr>
                    <td><label class="help-block">用户名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="username"></td>
                </tr>
                <tr>
                    <td><label class="help-block">密 码</label></td>
                    <td><input type="password" class="form-control" aria-describedby="helpBlock" name="password"></td>
                </tr>
                <tr>
                    <td><label class="help-block">确认密码</label></td>
                    <td><input type="password" class="form-control" aria-describedby="helpBlock" name="password2"></td>
                </tr>
                <tr>
                    <td><label class="help-block">手机号</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="phone"></td>
                </tr>
                <tr>
                    <td><label class="help-block">邮 箱</label></td>
                    <td><input type="email" class="form-control" aria-describedby="helpBlock" name="mail"></td>
                </tr>
                <tr>
                    <td><label class="help-block">地 址</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="address"></td>
                </tr>
                <tr>
                    <td><label class="help-block">位置数量</label></td>
                    <td><input type="number" min="1" class="form-control" aria-describedby="helpBlock" name="count"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button type="submit" class="btn btn-default">增加用户</button>
                    </td>
                </tr>
                <tr style="text-align: center">
                    <td colspan="2"><font color="red">${Tip_add}</font></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="part2">
    <div style="padding-left: 30px; padding-top: 20px">
        <form action="/manages_update" method="post">
            <table border="0" style="border-collapse: separate;border-spacing: 0px;">
                <tr>
                    <th colspan="2"><h4><span class="glyphicon glyphicon-triangle-right" aria-hidden="true">修改用户</span>
                    </h4></th>
                </tr>
                <tr>
                    <td><label class="help-block">用户名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="username"></td>
                </tr>
                <tr>
                    <td><label class="help-block">手机号</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="phone"></td>
                </tr>
                <tr>
                    <td><label class="help-block">邮 箱</label></td>
                    <td><input type="email" class="form-control" aria-describedby="helpBlock" name="mail"></td>
                </tr>
                <tr>
                    <td><label class="help-block">地 址</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="address"></td>
                </tr>
                <tr>
                    <td><label class="help-block">位置数量</label></td>
                    <td><input type="number" min="1" class="form-control" aria-describedby="helpBlock" name="count"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button type="submit" class="btn btn-default">修改用户</button>
                    </td>
                </tr>
                <tr style="text-align: center">
                    <td colspan="2"><font color="red">${Tip_update}</font></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="part3">
    <div style="padding-left: 150px;">
        <form action="/manages_search" method="post">
            <table style="text-align: center; border-collapse: separate;border-spacing: 3px;">
                <tr>
                    <th colspan="2"><h4><span class="glyphicon glyphicon-triangle-right" aria-hidden="true">查询用户</span>
                    </h4></th>
                </tr>
                <tr>
                    <td>
                        <label class="help-block">用户名</label>
                    </td>
                    <td>
                        <input type="text" class="form-control" aria-describedby="helpBlock" name="username">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button type="submit" class="btn btn-default">查询用户</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <small>${Tip_phone_fixed}</small>
                        </h4>
                    </td>
                    <td>
                        <h4>
                            <small>${Tip_phone}</small>
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <small>${Tip_mail_fixed}</small>
                        </h4>
                    </td>
                    <td>
                        <h4>
                            <small>${Tip_mail}</small>
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <small>${Tip_address_fixed}</small>
                        </h4>
                    </td>
                    <td>
                        <h4>
                            <small>${Tip_address}</small>
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <small>${Tip_count_fixed}</small>
                        </h4>
                    </td>
                    <td>
                        <h4>
                            <small>${Tip_count}</small>
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <small>${device_fixed}</small>
                        </h4>
                    </td>
                    <td style="text-align: left;padding-left: 18px;">
                        <h4>
                            <small>${device}</small>
                        </h4>
                    </td>
                </tr>
            </table>
            <font color="red" style="padding-left: 80px">${Tip_search}</font>
        </form>
    </div>
</div>

<div id="part4">
    <div style="padding-left: 30px;">
        <form action="/del_device" method="post">
            <table border="0" style="border-collapse: separate;border-spacing: 0px;">
                <tr>
                    <th colspan="2"><h4><span class="glyphicon glyphicon-triangle-right" aria-hidden="true">删除传感器</span>
                    </h4></th>
                </tr>
                <tr>
                    <td><label class="help-block">用户名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="username"></td>
                </tr>
                <tr>
                    <td><label class="help-block">表名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="table"></td>
                </tr>
                <tr>
                    <td><label class="help-block">设备名</label></td>
                    <td><input type="text" class="form-control" aria-describedby="helpBlock" name="device"></td>
                </tr>
                <tr>
                    <td><label class="help-block">位置</label></td>
                    <td><input type="number" min="0" class="form-control" aria-describedby="helpBlock" name="count"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <button type="submit" class="btn btn-default">删除</button>
                    </td>
                </tr>
                <tr style="text-align: center">
                    <td colspan="2"><font color="red">${Tip_del}</font></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>