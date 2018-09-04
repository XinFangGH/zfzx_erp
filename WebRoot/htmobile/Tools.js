
/**
 * 扩展 container
 * by gaomimi
 */
Ext.override(Ext.Container, {  
    getCmpByName : function(name) {  
        var getByName = function(container, name) {  
            var items = container.items;  
            if (items != null) {  
                for (var i = 0; i < items.getCount(); i++) {  
                    var comp = items.get(i);
                    var cp = getByName(comp, name);  
                    if (cp != null)  
                        return cp;
                    if (comp.getName && name == comp.getName()) {  
                        return comp;  
                        break;  
                    }
                    if(comp._itemId&&comp._itemId==name){
                    	return comp;
                    }
                }  
            }  
            return null;  
        };  
        return getByName(this, name);  
    },
    
    validate:function(fileds) {
    	
    	var isWrong = false;
    	for (var i = 0; i < fileds.length; i++) {
    		var item = fileds.get(i);
    		if(isInputFiled(item)){
	    		if (item.getRequired()&& isEmpty(item.getValue())) {
	    			isWrong = true;
	    			item.setStyle(wrongStyle);
	    		}
	    		var thisStyle = item.getStyle();
	    		if (!Ext.isEmpty(thisStyle) && thisStyle.indexOf(rightStyle)<0) {
	    			isWrong = true;
	    		}
    		}else{
    			if(item.xtype=='fieldset'){
    				isWrong = !this.validate(item.items);
    			}
    		}
    	}
    	if (isWrong) {
    		Ext.Msg.alert("警告", "数据录入不正确");
    		return false;
    	}

    	return true;
    }
    
});

Ext.override(Ext.MessageBox, {  
	statics: {
        OK    : {text: '确定',     itemId: 'ok',  ui: 'action'},
        YES   : {text: '是',    itemId: 'yes', ui: 'action'},
        NO    : {text: '否',     itemId: 'no'},
        CANCEL: {text: '取消', itemId: 'cancel'},

        INFO    : Ext.baseCSSPrefix + 'msgbox-info',
        WARNING : Ext.baseCSSPrefix + 'msgbox-warning',
        QUESTION: Ext.baseCSSPrefix + 'msgbox-question',
        ERROR   : Ext.baseCSSPrefix + 'msgbox-error',

        OKCANCEL: [
            {text: '取消', itemId: 'cancel'},
            {text: '确定',     itemId: 'ok',  ui : 'action'}
        ],
        YESNOCANCEL: [
            {text: '取消', itemId: 'cancel'},
            {text: '否',     itemId: 'no'},
            {text: '是',    itemId: 'yes', ui: 'action'}
        ],
        YESNO: [
            {text: '否',  itemId: 'no'},
            {text: '是', itemId: 'yes', ui: 'action'}
        ]
	}
});

Ext.define('mobile.FormTextField', {
	extend: 'Ext.field.Text',
	xtype: 'formTextfield',
	constructor: function (config) {
		var validate = config.validate;
		Ext.apply(config,{
			listeners : {
				scope : this,
				'blur' : function(cop, e, eOpt) {
					this.blurValidate(cop, e, eOpt, validate);
				}
			}
		});
		this.callParent([config]);
	},
	
	wrongStyle:"background:#d71345",
	rightStyle:"background:#fff",
	
    blurValidate:function(filed, e, eOpt, matchValue) {
    	var mobileValue = filed.getValue();
    	if (isStringEmpty(mobileValue)) {
    		if (!filed.getRequired()) {
    			filed.setStyle(this.rightStyle);
    		} else {
    			filed.setStyle(this.wrongStyle);
    		}
    	} else {
    		if (!mobileValue.match(matchValue)) {
    			filed.setStyle(this.wrongStyle);
    		} else {
    			filed.setStyle(this.rightStyle);
    		}
    	}
    }
	
});

// 底部工具条
Ext.define('mobile.ButtomToolBar', {
	extend : 'Ext.Toolbar',
	name : "buttomToolBar",
	constructor : function(config) {
		config = config || {};
		Ext.apply(config, {
			docked : 'bottom',
			items : [
			    {
					xtype : 'spacer'
				},{
					xtype : 'spacer'
				},{
					xtype : 'button',
					html : config.leftButtonName,
					handler:config.leftHandler,
					hidden:config.lhidden
				},
				{
					xtype : 'button',
					html : config.rightButtonName,
					handler:config.rightHandler,
					hidden:config.rhidden,
					margin:"0 0 0 3"
				},{
					xtype : 'spacer'
				}, {
					xtype : 'spacer'
				}
			]
		});

		this.callParent([config]);
	}
});

/**
 * 扩展list
 * by cjj
 */
