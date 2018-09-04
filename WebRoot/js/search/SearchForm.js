var SearchForm = new Ext.Panel({
			id : 'SearchForm',
			layout : 'hbox',
			layoutConfig : {
				padding : '2',
				align : 'top'
			},
			border : false,
			bodyStyle : 'background-color: transparent;',
			style:'margin-top:-15px;',
            defaults : {
				margins:{top:0,left:2,bottom:0,right:0}
				
			},
			items : [
				{
					id : 'searchContent',
					xtype : 'textfield',
					width : 120,
					height :22
				}, {
					id : 'searchType',
					width : 60,
					xtype : 'combo',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					store : [['news', '新闻'], ['mail', '邮件'], ['notice', '公告'],
							['document', '文档']],
					value : 'news'
				},{
					xtype : 'button',
					text : '搜索',
					iconCls : 'search',
					handler : function() {
						var searchContent = Ext.getCmp('searchContent').getValue();
						var searchType = Ext.getCmp('searchType').value;
						if (searchType == 'news') {
							App.clickTopTab('SearchNews',searchContent,function(){
								AppUtil.removeTab('SearchNews');
							});
						}else if(searchType == 'mail'){
							App.clickTopTab('SearchMail',searchContent,function(){
								AppUtil.removeTab('SearchMail');
							});
						}else if(searchType == 'notice'){
							App.clickTopTab('SearchNotice',searchContent,function(){
								AppUtil.removeTab('SearchNotice');
							});
						}else if(searchType == 'document'){
							App.clickTopTab('SearchDocument',searchContent,function(){
								AppUtil.removeTab('SearchDocument');
							});
						}
					}
				}
				]
			});
