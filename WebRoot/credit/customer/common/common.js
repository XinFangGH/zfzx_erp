/** 保存 保存成功后不关闭窗口，1
 * 返回 返回提示保存数据，
 * 取消 取消不提示保存数据，
 * 提交， 提交成功后关闭窗口 
 * 是否为空 保存调用查看是否为空*/



	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击取消按钮以后，判断是否为空， */
	var isEmpty = function(formPanelId , winObj , storeObj){
		var formObj = Ext.getCmp(formPanelId);
		var textfieldArray = formObj.findByType('textfield');
		var datefieldArray = formObj.findByType('datefield');
		var numberfieldArray = formObj.findByType('numberfield');
		var radiogroupArray = formObj.findByType('radiogroup');
		var comboArray = formObj.findByType('combo');
		var csRemoteComboArray = formObj.findByType('csRemoteCombo');
		for(var i = 0 ; i < textfieldArray.length ; i ++){
			var textfieldObj = textfieldArray[i] ;
			var val = textfieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < datefieldArray.length ; i ++){
			var datefieldObj = datefieldArray[i] ;
			var val = datefieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < numberfieldArray.length ; i ++){
			var numberfieldObj = numberfieldArray[i] ;
			var val = numberfieldObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < radiogroupArray.length ; i ++){
			var radiogroupObj = radiogroupArray[i] ;
			var val = radiogroupObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < comboArray.length ; i ++){
			var comboObj = comboArray[i] ;
			var val = comboObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}for(var i = 0 ; i < csRemoteComboArray.length ; i ++){
			var csRemoteComboObj = csRemoteComboArray[i] ;
			var val = csRemoteComboObj.getValue();
			if("" != val){
				Ext.MessageBox.confirm('状态', '是否确认保存数据', function(btn) {
					if (btn == 'yes') {
						formSavePerson(formPanelId , winObj , storeObj);
					}
				});
				return ;
			}
		}
	}
	
	/**客户管理模块 ，个人信息保存数据方法 ， 提供个人管理公共调用*/
	var formSavePerson = function(formPanelId ,winObj, storeObj ,objId){
		var formObj = Ext.getCmp(formPanelId);
		
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			clientValidation : true,
			success : function(form ,action) {
				obj = Ext.util.JSON.decode(action.response.responseText);
				if(obj.exsit == false){
					Ext.ux.Toast.msg('状态' ,obj.msg );
				}else{
					Ext.ux.Toast.msg('状态', '保存成功!');
						storeObj.removeAll();
						storeObj.reload();
						formObj.isflag = true ;
						if(formObj.findById){
							var eIdHidden = formObj.findById(objId) ;
							if(eIdHidden&&eIdHidden.setValue){
								eIdHidden.setValue(obj.newId) ;
								eIdHidden.originalValue = obj.newId ;
							}
						}
//						var childComponent = new Array();
//						var textfield = new Object();
//						childComponent = formObj.findByType('textfield');
//						for(var i=0;i<childComponent.length;i++){
//							textfield = childComponent[i];
//							textfield.setDisabled(true);
//						}
						//Ext.getCmp('submit').setDisabled(true);
						if(null != winObj){
							winObj.destroy();
						}
						formObj.doLayout();
				}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
			}
		})
	}
	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击保存按钮以后调用的方法。 */
	var formSave = function(formPanelId ,winObj ,storeObj){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
			success : function(form ,action) {
				Ext.Msg.alert('状态', '保存成功!');
					storeObj.reload();
					if(null != winObj){
						winObj.destroy();
					}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整');
			}
		})
	}
	
	/**客户管理模块 ，公共方法， 当表单填写数据后， 客户点击返回或取消按钮以后调用的方法。*/
	var formBack = function(formPanelId,winObj , storeObj){
		isEmpty(formPanelId,winObj , storeObj);
	}