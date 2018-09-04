/**
 * @author
 * @createtime
 * @class StatisticsArchYearReportPanel
 * @extends Ext.Window
 * @description RollFile表单
 * @company 智维软件
 */
StatisticsArchYearReportPanel = Ext.extend(Ext.Panel, {
	returnStatusName : null,
	timeLimitName : '文件保管期限',
	borrowReasonName : '借阅目的',
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
	this.initUIComponents();
	StatisticsArchYearReportPanel.superclass.constructor.call(this, {
		id : 'StatisticsArchYearReportPanel',
		iconCls : "menu-archYearReport",
		layoutConfig : {
			padding : '5',
			pack : 'center',
			align : 'middle'
		},
		layout : 'form',

		// items : [this.formPanel,this.fileGrid,this.tidyGrid],
		items : [ this.archFieldSet, this.fieldFieldSet, this.borrowFieldSet ],
		title : '档案年度统计',
		// buttonAlign : 'center',
		listeners : {

			'afterlayout' : function(window) {
			}
		}
	});
},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		// ///////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////

	var yearArray = new Array();

	for (i = 1980; i < 2050; i++) {
		yearArray.push( [ i, i ])
	}
	//查询年份
	this.yearComBox = new Ext.form.ComboBox( {
		name : 'arch.year',
		// xtype : 'combo',
		store : new Ext.data.ArrayStore( {
			fields : [ 'i', 'n' ],
			data : yearArray
		}),
		value : new Date().getFullYear(),
		displayField : 'n',
		valueField : 'i',
		typeAhead : true,
		mode : 'local',
		triggerAction : 'all',
		forceSelection : true
	});
	//可变标签
	this.form_YearLabel = new Ext.form.Label( {

		text : this.yearComBox.getValue() + '年度新进卷内文件与归档文件总数:',
		xtype : 'label'

	});
	this.formPanel = new Ext.FormPanel( {

		title : '档案统计年报表',
		layout : 'table',
		width : 800,
		layoutConfig : {
			columns : 3
		},
		// bodyStyle : 'padding:10px 100px',
		style : 'padding:10px 100px',//位置
		border : true,
		bodyBorder : false,
		hideBorders : false,
		region : 'north',
		autoScroll : false,
		defaults : {
			border : true,
			width : 300,
			height : 25,
			layout : 'form',
			// bodyStyle : 'padding:3px 0px',
			anchor : '96%,96%',
			xtype : 'panel'

		},
		items : [
		// 第一行
				{
					colspan : 2,
					width : 300,
					items : {
						text : '往年统计搜索:',
						xtype : 'label'

					}
				}, {
					items : this.yearComBox
				},
				// 第二行
				{
					colspan : 2,
					width : 300,
					items : {
						text : '全宗总数:',
						xtype : 'label'

					}
				}, {
					items : {
						name : 'arch.fondTotal',
						xtype : 'displayfield'

					}
				},
				// 第三行

				{
					colspan : 2,
					width : 300,
					items : {
						text : '馆内档案总数:',
						xtype : 'label'

					}
				}, {
					items : {
						name : 'arch.archTotal',
						xtype : 'displayfield'
					}
				},

				// 第五行
				{
					rowspan : 2,
					height : 50,
					width : 200,
					items : {

						text : '卷内文件与归档文件总数:',
						xtype : 'label'

					}
				}, {
					width : 100,
					items : {

						text : '卷次',
						xtype : 'label'

					}
				}, {

					items : {
						name : 'arch.rollTotal',
						xtype : 'displayfield'
					}
				}, {
					width : 100,
					items : {

						text : '件次',
						xtype : 'label'

					}
				}, {
					items : {
						name : 'arch.fileTotal',
						xtype : 'displayfield'
					}
				},

				// 第六行

				{
					rowspan : 2,
					height : 50,
					width : 200,
					items : this.form_YearLabel
				}, {
					width : 100,
					items : {

						text : '卷次',
						xtype : 'label'

					}
				}, {

					items : {
						name : 'arch.thisYearRollTotal',
						xtype : 'displayfield'
					}
				}, {
					width : 100,
					items : {

						text : '件次',
						xtype : 'label'

					}
				}, {
					items : {
						name : 'arch.thisYearFileTotal',
						xtype : 'displayfield'
					}
				}

		]
	});

	// /
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////

	this.f_fields = [];
	this.f_columns = [];

	this.f_fields.push('afNo');
	this.f_columns.push( {
		header : '全宗号',
		dataIndex : 'afNo'
	});
	//查询动态表头
	Ext.Ajax.request( {
		url : __ctxPath + '/system/loadItemRecordDictionary.do',
		method : 'POST',
		async : false,
		scope : this,
		success : function(response, opts) {
			var obj = Ext.decode(response.responseText);

			for ( var i = 0; i < obj.data.length; i++) {
				this.f_fields.push('' + obj.data[i].dicId + '');
				this.f_columns.push( {
					header : obj.data[i].itemValue,
					dataIndex : '' + obj.data[i].dicId + ''
				});

			}

		},

		failure : function(response, opts) {

		},
		params : {
			itemName : this.timeLimitName
		}
	});

	// alert(Ext.encode(this.f_fields));alert(Ext.encode(this.f_columns));

	// 远程数据源
	this.f_store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : __ctxPath + '/arch/yearReportFileArchReport.do',
			method : 'POST'
		}),

		reader : new Ext.data.JsonReader( {
			root : 'result',
			remoteSort : false,
			fields : this.f_fields
		})

	});

	this.f_store.on('beforeload', function(store) {

		if (this.yearComBox.getValue() != '') {
			store.baseParams = {

				'year' : this.yearComBox.getValue(),
				itemName : this.timeLimitName,
				start : 0,
				limit : 100000
			}
		} else {
			return false;
		}

	}, this);

	this.f_cm = new Ext.grid.ColumnModel( {
		columns : this.f_columns,
		defaults : {
			sortable : false,
			menuDisabled : true
		}
	});

	this.fileGrid = new Ext.grid.GridPanel( {
		width : 800,
		style : 'padding:0px 100px 0px',
		stripeRows : true,
		columnLines : true,
		frame : false,
		border : true,
		title : this.yearComBox.getValue() + '年度卷内文件统计',
		store : this.f_store,
		cm : this.f_cm,
		autoHeight : true,
		// autoWidth:false,
		// height:200,
		viewConfig : {
			forceFit : true,
			autoFill : true
		},
		listeners : {}
	});

	// /
	// //////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////

	this.t_fields = this.f_fields;
	this.t_columns = this.f_columns;

	// this.t_fields.push('afNo');
	// this.t_columns.push( {
	// header : '全宗号',
	// dataIndex : 'afNo'
	// });
	//
	// Ext.Ajax.request( {
	// url : __ctxPath + '/system/loadItemRecordDictionary.do',
	// method : 'POST',
	// async : false,
	// scope : this,
	// success : function(response, opts) {
	// var obj = Ext.decode(response.responseText);
	//
	// for ( var i = 0; i < obj.data.length; i++) {
	// this.t_fields.push('' + obj.data[i].dicId + '');
	// this.t_columns.push( {
	// header : obj.data[i].itemValue,
	// dataIndex : '' + obj.data[i].dicId + ''
	// });
	//
	// }
	//
	// },
	//
	// failure : function(response, opts) {
	//
	// },
	// params : {
	// itemName : this.timeLimitName
	// }
	// });

	// alert(Ext.encode(this.t_fields));alert(Ext.encode(this.t_columns));

	// 远程数据源
	this.t_store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : __ctxPath + '/arch/yearReportTidyArchReport.do',
			method : 'POST'
		}),

		reader : new Ext.data.JsonReader( {
			root : 'result',
			remoteSort : false,
			fields : this.t_fields
		})

	});

	this.t_store.on('beforeload', function(store) {

		if (this.yearComBox.getValue() != '') {
			store.baseParams = {

				'year' : this.yearComBox.getValue(),
				itemName : this.timeLimitName,
				start : 0,
				limit : 100000
			}
		} else {
			return false;
		}

	}, this);

	this.t_cm = new Ext.grid.ColumnModel( {
		columns : this.t_columns,
		defaults : {
			sortable : false,
			menuDisabled : true
		}
	});

	this.tidyGrid = new Ext.grid.GridPanel( {
		width : 800,
		style : 'padding:0px 100px 0px',
		stripeRows : true,
		frame : false,
		border : true,
		title : this.yearComBox.getValue() + '年度归档文件统计',
		store : this.t_store,
		cm : this.t_cm,
		autoHeight : true,
		// autoWidth:false,
		// height:200,
		viewConfig : {
			forceFit : true,
			autoFill : true
		},
		listeners : {}
	});

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////

	this.mainBorrowForm = new Ext.FormPanel( {
		title : '档案利用统计报表',
		layout : 'table',
		width : 800,
		layoutConfig : {
			columns : 3
		},
		// bodyStyle : 'padding:10px 100px',
		style : 'padding:0px 100px 0px',//位置
		border : true,
		bodyBorder : false,
		frame : false,
		hideBorders : true,
		region : 'north',
		autoScroll : false,
		defaults : {
			border : true,
			width : 300,
			height : 25,
			layout : 'form',
			// bodyStyle : 'padding:3px 0px',
			anchor : '96%,96%',
			xtype : 'panel'

		},
		items : [ {
			colspan : 2,
			width : 300,
			items : {
				text : '档案总利用次数:',
				xtype : 'label'

			}
		}, {
			items : {
				name : 'mainBorrowForm.totalCount',
				xtype : 'displayfield'

			}
		} ]
	});

	this.yearBorrowForm_yearLabel = new Ext.form.Label( {
		text : this.yearComBox.getValue() + '年度总利用次数:',
		xtype : 'label'

	});
	this.yearBorrowForm = new Ext.FormPanel( {
		title : this.yearComBox.getValue() + '年度档案利用目的统计',
		layout : 'table',
		width : 800,
		layoutConfig : {
			columns : 3
		},
		// bodyStyle : 'padding:10px 100px',
		style : 'padding:0px 100px 0px',
		border : true,
		bodyBorder : false,
		hideBorders : true,
		region : 'north',
		autoScroll : false,
		defaults : {
			border : true,
			width : 300,
			height : 25,
			layout : 'form',
			// bodyStyle : 'padding:3px 0px',
			anchor : '96%,96%',
			xtype : 'panel'

		},
		items : [ {
			colspan : 2,
			width : 300,
			items : this.yearBorrowForm_yearLabel
		}, {
			items : {
				name : 'yearBorrowForm.totalCount',
				xtype : 'displayfield'

			}
		}, {
			rowspan : 2,
			height : 50,
			width : 200,
			items : {

				text : '利用档案:',
				xtype : 'label'

			}
		}, {
			width : 100,
			items : {

				text : '卷次',
				xtype : 'label'

			}
		}, {

			items : {
				name : 'yearBorrowForm.rollTotal',
				xtype : 'displayfield'
			}
		}, {
			width : 100,
			items : {

				text : '件次',
				xtype : 'label'

			}
		}, {
			items : {
				name : 'yearBorrowForm.fileTotal',
				xtype : 'displayfield'
			}
		} ]
	});

	this.detailBoorowForm_Items = [];
	//查询动态显示行
	Ext.Ajax.request( {
		url : __ctxPath + '/system/loadItemRecordDictionary.do',
		method : 'POST',
		async : false,
		scope : this,
		success : function(response, opts) {
			var obj = Ext.decode(response.responseText);

			for ( var i = 0; i < obj.data.length; i++) {// 一行

			this.detailBoorowForm_Items.push( {
				rowspan : 2,
				height : 50,
				width : 200,
				items : {

					text : obj.data[i].itemValue + ':',
					xtype : 'label'

				}
			});
			this.detailBoorowForm_Items.push( {
				width : 100,
				items : {

					text : '卷次',
					xtype : 'label'

				}
			});
			this.detailBoorowForm_Items.push( {

				items : {
					name : 'detailBorrowForm.rollTotal' + obj.data[i].dicId,
					xtype : 'displayfield'
				}
			});
			this.detailBoorowForm_Items.push( {
				width : 100,
				items : {

					text : '件次',
					xtype : 'label'

				}
			});

			this.detailBoorowForm_Items.push( {
				items : {
					name :  'detailBorrowForm.fileTotal' + obj.data[i].dicId,
					xtype : 'displayfield'
				}
			});

		}

	},

	failure : function(response, opts) {

	},
	params : {
		itemName : this.borrowReasonName
	}
	});




	this.detailBorrowForm = new Ext.FormPanel( {
		title : this.yearComBox.getValue() + '年度档案利用目的详细',
		layout : 'table',
		width : 800,
		layoutConfig : {
			columns : 3
		},
		// bodyStyle : 'padding:10px 100px',
		style : 'padding:0px 100px 0px',
		border : true,
		bodyBorder : false,
		hideBorders : true,
		region : 'north',
		autoScroll : false,
		defaults : {
			border : true,
			width : 300,
			height : 25,
			layout : 'form',
			// bodyStyle : 'padding:3px 0px',
			anchor : '96%,96%',
			xtype : 'panel'

		},
		items : this.detailBoorowForm_Items
	});

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////
	this.load();
	//框图
	this.archFieldSet = new Ext.form.FieldSet( {
		xtype : 'fieldset',
		title : '档案统计',
		anchor : '98%,98%',
		items : this.formPanel
	});

	this.fieldFieldSet = new Ext.form.FieldSet( {
		layout : 'form',
		items : [ this.fileGrid, this.tidyGrid ],
		xtype : 'fieldset',
		title : '期限统计',
		anchor : '98%,98%'
	});
	this.borrowFieldSet = new Ext.form.FieldSet( {
		layout : 'form',
		items : [ this.mainBorrowForm, this.yearBorrowForm,
				this.detailBorrowForm ],
		xtype : 'fieldset',
		title : '借阅统计',
		anchor : '98%,98%'
	});

	this.yearComBox.on('select', function(combo, record, index) {
		this.load();
	}, this);

},// end of the initcomponents
load : function() {
	// 加载表单对应的数据

	this.form_YearLabel
			.setText(this.yearComBox.getValue() + '年度新进卷内文件与归档文件总数:');
	this.yearBorrowForm_yearLabel
			.setText(this.yearComBox.getValue() + '年度总利用次数:');
	this.fileGrid.setTitle(this.yearComBox.getValue() + '年度卷内文件统计');
	this.tidyGrid.setTitle(this.yearComBox.getValue() + '年度归档文件统计');
	this.yearBorrowForm.setTitle(this.yearComBox.getValue() + '年度档案利用目的统计');
	this.detailBorrowForm.setTitle(this.yearComBox.getValue() + '年度档案利用目的详细');

	this.formPanel.loadData( {
		url : __ctxPath + '/arch/yearReportArchArchReport.do?year='
				+ this.yearComBox.getValue(),
		root : 'data',
		preName : 'arch'
	});

	this.mainBorrowForm.loadData( {
		url : __ctxPath + '/arch/yearReportBorrowMainArchReport.do?year='
				+ this.yearComBox.getValue(),
		root : 'data',
		preName : 'mainBorrowForm'
	});

	this.yearBorrowForm.loadData( {
		url : __ctxPath + '/arch/yearReportBorrowYearArchReport.do?year='
				+ this.yearComBox.getValue(),
		root : 'data',
		preName : 'yearBorrowForm'
	});

	this.detailBorrowForm.loadData( {
		
		url : __ctxPath + '/arch/yearReportBorrowDetailArchReport.do?year='
				+ this.yearComBox.getValue(),//+'&itemName='+this.borrowReasonName,
		root : 'data',
		method :'POST',
		params:{itemName:this.borrowReasonName},
		preName : 'detailBorrowForm'
			
	});
	
	this.f_store.load();
	this.t_store.load();
	
}

});