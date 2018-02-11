/**
 * 上传视频
 */
var upload_photo = (function() {

	//获取随机开关值
	$("#random-switch").bind("click", function() {
		if ($("#random").val() == "off") {
			$("#random").val("2");
		} else {
			$("#random").val("1");
		}
	});
	
	var GUID = WebUploader.Base.guid();
	//初始化webuploader组件
	var uploader = WebUploader.create({
		swf: '../js/Uploader.swf',
	    // 文件接收服务端。
	    server: rootPath + '/wechat/upload/video',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#picker',
			multiple : false
		},
		fileSizeLimit : 100 * 1024 * 1024,
		fileSingleSizeLimit : 100 * 1024 * 1024,
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize : true,
		// 只允许选择视频文件文件
		accept : {
			title : 'Videos',
			extensions: 'mp4,flv,rmvb,avi',
			mimeTypes : 'video/mp4,video/flv,video/rmvb,video/avi'
		},
		formData: { guid: GUID },
		duplicate:false,//是否可重复选择同一文件  
        chunked: true,  //分片处理  
        chunkSize: 5 * 1024 * 1024, //每片5M  
        chunkRetry:2,//如果失败，则不重试  
        threads:3,//上传并发数。允许同时最大上传进程数。  
        fileNumLimit:1,//上传的文件总数  
        // 禁掉全局的拖拽功能。  
        disableGlobalDnd: true  
	});

	//开始上传
	$('#ctlBtn').click(function() {

		if (!$('.state').html()) {
			myc.toast({
				msg : '请选择您要上传的视频'
			});
			return false;
		}

		uploader.options.formData.title = $('#title').val()||"我珍藏的视频，打开有惊喜哦！！！";
		uploader.options.formData.descr = $('#descr').val()||"我珍藏的视频，打开有惊喜哦！！！";
		uploader.options.formData.blur = $('#blur').val();
		uploader.options.formData.random = $('#random').val();
		uploader.options.formData.price = $('#price').val();
		uploader.options.formData.fileCategory = 2;
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
	
	//显示用户选择
	// 当有文件被添加进队列的时候 由于webuploader不处理UI逻辑，所以需要去监听fileQueued事件来实现。
	uploader.on('fileQueued', function(file) {
		// myc.toast({
		// 	msg : '选择成功'
		// })
		// 隐藏上传按钮
		$('.btns').css('display', 'none');

		$list = $('#thelist');

		var $li = $('<div id="' + file.id
				+ '" class="file-item thumbnail" style="margin:20px 0" > '
				+ '<img>' + '<div class="info">' + file.name + '</div>'
				+ '<div class="state" style="color:red;">' + sizeBig(file.size)
				+ '</div>' + '<p class="state" style="color:red;">等待上传</p>' +

				'</div>'), $img = $li.find('img');
		// $list为容器jQuery实例
		$list.append($li);

		// 创建缩略图
		// 如果为非图片文件，可以不用调用此方法。
		// thumbnailWidth x thumbnailHeight 为 100 x 100
		uploader.makeThumb(file, function(error, src) {
			if (error) {
				$img.replaceWith('<span>视频文件</span>');
				return;
			}

			$img.attr('src', src);
		}, 100, 100);
	});
	
	//文件上传进度 文件上传中，Web Uploader会对外派送uploadProgress事件，其中包含文件对象和该文件当前上传进度。
	// 文件上传过程中创建进度条实时显示。
	uploader.on(
					'uploadProgress',
					function(file, percentage) {
						var $li = $('#' + file.id), $percent = $li
								.find('.barContainer .bar');

						// 避免重复创建
						if (!$percent.length) {
							$percent = $(
									'<div class="barContainer"><div class="bar" style="width: 100%"></div></div>')
									.appendTo($li).find('.bar');
						}

						$li.find('p.state').text('上传中');

						$percent.html((percentage * 100).toFixed(2) + '%');
						myc.showProgress({
							title : '上传中',
							text : (percentage * 100).toFixed(2) + '%'
						});
						if (percentage * 100 == 100) {
					    	myc.showProgress({
					    		title:'等待服务器处理'
					    	});
						}
					});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file, response) {
		$('#' + file.id).find('p.state').text('已上传');
		myc.hideProgress();
		if (response.status) {
			var timeAjax = 60;
			repeat(timeAjax, response.key);
		} else {
			myc.toast({
				msg : response.msg||'上传出错,请重新上传'
			});
		}
	});
	

	// 不断请求服务器上传完没有
	function repeat(time, key) {
		myc.showProgress({
			title : '等待服务器处理'
		});
		if (time == 0) {
			return false;
			myc.hideProgress();
		} else {
			//操作
			uploadSuccessAjax(key, function(type) {
				if (type == 1) {
					myc.hideProgress();
					return false;
				} else {
					time--;
					if (time == 60) {
						repeat(time, key);
					} else {
						setTimeout(function() {
							repeat(time, key);
						}, 5000);
					}
				}
			});
		}
	}

	function uploadSuccessAjax(key, callback) {
		callback(1);
		wx.closeWindow();
	}

	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function(file) {
		$('#' + file.id).find('p.state').text('上传出错,请重新上传');
		myc.toast({
			msg : '上传出错,请重新上传'
		});
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function(file) {
//		console.log('完成上传完了，成功或者失败')
	});
	
	//验证上传的视频
	uploader.on('error', function(file) {
		if (file == "Q_EXCEED_NUM_LIMIT") {
			myc.alert({
				msg : '您已经设置过上传的文件'
			});
			return false;
		} else if (file == "Q_TYPE_DENIED") {
			myc.alert({
				msg : '不支持这个视频格式'
			});
			return false;
		} else if (file == 'Q_EXCEED_SIZE_LIMIT') {
			myc.toast({
				msg : '不能上传超过100M的视频'
			});
			return false;
		}
	});
})();
