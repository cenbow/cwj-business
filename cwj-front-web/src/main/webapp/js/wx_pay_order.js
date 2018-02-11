/****************
	订单	
****************/
/**
 * order公共js类
 */
$(document).ready(function(){
	//获取订单号
	id = my.getUrlParam("id");
	orderNo = "";
	//初始化微信支付
	payInit();
});

/**
 * 初始化微信支付
 */
function payInit() {
	var url = location.href.split('#')[0];
	my.ajaxGet("/wechat/getWechatConfig?url=" + url,function(data,err,status){
		wx.config({
			debug: false,
			appId: data.appId,
			nonceStr: data.nonceStr,
			signature: data.signature,
			timestamp: parseInt(data.timestamp),
			jsApiList: [
	            'checkJsApi',
	            'chooseWXPay'
	        ]
		});
	},false);
};

/**
 * 微信支付回调
 */
function paymentCallback(productId) {
	//跳转支付结果
	window.location.href="wx_wxpay_result.html?orderNo="+orderNo+"&id="+productId; 
}

/**
 * 订单支付
 * @param productId
 */
function payOrder(productId){
	productId = productId || id;
	//生产
	wx.ready(function () {
		my.ajaxGet("/wechat/getWechatPayConfig?productId="+productId,function(data,err,status){
			if(data != null){
				orderNo = data.orderNo;
			    wx.chooseWXPay({
			    	 timestamp: data.timestamp,
			    	 nonceStr: data.nonceStr,
			    	 package: data.pkg,
			    	 signType: 'MD5',
			    	 paySign: data.paySign,
			    	 success: function (res) {
			    		 // 支付成功后的回调函数
			    		 paymentCallback(productId);
			    	 },
			    	 fail: function (){
			    		 myc.toast({
			    				location:'middle',
			    				msg:"支付失败"
			    			});
			    	 }
			    });
			}else{
				myc.toast({
    				location:'middle',
    				msg:"支付异常"
    			});
			}
		},true);
	});
}

/**
 * 删除订单
 */
function deleteOrder(productId){
	productId = productId || id;
	var bool =window.confirm('是否删除订单？');	
	if(bool){
		my.ajaxPost("/wechat/member/order/delete",{productId:productId},function(result,err,status){
			if(result.status){
        		myc.toast({
    				location:'middle',
    				msg:'订单删除成功'
    			});
        		setTimeout(function(){
        			 location.href = "order_me.html";
        		},2000);
        	}else{
        		myc.toast({
    				location:'middle',
    				msg:result.msg
    			});
        	}
		},false);
	}
}
