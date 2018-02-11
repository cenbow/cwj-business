/**
 * 打赏圈
 */
// 分页参数
var start = 0;
var count = 10;
var flag = true;
var dataPswpUid = 0;

//用户信息
//当前用户id
var openId = my.getUrlParam("openId");
var isMyOrder = my.getUrlParam("isMyOrder");
//当前用户头像
var userHeadimgurl = null;
//当前用户昵称
var userNickName = null;

(function() {
	//初始化下拉刷新组件
	$(document.body).pullToRefresh();
	//刷新触发事件
	$(document.body).on("pull-to-refresh", function() {
		window.location.reload();
		//关闭刷新
		$(document.body).pullToRefreshDone();
	});
	//初始哈数据
	initData(openId);
	//分页判断
	window.onscroll = function() {
		//判断滚动条到达底部的条件,并自动触发下面AJAX
		if (getScrollTop() + getClientHeight() == getScrollHeight()) {									
			initData(openId);
		}
	} 
})();

//初始化图片浏览插件
function initPhotoBrowser(id,imagePath) {
	var pb1 = $.photoBrowser({
        items: [
          imagePath,
        ]
      });
      $("#"+id).click(function() {
        pb1.open();
      });
}


function openBlurShare(id) {
	location.href = "wx_blur_image_share.html?id="+id;
}

function openBlurPay(id) {
	location.href = "wx_image_wxpay.html?id="+id;
}

function openMyHome(openId) {
	location.href = "index.html?openId="+openId;
}

function more(obj,id) {
	if ($('#txt'+id).is(":hidden")) {
		$('#p'+id).hide();
		$('#txt'+id).show();
		obj.innerHTML='收起';
	} else {
		$('#p'+id).show();
		$('#txt'+id).hide();
		obj.innerHTML='全文';
	}
}

//传入数据源，利用模版生成HTML
function createDatasLeft(dataInters) {
	var interText = doT.template(document.getElementById("tmprecodes_left").innerText);
	document.getElementById("recodesLeft").insertAdjacentHTML('beforeEnd', interText(dataInters));
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
	//查询当前用户列表
	var	urlPost = '/userFiles/'+ start +'/' + count + '/list?openId='+openId+'&isMyOrder='+isMyOrder
//	myc.showProgress();
	//获取当前用户头像昵称
	my.ajaxGet('/user/queryUserInfo?openId='+openId,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data;
			userHeadimgurl = data.headimgurl;
			userNickName = data.nickName;
			$("#userHeadimgurl").attr("src",userHeadimgurl);
			$("#userNickName").text(decodeURIComponent(userNickName));
		}
	})
	my.ajaxGet(urlPost,function(ret,err,status){
//		myc.hideProgress();
		if(ret && ret.status){
			if(ret.data && ret.data.content){
				var data = ret.data.content;
				if(data.length){
					var dataInters = '';
					if(count > data.length) {
						$(".weui-loadmore").hide();
					}
					for(var i = 0; i < data.length; i++){
						dataPswpUid++;
						dataInters = {
								key : data[i].id,
								openId : data[i].openId,
								nickName : decodeURIComponent(data[i].user.nickName),
								descr : data[i].descr,
								headimgurl : data[i].user.headimgurl,
								blurUrl : data[i].smallFilePath,
								createTime:data[i].createTime,
								fileCategory:data[i].fileCategory,
								index : dataPswpUid
							};
						createDatasLeft(dataInters);
						if(data[i].filePath) {
							initPhotoBrowser(data[i].id,data[i].filePath);
						} else {
							initPhotoBrowser(data[i].id,data[i].blurFilePath);
						}
						
						//监听video播放事件
						var myvideo=document.getElementById(data[i].id+'video');
						if(my.isNotEmpty(myvideo) && !my.isNotEmpty(openId)) {
							myvideo.addEventListener('play',function(){
								payVideoTip(data[i].id);
							});
						}
					}
					start ++;
				} else{
					if(start > 0) {
						$("#dot_data").show();
					} else {
						flag = false;
						$(".weui-loadmore").hide();
					}
				}
			} else {
				$(".weui-loadmore").hide();
			}
		}
	},false);
}

//打赏视频提示
function payVideoTip(id) {
	if(!my.isNotEmpty(openId)){
		myc.confirm({
			msg:"我是付费打赏视频，请打赏后再欣赏",
			buttons: [
				"分享",
				"打赏",
				"取消"
			]
		},function(index){
			switch(index.buttonIndex)
			{
			case 1:
				openBlurShare(id)
				break;
			case 2:
				openBlurPay(id)
			  	break;
			}
		});
//		$.modal({
//			title: "提示",
//			text: "我是付费打赏视频，请打赏后再欣赏",
//			buttons: [
//				          { text: "分享", onClick: function(){openBlurShare(id)}},
//			        	  { text: "打赏", onClick: function(){openBlurPay(id)}},
//			        	  { text: "取消", className: "default", onClick: function(){}}
//		        	 ]
//        	  });
	  }
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
