/**
 * 上传图片
 */
var upload_photo = (function() {

	//获取随机开关值
	$("#random-switch").bind("click", function() {
		if ($("#random").val() == "1") {
			$("#random").val("2");
		} else {
			$("#random").val("1");
		}
	});
	
	//开始上传
	$('#ctlBtn').click(function() {

		if (!$('.state').html()) {
			myc.toast({
				msg : '请选择您要上传的图片'
			});
			return false;
		}

		uploader.options.formData.title = $('#title').val() || "我珍藏的一张图片，打开有惊喜哦！！！";
		uploader.options.formData.descr = $('#descr').val() || "我珍藏的一张图片，打开有惊喜哦！！！";
		uploader.options.formData.blur = $('#blur').val();
		uploader.options.formData.random = $('#random').val();
		uploader.options.formData.price = $('#price').val();
		uploader.retry();
	});
	
	// 显示文件大小
	function sizeBig(num) {
		if (num < 1024) {
			return num + 'B';
		} else if (num < 1024 * 1024) {
			return (num / 1024).toFixed(2) + 'KB';
		} else if (num < 1024 * 1024 * 1024) {
			return ((num / 1024) / 1024).toFixed(2) + 'M';
		}
	}
	
	var GUID = WebUploader.Base.guid();
	//初始化webuploader组件
	var uploader = WebUploader.create({
//		auto : true, //设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
	    // swf文件路径
	    swf: '../js/Uploader.swf',
	
	    // 文件接收服务端。
	    server: rootPath +'/wechat/upload/image',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: {
	    	id : '#picker',
	    	multiple : false
	    },
		compress  : false,
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    // 只允许选择图片文件
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png' //如果写成image/*会出现响应慢的问题
        },
        formData: { guid: GUID },
		duplicate:false,//是否可重复选择同一文件  
        chunked: true,  //分片处理  
        chunkSize: 1 * 1024 * 1024, //每片5M  
        chunkRetry:2,//如果失败，则不重试  
        threads:3,//上传并发数。允许同时最大上传进程数。  
        fileNumLimit:1,//上传的文件总数  
        fileSingleSizeLimit: 10 * 1024 * 1024,// 限制在10M  
        // 禁掉全局的拖拽功能。  
        disableGlobalDnd: true,  
     // 配置生成缩略图的选项
        thumb:{
            // 缩略图的宽高，当取值介于0-1时，被当成百分比使用
            width:500,
            height:250,
            // 强制转换成指定的类型
            type:"image/jpeg",
            // 图片质量，只有type为image/jpeg的时候才有效
            quality:70,
            // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false
            allowMagnify:false,
            // 是否允许裁剪
            crop:true            
        },      

        // 是否压缩图片, 默认如果是jpeg文件上传前会压缩，如果是false, 则图片在上传前不进行压缩
        compress:{
            // 压缩后的尺寸
            width: 960,
            height: 1280,
            // 图片质量，只有type为image/jpeg的时候才有效。
            quality: 90,
            // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false
            allowMagnify: false,
            // 是否允许裁剪
            crop: false,
            // 是否保留头部meta信息
            preserveHeaders: true,
            // 如果发现压缩后文件大小比原来还大，则使用原来图片，此属性可能会影响图片自动纠正功能
            noCompressIfLarger: false,
            // 单位字节，如果图片大小小于此值，不会采用压缩
            compressSize: 200 * 1024
        }
	});
	
	//显示用户选择
	// 当有文件被添加进队列的时候 由于webuploader不处理UI逻辑，所以需要去监听fileQueued事件来实现。
	uploader.on('fileQueued', function(file) {
		// 隐藏上传按钮
		$('.btns').css('display', 'none');

		$list = $('#thelist');

		var $li = $(
	            '<div id="' + file.id + '" class="file-item thumbnail" style="margin:20px 0" > ' +
	                '<img>' +
	                '<div class="info">' + file.name + '</div>' +
	                '<p class="state" style="color:red;">'+sizeBig(file.size)+'</p>' +
	                '<p class="state" style="color:red;">等待上传</p>' +
	                
	            '</div>'
	            ),
				
		$img = $li.find('img');
		// $list为容器jQuery实例
		$list.append($li);
		// 创建缩略图
		// 如果为非图片文件，可以不用调用此方法。
		// thumbnailWidth x thumbnailHeight 为 100 x 100
		uploader.makeThumb(file, function(error, src) {
			if (error) {
				$img.replaceWith('<span>图片文件</span>');
				return;
			}
			$img.attr('src', src);
		}, 200, 200);
	});
	
	/**
	 * 文件上传进度 文件上传中，Web Uploader会对外派送uploadProgress事件，其中包含文件对象和该文件当前上传进度。
	 * 文件上传过程中创建进度条实时显示。
	 */
	uploader.on( 'uploadProgress', function( file, percentage ) {
	   	myc.showProgress({
	   		title : '上传中',
	   		text : (percentage * 100).toFixed(2) + '%'
	   	});
	    if(percentage*100 == 100){			    	
	    	myc.showProgress({
	    		title:'等待服务器处理'
	    	});
	    }
	});
	
	/**
	 * 文件上传成功，给item添加成功class, 用样式标记上传成功。
	 */
	uploader.on( 'uploadSuccess', function( file , response ) {
	    myc.hideProgress();
		if(response.status){
			myc.toast({
				location:'middle',
				msg:'上传成功'
			});
			setTimeout(function(){
				wx.closeWindow();
			},2000);
		}else{
			myc.toast({
				msg : response.msg||'上传出错,请重新上传'
			});
		}		     			     			      
	});
	
	/**
	 * 只能上传一个图片文件
	 */
	uploader.on('error', function(file) {

		if(file=="Q_EXCEED_NUM_LIMIT"){
			myc.toast({
				msg:'只能上传一个图片文件'
			});
			return false;
		}else if(file=="Q_TYPE_DENIED"){
			myc.toast({
				msg:'不支持这个图片格式'
			});
			return false;
		}
	});
	
	/**
	 * 文件上传失败，显示上传出错。
	 */
	uploader.on( 'uploadError', function( file ) {
		myc.toast({
			msg:'上传出错,请重新上传'
		});
	});
	
	/**
	 * 完成上传完了，成功或者失败，先删除进度条。
	 */
	uploader.on( 'uploadComplete', function( file ) {
		myc.hideProgress();
	});
})();