Ext.define('mobile.List', {
    extend: 'Ext.List',
    
    name: 'htList',

    constructor: function (config) {
    	config = config || {};
    	this.username = config.username;
        if(Ext.isEmpty(config.autoLoad)){
          this.autoLoad=true;
        }else{
          this.autoLoad=config.autoLoad;
        }
		Ext.define('taskItem'+config.modeltype, {
			extend: 'Ext.data.Model',
			config: {
				fields: config.fields
			}
		});
		
		this.store = Ext.create('Ext.data.Store', {
			model: 'taskItem'+config.modeltype,
			sorters : config.sorters,
		//	params:config.params,
			grouper : {
						groupFn : function(record) {
							var groupedFiled=config.groupedFiled;
							if(config.grouped){
								if(config.isGroupedAll){
									return record.get(groupedFiled);
								}else{
									return toPinYin(record.get(groupedFiled))[0][0];
								}
								
							}
							return '';
						}
					},
			proxy: {
		        type: "ajax",
		        url : config.url,
	            actionMethods: {
	                create : 'POST',
	                read   : 'POST', 
	                update : 'POST',
	                destroy: 'POST'
	            },
	            extraParams: config.params,
		        reader: {
		            type: "json",
		            rootProperty: config.root,
		           	totalProperty: config.totalProperty
		        }
		    },
			pageSize:limit,
		    autoLoad:this.autoLoad
		});
		if(!Ext.isEmpty(config.isload)){
		 this.store.addListener('load',function(this1, records, successful, operation, eOpts ){
		 	config.loadfunction(this1,records);
		 })
		
		}
/*		//设置定时器 每隔5分钟就重新刷新list数据
		var me=this;
		window.setInterval(function(){
			me.store.loadPage(1);
		},1000*60*5);*/
		this.searchCol = config.searchCol;
		this.selfSearch = config.selfSearch;
		this.searchTip=Ext.isEmpty(config.searchTip)?"搜索。。。":config.searchTip;
		var toolbars = [];
		
		var topToolbar = config.topToolbar;
		if(typeof this.searchCol!='undefined'){
			topToolbar = Ext.create('Ext.Toolbar', {
	            docked: 'top',
		        items: [
		            {xtype:'spacer'},
		            {
		                xtype: 'searchfield',
		                placeHolder: this.searchTip,
		                name:'searchField',
		                style:'',
		                flex:9
		            },
		            {
		                xtype: 'button',
			            iconCls: 'search',
			            iconMask: true,
		                scope:this,
		                flex:.5,
		                handler: typeof selfSearch != 'undefined'? selfSearch:this.onSearchTask
		            },
		            {xtype:'spacer'}
		        ]
			});
			toolbars.push(topToolbar);
		}
		
		var bottomToolbar = config.bottomToolbar;
		if(typeof bottomToolbar!='undefined'){
			toolbars.push(bottomToolbar);
		}
		//取消store分组
		var isGrouper = config.grouped;
		if(!isGrouper){
			this.store.setGrouper(null);
		}
		var plugins =[];
		if(!Ext.isEmpty(config.plugins)){
			plugins =config.plugins;
		}
		
		// 下拉刷新
		var pullRefresh = config.pullRefresh;
		if(pullRefresh){
			plugins.push({
	        	xclass: 'Ext.plugin.PullRefresh',
	        	pullRefreshText: '下拉刷新',
	            releaseRefreshText: '释放刷新',
	            lastUpdatedText: '上次刷新:',
	            loadingText: '请稍候',
	        	list:this.store,
	        	refreshFn:this.refreshStore
	         });
		}
		// 分页
		var listPaging = config.listPaging;
		if(listPaging){
			plugins.push({
				xclass: 'Ext.plugin.ListPaging',
	        	loadMoreText:'<span class="loadMoreText">查看更多</span>',
	        	noMoreRecordsText:'<span class="noMoreRecordsText">没有更多数据</span>'
			});
		}
         if(!Ext.isEmpty(config.items)){
            config.items.forEach(function(e){  
	          toolbars.push(e);
	        }) 
         	
         }
    	Ext.apply(config,{
		    fullscreen: true,
		    store: this.store,
            items: toolbars,
            emptyText: '<div class="noMoreRecordsText">暂无数据</div>',
		    plugins: plugins
		//    items:config.items
    	});
    	
    	if (typeof config.useSelect != 'undefined' && config.useSelect) {
			var isSelectAll=true;
			var hasBottmToolbar=!Ext.isEmpty(config.bottomToolbar);
			Ext.apply(config, {
				editCls : 'simpleList',
				// 是否在多选状态（不可更改）
				isSimple : false,
				// 设置数据主键(可配置)
				ckId : 'id',
				// 加上这个 ，如果不加 会出现 bug
				onItemDisclosure: false,
				// 多选时弹出菜单栏(可配置)
				simpleToolBar : {
					// 默认位置
					docked : 'bottom',
					items : [{
								xtype : 'button',
								text : '全选',
								align : 'left',
								listeners : {
									tap : function(button) {
										var list = button.up('list');
										if (isSelectAll) {
											this.setText('取消全选');
											list.selectAll();
										} else {
											this.setText('全选');
											list.deselectAll();
										}
										isSelectAll = !isSelectAll;
									}
								}
							}, {
								cls : 'moreButton',
								xtype : 'button',
								text : '取消',
								align : 'right',
								listeners : {
									tap : function(button) {
										var list = button.up('list');
										// 移除多选栏
										this.getParent().getParent().hide();
										//显示添加栏
										if (hasBottmToolbar) {
											this.getParent().getParent()
													.getParent().config.bottomToolbar
													.show();
										}
										this.getParent().getParent().destroy();
										// 结束多选
										list.endSimple();
										isSelectAll=true;
									}
								}
							}, {
								cls : 'moreButton',
								xtype : 'button',
								text : config.buttonText,
								align : 'right',
								listeners : {
									tap : function(button) {
										var list = button.up('list');
										var items = list.getSelection();
										// 获取选中项
										var values = [];
										var returnFileds=list.config.returnFileds;
										var hasReturnFileds=!Ext.isEmpty(returnFileds);
										if(hasReturnFileds){
											values="{";
											for(var i=0,field;field=returnFileds[i];i++){
												values+="\""+field+"\":[],";
											}
											values=values.substring(0,values.length-1);
											values+="}";
											values=Ext.util.JSON.decode(values);
										}
										for (var i = 0, item; item = items[i]; i++) {
											if(hasReturnFileds){
												for(var j=0;j<returnFileds.length;j++){
													var temp=values[returnFileds[j]];
													temp.push(item.data[returnFileds[j]]);
												}
											}else{
												values.push(item.data[list.config.ckId]);
											}
										}
										var thisBar=this.getParent().getParent();
										// 移除多选边栏
										if (!Ext.isEmpty(values)) {
											Ext.Msg.show({
												message : '确认操作？',
												width : 300,
												buttons : [{
															text : '确定',
															itemId : '1'
														}, {
															text : '取消',
															itemId : '0'
														}],
												fn : function(itemId) {
													if (itemId != '0') {
														thisBar.hide();
														if (hasBottmToolbar) {
															thisBar.getParent().config.bottomToolbar.show();
														}
														thisBar.getParent().config.doneSuccess(values);
														thisBar.destroy();
														// 结束多选
														list.endSimple();
													}
												}
											});
										} else {
											Ext.Msg.alert("警告", "请选择数据");
										}
										isSelectAll=true;
									}
								}
							}]
				},
				listeners : {
					// 监控是否在多选状态
					itemtap : function(list, index, target, record, e) {
						// 如果在多选状态停止后续事件的执行
						if (this.config.isSimple) {
							e.stopEvent();
						}
					},
					itemsingletap : function(list, index, target, record, e) {
						if (!Ext.isEmpty(this.config.itemsingletap)) {
							this.config.itemsingletap(list, index, target,record);
						}
					},
					// 只要按键长按住就会触发，和按键是否离开没有关系
					itemtaphold : function(list, index, target, record, e) {
						// 开始多选
						this.beginSimple();
					},
					initialize : function(list,opra){
						if(this.config.selectWhenIN){
							this.beginSimple();
						}
					}
				}
			});
		}
		var me=this;
		me.callParent([config]);
//		window.setInterval(function(){
//				me.callParent([config]);
//		},2000);
    },
    // 进入多选状态
	beginSimple : function() {
		if (!this.config.isSimple) {
			// 取消全选
			this.deselectAll();
			// 进入多选模式
			this.setMode('SIMPLE');
			// 添加css
			this.addCls(this.config.editCls);
			// 显示OnItemDisclosure
			this.setOnItemDisclosure(true);
			// 加入标记，以便在itemtap事件中进行判定
			this.config.isSimple = true;
			//隐藏标记栏
			if(!Ext.isEmpty(this.config.bottomToolbar)){
			    this.config.bottomToolbar.hide();
			}
			// 添加多选边栏
			this.add(this.getSimpleToolBar());
		}
	},// 获取多选边栏
	getSimpleToolBar : function() {
		var simpleToolBar = Ext.create('Ext.TitleBar',
				this.config.simpleToolBar);
		return simpleToolBar;
	},
	// 结束多选模式
	endSimple : function() {
		console.log('endSimple');
		// 取消全选
		this.deselectAll();
		// 进入单选模式
		this.setMode('SINGLE');
		// 移除css
		this.removeCls(this.config.editCls);
		// 隐藏OnItemDisclosure
		this.setOnItemDisclosure(false);
		// 加入标记，以便在itemtap事件中进行判定
		this.config.isSimple = false;
	},
	// 更新OnItemDisclosure需要
	updateOnItemDisclosure : function(newConfig, oldConfig) {
		if (oldConfig == null) {
			return;
		}
		var items = this.listItems;
		for (var i = 0, ln = items.length; i < ln; i++) {
			var dItem = items[i].getDisclosure();
			newConfig === false ? dItem.hide() : dItem.show();
		}
	},
    
    onSearchTask:function(){
    	var searchVal = this.down('toolbar').getCmpByName('searchField');
    	this.store.getProxy().setExtraParam(this.searchCol, searchVal.getValue());
		this.store.loadPage(1);
    },
    
    refreshStore:function(config){
    	var store = config.config.list;
    	store.loadPage(1);
    }

});


/**
 * 手机用户信息
 * by cjj
 */

Ext.define('mobile.UserInfo', {
    extend: 'Ext.Toolbar',
    
    constructor: function (config) {
		
    	config = config || {};
    
		Ext.apply(config,{
		    docked: 'bottom',
    	    items: [
    	        { xtype: 'spacer' },
    	        {
    	            xtype: 'label',
    	            html: config.username
    	        },
    	        { xtype: 'spacer' }
    	    ]
		});
		
		this.callParent([config]);
	}
});

Ext.override(Ext.DateExtras, 
	Ext.Date.monthNames =  ["1月", "2月", "3月", "4月","5月", "6月",
							"7月", "8月", "9月", "10月", "11月", "12月"]
	);
	
	
/**
 * @filename Fileup.js
 * 
 * @name File uploading component
 * @fileOverview File uploading component based on Ext.Button
 *
 * @author Constantine V. Smirnov kostysh(at)gmail.com
 * @date 20130614
 * @version 2.0.1
 * @license GNU GPL v3.0
 *
 * @requires Sencha Touch 2.1.1
 * 
 * This component can works in two modes (switched by loadAsDataUrl config):
 * 1) Load local files as dataUrl. 
 * Will be useful if you want to load a local file. For example you can load
 * image and display it inside dom or store it into localStorage.
 * 2) Upload files to server (you should also setup a server part)
 * Current PHP version of server part located in src/php folder (getfile.php)
 * 
 * Server response format (JSON):
 * {
 *     success: true,// or false
 *     message: ''// error message if success === false
 * }
 * 
 * Component has three states:
 * 1) Browse: Initial state, you can browse and select file
 * 2) Ready: File selected and ready for load or upload
 * 3) Uploading: File loading or uploading in process
 * 
 * You can configure these states (add custom text and styles).
 * Default configuration below:
 * 
 

items: [

    //Fileup configuration for "Load local file" mode
    {
        xtype: 'fileupload',
        autoUpload: true,
        loadAsDataUrl: true,
        states: {
            browse: {
                text: 'Browse and load'
            },
            ready: {
                text: 'Load'
            },

            uploading: {
                text: 'Loading',
                loading: true// Enable loading spinner on button
            }
        }
    },
    
    //Fileup configuration for "Upload file" mode
    {
        itemId: 'fileBtn',
        xtype: 'fileupload',
        autoUpload: false,
        url: 'src/php/getfile.php'
    }
]

 
 * 
 */

/**
 * @event success
 * Fired when file uploaded successfully
 * @param {Object} response Response object obtained from server
 * @param {Object} xhr Link to XMLHttpRequest object
 * @param {Object} e Success event
 */

/**
 * @event failure
 * Fired when file not uploaded or server just returns error message
 * @param {String} message Parsed error message obtained from server
 * @param {Object} response Response object obtained from server
 * @param {Object} xhr Link to XMLHttpRequest object
 * @param {Object} e Uploading error event
 */

/**
 * @event loadsuccess
 * Fired when file uploaded successfully
 * @param {Object} dataUrl DataUrl source of readed file
 * @param {Object} reader Link to FileReader object
 * @param {Object} e Load event
 */

