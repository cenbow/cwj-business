/**
 * 收支明细列表
 */
// 分页参数
var start = 0;
var count = 14;
var flag = true;
//当前用户id
var openId = my.getUrlParam("openId");

(function() {
	//初始化下拉刷新组件
	$(document.body).pullToRefresh();
	//刷新触发事件
	$(document.body).on("pull-to-refresh", function() {
		window.location.reload();
		//关闭刷新
		$(document.body).pullToRefreshDone();
	});
	
	//加载数据
	ajaxDetails(1);	
	
	window.onscroll = function() {			
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {//判断滚动条到达底部的条件,并自动触发下面AJAX				
			ajaxDetails(2);
		}
	} 			
})();

//加载数据
function ajaxDetails(type){
	if(!flag){
		return false;
	}
	var	urlPost = '/userInOutDetail/'+ start +'/' + count + '/record/list';
	my.ajaxGet(urlPost,function(ret,err,status){
	    if(ret && ret.status){
			if(ret.data && ret.data.length>0 && ret.data[0].content.length > 0){
				var data = ret.data[0].content;
				var dataInters = '';
				if(count > data.length) {
					$(".weui-loadmore").hide();
				}
				for(var i = 0; i < data.length; i++){
					dataInters = {									
						headimgurl : data[i].headimgurl,
						userHeadImage : data[i].userHeadImage,		
						nickName : decodeURIComponent(data[i].nickName),
						userNickName : decodeURIComponent(data[i].userNickName),
						pay_time : data[i].createTime,
						pay_type : data[i].payType,
						amount : data[i].amount,
						tax : data[i].fee
					};
					if(data[i].key){
						dataInters.key = data[i].key;
					}else{
						dataInters.key = '';
					}
					if(data[i].pay_type == 1){
						if(data[i].nickName){
							dataInters.nickName = '"' + data[i].nickName + '"的';
						}else{
							dataInters.nickName = '"微信用户的"';
						}
					}
					if(data[i].nickName == null){
						dataInters.nickName = '[没有看到内容]';
					}
					createDatas(dataInters);
				}
				// 分页
				start ++;	
			} else{
				if(start > 0) {
					$("#dot_data").show();
				} else {
					flag = false;
					$(".weui-loadmore").hide();
				}
				if(type == 1) {
					$("#no_data").show();
					return;
				}
			}
	    }
	});
}

//传入数据源，利用模版生成HTML
function createDatas(dataInters) {
	var interText = doT.template(document.getElementById("interpolationtmpl").innerText);
	document.getElementById("interpolation").insertAdjacentHTML('beforeEnd', interText(dataInters));
}

//获取滚动条当前的位置 
function getScrollTop() {
	var scrollTop = 0;
	if (document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else if (document.body) {
		scrollTop = document.body.scrollTop;
	}
	return scrollTop;
}

//获取当前可是范围的高度 
function getClientHeight() {
	var clientHeight = 0;
	if (document.body.clientHeight && document.documentElement.clientHeight) {
		clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
	} else {
		clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
	}
	return clientHeight;
}

//获取文档完整的高度 
function getScrollHeight() {
	return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
