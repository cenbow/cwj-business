<%@page language="java" contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>注册-成功</title>
<link href="${pageContext.request.contextPath}/static/css/public.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
</head>

<body>
<!-- 提示区域 -->
<div class="dow-pop">
    <div class="close-box"><img src="${pageContext.request.contextPath}/static/images/close.png"/></div>
    <div class="title-box">华夏通移动商城注册送优惠券</div>
    <div class="dow-app"><a href="#">下载APP</a></div>
</div>
<!-- 头部 -->
<div class="sn-nav">
    <div class="sn-nav-back"><a class="sn-iconbtn" href="javascript:history.back(1)">返回</a></div>
    <div class="sn-nav-title">验证已注册用户</div>
</div>
<!-- 内容区 -->
<div class="main">
    <div class="common-wrapper">
        <!-- 文字提示 -->
        <div class="reg-success mb1">
            <p class="orange"><font>恭喜您，验证成功！</font></p>
            <p>下载客户端，登录后购买喜欢的商品</p>
        </div>
        <!-- 按钮 -->
        <div class="reg-btn mb1 w80"><input type="button" value="下载客户端"></div>
    </div>
</div>
</body>
</html>
