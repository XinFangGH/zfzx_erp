/**
 * @author
 * @createtime
 * @class SlFundIntentForm
 * @extends Ext.Window
 * @description SlFundIntent表单
 * @company 智维软件
 */
selectCustMember = function(funname){
			this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							autoScroll : true,
							region : 'north',
							
							border : false,
							height : 50,
							anchor : '80%',
							layoutConfig: {
				               align:'middle'
				            },
				             bodyStyle : 'padding:10px 10px 10px 10px',
							items:[{
					     		columnWidth :.3,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'用户名',
											name : 'Q_loginname_S_LK',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
											}]
					     	},{
					     		columnWidth :.3,
								layout : 'form',
								labelWidth : 80,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel:'真实姓名',
											name : 'Q_truename_S_LK',
											flex:1,
											anchor : "90%",
											xtype : 'textfield'
										}]
				     	},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '查询',
							xtype : 'button',
							scope : this,
							style :'margin-left:30px',
							anchor : "90%",
							iconCls : 'btn-search',
							handler : function(){
								$search({
									searchPanel:this.searchPanel,
									gridPanel:this.gridPanel
								});
							}
						}]
		     		},{
		     			columnWidth :.1,
						layout : 'form',
						border : false,
						labelWidth :50,
						items :[{
							text : '重置',
							style :'margin-left:30px',
							xtype : 'button',
							scope : this,
							//width : 40,
							anchor : "90%",
							iconCls : 'btn-reset',
							handler : function(){
								this.searchPanel.getForm().reset();
							}
						}]
		     		}]
								
				});// end of searchPanel
	this.reset = function(){
				this.searchPanel.getForm().reset();
			}
			//按条件搜索
      	this.search = function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			}
	this.gridPanel = new HT.GridPanel({
					isShowTbar : false,
					region : 'center',
					// 使用RowActions
					rowActions : false,
					id : 'selectAccountlGrid',
					url : __ctxPath + "/customer/listBpCustMember.do",
					baseParams :{
					start : 0,
					limit : 100
					
					},
				id:'BpCustMemberGrid',
					url : __ctxPath + "/p2p/listBpCustMember.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
								,'loginname'
								,'truename'
								,'password'
								,'plainpassword'
								,'telphone'
								,'email'
								,'type'
								,'sex'
								,'sexname'
								,'cardtype'
								,'cardtypename'
								,'cardcode'
								,'birthday'
								,'headImage'
								,'nativePlaceProvice'
								,'nativePlaceCity'
								,'nation'
								,'homePhone'
								,'relationAddress'
								,'postCode'
								,'QQ'
								,'MSN'
								,'paymentCode'
								,'securityQuestion'
								,'securityAnswer'
								,'roleId'
								,'registrationDate'
								,'liveProvice'
								,'liveCity'
								,'marry'
								,'fax'
								,'memberOrderId'
								,'isDelete'
								,'isForbidden'
								,'memberGrade'
								,'directReferralsName'
								,'indirectReferenceName'
								,'score'
								,'categoryName'
                                ,'registrationDate',
                                'thirdPayFlagId'
																																			],
					columns:[
								{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '登录名',	
									dataIndex : 'loginname'
										,width:100	

								},{
									header : '真实姓名',	
									dataIndex : 'truename'
										,width:70
								},{
									header : '第三方账号',	
									dataIndex : 'thirdPayFlagId'
										,width:70
								},{
									header : '手机号码',	
									dataIndex : 'telphone'
										,width:100
								},{
									header : '邮箱',	
									dataIndex : 'email'
									,width:40
								}],
								listeners:{
								     'rowdblclick' : function(grid,rowIndex,e) {
	
	                                                grid.getSelectionModel().each(function(rec) {
			                                       	funname(rec.data);
		                      	                    });
	                                         selectAccountlWindow.destroy();
			}
		}
						// end of columns
				});
	var selectAccountlWindow = new Ext.Window({
		width: (screen.width-180)*0.7,
		title : '投资人列表',
		height : 600 ,//高度自by chencc
		collapsible : true,
		region : 'center',
		layout : 'border',
		modal : true,
		resizable : false,
		items : [searchPanel, gridPanel]
	});
selectAccountlWindow.show();
}