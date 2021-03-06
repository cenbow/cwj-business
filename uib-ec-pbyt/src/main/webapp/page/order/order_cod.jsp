<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>e-life</title>
<link href="${pageContext.request.contextPath}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/orders.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/hover.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.2.min.js"></script>
<script >
/* var orderNo=2015110510637;
var strAmount="200";
var fullNam="easy";
var description="123";
function doSubmit() {
window.location='f/pay/unionPay?orderNo='+orderNo+"&strAmount="+strAmount+"&fullNam="+fullNam+"&description="+description;	
} */


</script>
</head>
 
<body>
 <div class="topWrap">
		<div class="site-nav w980">
        <div class="tool_link">
				<c:choose>
					<c:when test="${not empty(member)}">
							${member.username},欢迎光临初云汇商城！   <a
							href="${pageContext.request.contextPath}/f/logout">退出</a>
					</c:when>
					<c:otherwise>
							欢迎光临初云汇商城！&nbsp;<a
							href="${pageContext.request.contextPath}/f/login">登录</a>&nbsp;或&nbsp;<a
							href="${pageContext.request.contextPath}/f/reg/registerView">免费注册</a>
					</c:otherwise>
				</c:choose>
			</div>
			<ul class="quick-menu">
            <li class="user menu-item">
				<div class="menu">
						<span class="menu-hd"><s></s>我的账户<b></b></span>
						<div class="menu-bd">
							<div class="menu-bd-panel">
								<ul>
									<li><a	href="${pageContext.request.contextPath}/f/member/myOrder">我的订单</a></li>
									<!-- <li><a href="#">地址管理</a></li> -->
								</ul>
							</div>
						</div>
					</div>
			  </li>
              <li class="cart menu-item">
				  <div class="menu">
						<span class="menu-hd"><s></s><a
							href="${pageContext.request.contextPath}/f/cart/list">购物车(<font>10</font>)
						</a><b></b></span>
						<div class="menu-bd">
							<dl>
								<dt>
									<a href="#"><img
										src="${pageContext.request.contextPath  }/static/images/cpone.jpg" /></a>
									<p>
										<a class="tit" href="#">神舟 灵雅X50 TS(白) 3G手机 TD-SCDMA/GSM
											双卡双待</a> <span>¥34.9</span> <a class="del" href="#">删除</a>
									</p>
								</dt>
								<dt>
									<a href="#"><img
										src="${pageCsontext.request.contextPath  }/static/images/cpone.jpg" /></a>
									<p>
										<a class="tit" href="#">神舟 灵雅X50 TS(白) 3G手机 TD-SCDMA/GSM
											双卡双待</a> <span>¥34.9</span> <a class="del" href="#">删除</a>
									</p>
								</dt>
								<dd>
									<em>购物车里还有1种商品</em> <a href="#">去购物车并结算</a>
								</dd>
							</dl>
						</div>
					</div>
			  </li>
             
              <li class="language menu-item">
				  <div class="menu">
					  <a class="menu-hd" href="#">语言选择<b></b></a> 
					  <div class="menu-bd">
						  <div class="menu-bd-panel">
								  <a href="#">简体中文</a>
								  <a href="#">繁體中文</a> 
						  </div>
					  </div>
				  </div>
			  </li>
			  <li class="services menu-item">
				  <div class="menu">
					  <a class="menu-hd" href="#" >网站导航<b></b></a> 
					  <div class="menu-bd fontbox">
						  <div class="menu-bd-panel">
							  <dl>
								<dt><a href="#">购物指南</a>
								<dd>
										<!-- <a href="#">购物流程</a>  -->
										<!-- <a href="#">会员等级</a>  -->
										<a href="${pageContext.request.contextPath  }/f/reg/registerView">新用户注册</a>
										<!-- <a href="#">预存款支付</a>  -->
										<!-- <a href="#">退换货政策</a> -->
								</dd>
							</dl>
							 <!--  <dl>
								<dt><a href="#">关于我们</a> 
								<dd>
                                <a href="#">公司简介</a>
                                <a href="#">联系我们</a>
								</dd>
							</dl> 
							  <dl class="last">
								<dt>
                                <a href="#">帮助中心</a> 
								<dd><a href="#">交易安全</a>
                                <a href="#">维权中心</a> </dd>
                            </dl> -->
						  </div>
						
					  </div>
				  </div>
			  </li>
		    </UL>
		</div>
