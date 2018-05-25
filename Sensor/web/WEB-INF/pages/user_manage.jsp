<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-3-23
  Time: 下午9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>智能养殖系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body style="padding: 0;margin: 0;">
<%@include file="/header.jsp" %>
<%
    String name = (String) session.getAttribute("username");
%>
<%--查看实时数据--%>
<div class="panel panel-default" style="margin: 2px; text-align: center;">
    <div class="panel-heading"><h4><%=name%>,欢迎登陆！</h4>
        <h4>
            <small>${address_fixed} &nbsp; ${address}</small>
            <a href="/suggest.jsp" style="font-size: 0.5em; padding-left: 20px;">查看建议</a>
        </h4>
        <h4>
            <small></small>
        </h4>
        <div id="time">
            <script>
                document.getElementById('time').innerHTML = new Date().toLocaleString() + ' 星期' + '日一二三四五六'.charAt(new Date().getDay());
                setInterval("document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
            </script>
        </div>
    </div>
    <div class="panel-body" style="padding: 0;margin: 0;">
        <img src="/images/Fishpond.jpeg" style="width: 100%;height: 100%; z-index: -1;">
        <div id="showPosition" style="position: absolute;top: 200px; left: 100px; opacity: 0.7; ">
            位置0
        </div>
        <div style="position: absolute;top: 200px; left: 300px; opacity: 0.7;">
            <%--<c:if test="${temperature0!=null}" var="result">--%>
                    <button onclick="ajax('showtemperatureplot')" type="submit" class="btn btn-default" style="background-color: #317da0;">查看温度历史记录
                    </button>
            <%--</c:if>--%>
        </div>
        <div style="position: absolute;top: 200px; left: 500px; opacity: 0.7;">
            <%--<c:if test="${ph0!=null}" var="result">--%>
                    <button onclick="ajax('showphplot')" type="submit" class="btn btn-default" style="background-color: #317da0" ;>查看PH历史记录
                    </button>
            <%--</c:if>--%>
        </div>

        <div style="position: absolute;top: 200px; left: 700px; opacity: 0.7; ">
            <%--<c:if test="${humidity0!=null}" var="result">--%>
                    <button onclick="ajax('showhumidityplot')" type="submit" class="btn btn-default" style="background-color: #317da0;">查看湿度历史记录</button>
            <%--</c:if>--%>
        </div>
        <div style="position: absolute;top: 200px; left: 900px; opacity: 0.7; ">
            <%--<c:if test="${oxygen_rate0!=null}" var="result">--%>
                    <button onclick="ajax('showoxygenrateplot')" type="submit" class="btn btn-default" style="background-color: #317da0;">查看溶解氧历史记录</button>
            <%--</c:if>--%>
        </div>
        <div style="position: absolute;top: 200px; left: 1100px; opacity: 0.7; ">
            <%--<c:if test="${oxygen_rate0!=null}" var="result">--%>
            <button onclick="openConfig()" type="submit" class="btn btn-default" style="background-color: #317da0;">配置</button>
            <%--</c:if>--%>
        </div>



        <%--位置信息--%>
        <div style="position: absolute;top: 450px; left: 80px;width: 100px;height: 100px;background-color: ${color0} ;opacity:0.5;border-radius: 50%;padding-top: 31px;">
            <span title="${temperature0}&#10; ${ph0}&#10; ${humidity0}&#10; ${oxygen_rate0} "
                  class="glyphicon glyphicon-map-marker" aria-hidden="true" style="cursor:pointer;font-size: 2em;"
                  onclick="show(0)"></span>
        </div>
        <div style="position: absolute;top: 600px; left: 180px; width: 100px; height: 100px; background-color: ${color1} ;opacity: 0.5;border-radius: 50%;padding-top: 31px;">
            <span title="${temperature1}&#10; ${ph1}&#10; ${humidity1}&#10; ${oxygen_rate1}&#10;假的，别信 "
                  class="glyphicon glyphicon-map-marker" aria-hidden="true" style="cursor:pointer;font-size: 2em;"
                  onclick="show(1)"></span>
        </div>
        <div style="position: absolute;top: 500px; left: 580px; width: 100px; height: 100px; background-color:${color2} ;opacity: 0.5;border-radius: 50%;padding-top: 31px;">
            <span title=" ${temperature2}&#10; ${ph2}&#10; ${humidity2}&#10; ${oxygen_rate2} "
                  class="glyphicon glyphicon-map-marker" aria-hidden="true" style="cursor:pointer;font-size: 2em;"
                  onclick="show(2)"></span>
        </div>
        <div style="position: absolute;top: 380px; left: 850px; width: 100px; height: 100px; background-color:${color3} ;opacity: 0.5;border-radius: 50%;padding-top: 31px;">
            <span title=" ${temperature3}&#10; ${ph3}&#10; ${humidity3}&#10; ${oxygen_rate3} "
                  class="glyphicon glyphicon-map-marker" aria-hidden="true" style="cursor:pointer;font-size: 2em;"
                  onclick="show(3)"></span>
        </div>
        <div style="position: absolute;top: 600px; left: 1080px; width: 100px; height: 100px; background-color:${color4} ;opacity: 0.5;border-radius: 50%;padding-top: 31px;">
            <span title=" ${temperature4}&#10; ${ph4}&#10; ${humidity4}&#10; ${oxygen_rate4} "
                  class="glyphicon glyphicon-map-marker" aria-hidden="true" style="cursor:pointer;font-size: 2em;"
                  name="position5"
                  onclick="show(4)"></span>
        </div>
    </div>

</div>
<script src="https://cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
<script type="text/javascript">
    var myposition = 0;
    function show(value) {
        myposition = value;
        var position = document.getElementById('showPosition');
        position.innerText = '位置'+value;

    }
    function ajax(url) {
        document.location.href = url+"?position="+myposition;
    }
    function openConfig(){
        document.location.href = "config";
    }

</script>
</body>
</html>