/**
 * @event loadfailure
 * Fired when file not uploaded or server just returns error message
 * @param {String} message Parsed error message obtained from server
 * @param {Object} reader Link to FileReader object
 * @param {Object} e Loading error event
 */

Ext.define('Ext.ux.Fileup', {
    extend: 'Ext.Button',
    xtype: 'fileupload',
    
    requires: [
        'Ext.MessageBox',
//        'Ext.device.Notification',
        'Ext.Array'
    ],
    
    template: [
        
        // Default button elements (do not change!)
        {
            tag: 'span',
            reference: 'badgeElement',
            hidden: true
        },
        {
            tag: 'span',
            className: Ext.baseCSSPrefix + 'button-icon',
            reference: 'iconElement',
            hidden: true
        },
        {
            tag: 'span',
            reference: 'textElement',
            hidden: true
        },
        
        // Loading spinner
        {
            tag: 'div',
            className: Ext.baseCSSPrefix + 'loading-spinner',
            reference: 'loadingElement',
            hidden: true,
            
            children: [
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-top'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-right'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-bottom'
                },
                {
                    tag: 'span',
                    className: Ext.baseCSSPrefix + 'loading-left'
                }
            ]
        },
                
        // Hidden file element
        {
            tag: 'form',
            reference: 'formElement',
            hidden: false,            
            
            children: [
                {
                    tag: 'input',
                    reference: 'fileElement',
                    type: 'file',
                    name: 'userfile',
					//accept: "image/*",	//临时做的限制图片上传的
                    tabindex: -1,
                    hidden: false,
                    style: 'opacity:0;position:absolute;top:-3px;right:-3px;bottom:-3px;left:-3px;z-index:16777270;'
                }
            ]
        }
    ],
    
    // Default button states config
    defaultStates: {
         browse: {
            text: 'Browse',
            cls: Ext.baseCSSPrefix + 'fileup',
            ui: 'filebrowse'
        },

        ready: {
            text: 'Upload',
            cls: Ext.baseCSSPrefix + 'fileup-ready',
            ui: 'fileready'
        },

        uploading: {
            text: 'Uploading',
            cls: Ext.baseCSSPrefix + 'fileup-uploading',
            ui: 'fileupload',
            loading: true
        },
		
		success: {
            text: 'success',
            cls: Ext.baseCSSPrefix + 'fileup-ready',
            ui: 'fileready'
        }

    },
    
    // Current button state
    currentState: null,
    
    config: {
        cls: Ext.baseCSSPrefix + 'fileup',
        
        /**
         * @cfg {String} name Input element name, check on server for $_FILES['userfile']
         */        
        name: 'userfile',
        
        /**
         * @cfg {Boolean} autoUpload 
         * If true then "uploading" state will start after "ready" event automatically
         */
        autoUpload: false,
        
        /**
         * @cfg {Object} states 
         */
        states: true,
        
        /**
         * @cfg {Boolean} loadAsDataUrl
         */
        loadAsDataUrl: false,
        
        /**
         * @cfg {String} url URL to uploading handler script on server
         */
        url: '',
               
        /**
         * @cfg {Boolean} signRequestEnabled Enable or disable request signing feature
         */
        signRequestEnabled: false,
        
        /**
         * @cfg {Boolean} can only upload one time
         */
        single: false,
        
        /**
         * @cfg {String} signHeader Signing token header name
         */
        signHeader: '',
        
        /**
         * @cfg {Array} defaultSuccessCodes Http response success codes
         */
        defaultSuccessCodes: [200, 201]
    },
    
    // @private
    applyStates: function(states) {
        var me = this;
        
        if (states) {
            
            if (Ext.isObject(states)) {
                
                // Merge custom config with default
                return Ext.merge({}, me.defaultStates, states);
            } else {
                return me.defaultStates;
            }
        } else {
            return me.defaultStates;
        }
    },
    
    // @private
    initialize: function() {
        var me = this;
        me.callParent();
        
        me.fileElement.dom.onchange = function() {
            me.onChanged.apply(me, arguments);
        };
        
        me.on({
            scope: me,
            buffer: 250,// Avoid multiple tap 
            tap: me.onButtonTap
        });
        
        // Stup initial button state
        me.changeState('browse');
    },
    
    // @private
    onButtonTap: function() {
        var me = this;
        
        switch (me.currentState) {
            
            // Currently we handle tap event while button in ready state
            // because in all other states button is not accessible
            case 'ready':                
                me.changeState('uploading');
                var file = me.fileElement.dom.files[0];
                                
                if (!me.getLoadAsDataUrl()) {
                    me.fireEvent('uploadstart', file);
                    me.doUpload(file);                
                } else {
                    me.doLoad(file);
                }
                break;
        }
    },
    
    // @private
    onChanged: function(e) {
        var me = this;
        
        if (e.target.files.length > 0) {
            me.fireAction('ready', [e.target.files[0]], function() {
                me.changeState('ready');
                me.onButtonTap();
            }, me);
        } else {
//            Ext.device.Notification.show({
//                title: 'Error',
//                message: 'File selected but not accessible',
//                buttons: Ext.MessageBox.OK,
//                callback: function() {
//                    me.changeState('browse');
//                }
//            });
        }
    },
    
    // @private
    changeState: function(state) {
        var me = this;
        var states = me.getStates();
        
        if (Ext.isDefined(states[state])) {
            
            // Common tasks for all states
            if (states[state].text) {
                me.setText(states[state].text);
            } else {
                me.setText('');
            }
            
            if (states[state].cls) {
                me.setCls(states[state].cls);
            } else {
                me.setCls('');
            }
            
            if (states[state].ui) {
                me.setUi(states[state].ui);
            } else {
                me.setUi('normal');
            }
            
            if (states[state].loading) {
            	mobileView.setMasked({xtype: 'loadmask',message: defaultsValues.loadMaskText});
            } else {
            	mobileView.setMasked(false);
            }
            
            // State specific tasks
            switch (state) {
                case 'browse':
                    me.currentState = 'browse';
                    me.reset();                    
                    break;
                    
                case 'ready':
                    me.currentState = 'ready';
                    me.fileElement.hide();
                    
                    if (me.getAutoUpload()) {
                        me.onButtonTap();
                    }                    
                    break;
                    
                case 'uploading':
                    me.currentState = 'uploading';
                    break;
            }
        } else {
            // <debug>
            Ext.Logger.warn('Config for FileUp state "'+ state +'" not found!');
            // </debug>
        }
    },
    
    /**
     * @private
     * @method doLoad
     * Read selected file as dataUrl value.
     * If you wish to get dataUrl content 
     * then you should listen for "loadsuccess" event
     * @param {Object} file Link to loaded file element
     */
    doLoad: function(file) {
        var me = this;                
        var reader = new FileReader();

        reader.onerror = function(e) {
            var message;
            switch (e.target.error.code) {
                case e.target.error.NOT_FOUND_ERR:
                    message = 'File Not Found';
                    break;

                case e.target.error.NOT_READABLE_ERR:
                    message = 'File is not readable';
                    break;

                case e.target.error.ABORT_ERR:
                    break;

                default:
                    message = 'Can not read file';
            };
            me.fireEvent('loadfailure', message, me, e);
        };

        reader.onload = function(e) {
            me.fireEvent('loadsuccess', this.result, me, e);
            me.changeState('browse');
        };

        // Read image file
        reader.readAsDataURL(file);
    },
    
    /**
     * @private
     * @method doUpload
     * Upload selected file using XMLHttpRequest.
     * @param {Object} file Link to loaded file element
     */
    doUpload: function(file) {
        var me = this;        
        var http = new XMLHttpRequest();
        
        if (http.upload && http.upload.addEventListener) {
            
            // Uploading progress handler
            http.upload.onprogress = function(e) {
                if (e.lengthComputable) {
                    var percentComplete = (e.loaded / e.total) * 100; 
                    me.setBadgeText(percentComplete.toFixed(0) + '%');
                }
            };
            
            // Response handler
            http.onreadystatechange = function (e) {
                if (this.readyState == 4) {
				
                    if(Ext.Array.indexOf(me.getDefaultSuccessCodes(), parseInt(this.status)) != -1) {
                        
                        var response = me.decodeResponse(this);
                        
                        if (response && response.success) {
                            // Success
                            me.fireEvent('success', response, me, e);
                            var mailformpanle = me.up('formpanel');
							var htmls = '';
							var setHtmls = function(filenames, fileIds) {
								if (filenames != null) {
									files = filenames.split(",");
									fileids = fileIds.split(",");
									for (var i = 0; i < files.length - 1; i++) {
										htmls = htmls
												+ "<ul id='"
												+ fileids[i]
												+ "'><div style='float:left;font-weight:bold;font-size:16px;'><a title='单击下载该文件' tyle='color:green;' href='"
												+ __ctxPath
												+ "/file-download?fileId="
												+ fileids[i]
												+ "'>"
												+ files[i]
												+ "</a>"
												+ "<a style='color:#d93a49;font-size:12px;' href="
												+ "'javascript:delMailAttach("
												+ fileids[i] + ")'>" + " 删除</a></ul></br>";
									}

								}
							};
							var filename = mailformpanle
									.getCmpByName('filename');
							var filenames = mailformpanle
									.getCmpByName('filenames').getValue();// 获取FormPanel的邮件控件的值
							filenames = filenames + response.fileName + ",";
							mailformpanle.getCmpByName('filenames')
									.setHtml(filenames);
							var fileIds = mailformpanle.getCmpByName('fileIds')
									.getValue();// 获取FormPanel的附件ID控件的值
							fileIds = fileIds + response.fileId + ",";
							mailformpanle.getCmpByName('fileIds')
									.setValue(fileIds);
							setHtmls(filenames, fileIds);
							filename.setHtml(htmls);
							// Single upload
							if (me.getSingle()) {
								me.changeState('success');
								return ;
							}
                        } else if (response && response.message) {
                            // Failure
                            me.fireEvent('failure', response.message, response, me, e);
                        } else {
                            // Failure
                            me.fireEvent('failure', 'Unknown error', response, me, e);
                        }
                        
                    } else {
                        
                        // Failure
                        me.fireEvent('failure', this.status + ' ' + this.statusText, response, me, e);
                    }
                    
                    me.changeState('browse');
                }
            };
            
            // Error handler
            http.upload.onerror = function(e) {
                me.fireEvent('failure', this.status + ' ' + this.statusText, {}, me, e);
            };
        }
        
        // Send form with file using XMLHttpRequest POST request
        http.open('POST', me.getUrl());
        
        if (me.getSignRequestEnabled()) {
            
            // Sign the request and then send.
            me.signRequest(http, function(http) {
    
              // Send the form.
              http.send(me.getForm(file));
            });
        } else {
            http.send(me.getForm(file));
        }
        
    },
    
    /**
     * @method getForm
     * Returns the form to send to the browser.
     *
     * @param {Object} file Link to loaded file element
     */
    getForm: function(file) {
      // Create FormData object
      var form = new FormData();

      // Add selected file to form
      form.append(this.getName(), file);

      // Return the form.
      return form;
    },

    /**
     * @method reset
     * Component reset
     */
    reset: function() {
        var me = this;
        
        me.setBadgeText(null);
        me.formElement.dom.reset();
        me.fileElement.show();
    },
    
    /**
     * @private
     * @method decodeResponse
     * Decodes a server response.
     *
     * @param {Object} response The response from the server to decode
     * @return {Object} The response to provide to the library
     */
    decodeResponse: function(response) {
        return Ext.decode(response.responseText, true);
    },
    
    /**
     * @private
     * @method signRequest
     * Sign the request before sending it.
     *
     * @param {Object} request The XHR request object.
     * @param {Function} callback Called when the request has been signed.
     */
    signRequest: function(http, callback) {
        var me = this;
        var header = me.getSignHeader(); 
        
        if (!header) {
            me.fireEvent('failure', 'Request signing header is not defined');
        }
        
        me.signProvider( 
            function(token) {
                http.setRequestHeader(header, token);
                callback(http);
            },
            function(failureText) {
                me.fireEvent('failure', 'Request signing is failed! ' + 
                                        failureText, {}, this);
            });
    },
    
    /**
     * @private
     * @method signProvider
     * Default token provider (should be redefined)
     *
     * @param {Function} success Success callback
     * @param {Function} callback Signing failure callback
     */
    signProvider: function(success, failure) {
        success('default-token');// Default behaviour
    }
});