</div>
<!-- 顶部栏目结束 -->
<!-- 搜索栏开始 -->
<div class="logoWrap w980">
<div class="life_logo"><img src="${pageContext.request.contextPath  }/static/images/life_logo.png"/></div>
<div class="shop_titleright">
<ul>
<li class="lione white">选择支付方式</li>
<li class="litwo gray666">核对支付信息</li>
<li class="litre gray666">支付结果信息</li>
</ul>
</div>
</div>
<!-- 搜索栏结束 -->
<!-- 购物车 -->
<div class="shop_cart">
<!-- 订单提交信息 -->
<div class="orderover_content">
<div class="orderover_success"></div>
<div class="orderover_tips">
<p class="font18 green">订单已提交成功，我们将尽快为你发货！</p>
<p>订单号：${orderNo} 应付金额:<span class="red font14">¥${amount}</span>元(含手续费:<span class="red font14">¥0.00</span>)</p>
<!-- <p>温馨提示：请您在提交订单后<span class="red">24小时内</span>完成支付，否则订单会自动取消。</p>
<P class="order_over_line"><a class="blue" href="#">查看订单状况</a><a class="blue" href="#">继续购物</a></P> -->
</div>
</div>
<!-- 选择支付方式 -->
 <%-- <div class="order_payment mt20">
<div class="order_payment_title">
<ul>
<li id="one1" onmouseover="setTab('one',1,2)" class="hover">网银支付</li>
<li id="one2" onmouseover="setTab('one',2,2)">平台支付</li>
</ul>
</div>
<div class="order_payment_content">
<!-- 银行卡内容 -->
<div id="con_one_1">
<h3>请选择银行（需开通网银）</h3>
<ul>
<li>
<label>
<input name="" type="radio" value="" />
<img src="${pageContext.request.contextPath  }/static/images/bank01.gif" alt="中国银行"/>
</label>
</li>
</ul>
<div class="orderpayment_btn"><a class="orderpayment_btnbox font18" href="order_check.html">下一步</a></div>
</div>
<!-- 平台支付 -->
<div id="con_one_2" style="display:none">
<h3>平台支付</h3>
<ul>
<li>
<label>
<img src="${pageContext.request.contextPath}/static/images/easypay.gif" alt="easypay"/>
<input name="paymentPluginId" type="radio" value="1" />
</label>
<label>
<input name="paymentPluginId" type="radio" value="2" />
<img src="${pageContext.request.contextPath}/static/images/china-union.gif" alt="中国银联"/>
</label>
</li>
</ul>
<div class="orderpayment_btn"><a class="orderpayment_btnbox font18" href="#" onclick="doSubmit()" >立即付款</a></div>
</div>
</div>
</div>
</div> --%>
<!-- 五楼 -->
<div class="footerWrap">
<div class="promise_nav">
<ul>
<li class="promise_one"><s></s>品质保障<br/><span>品质护航&nbsp;&nbsp;购物无忧</span></li>
<li class="line-nav"></li>
<li class="promise_two"><s></s>品质保障<br/><span>正品行货&nbsp;&nbsp;精致服务</span></li>
<li class="line-nav"></li>
<li class="promise_tre"><s></s>专业配送<br/><span>国际物流配送&nbsp;&nbsp;急速到货</span></li>
<li class="line-nav"></li>
<li class="promise_four"><s></s>帮助中心<br/><span>您的购物指南</span></li>
</ul>
</div>
    <div class="sub_nav">
    	<div class="sub_nav_left">
        	<div class="sub_nav1">
           		<s></s>
            	<h2>购物指南</h2>
                <ul>
                	<li><a href="">购物流程</a></li>
                    <li><a href="">会员介绍</a></li>
                    <li><a href="">常见问题</a></li>
                    <li><a href="">联系客服</a></li>
                </ul>
            </div>
            <div class="sub_nav2">

           		<s></s>
            	<h2>配送方法</h2>
                <ul>
                	<li><a href="">海外配送</a></li>
                    <li><a href="">包邮配送</a></li>
                    <li><a href="">配送服务查询</a></li>
                    <li><a href="">配送费收取标准</a></li>
                </ul>
            </div>
            <div class="sub_nav3">
            	<s></s>
            	<h2>支付方式</h2>
                <ul>
                	<li><a href="">货到付款</a></li>
                    <li><a href="">在线支付</a></li>
                </ul>
            </div>
            <div class="sub_nav4">
            	<s></s>
            	<h2>售后服务</h2>
                <ul>
               	  <li><a href="">售后政策</a></li>
                  <li><a href="">退款说明</a></li>
                  <li><a href="">取消订单</a></li>
                  <li><a href="">返修/退换货</a></li>
                </ul>
            </div>
        </div>
        <div class="sub_nav_right"><a href=""><img src="${pageContext.request.contextPath  }/static/images/buy-security.jpg"/></a></div>
    </div>
    <div class="footer">
    	<div class="footer_nav">
        	<a href="">关于我们</a>|
            <a href="">联系我们</a>|
            <a href="">诚征英才</a>|
            <a href="">欢迎合作</a>|
            <a href="">知识产权</a>|
            <a href="">著作权与商标声明</a>|
            <a href="">法律声明</a>|
            <a href="">服务条款</a>|
            <a href="">隐私声明</a>|
            <a href="">网站地图</a>
        </div>
    </div>
    </div>
</body>
</body>
</html>