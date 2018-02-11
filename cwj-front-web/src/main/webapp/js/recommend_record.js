/**
 * 提现记录列表
 */
// 分页参数
var start = 0;
var count = 2;
var flag = true;
var totalElements = 0;
//用户信息
//当前用户id
var openId = my.getUrlParam("openId");
(function() {
	//初始哈数据
	initData(openId);
	//分页判断
	window.onscroll = function() {					
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {//判断滚动条到达底部的条件,并自动触发下面AJAX									
			initData(openId);
		}
	} 
})();

//传入数据源，利用模版生成HTML
function createDatas(dataInters) {
   var interText = doT.template(document.getElementById("tmprecodes_left").innerText);
   document.getElementById("tagContent").insertAdjacentHTML('beforeEnd', interText(dataInters));
}   

/**
 * 初始化数据
 * @returns {Boolean}
 */
function initData(openId) {
	if(openId == "undefined" || openId == undefined){
		openId = "";
	}
	if(!flag){
		return false;
	}
	
	my.ajaxGet("/userRecommend/userRecommendQrImage?refOpenid="+openId,function(ret,err,status){
		if(ret && ret.status && !my.isNotEmpty(localStorage.sceneUrl)){
			localStorage.sceneUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ret.data;
			$("#sceneUrl").attr("src",localStorage.sceneUrl);
		} else {
			$("#commendQrcode").attr("src",localStorage.sceneUrl);
		}
	},true);
	
	//查询当前用户列表
	var	urlPost = '/userRecommend/'+ start +'/' + count + '/record/list';
	
	my.ajaxGet(urlPost,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data[0].content;
			if(totalElements <= 0) {
				totalElements = ret.data[0].totalElements;
			}
			$("#sum").text(totalElements);
			if(data){
				if(data.length){
					var dataInters = '';
					for(var i = 0; i < data.length; i++){
						dataInters = {
								createTime : myc.getTime((new Date(data[i].createTime))),
								nickName : decodeURIComponent(data[i].nickName),
								headimgurl : data[i].headimgurl,
								sex : data[i].sex
							};
						createDatas(dataInters);
					}
					start ++;
				} else{
					myc.toast({
						msg : '没有更多了'
					});
					flag = false;
				}
			}
		}
	},true);
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
