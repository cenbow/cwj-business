/**
 * 提现
 */
(function() {
	//初始化数据
	getAmount(1);	
	//点击提现
	$('#tixianBtn').click(function(){
		var money_num = parseFloat($('#money_num').html());	
		var moneyInput = parseFloat($('#moneyInput').val());
		if(!moneyInput){
			myc.toast({
				msg : '请输入金额'
			});
			return false;
		}
		if(moneyInput <= money_num){
			if(moneyInput >= 2){
				myc.showProgress({
					title : '提现中'
				});
				my.ajaxPost('/userWithdrawal/save',{amount:moneyInput},function(ret,err,status){
					myc.hideProgress();
					if(ret && ret.status){
						myc.toast({
							msg : '提现申请成功'
						});
						setTimeout(function(){
							getAmount(2);	
							$('#moneyInput').val('');
						},2000);
					}else{	
						myc.toast({
							msg : ret.msg
						});					
					}
				},false);
			}else{
				myc.toast({
					msg : '由于微信支付的规定限制，必须金额大于2元才能提现。'
				});
			}
		}else{
			myc.toast({
				msg : '输入金额不能大于余额。'
			});
		}				
	});
})();

//获取余额
function getAmount(type){
	myc.showProgress();
	//查询当前用户列表
	var	urlPost = '/user/queryUserBalanceInfo';
	my.ajaxGet(urlPost,function(ret,err,status){
		myc.hideProgress();
		if(ret && ret.status){
			var data = ret.data;
			if(data){
				$('#money_num').html(my.formatPrice(data.balance-data.withdrawBalance) || '0');
				$('#withdrawDayLimit').html(data.withdrawDayLimit + "次"  || 1 + "次");
				$('#withdrawDayMaxMoney').html(data.withdrawDayMaxMoney || '200');
				$('#feeScale').html(data.feeScale || 10);
			}											
		}
	});
}