// 提交添加mask
Ext.define('Ext.Ajax', {
	extend : 'Ext.data.Connection',
	singleton : true,

	/**
	 * @property {Boolean} autoAbort Whether a new request should abort
	 *           any pending requests.
	 */
	autoAbort : false,
	request : function(options) {
		options = options || {};
		options.timeout = defaultsValues.timeout;
		if(options.url.indexOf("autoload=true")==-1){
			mobileView.setMasked({xtype: 'loadmask',message: defaultsValues.loadMaskText});
		}
		options.failure = function() {
			Ext.Msg.alert("错误", "链接失败");
			mobileView.setMasked(false);
		};
		if(options.success){
			var successFunction=options.success;
			options.success=function(response){
				successFunction(response);
				mobileView.setMasked(false);
			};
		}else if(options.exception){
			var exceptionFunction=options.exception;
			options.exception=function(response){
				exceptionFunction(response);
				mobileView.setMasked(false);
			};
		}else{
			options.success=function(response){
				mobileView.setMasked(false);
			};
		}
			
		var me = this, scope = options.scope || window, username = options.username
				|| me.getUsername(), password = options.password
				|| me.getPassword() || '', async, requestOptions, request, headers, xhr;

		if (me.fireEvent('beforerequest', me, options) !== false) {
			requestOptions = me.setOptions(options, scope);

			if (this.isFormUpload(options) === true) {
				this.upload(options.form, requestOptions.url,
						requestOptions.data, options);
				return null;
			}

			// if autoabort is set, cancel the current transactions
			if (options.autoAbort === true || me.getAutoAbort()) {
				me.abort();
			}

			// create a connection object
			xhr = this.getXhrInstance();

			async = options.async !== false
					? (options.async || me.getAsync())
					: false;

			// open the request
			if (username) {
				xhr.open(requestOptions.method, requestOptions.url, async,
						username, password);
			} else {
				xhr.open(requestOptions.method, requestOptions.url, async);
			}

			headers = me.setupHeaders(xhr, options, requestOptions.data,
					requestOptions.params);

			// create the transaction object
			request = {
				id : ++Ext.data.Connection.requestId,
				xhr : xhr,
				headers : headers,
				options : options,
				async : async,
				timeout : setTimeout(function() {
							request.timedout = true;
							me.abort(request);
						}, options.timeout || me.getTimeout())
			};
			me.requests[request.id] = request;

			// bind our statechange listener
			if (async) {
				xhr.onreadystatechange = Ext.Function.bind(me.onStateChange,
						me, [request]);
			}

			// start the request!
			xhr.send(requestOptions.data);
			if (!async) {
				return this.onComplete(request);
			}
			return request;
		} else {
			Ext.callback(options.callback, options.scope, [options, undefined,
							undefined]);
			return null;
		}
	}
});

