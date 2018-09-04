/** 企业-员工结构--jiang*/
var obj;
var employeeStructure = function(orgVal,isReadOnly){
	
	Ext.Ajax.request({   
    	url: __ctxPath + '/creditFlow/customer/enterprise/isEntEmptyEnterprise.do',   
   	 	method:'post',   
    	params:{organizecode:orgVal}, 
    	success: function(response, option) {
    		obj = Ext.decode(response.responseText);
        	var enterpriseId = obj.data.id;
        	employeeStructure_(enterpriseId,isReadOnly);
    	},
    	failure: function(response, option) {   
        	return true;
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	} 
    })
}

var employeeStructure_ = function(enterpriseId,isReadOnly){
	
	var panel_;
	var win_;
	var jStore_;
	
	var html_ = '<form id="form1" name="form1" method="post" action="">' +
			'<table width="95%" height="95%" border="2" align="center" cellspacing="0" bordercolor="white" >' +
				'<tr bgcolor="#E8EEF7">' +
					'<td width="97" class="div_mid" style="font-size:12px;text-align:center;">分类标准</td>' +
					'<td width="182" class="div_mid" style="font-size:12px;text-align:center;">类别</td>' +
					'<td width="168" class="div_mid" style="font-size:12px;text-align:center;">人数</td>' +
					'<td width="164" class="div_mid" style="font-size:12px;text-align:center;">所占比例%</td>' +
				'</tr>' +
				'<tr>' +
					'<td rowspan="5" class="div_mid" style="font-size:12px;text-align:center;">教育程度</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">大专及本科以下</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx1" id="id_xxx_xxx1" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx2" id="id_xxx_xxx2" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">本科</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx3" id="id_xxx_xxx3" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx4" id="id_xxx_xxx4" /></td>' +
				'</tr>' +
				'<tr>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">硕士</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx5" id="id_xxx_xxx5" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx6" id="id_xxx_xxx6" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">博士</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx7" id="id_xxx_xxx7" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx8" id="id_xxx_xxx8" /></td>' +
				'</tr>' +
				'<tr>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">博士后</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx9" id="id_xxx_xxx9" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx10" id="id_xxx_xxx10" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td rowspan="3" class="div_mid" style="font-size:12px;text-align:center;">技术职称</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">高级职称</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx11" id="id_xxx_xxx11" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx12" id="id_xxx_xxx12" /></td>' +
				'</tr>' +
				'<tr bgcolor="#DFE8F6">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">中级职称</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx13" id="id_xxx_xxx13" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx14" id="id_xxx_xxx14" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">其它</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx15" id="id_xxx_xxx15" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx16" id="id_xxx_xxx16" /></td>' +
				'</tr>' +
				'<tr>' +
					'<td rowspan="3" class="div_mid" style="font-size:12px;text-align:center;">工作性质</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">管理人员</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx17" id="id_xxx_xxx17" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx18" id="id_xxx_xxx18" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">技术人员</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx19" id="id_xxx_xxx19" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx20" id="id_xxx_xxx20" /></td>' +
				'</tr>' +
				'<tr>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">其它</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx21" id="id_xxx_xxx21" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx22" id="id_xxx_xxx22" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td rowspan="4" class="div_mid" style="font-size:12px;text-align:center;">年龄段</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">20-30</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx23" id="id_xxx_xxx23" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx24" id="id_xxx_xxx24" /></td>' +
				'</tr>' +
				'<tr bgcolor="#DFE8F6">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">30-40</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx25" id="id_xxx_xxx25" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx26" id="id_xxx_xxx26" /></td>' +
				'</tr>' +
				'<tr bgcolor="#E8EEF7">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">40-50</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx27" id="id_xxx_xxx27" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx28" id="id_xxx_xxx28" /></td>' +
				'</tr>' +
				'<tr bgcolor="#DFE8F6">' +
					'<td class="div_mid" style="font-size:12px;text-align:center;">50岁以上</td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx29" id="id_xxx_xxx29" /></td>' +
					'<td class="div_mid" style="font-size:12px;text-align:center;"><input type="text" name="id_xxx_xxx30" id="id_xxx_xxx30" /></td>' +
				'</tr>' +
			'</table>' +
		'</form>';


	var btn_reset = new Ext.Button({
		text : '重置',
		iconCls : 'resetIcon',
		handler : function(){
			document.forms['form1'].reset()
		}
	});
	
	var btn_save = new Ext.Button({
		text : '保存',
		iconCls : 'saveIcon',
		handler : function(){
			
			var pbar = Ext.MessageBox.wait('数据保存中','请等待',{
				interval:200,
		        increment:15
			});
			
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/enterprise/saveEmpoyeeStEnterpriseEmployeeStructure.do',
				method : 'POST',
				success : function(response) {
					var ok = Ext.util.JSON.decode(response.responseText);
					
					if(ok.success){
						Ext.ux.Toast.msg('状态','保存成功！');
						win_.close()
					}else{
						Ext.ux.Toast.msg('错误','保存失败！');
					}
					pbar.getDialog().close();
					
				},
				failure : function(response){
					Ext.ux.Toast.msg('错误','操作失败，请检查网络');
				},
				params : {
					enterpriseId : enterpriseId
				},
				form : 'form1'
			});
			
		}
	});
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	panel_ = new  Ext.Panel({
		frame : true,
		autoWidth : true,
		autoHeight : true,
		html : html_
	});
	
	win_ = new Ext.Window({
		title : '<font color=red><'+obj.data.shortname+'></font>员工结构',
		width: (screen.width-180)*0.7,
		height : 400,
		collapsible : true,
		maximizable : true,
		autoScroll : true,
		layout : 'fit',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items : [panel_],
		tbar : isReadOnly?[]:[btn_save,btn_reset]
	});
	
	win_.show();
	
	
	jStore_ = new Ext.data.JsonStore({
		url : __ctxPath + '/creditFlow/customer/enterprise/getEmployeeStEnterpriseEmployeeStructure.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		autoLoad : true,
		fields : [{name : 'id'},
					{name: 'textfieldId'},
					{name: 'textfieldText'},
					{name: 'enterpriseId'}],
		baseParams:{
			enterpriseId : enterpriseId
		},
		listeners : {
			'load' : function(t,records,options){
				
				t.each(function(r){
//					alert(r.get('textFeildText'));
					document.getElementById(r.get('textfieldId')).value=r.get('textfieldText');
				});
			}
		}//listeners
	});//jStore_
}
