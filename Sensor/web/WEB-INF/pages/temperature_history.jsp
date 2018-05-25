<%--
  Created by IntelliJ IDEA.
  User: glz
  Date: 18-4-26
  Time: 上午10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/images/s.ico" />
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>温度</title>
    <meta charset="utf-8">
    <!-- 引入 ECharts 文件 -->
    <script src="/js/echarts.js"></script>
</head>
<body>
<%@include file="/header.jsp"%>
<input type="hidden" id="time_list" value="${time_list}" />
<input type="hidden" id="data_list" value="${data_list}" />
<input type="hidden" id="device" value="${device}" />
<center><div id="main" style="width: 90%;height:80%;padding-top: 20px;"></div></center>
<button class="btn btn-default" onclick="ajax('showtemperatureplot_all')">查看全部数据</button>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    myChart.showLoading();
    var device = document.getElementById("device").value;
    var time_list = document.getElementById("time_list").value;
    var data_list = document.getElementById("data_list").value;
    var array_device =  eval('(' + device + ')');              //数组，存放设备名
    var array_time_list = eval('(' + time_list + ')');          //数组，存放时间
    var array_data_list = eval('(' + data_list + ')');          //数组，存放温度值
    var count = array_device.length;                         //折线图的数量
    var  s  = {
            'time': array_time_list[0]
    };
    var d = [];
    for(var i=0;i<count;i++){
        s[array_device[i]] =  array_data_list[i];
        d[i] = {type: 'line'};
    }
    var option = {
        title:{text:"温度折线图"},
        legend: {},
        tooltip: {},
        dataset: [{
            // 按列的 key-value 形式。
            source: s,
        }],
        xAxis: {type: 'category'},
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: d
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.hideLoading();
    myChart.setOption(option);


    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); // 匹配目标参数
        var result = window.location.search.substr(1).match(reg); // 对querystring匹配目标参数
        if (result != null) {
            return decodeURIComponent(result[2]);
        } else {
            return null;
        }
    }
    function ajax(url) {
        var position = getQueryString('position');
        document.location.href = url+"?position="+position;
    }
</script>
</body>
</html>
