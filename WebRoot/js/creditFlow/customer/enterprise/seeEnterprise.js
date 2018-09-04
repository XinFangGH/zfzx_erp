//add by lisl 2012-2-17 修改查看企业信息方法 
var seeEnterpriseCustomer = function(enterpriseId) {
	Ext.Ajax.request({
		url : __ctxPath+ '/creditFlow/customer/enterprise/loadInfoEnterprise.do',
		method : 'POST',
		success : function(response,request) {
			var obj = Ext.decode(response.responseText);
			enterpriseData = obj.data.enterprise;
			personData = obj.data.person;
			seeEnterpriseWin(enterpriseData,personData);
		},
		failure : function(response) {
			if(response.status==0){
				Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
			}else if(response.status==-1){
				Ext.ux.Toast.msg('状态','连接超时，请重试!');
			}else{
				Ext.ux.Toast.msg('状态','查看失败!');
			}
		},
		params: { id: enterpriseId }
	});
}
//end 
function seeEnterpriseWin(enterpriseData,personData) {
	
	var anchor = '100%';
	var window_see = new Ext.Window({
				id : 'win_seeEnterprise',
				title : '企业信息',
				layout : 'fit',
//				iconCls : 'btn-detail',
				width : (screen.width - 180) * 0.7 + 160,
				maximizable:true,
				height : 460,
				closable : true,
				modal : true,
				plain : true,
				border : false,
				items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,isReadOnly:true})]
			});
	window_see.show();
}