// 下拉汉化
Ext.override(Ext.picker.Picker, {
	applyCancelButton: function(config) {
        if (config) {
            if (Ext.isBoolean(config)) {
                config = {};
            }

            if (typeof config == "string") {
                config = {
                    text: config
                };
            }

            Ext.applyIf(config, {
                align: 'left',
                text: '取消'
            });
        }

        return Ext.factory(config, 'Ext.Button', this.getCancelButton());
    },
    applyDoneButton: function(config) {
        if (config) {
            if (Ext.isBoolean(config)) {
                config = {};
            }

            if (typeof config == "string") {
                config = {
                    text: config
                };
            }

            Ext.applyIf(config, {
                ui: 'action',
                align: 'right',
                text: '确定'
            });
        }

        return Ext.factory(config, 'Ext.Button', this.getDoneButton());
    }
});
Ext.define('tableHeader', {
    extend: 'Ext.Panel',
    name: 'tableHeader',
      constructor: function (config) {
      	/*var headerStr=config.headerStr;
      	var headerArray=new Array(headerStr);*/
      		var headerArray=config.header;
      	var html="";
      	for(var idx0=0;idx0<headerArray.length;idx0++){
      	  html=html+headerArray[idx0]+"&nbsp;&nbsp;"
      	}
        Ext.apply(config,
        {docked:'top',
        laout:'hbox',
        height:50,
        style:"margin-left:13px;",
        items:[
        	{html:html
           }]
        })
        
        this.callParent([config]);
        
      }
    });
 Ext.define('DicIndep', {
    extend: 'Ext.data.Model',
    config: {
        fields: [{
									name : 'text',
									type : 'string'
								}, {
									name : 'value',
									type : 'string'
								}]
    }
});  
//{"success":true,"totalCounts":2,"result":[{"text":"30/360","value":"dateModel_360"},{"text":"30/365","value":"dateModel_365"}]}

  Ext.define('DicIndepCombo', { 
  	extend: 'Ext.field.Select',
  	xtype: 'dicIndepCombo',
    name: 'DicIndepCombo',
    constructor : function(config) {
    	  Ext.apply(this, config);
    	 var nodeKey = this.nodeKey;
        var isDisplayItemName = this.isDisplayItemName;
    	var store=Ext.create('Ext.data.Store', {
          	 	model: "DicIndep",
                autoLoad : true,
                proxy: {
		        type: "ajax",
		        url : __ctxPath + "/htmobile/loadIndepItemsVmInfo.do?nodeKey="+nodeKey,
		        reader : {
							type : 'json',
							rootProperty : 'result'
						}
                }
                });
          Ext.apply(config,{
          	 store : store,
          	 displayField: 'text',
           	 valueField: 'value'
          	 
        })
        
        this.callParent([config]);
    }
});

  Ext.define('Dickeycombo', { 
  	extend: 'Ext.field.Select',
  	xtype: 'dickeycombo',
    name: 'dickeycombo',
    constructor : function(config) {
    	  Ext.apply(this, config);
    	 var nodeKey = this.nodeKey;
        var isDisplayItemName = this.isDisplayItemName;
    	var store=Ext.create('Ext.data.Store', {
          	 	model: "DicIndep",
                autoLoad : true,
                proxy: {
		        type: "ajax",
		          url : __ctxPath + "/htmobile/loadItemByNodeKeyVmInfo.do?nodeKey="+nodeKey,
		        reader : {
							type : 'json',
							rootProperty : 'result'
						}
                }
                });
          Ext.apply(config,{
          	 store : store,
          	 displayField: 'text',
           	 valueField: 'value'
          	 
        })
        
        this.callParent([config]);
    }
});
 Ext.define('Diccombo', { 
  	extend: 'Ext.field.Select',
  	xtype: 'diccombo',
    name: 'diccombo',
    constructor : function(config) {
    	  Ext.apply(this, config);
    	var itemName = this.itemName;
        var isDisplayItemName = this.isDisplayItemName;
    	var store=Ext.create('Ext.data.Store', {
          	 	model: "DicIndep",
                autoLoad : true,
                proxy: {
		        type: "ajax",
		        url : __ctxPath + "/htmobile/loadItemVmInfo.do?itemName="+encodeURI(encodeURI(itemName)),
		          
		        reader : {
							type : 'json',
							rootProperty : 'result'
						}
                }
                });
          Ext.apply(config,{
          	 store : store,
          	 displayField: 'text',
           	 valueField: 'value'
          	 
        })
        
        this.callParent([config]);
    }
});
  Ext.define('StoreSelect', { 
  	extend: 'Ext.data.Store',
  	xtype: 'storeSelect',
    name: 'storeSelect',
    constructor : function(config) {
    	  Ext.apply(this, config);
          Ext.apply(config,{
          	 	model: "DicIndep",
                autoLoad : true,
                proxy: {
		        type: "ajax",
		          url : config.url,
		        reader : {
							type : 'json',
							rootProperty : 'result'
						}
                }
          	 
        })
        
        this.callParent([config]);
    }
});


 Ext.define('Tellphone', { 
  	extend: 'Ext.Panel',
  	xtype: 'tellphone',
    name: 'tellphone',
    constructor : function(config) {
    	  var cellphone=config.value;
    	   var label=config.label;
    	  Ext.apply(this, config);
          Ext.apply(config,{
          html:'<div class="fieldlabel">'+label+'</div><br/><a href="tel:'+cellphone+'" class="fieldtext">'+cellphone+'</a>'																																																																																																																												
		                   
          })
        
        this.callParent([config]);
    }
});
//因为原来的文本不能自动高度
 Ext.define('Textarea', { 
  	extend: 'Ext.Panel',
  	xtype: 'textarea',
    name: 'textarea',
    constructor : function(config) {
    	
    	  var value=config.value;
    	   var label=config.label;
    	  Ext.apply(this, config);
          Ext.apply(config,{
          html:'<div class="fieldlabel">'+label+'</div><br/><div class="fieldtext">'+value+'</div>'																																																																																																																												
		                   
          })
        
        this.callParent([config]);
    }
});
 /*Ext.define('Moneyfield', { 
  	extend: 'Ext.field.Text',
  	xtype: 'moneyfield',
    name: 'moneyfield',
    constructor : function(config) {
    	  var value=config.value;
    	  numberFormat=function(num) {
		    				var num = new Number(num);
						  return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
						}  
    	  Ext.apply(this, config);
          Ext.apply(config,{
		    value:numberFormat(value)               
          })
        
        this.callParent([config]);
    }
});*/
 Ext.define('Textfieldlabelleft', { 
  	extend: 'Ext.field.Text',
  	xtype: 'fieldlabelleft',
    name: 'fieldlabelleft',
    constructor : function(config) {
    	  Ext.apply(this, config);
          Ext.apply(config,{
		    label:"<div class='fieldlabelleft'>"+this.label+"</div>"              
          })
        
        this.callParent([config]);
    }
});
Ext.define('ux.field.Select', {
    extend: 'Ext.field.Text',
    xtype: 'uxSelectfield',
    alternateClassName: 'ux.form.Select',
    requires: [
        'Ext.Panel',
        'Ext.picker.Picker',
        'Ext.data.Store',
        'Ext.data.StoreManager',
        'Ext.dataview.List'
    ],

    /**
     * @event change
     * Fires when an option selection has changed
     * @param {Ext.field.Select} this
     * @param {Mixed} newValue The new value
     * @param {Mixed} oldValue The old value
     */

    /**
     * @event focus
     * Fires when this field receives input focus. This happens both when you tap on the field and when you focus on the field by using
     * 'next' or 'tab' on a keyboard.
     *
     * Please note that this event is not very reliable on Android. For example, if your Select field is second in your form panel,
     * you cannot use the Next button to get to this select field. This functionality works as expected on iOS.
     * @param {Ext.field.Select} this This field
     * @param {Ext.event.Event} e
     */

    config: {
        /**
         * @cfg
         * @inheritdoc
         */
        ui: 'select',

        /**
         * @cfg {Boolean} useClearIcon
         * @hide
         */

        /**
         * @cfg {String/Number} valueField The underlying {@link Ext.data.Field#name data value name} (or numeric Array index) to bind to this
         * Select control.
         * @accessor
         */
        valueField: 'value',

        /**
         * @cfg {String/Number} displayField The underlying {@link Ext.data.Field#name data value name} (or numeric Array index) to bind to this
         * Select control. This resolved value is the visibly rendered value of the available selection options.
         * @accessor
         */
        displayField: 'text',

        /**
         * @cfg {Ext.data.Store/Object/String} store The store to provide selection options data.
         * Either a Store instance, configuration object or store ID.
         * @accessor
         */
        store: null,

        /**
         * @cfg {Array} options An array of select options.
         *
         *     [
         *         {text: 'First Option',  value: 'first'},
         *         {text: 'Second Option', value: 'second'},
         *         {text: 'Third Option',  value: 'third'}
         *     ]
         *
         * __Note:__ Option object member names should correspond with defined {@link #valueField valueField} and {@link #displayField displayField} values.
         * This config will be ignored if a {@link #store store} instance is provided.
         * @accessor
         */
        options: null,

        /**
         * @cfg {String} hiddenName Specify a `hiddenName` if you're using the {@link Ext.form.Panel#standardSubmit standardSubmit} option.
         * This name will be used to post the underlying value of the select to the server.
         * @accessor
         */
        hiddenName: null,

        /**
         * @cfg {Object} component
         * @accessor
         * @hide
         */
        component: {
            useMask: true
        },

        /**
         * @cfg {Boolean} clearIcon
         * @hide
         * @accessor
         */
        clearIcon: false,

        /**
         * 请勿改动此配置
         */
        usePicker: false,

        /**
         * @cfg {Boolean} autoSelect
         * `true` to auto select the first value in the {@link #store} or {@link #options} when they are changed. Only happens when
         * the {@link #value} is set to `null`.
         */
        autoSelect: true,

        /**
         * @cfg {Object} defaultPhonePickerConfig
         * The default configuration for the picker component when you are on a phone.
         */
        defaultPhonePickerConfig: null,

        /**
         * @cfg {Object} defaultTabletPickerConfig
         * The default configuration for the picker component when you are on a tablet.
         */
        defaultTabletPickerConfig: null,

        /**
         * @cfg
         * @inheritdoc
         */
        name: 'picker',

        /**
         * @cfg {String} pickerSlotAlign
         * The alignment of text in the picker created by this Select
         * @private
         */
        pickerSlotAlign: 'center'
    },

    platformConfig: [
        {
            theme: ['Windows'],
            pickerSlotAlign: 'left'
        },
        {
            theme: ['Tizen'],
            usePicker: false
        }
    ],

    // @private
    initialize: function () {
        var me = this,
            component = me.getComponent();

        me.callParent();

        component.on({
            scope: me,
            masktap: 'onMaskTap'
        });

        component.doMaskTap = Ext.emptyFn;

        if (Ext.browser.is.AndroidStock2) {
            component.input.dom.disabled = true;
        }

        if (Ext.theme.is.Blackberry) {
            this.label.on({
                scope: me,
                tap: "onFocus"
            });
        }
    },

    getElementConfig: function () {
        if (Ext.theme.is.Blackberry) {
            var prefix = Ext.baseCSSPrefix;

            return {
                reference: 'element',
                className: 'x-container',
                children: [
                    {
                        reference: 'innerElement',
                        cls: prefix + 'component-outer',
                        children: [
                            {
                                reference: 'label',
                                cls: prefix + 'form-label',
                                children: [{
                                    reference: 'labelspan',
                                    tag: 'span'
                                }]
                            }
                        ]
                    }
                ]
            };
        } else {
            return this.callParent(arguments);
        }
    },

    /**
     * @private
     */
    updateDefaultPhonePickerConfig: function (newConfig) {
        var picker = this.picker;
        if (picker) {
            picker.setConfig(newConfig);
        }
    },

    /**
     * @private
     */
    updateDefaultTabletPickerConfig: function (newConfig) {
        var listPanel = this.listPanel;
        if (listPanel) {
            listPanel.setConfig(newConfig);
        }
    },

    /**
     * @private
     * Checks if the value is `auto`. If it is, it only uses the picker if the current device type
     * is a phone.
     */
    applyUsePicker: function (usePicker) {
        if (usePicker == "auto") {
            usePicker = (Ext.os.deviceType == 'Phone');
        }

        return Boolean(usePicker);
    },

    syncEmptyCls: Ext.emptyFn,

    /**
     * @private
     */
    applyValue: function (value) {
        var record = value,
            index, store;

        //we call this so that the options configruation gets intiailized, so that a store exists, and we can
        //find the correct value
        this.getOptions();

        store = this.getStore();

        if ((value != undefined && !value.isModel) && store) {
            index = store.find(this.getValueField(), value, null, null, null, true);

            if (index == -1) {
                index = store.find(this.getDisplayField(), value, null, null, null, true);
            }

            record = store.getAt(index);
        }

        return record;
    },

    updateValue: function (newValue, oldValue) {
        this.record = newValue;
        this.callParent([(newValue && newValue.isModel) ? newValue.get(this.getDisplayField()) : '']);
    },

    getValue: function () {
        var record = this.record;
        return (record && record.isModel) ? record.get(this.getValueField()) : null;
    },

    /**
     * Returns the current selected {@link Ext.data.Model record} instance selected in this field.
     * @return {Ext.data.Model} the record.
     */
    getRecord: function () {
        return this.record;
    },

    // @private
    getPhonePicker: function () {
        var config = this.getDefaultPhonePickerConfig();

        if (!this.picker) {
            this.picker = Ext.create('Ext.picker.Picker', Ext.apply({
                slots: [
                    {
                        align: this.getPickerSlotAlign(),
                        name: this.getName(),
                        valueField: this.getValueField(),
                        displayField: this.getDisplayField(),
                        value: this.getValue(),
                        store: this.getStore()
                    }
                ],
                listeners: {
                    change: this.onPickerChange,
                    scope: this
                }
            }, config));
        }

        return this.picker;
    },

    // @private
    getTabletPicker: function () {
        var config = this.getDefaultTabletPickerConfig();

        if (!this.listPanel) {
            this.listPanel = Ext.create('Ext.Panel', Ext.apply({
                left: 0,
                top: 0,
                modal: true,
                cls: Ext.baseCSSPrefix + 'select-overlay',
                layout: 'fit',
                hideOnMaskTap: true,
                width: Ext.os.is.Phone ? '14em' : '18em',
                height: (Ext.os.is.BlackBerry && Ext.os.version.getMajor() === 10) ? '12em' : (Ext.os.is.Phone ? '12.5em' : '22em'),
                items: [{
                    xtype: 'toolbar',
                    docked: 'top',
                    items: [
                        //新增的搜索栏，用于支持模糊查询
                        {
                            xtype: 'searchfield',
                            placeHolder: '请输入关键词',
                            width:'100%',
                            clearIcon:false,
                            listeners: {
                                keyup: 'onSearch',
                                scope: this
                            }
                        }
                    ]
                }, {
                    xtype: 'list',
                    store: this.getStore(),
                    itemTpl: '<span class="x-list-label">{' + this.getDisplayField() + ':htmlEncode}</span>',
                    listeners: {
                        select: this.onListSelect,
                        itemtap: this.onListTap,
                        scope: this
                    }
                }]
            }, config));
        }

        return this.listPanel;
    },
    //进行模糊查询
    onSearchKeyUp: function (value) {
        //得到数据仓库和搜索关键词
        var store = this.getStore();

        //如果是新的关键词，则清除过滤
        store.clearFilter(!!value);
        //检查值是否存在
        if (value) {
            //the user could have entered spaces, so we must split them so we can loop through them all
            var key = this.getDisplayField(),
             searches = value.split(','),
                regexps = [],
                //获取现实值的name
                i, regex;

            //loop them all
            for (i = 0; i < searches.length; i++) {
                //if it is nothing, continue
                if (!searches[i]) continue;

                regex = searches[i].trim();
                regex = regex.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");

                //if found, create a new regular expression which is case insenstive
                regexps.push(new RegExp(regex.trim(), 'i'));
            }

            //now filter the store by passing a method
            //the passed method will be called for each record in the store
            store.filter(function (record) {
                var matched = [];

                //loop through each of the regular expressions
                for (i = 0; i < regexps.length; i++) {
                    var search = regexps[i],
                        didMatch = search.test(record.get(key));

                    //if it matched the first or last name, push it into the matches array
                    matched.push(didMatch);
                }

                return (regexps.length && matched.indexOf(true) !== -1);
            });
        }
    },
    //进行模糊查询
    onSearch: function (field) {
        this.onSearchKeyUp(field.getValue());
    },
    // @private
    onMaskTap: function () {
        this.onFocus();

        return false;
    },

    /**
     * Shows the picker for the select field, whether that is a {@link Ext.picker.Picker} or a simple
     * {@link Ext.List list}.
     */
    showPicker: function () {
        var me = this,
            store = me.getStore(),
            value = me.getValue();

        //check if the store is empty, if it is, return
        if (!store || store.getCount() === 0) {
            return;
        }
        if (me.getReadOnly()) {
            return;
        }
        me.isFocused = true;

        if (me.getUsePicker()) {
            var picker = me.getPhonePicker(),
                name = me.getName(),
                pickerValue = {};

            pickerValue[name] = value;
            picker.setValue(pickerValue);

            if (!picker.getParent()) {
                Ext.Viewport.add(picker);
            }

            picker.show();
        } else {
            //先过滤一下避免加载过慢
            var record = this.getRecord(),
            //gaomimi     text='请搜索';
            text='';
            if (record) {
                 text = record.get(this.getDisplayField());
            }
            this.onSearchKeyUp(text);

            var listPanel = me.getTabletPicker(),
                list = listPanel.down('list'),
                index, record;

            if (!listPanel.getParent()) {
                Ext.Viewport.add(listPanel);
            }
            //为搜索栏赋值
            listPanel.down('searchfield').setValue(text);
            listPanel.showBy(me.getComponent(), null);
            if (value || me.getAutoSelect()) {
                store = list.getStore();
                index = store.find(me.getValueField(), value, null, null, null, true);
                record = store.getAt(index);

                if (record) {
                    list.select(record, null, true);
                }
            }
        }
    },

    // @private
    onListSelect: function (item, record) {
        var me = this;
        if (record) {
            me.setValue(record);
        }
        if(this.config.callback){
          
           this.config.callback(record)
        }
    },

    onListTap: function () {
        this.listPanel.hide({
            type: 'fade',
            out: true,
            scope: this
        });
    },

    // @private
    onPickerChange: function (picker, value) {
        var me = this,
            newValue = value[me.getName()],
            store = me.getStore(),
            index = store.find(me.getValueField(), newValue, null, null, null, true),
            record = store.getAt(index);

        me.setValue(record);
    },

    onChange: function (component, newValue, oldValue) {
        var me = this,
            store = me.getStore(),
            index = (store) ? store.find(me.getDisplayField(), oldValue, null, null, null, true) : -1,
            valueField = me.getValueField(),
            record = (store) ? store.getAt(index) : null;

        oldValue = (record) ? record.get(valueField) : null;

        me.fireEvent('change', me, me.getValue(), oldValue);
    },

    /**
     * Updates the underlying `<options>` list with new values.
     *
     * @param {Array} newOptions An array of options configurations to insert or append.
     *
     *     selectBox.setOptions([
     *         {text: 'First Option',  value: 'first'},
     *         {text: 'Second Option', value: 'second'},
     *         {text: 'Third Option',  value: 'third'}
     *     ]).setValue('third');
     *
     * __Note:__ option object member names should correspond with defined {@link #valueField valueField} and
     * {@link #displayField displayField} values.
     *
     * @return {Ext.field.Select} this
     */
    updateOptions: function (newOptions) {
        var store = this.getStore();

        if (!store) {
            this.setStore(true);
            store = this._store;
        }

        if (!newOptions) {
            store.clearData();
        }
        else {
            store.setData(newOptions);
            this.onStoreDataChanged(store);
        }
        return this;
    },

    applyStore: function (store) {
        if (store === true) {
            store = Ext.create('Ext.data.Store', {
                fields: [this.getValueField(), this.getDisplayField()],
                autoDestroy: true
            });
        }

        if (store) {
            store = Ext.data.StoreManager.lookup(store);

            store.on({
                scope: this,
                addrecords: 'onStoreDataChanged',
                removerecords: 'onStoreDataChanged',
                updaterecord: 'onStoreDataChanged',
                refresh: 'onStoreDataChanged'
            });
        }

        return store;
    },

    updateStore: function (newStore) {
        if (newStore) {
            this.onStoreDataChanged(newStore);
        }

        if (this.getUsePicker() && this.picker) {
            this.picker.down('pickerslot').setStore(newStore);
        } else if (this.listPanel) {
            this.listPanel.down('dataview').setStore(newStore);
        }
    },

    /**
     * Called when the internal {@link #store}'s data has changed.
     */
    onStoreDataChanged: function (store) {
        var initialConfig = this.getInitialConfig(),
            value = this.getValue();

        if (value || value == 0) {
            this.updateValue(this.applyValue(value));
        }
//gaomimi  不自动取第一个数据
      /*  if (this.getValue() === null) {
            if (initialConfig.hasOwnProperty('value')) {
                this.setValue(initialConfig.value);
            }

            if (this.getValue() === null && this.getAutoSelect()) {
                if (store.getCount() > 0) {
                    this.setValue(store.getAt(0));
                }
            }
        }*/
    },

    /**
     * @private
     */
    doSetDisabled: function (disabled) {
        var component = this.getComponent();
        if (component) {
            component.setDisabled(disabled);
        }
        Ext.Component.prototype.doSetDisabled.apply(this, arguments);
    },

    /**
     * @private
     */
    setDisabled: function () {
        Ext.Component.prototype.setDisabled.apply(this, arguments);
    },

    // @private
    updateLabelWidth: function () {
        if (Ext.theme.is.Blackberry) {
            return;
        } else {
            this.callParent(arguments);
        }
    },

    // @private
    updateLabelAlign: function () {
        if (Ext.theme.is.Blackberry) {
            return;
        } else {
            this.callParent(arguments);
        }
    },

    /**
     * Resets the Select field to the value of the first record in the store.
     * @return {Ext.field.Select} this
     * @chainable
     */
    reset: function () {
        var me = this,
            record;

        if (me.getAutoSelect()) {
            var store = me.getStore();

            record = (me.originalValue) ? me.originalValue : store.getAt(0);
        } else {
            var usePicker = me.getUsePicker(),
                picker = usePicker ? me.picker : me.listPanel;

            if (picker) {
                picker = picker.child(usePicker ? 'pickerslot' : 'dataview');

                picker.deselectAll();
            }

            record = null;
        }

        me.setValue(record);

        return me;
    },

    onFocus: function (e) {
        if (this.getDisabled()) {
            return false;
        }
        var component = this.getComponent();
        this.fireEvent('focus', this, e);

        if (Ext.os.is.Android4) {
            component.input.dom.focus();
        }
        component.input.dom.blur();

        this.isFocused = true;

        this.showPicker();
    },

    destroy: function () {
        this.callParent(arguments);
        var store = this.getStore();

        if (store && store.getAutoDestroy()) {
            Ext.destroy(store);
        }

        Ext.destroy(this.listPanel, this.picker);
    }
});

