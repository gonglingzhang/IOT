<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-5-21
  Time: 上午11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>配置</title>
</head>
<body>
<%@include file="/header.jsp" %>
<div style="width: 60%; margin: auto; padding-top: 20px;">
<form class="form-horizontal" action="/updateconfig" method="post">
    <div class="form-group">
        <label  class="col-sm-2 control-label">温度最小值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="temp_min" value="${config.temp_min}">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">温度最大值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="temp_max" value="${config.temp_max}">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">PH最小值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="ph_min" value="${config.ph_min}">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">PH最大值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="ph_max" value="${config.ph_max}">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">湿度最小值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="hum_min" value="${config.hum_min}" >
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">湿度最大值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="hum_max" value="${config.hum_max}"  >
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">溶解氧最小值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="oxy_min" value="${config.oxy_min}" >
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">溶解氧最大值</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="oxy_max" value="${config.oxy_max}" >
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">确认</button>
        </div>
    </div>
</form>
</div>
</body>
</html>
