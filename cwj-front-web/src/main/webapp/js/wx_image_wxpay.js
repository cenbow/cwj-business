/**
 * 支付打赏
 */
var id = my.getUrlParam("id");
var image_pay = (function() {
	//初始化
	init();
    setTimeout(function() {
         //当图片高度小于360px时，打赏图片按钮的top为30%
         var imgW = $('.weui-article img').height();
         var newTop = (imgW/2 + 66) + 'px';
         $('a.reward').css({top:newTop}); 
         //弹出窗开启
         $('a.reward').click(function() {
             $('.reward-dialog').show();
         });
         //弹出窗关闭
         $('.dialog-close').click(function() {
             $('.reward-dialog').hide();
         });
         var dia_top = '-' + (imgW/2 + 166) + 'px';
         $('.reward-dialog').css({top:dia_top});
     },300);
//     wx.config({
//         debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//         appId: "wxa4b944d0cb27a7d4",
//         timestamp: "1414587457",
//         nonceStr: "Wm3WZYTPz0wzccnW",
//         signature: "f7cb20e4db00b694fc5037d68e3d1d2c28541528",
//         jsApiList: [
//         'hideAllNonBaseMenuItem',l
//         'hideOptionMenu',
//         ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//     });   
//     wx.ready(function(){
//         //隐藏所有非基础按钮接口
//         wx.hideOptionMenu();
//     });   
})();

function init() {
	//根据文件ID查询模糊图片信息
	my.ajaxGet('/userFiles/query/blur/'+id,function(ret,err,status){
		if(ret && ret.status){
			var data = ret.data;
			$("#blur_image").attr("src",rootPath+data.blurFilePath);
			var price = data.price||"0.00";
			$("#price").text("￥" + price.toFixed(2) +"元");
			$("#tips-content").html('该内容由<img src="'+data.headimgurl+'" class="head_img">'+decodeURIComponent(data.nickName)+' 发布并收费');
		}
	},true)
}