Ext.define('', { override: 'Ext.picker.Date',

	  setValue: function(value, animated) { //设置默认定位当前日期
       
    	if (!value) {
            value = new Date();
        } 
        if (Ext.isDate(value)) {
            value = {
                day  : value.getDate(),
                month: value.getMonth() + 1,
                year : value.getFullYear()
            };
        }

        this.callParent([value, animated]);
    
    }
	
});

Ext.define('com.hurong.timePicker',{
  extend:'Ext.DatePicker',
  xtype:'todaypickerux',
  alias:'widget.timepickerux',
/*  requires:[
     'Ext.DateExtras',
     'Ext.util.InputBlocker'
  
  ],*/
  config:{
    	border:0,
    	stretchx:false,
    	userTitles:true,
        dayText: '日',
        monthText: '月',
        slotOrder: [
            'year',
            'month',
            'day'
        ],
        yearFrom: 2000,
        yearText: '年',
        yearTo: 2050,
        doneButton: '确定',
        cancelButton: '取消'
    },
  setValue: function(value, animated) { //设置默认定位当前日期
    	if (!value) {
            value = new Date();
        } 
        if (Ext.isDate(value)) {
            value = {
                day  : value.getDate(),
                month: value.getMonth() + 1,
                year : value.getFullYear()
            };
        }

        this.callParent([value, animated]);
    
    }
	/* setValue: function(value, animated) { //设置默认定位当前日期
        if (!value) {
            value = new Date();
        }
        if(Ext.isDate(value)) {
            value = {
                day: value.getDate(),
                month: value.getMonth() + 1,
                year: value.getFullYear(),
                hour: value.getHours(),
                minute: value.getMinutes()
            };
            com.newgrand.timePicker.superclass.superclass.setValue.call(this, value, animated);
            this.onSlotPick();
        }
    },*/
})
//弹出键盘挡住输入框的解决办法
Ext.define('UX.viewport.FieldScroller', {  
    override: 'Ext.viewport.Default',  
  
    onElementFocus: function() {  
        this.callParent(arguments);  
  
        this.scrollFocusedFieldIntoView();  
    },  
  
    scrollFocusedFieldIntoView: function() {  
        var me = this,  
            focusedDom = me.focusedElement,  
            fieldEl = focusedDom && Ext.fly(focusedDom).up('.x-field'),  
            fieldId = fieldEl && fieldEl.id,  
            fieldCmp = fieldId && Ext.getCmp(fieldId),  
            offsetTop = 0,  
            scrollingContainer, scroller, scrollerEl, domCursor, thresholdY, containerHeight;  
  
        if (!fieldCmp) {  
            return;  
        }  
  
        scrollingContainer = fieldCmp.up('{getScrollable()}');  
  
        if (scrollingContainer) {  
            scroller = scrollingContainer.getScrollable().getScroller();  
            scrollerEl = scroller.getElement();  
            domCursor = focusedDom;  
  
            while (domCursor && domCursor !== scrollerEl.dom) {  
                offsetTop += domCursor.offsetTop;  
                domCursor = domCursor.offsetParent;  
            }  
  
            containerHeight = scroller.getContainerSize().y;  
            thresholdY = offsetTop + fieldEl.getHeight() + (me.config.fieldFocusPadding || 40);  
            // console.log('offsetTop=%o, containerHeight=%o, thresholdY=%o', offsetTop, containerHeight, thresholdY);  
  
            if (scroller.position.y + containerHeight < thresholdY) {  
                // console.log('scrolling to ', thresholdY - containerHeight);  
                scroller.scrollTo(0, thresholdY - containerHeight, true);  
            }  
        }  
    }  
}, function() {  
    Ext.onSetup(function() {  
        Ext.Viewport.on('resize', 'scrollFocusedFieldIntoView');  
    });  
});

