/**
 * 查看打赏
 */
(function() {
	//获取商品ID
	id = my.getUrlParam("id");
	orderNo = my.getUrlParam("orderNo");
	//初始化数据
	init(id);	
	
	 $("#toAttention").attr("href",'https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzU1MTE3OTA0Mg==&scene=124#wechat_redirect"');
})();

function initPhotoBrowser(imagePath) {
	var pb1 = $.photoBrowser({
        items: [
                imagePath
        ],

        onSlideChange: function(index) {
        	//change事件
//        	console.log(this, index);
        },

        onOpen: function() {
        	//open事件
//        	console.log("onOpen", this);
        },
        onClose: function() {
        	//关闭事件
//        	console.log("onClose", this);
        }
      });
      $("#toFilePath").click(function() {
        pb1.open();
      });
}

//初始化数据
function init(id){
	//查询当前用户列表
	var	urlPost = '/userFiles/query/baseInfo/'+orderNo+'/'+id;
	my.ajaxGet(urlPost,function(ret,err,status){
		myc.hideProgress();
		if(ret && ret.status){
			var data = ret.data;
			if(data){
				$("#price").text(my.formatPrice(data.price));
				$("#title").text(data.title);
				$("#descr").text(data.descr);
				initPhotoBrowser(data.filePath);
			}											
		}
	});
}