//树
Ext.define('ux.MultipleTreeListView', {
    extend : 'Ext.Container',
    xtype : 'multipletreelistview',
    alternateClassName : 'multipletreelistview',
    config : {
        isCheckTap : false,
        flowId : null,
        roleId : null,
        store : null,
        currentRecord : null,
        currentIndex : null,
        layout : 'fit',
        items : [{
            xtype : 'dataview',
            styleHtmlContent : true,
            margin : '0 10 0 10',
            scrollable : {
                direction : 'vertical',
                directionLock : true
            },
            itemTpl : new Ext.XTemplate('<div class="{parentNode}" id="{node}">' + '<table style="width:100%;margin:0;">' + '<tr>' + '<tpl if="level &gt; 0">' + '<td style="width:{level*18}px;"></td>' + '</tpl>' +
            // + '<tpl if="!leaf">'//如果是boolean类型，在data[]里面可以不用指定他的类型为boolean，
            //如果是store则必须在type里面指定类型为boolean，否则不能识别为boolean类型
            '<tpl if="iconCls != \'user\'">' + '<tpl if="isOpen">' + '<td style="width:18px;padding:0"><image src="resources/images/nolines_minus.gif"></image></td>' + '<tpl if="iconCls == \'subrole\'">' + '<td style="width:18px;padding:0"><image style="width:18px;" src="resources/images/sl.png"></image></td>' + '<tpl else>' + '<td style="width:18px;padding:0"><image src="resources/images/folderopen.gif"></image></td>' + '</tpl>' + '<tpl else>' + '<td style="width:18px;padding:0"><image src="resources/images/nolines_plus.gif"></image></td>' + '<tpl if="iconCls == \'subrole\'">' + '<td style="width:18px;padding:0"><image style="width:18px;" src="resources/images/sl.png"></image></td>' + '<tpl else>' + '<td style="width:18px;padding:0"><image src="resources/images/folder.gif"></image></td>' + '</tpl>' + '</tpl>' + '<tpl else>' + '<td style="width:18px;padding:0"></td>' + '<td style="width:18px;padding:0"><image style="width:18px;" src="resources/images/pp.png"></image></td>' + '</tpl>' + '<td style="word-break:break-all;"><input type=\'checkbox\' onclick="util.clickFn(\'{nodeType}\', \'{node}\')" id = \'check_{node}\' class = "results" <tpl if="isChecked"> checked="checked" </tpl>/>{text}</td>' + '</tr>' + '</table>' + '</div>')
        }, {
            xtype : 'toolbar',
            docked : 'top',
            title : '人员选择树',
            items : [{
                xtype : 'button',
                iconCls : 'arrow_left',
                handler : function(button) {
                    util.backView();
                }
            }, {
                xtype : 'button',
                iconCls : 'check',
                right : 0,
                top : 5,
                handler : function(button) {
                    if (window.confirm("确认选择吗？")) {
                        var me = button.getParent().getParent();
                        var classElements = document.getElementsByClassName("results");
                        var len = classElements.length;
                        var texts = '';
                        var nodes = '';
                        for (var i = 0; i < len; i++) {
                            if (classElements[i].checked) {
                                var node = classElements[i].id.substring(6);
                                var record = me.getRecordByNode(node);
                                texts += record.get('text');
                                texts += ",";
                                var node;
                                var nowNodes = record.get('node').split(",");
                                if (nowNodes.length > 1) {
                                    node = nowNodes[nowNodes.length - 1];
                                } else {
                                    node = record.get('node');
                                    //处理根节点没逗号的问题
                                }
                                nodes += node;
                                nodes += ",";
                            }
                        }
                        texts = texts.substring(0, texts.length - 1);
                        nodes = nodes.substring(0, nodes.length - 1);
                        util.backView();
                        var view = Ext.Viewport.getActiveItem();
                        view.setCsfDisplay(texts);
                        view.setCsf(nodes);
                    }
                }
            }]
        }]
    },
    /**
     * 初始化方法
     */
    initialize : function() {
        var me = this;
        me.callParent();
        var store = Ext.create('FaultOrder.store.TreeStore');
        store.setData([{
            node : "-1",
            nodeType : "root",
            parentNode : "",
            parentNodeType : "",
            text : "可选子角色",
            iconCls : "dict",
            leaf : false,
            //以下两个是客户端为达到树形效果增加的两个属性，不用服务器传来
            level : 0,
            isOpen : false,
            isChecked : false
        }]);
 
        me.items.items[0].setStore(store);
        me.items.items[0].on('itemsingletap', me.itemsingletapFn, me);
    },
    /**
     * 页面加载完成
     */
    show : function() {
        var store = this.items.items[0].getStore();
        var len = store.getCount();
        for (var i = 0; i < len; i++) {
            var record = store.getAt(i);
            //获得节点对应的等级
            var level = record.get('level');
            var node = record.get('node');
            var div = document.getElementById(node);
            if (level == 0) {//初始化时，0级节点才显示
                div.style.display = 'block';
            } else {
                div.style.display = 'none';
            }
        }
    },
    /**
     * 单击
     * @param {} list
     * @param {} index
     * @param {} target
     * @param {} record
     * @param {} e
     * @param {} eOpts
     */
    itemsingletapFn : function(list, index, target, record, e, eOpts) {
        if (this.getIsCheckTap()) {
            //解开锁并返回
            this.setIsCheckTap(false);
            return;
        }
        this.config.x = e.pageX;
        this.config.y = e.pageY;
        this.setCurrentIndex(index);
        var me = this;
        var level = record.get('level');
        var isOpen = record.get('isOpen');
        var leaf = record.get('leaf');
        var node = record.get('node');
        var nodeType = record.get('nodeType');
        var parentNode = record.get('parentNode');
        var display = 'none';
 
        //获得所有class=node的块（子级节点）
        var classElements = document.getElementsByClassName(node);
        var len = classElements.length;
        record.set('isOpen', !isOpen);
        //切换打开/关闭节点
        this.setCurrentRecord(record);
        if (len < 1) {//没有子节点，则可能是还未向服务器请求到数据，此时向服务器发起请求
            this.acquireNodeRequest(record);
        } else {//执行树节点打开关闭操作
            if (!isOpen) {//当前的isOpen和保存isOpen变量时是相反的
                display = 'block';
            } else {
                display = 'none';
            }
            me.setDisplays(node, display);
        }
    },
    /**
     * 设置div块显示还是隐藏
     * @param {} node
     * @param {} display
     */
    setDisplays : function(node, display) {
        var me = this;
        //获得所有class=node的块
        var classElements = document.getElementsByClassName(node);
        var len = classElements.length;
        for (var i = 0; i < len; i++) {
            var childId = classElements[i].id;
            //必须先设置isOpen属性后设置显示或隐藏才生效
            me.setOpenBynode(childId, false);
            classElements[i].style.display = display;
            if (display == 'none') {//只有隐藏的时候才把子节点隐藏，展开时没必要把子节点也展开
                var childElements = document.getElementsByClassName(childId);
                if (childElements.length > 0) {//如果该节点有子节点
                    me.setDisplays(childId, "none");
                    //当前节点的子节点全部隐藏
                }
            }
        }
    },
    /**
     * 通过node找到对应的record并设置起isOpen属性
     * @param {} node
     * @param {} isOpen
     */
    setOpenBynode : function(node, isOpen) {
        //list没绑定store直接绑定data的，在list渲染过程中会生成一个store绑定到list
        var store = this.items.items[0].getStore();
        var storeLen = store.getCount();
        for (var i = 0; i < storeLen; i++) {
            var record = store.getAt(i);
            if (node == record.get('node')) {
                record.set('isOpen', isOpen);
                break;
            }
        }
    },
    acquireNodeRequest : function(record) {
        var flowId = this.getFlowId();
        var roleId = this.getRoleId();
        var node;
        var nowNodes = record.get('node').split(",");
        if (nowNodes.length > 1) {
            node = nowNodes[nowNodes.length - 1];
        } else {
            node = record.get('node');
            //处理根节点没逗号的问题
        }
        var nodeType = record.get('nodeType');
        var parentNode;
        var nowParentNodes = record.get('parentNode').split(",");
        if (nowParentNodes.length > 1) {
            parentNode = nowParentNodes[nowParentNodes.length - 1];
        } else {
            parentNode = record.get('parentNode');
            //处理根节点没逗号的问题
        }
        var parentNodeType = record.get('parentNodeType');
 
        var xmlData = util.setParameterSoap('subRoleTree', config.WorkSheetMobile, 'roleId', roleId, 'flowId', flowId, 'node', node, 'nodeType', nodeType, 'parentNode', parentNode, 'parentNodeType', parentNodeType);
        var me = this;
        Ext.Ajax.request({
            xmlData : xmlData,
            success : me.successFnOfacquireNodeRequest,
            scope : me
        });
    },
    /**
     * 节点
     * @param {} result
     */
    successFnOfacquireNodeRequest : function(result) {
        var currentRecord = this.getCurrentRecord();
        var currentIndex = this.getCurrentIndex();
        var store = this.items.items[0].getStore();
        var storeLen = store.getCount();
        var firstDatas = store.getRange(0, currentIndex);
        var secondDatas = [];
        if (currentIndex < (storeLen - 1)) {//如果当前的index大于等于（等于时前面和后面都包含最后一个）时就不执行
            secondDatas = store.getRange(currentIndex + 1, storeLen - 1);
        }
        var str = $(result.responseText).children().text();
        str = util.replaceAll(str, '\"id\":null', '\"id\":\"null\"');
        //处理其他：里面的id:null转换为json后为id: "null"
        var requestData = [];
        //将字符串转换为json对象
        var strData = new Ext.data.reader.Json().getResponseData(str);
        var strLen = strData.length;
        for (var i = 0; i < strLen; i++) {
            requestData[i] = {
                text : strData[i].text,
                iconCls : strData[i].iconCls,
                leaf : strData[i].leaf,
                nodeType : strData[i].nodeType,
                node : currentRecord.get('node') + "," + strData[i].id, //因为传来的类似：传输网这些很多相同的节点（node相同）
                parentNode : currentRecord.get('node'),
                parentNodeType : currentRecord.get('nodeType'),
                level : currentRecord.get('level') + 1,
                isOpen : false,
                isChecked : false
            };
        }
        //将处理后的新数据通过拼接数组放到store里面显示
        store.insert(currentIndex + 1, requestData);
        var isOpen = currentRecord.get('isOpen');
        var node = currentRecord.get('node');
        //因为此时发生在点击后，则isOpen相当于点之前来说是相反的
        if (isOpen) {
            display = 'block';
        } else {
            display = 'none';
        }
        this.setDisplays(node, display);
    },
    /**
     * 重写隐藏方法，摧毁掉弹出框
     */
    hide : function() {
        this.destroy();
    },
    /**
     * 通过node获得record
     * @param {} node
     * @return {}
     */
    getRecordByNode : function(node) {
        var store = this.items.items[0].getStore();
        var storeLen = store.getCount();
        for (var i = 0; i < storeLen; i++) {
            var record = store.getAt(i);
            if (node == record.get('node')) {
                return record;
                break;
            }
        }
        return null;
    }
});