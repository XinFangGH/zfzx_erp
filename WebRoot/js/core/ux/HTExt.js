
/**
 * 用于扩展一些常用的ExtJs，以简化大量重复性的代码
 */
Ext.ns('HT');
Ext.ns('HT.ux.plugins');
/**
 * 查找
 * 
 * @param {}
 *            grid
 * @param {}
 *            idName
 * @return {}
 */
function $getGdSelectedIds(grid, idName) {
    var selRs = grid.getSelectionModel().getSelections();
    var ids = Array();
    for (var i = 0; i < selRs.length; i++) {
    	if(null!=eval('selRs[i].data.' + idName) && eval('selRs[i].data.' + idName)!=''){
    		ids.push(eval('selRs[i].data.' + idName));
    	}else{
    		grid.getStore().remove(selRs[i]);
			grid.getView().refresh();
    	}
    }
    return ids;
}
function $getGdSelectedSum(grid, idName) {
    var selRs = grid.getSelectionModel().getSelections();
    var sum=0;
    for (var i = 0; i < selRs.length; i++) {
    	if(null!=eval('selRs[i].data.' + idName) && eval('selRs[i].data.' + idName)!=''){
    		sum=sum+parseFloat(eval('selRs[i].data.' + idName));
    	}
    }
    return sum;
}
function $postDel(conf) {
	var errorMsg= '您确认要删除所选记录吗？';
	/*if(conf.errorMsg!=""&&conf.errorMsg!="undefined"){
	      errorMsg=conf.errorMsg;
	}*/
    Ext.Msg.confirm('信息确认', errorMsg, function(btn) {
        if (btn == 'yes') {
            Ext.Ajax.request({
                url : conf.url,
                params : {
                    ids : conf.ids
                },
                method : 'POST',
                success : function(response, options) {
                	if(response.responseText.trim()=="{success:true}"){
                		Ext.ux.Toast.msg('操作信息', '删除成功！');
	                    if (conf.callback) {
	                        conf.callback.call(this);
	                        return;
	                    }
                	}else{
                		Ext.ux.Toast.msg('操作信息', '删除失败，请先清空该栏目内容，再删除！');
	                    if (conf.callback) {
	                        conf.callback.call(this);
	                        return;
	                    }
                	}
                    
                    if (conf.grid) {
                        conf.grid.getStore().reload();
                    }
                },
                failure : function(response, options) {
                    Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
                }
            });
        }
    });
}
/**
 * 提交表单
 * 
 * @param {}
 *            conf
 */
function $postForm(conf) {
	
    if (conf.formPanel.getForm().isValid()) {
        var scope = conf.scope ? conf.scope : this;
        var isShow = conf.isShow ? conf.isShow : false;
        var msg=conf.msg
        conf.formPanel.getForm().submit({
            scope : scope,
            url : conf.url,
            method : 'post',
            params : conf.params,
            waitMsg : '正在提交数据...',
            success : function(fp, action) {
            	var obj = Ext.util.JSON.decode(action.response.responseText);
				if(typeof(obj.id) == "undefined"){
	                Ext.ux.Toast.msg('操作信息', (typeof(msg)!='undefined' && null!=msg)?msg:'保存成功！');
				}
                if (conf.callback) {
                    conf.callback.call(scope, fp, action);
                }
            },
            failure : function(fp, action) {
            	if(isShow){
                Ext.MessageBox.show({
                    title : '操作信息',
                    msg : '信息保存出错，请联系管理员！',
                    buttons : Ext.MessageBox.OK,
                    icon : 'ext-mb-error'
                });}
                if (conf.callback) {
                    conf.callback.call(scope, fp, action);
                }
            }
        });
    }
}
/**
 * 
 * @param {}
 *            conf
 */
function $delGridRs(conf) {
    var ids = $getGdSelectedIds(conf.grid, conf.idName);
    if (ids.length == 0) {
        Ext.ux.Toast.msg("操作信息", "请选择要删除的记录！");
        return;
    }
    var errorinfo="";
    if(typeof(conf.error)!="undefined"){
    	   errorinfo=conf.error;
    };
    var params = {
        url : conf.url,
        ids : ids,
        errorMsg:errorinfo,
        grid : conf.grid
    };
    $postDel(params);
}
/**
 * 搜索，把查询的Panel提交，并且更新gridPanel的数据， 使用以下所示： $search({
 * searchPanel:this.searchPanel, gridPanel:this.gridPanel });
 * 
 * @param {}
 *            conf
 */
function $search(conf) {
    var searchPanel = conf.searchPanel;
    var gridPanel = conf.gridPanel;
    if (searchPanel.getForm().isValid()) {// 如果合法
        var store = gridPanel.getStore();
        var baseParam = Ext.Ajax.serializeForm(searchPanel.getForm().getEl());
        var deParams = Ext.urlDecode(baseParam);
        deParams.start = 0;
        deParams.limit = store.baseParams.limit;
        store.baseParams = deParams;
        if(gridPanel.getBottomToolbar()!=null&&gridPanel.getBottomToolbar()!='undefined'){
        	gridPanel.getBottomToolbar().moveFirst();
        }else{
       		store.load();	
        }
        
    }
}

/**
 * 搜索Panel
 * 
 * @class HT.SearchPanel
 * @extends Ext.form.FormPanel
 */
HT.SearchPanel = Ext.extend(Ext.form.FormPanel, {
    constructor : function(conf) {
        // 查看其是否允许多行
        var colNums = conf.colNums ? conf.colNums : 1;
        Ext.apply(this, conf);

        if (colNums > 1 && conf.items) {
            this.items = [];
            var row = null;
            var validCnt = 0;
            for (var i = 0; i < conf.items.length; i++) {
                var cmp = conf.items[i];
                if (cmp.xtype != 'hidden') {
                    if (validCnt % colNums == 0) {
                        row = {
                            xtype : 'compositefield',
                            fieldLabel : cmp.fieldLabel,
                            items : [],
                            defaults : {
                                style : 'margin:0 0 0 0'
                            }
                        };
                        this.items.push(row);
                    } else {
                        // 设置分隔符
                        var sepr = ":";
                        if (this.superclass.labelSeparator) {
                            sepr = this.superclass.labelSeparator;
                        };
                        // 设置label长度
                        var width = 100;
                        if (this.labelWidth) {
                            width = this.labelWidth;
                        };
                        if (cmp.labelWidth) {
                            width = cmp.labelWidth;
                        };
                        // 设置label左右位置
                        var textAlign = 'text-align:left';
                        if ('right' == this.labelAlign) {
                            textAlign = 'text-align:right';
                        }

                        if (cmp.fieldLabel) {
                            row.items.push({
                                xtype : 'label',
                                width : width,
                                style : textAlign,
                                text : cmp.fieldLabel + sepr
                            });
                        }
                    }
                    row.items.push(cmp);
                    validCnt++;
                } else {
                    this.items.push(cmp);
                }
            }
        }
        HT.SearchPanel.superclass.constructor.call(this, {
            autoHeight : true,
            border : false,
            style : 'padding:6px;background-color: white',
            buttonAlign : 'center'
        });
    }
});

/**
 * 取到某项目的数据字典
 * 
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
DicCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var itemName = this.itemName;
        var isDisplayItemName = this.isDisplayItemName;
        DicCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    itemName : itemName
                },
                url : __ctxPath + '/system/loadItemDictionary.do',
                fields : ['itemId', 'itemName']
            }),
            displayField : 'itemName',
            valueField : isDisplayItemName ? 'itemName' : 'itemId'
        });
    }
});

Ext.reg('diccombo', DicCombo);

/**
 * 根据nodeKey取得某项目的数据字典
 * 
 */
DicKeyCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var nodeKey = this.nodeKey;
        var isDisplayItemName = this.isDisplayItemName;
        DicKeyCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    nodeKey : nodeKey
                },
                url : __ctxPath + '/system/loadItemByNodeKeyDictionary.do',
                fields : ['itemId', 'itemName']
            }),
            displayField : 'itemName',
            valueField : isDisplayItemName ? 'itemName' : 'itemId'
        });
    }
});

Ext.reg('dickeycombo', DicKeyCombo);


P2PDicKeyCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var nodeKey = this.nodeKey;
        var isDisplayItemName = this.isDisplayItemName;
        P2PDicKeyCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    nodeKey : nodeKey
                },
                url : __ctxPath + '/system/loadItemByNodeKey2Dictionary.do',
                fields : ['dicKey', 'itemValue']
            }),
            displayField : 'itemValue',
            valueField : 'dicKey'
        });
    }
});

Ext.reg('p2pdickeycombo', P2PDicKeyCombo);

/**
 * 取到global_type的数据字典
 * 
 * @class GlobalCombo
 * @extends Ext.form.ComboBox
 */
GlobalCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var typeName = this.typeName;
        var isDisplayItemName = this.isDisplayItemName;
        DicCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    typeName : typeName
                },
                url : __ctxPath + '/system/getListByTypeNameGlobalType.do',
                //url : __ctxPath + '/system/loadItemDictionary.do',
                fields : ['proTypeId', 'typeName']
            }),
            displayField : 'typeName',
            valueField : isDisplayItemName ? 'typeName' : 'proTypeId'
        });
    }
});

Ext.reg('globalCombo', GlobalCombo);

/**
 * 根据nodeKey取得独立数据字典的items
 * 
 */
DicIndepCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var nodeKey = this.nodeKey;
        var isDisplayItemName = this.isDisplayItemName;
        DicIndepCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    nodeKey : nodeKey
                },
                url : __ctxPath + '/system/loadIndepItemsDictionaryIndependent.do',
                fields : ['dicKey', 'itemValue']
            }),
            displayField : 'itemValue',
            valueField : isDisplayItemName ? 'itemValue' : 'dicKey'
        });
    }
});

Ext.reg('dicIndepCombo', DicIndepCombo);
/**
 * 取到某项目的数据字典
 * 
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
CSDicCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var itemVale = this.itemVale;
        var isDisplayItemName = this.isDisplayItemName;
        /*
         * var store = new Ext.data.JsonStore({ autoLoad : true, root :
         * 'topics', fields : [{ name : 'itemId', mapping : 'id' }, { name :
         * 'itemValue', mapping : 'value' }], baseParams : { dictionaryId :
         * itemVale }, url : __ctxPath +
         * '/publicmodel/dictionary/getOptionList.do' });
         */
        CSDicCombo.superclass.constructor.call(this, {
            store : new Ext.data.JsonStore({
            autoLoad : true,
            root : 'topics',
            fields : [{
                name : 'itemId',
                mapping : 'id'
            }, {
                name : 'itemValue',
                mapping : 'value'
            }],
            baseParams : {
                dictionaryId : itemVale
            },
            url : __ctxPath + '/publicmodel/dictionary/getOptionList.do'
        }),
            triggerAction : 'all',
            lazyInit : false,
            displayField : 'itemValue',
            valueField : isDisplayItemName ? 'itemValue' : 'itemId'
        });
    }
});

Ext.reg('csdiccombo', CSDicCombo);

/**
 * 取到某项目的多级数据字典
 * 
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
MorDicCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var itemValue = this.itemValue; // id号
        var cid = this.cid;// 子控件id号
        var isDisplayItemName = this.isDisplayItemName;
        MorDicCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    parentId : itemValue
                },
                url : __ctxPath + '/system/getByParentIdDicAreaDynam.do',
                fields : ['itemId', 'itemName']
            }),
            displayField : 'itemName',
            valueField : isDisplayItemName ? 'itemName' : 'itemId',
            listeners : {
                select : function(combo, record, index) {
                    var selectValue = record.get('itemId');
                    var arrStore = new Ext.data.ArrayStore({
                        url : __ctxPath
                                + '/system/getByParentIdDicAreaDynam.do',
                        fields : ['itemId', 'itemName'],
                        baseParams : {
                            parentId : selectValue
                        }
                    });
                    Ext.getCmp(cid).clearValue();
                    Ext.getCmp(cid).store = arrStore;
                    arrStore.load();
                    if (Ext.getCmp(cid).view) { // 刷新视图,避免视图值与实际值不相符
                        Ext.getCmp(cid).view.setStore(arrStore);
                    }
                },
                afterrender:function (combox){
                                                 var st=combox.getStore();
                                                 st.on("load",function(){
                                                       combox.clearInvalid();
                                                       if(combox.getValue()>0)
                                                       {
                                                              combox.setValue(combox.getValue());
                                                          
                                                       }
                                                       })
                }
            }
        });
    }
});

Ext.reg('mordiccombo', MorDicCombo);

/**
 * 取到某项目的多级数据字典
 * 
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
SMDicCombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var itemValue = this.itemValue; // id号
        var isDisplayItemName = this.isDisplayItemName;
        SMDicCombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    parentId : itemValue
                },
                url : __ctxPath + '/system/getByParentIdDicAreaDynam.do',
                fields : ['itemId', 'itemName']
            }),
            displayField : 'itemName',
            valueField : isDisplayItemName ? 'itemName' : 'itemId'
        });
    }
});
Ext.reg('smdiccombo', SMDicCombo);

/**
 * 取到小贷所有合同类型
 * 
 * @class DicCombo
 * @extends Ext.form.ComboBox
 */
CONTRACTTYPECombo = Ext.extend(Ext.form.ComboBox, {
    constructor : function(config) {
        Ext.apply(this, config);
        var itemValue = this.itemValue; // id号
        var isDisplayItemName = this.isDisplayItemName;
        CONTRACTTYPECombo.superclass.constructor.call(this, {
            triggerAction : 'all',
            store : new Ext.data.ArrayStore({
                autoLoad : true,
                baseParams : {
                    mark : itemValue
                },
                url : __ctxPath + '/contract/getAllByOnlymarkDocumentTemplet.do',
                fields : ['itemId', 'itemName']
            }),
            displayField : 'itemName',
            valueField : isDisplayItemName ? 'itemName' : 'itemId'
        });
    }
});
Ext.reg('contracttypecombo', CONTRACTTYPECombo);
/**
 * 在分页栏中的导出插件
 * 
 * @class HT.ux.plugins.Export
 * @extends Object
 */
HT.ux.plugins.Export = Ext.extend(Object, {
    constructor : function(config) {
        Ext.apply(this, config);
        HT.ux.plugins.Export.superclass.constructor.call(this, config);
    },

    init : function(pagingToolbar) {
        var excelBtn = new Ext.SplitButton({
            text : '导出',
            iconCls : 'btn-export',
            menu : new Ext.menu.Menu({
                items : [{
                    text : '导出当前页EXCEL',
                    iconCls : 'btn-export-excel',
                    listeners : {
                        click : function() {
                            var gp = pagingToolbar.findParentBy(function(ct,
                                    cmp) {
                                ;
                                return (ct instanceof Ext.grid.GridPanel)
                                        ? true
                                        : false;
                            });
                            CommonExport(gp, false, 'xls');
                        }
                    }
                }, {
                    text : '导出全部记录EXCEL',
                    iconCls : 'btn-export-excel',
                    listeners : {
                        click : function() {
                            var gp = pagingToolbar.findParentBy(function(ct,
                                    cmp) {
                                ;
                                return (ct instanceof Ext.grid.GridPanel)
                                        ? true
                                        : false;
                            });
                            CommonExport(gp, true, 'xls');
                        }
                    }
                }, '-', {
                    text : '导出当前页PDF',
                    iconCls : 'btn-export-pdf',
                    listeners : {
                        click : function() {
                            var gp = pagingToolbar.findParentBy(function(ct,
                                    cmp) {
                                ;
                                return (ct instanceof Ext.grid.GridPanel)
                                        ? true
                                        : false;
                            });
                            CommonExport(gp, false, 'pdf');
                        }
                    }
                }, {
                    text : '导出全部记录PDF',
                    iconCls : 'btn-export-pdf',
                    listeners : {
                        click : function() {
                            var gp = pagingToolbar.findParentBy(function(ct,
                                    cmp) {
                                ;
                                return (ct instanceof Ext.grid.GridPanel)
                                        ? true
                                        : false;
                            });
                            CommonExport(gp, true, 'pdf');
                        }
                    }
                }]
            })
        });
        pagingToolbar.add('->');
        pagingToolbar.add('-');
        pagingToolbar.add(excelBtn);
        pagingToolbar.add('-');

        pagingToolbar.on({
            beforedestroy : function() {
                excelBtn.destroy();
            }
        });
    }
});

/**
 * 导出公共方法，在本页下载，不打开另一页 param: gridObj 取得grid isExportAll 是否导出所有 exportType 导出的类型
 */
function CommonExport(gridObj, isExportAll, exportType) {

    var cols = gridObj.getColumnModel().columns;
    var colName = '';
    var colId = '';
    for (var index = 0; index < cols.length; index++) {
        if (index > 2 && cols[index].isExp == true) {
            colName += cols[index].header + ',';
            if (cols[index].javaRenderer != null) {
                colId += "javaRenderer" + cols[index].javaRenderer + ",";
            } else {
                colId += cols[index].dataIndex + ',';
            }
        }
    }

    if (colName.length > 0) {
        colName = colName.substring(0, colName.length - 1);
        colId = colId.substring(0, colId.length - 1);
    }

    var parasArr = {
        isExport : true,
        isExportAll : isExportAll,
        exportType : exportType,
        colId : colId,
        colName : colName
    };

    Ext.apply(parasArr, gridObj.store.baseParams);

    var elemIF = document.getElementById('downloadFrame');
    if (!elemIF) {
        elemIF = document.createElement("iframe");
        elemIF.setAttribute('id', 'downloadFrame');
        document.body.appendChild(elemIF);
    }

    var ifrdoc;
    if (elemIF.contentDocument) {
        ifrdoc = elemIF.contentDocument;
    } else if (elemIF.contentWindow) {
        ifrdoc = elemIF.contentWindow.document;
    } else {
        ifrdoc = elemIF.document;
    }

    if (ifrdoc.document) {
        ifrdoc = ifrdoc.document;
    }
    var body = ifrdoc.body;
    if (!body) {
        ifrdoc
                .write("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head>");
        body = ifrdoc.createElement('body');
        ifrdoc.appendChild(body);
    }

    var elemForm = ifrdoc.getElementById('downloadForm');

    // if exist then remove
    if (elemForm) {
        ifrdoc.body.removeChild(elemForm);
    }

    // create new form and add new parameters
    elemForm = ifrdoc.createElement("form");
    elemForm.id = 'downloadForm';

    ifrdoc.body.appendChild(elemForm);

    var url = gridObj.store.proxy.url;
    for (var v in parasArr) {
        var elmObj = ifrdoc.createElement("input");
        elmObj.type = "hidden";
        elmObj.name = v;
        elmObj.value = parasArr[v];
        elemForm.appendChild(elmObj);
    }

    elemForm.method = 'post';
    elemForm.action = url;
    elemForm.submit();

};

/**
 * 打印plugin
 * 
 * @class HT.ux.plugins.Print
 * @extends Object
 */
HT.ux.plugins.Print = Ext.extend(Object, {
    constructor : function(config) {
        Ext.apply(this, config);
        HT.ux.plugins.Export.superclass.constructor.call(this, config);
    },

    init : function(pagingToolbar) {
        var printBtn = new Ext.Button({
            text : '打印',
            iconCls : 'btn-print',
            listeners : {
                click : function() {
                    var gp = pagingToolbar.findParentBy(function(ct, cmp) {
                        ;
                        return (ct instanceof Ext.grid.GridPanel)
                                ? true
                                : false;
                    });
                    gpObj = document.getElementById(gp.id);
                    window.open(__ctxPath + '/js/printer/Print.jsp');
                }
            }
        });

        pagingToolbar.add('->');
        pagingToolbar.add('-');
        pagingToolbar.add(printBtn);

        pagingToolbar.on({
            beforedestroy : function() {
                printBtn.destroy();
            }
        });
    }
});

HT.PagingBar = Ext.extend(Ext.PagingToolbar, {
    constructor : function(conf) {
        var newConf = {
            pageSize : conf.store.baseParams.limit
                    ? conf.store.baseParams.limit
                    : 25,
            displayInfo : true,
            displayMsg : '当前显示从{0}至{1}， 共{2}条记录',
            emptyMsg : '当前没有记录',
            plugins : [new Ext.ux.plugins.PageComboResizer()]
        };
        if (conf.exportable) {
            newConf.plugins.push(new HT.ux.plugins.Export({
                store : conf.store
            }));
        }
        if (conf.printable) {
            newConf.plugins.push(new HT.ux.plugins.Print());
        }

        Ext.apply(newConf, conf);
        HT.PagingBar.superclass.constructor.call(this, newConf);
    }
});

HT.JsonStore = Ext.extend(Ext.data.JsonStore, {
    constructor : function(conf) {
        var baseParams = conf.baseParams ? conf.baseParams : {};
        if(null==baseParams.limit){
        baseParams.start = 0;
        baseParams.limit = 25;
        }
        var def = {
            baseParams : baseParams,
            root : 'result',
            totalProperty : 'totalCounts',
            remoteSort : true
        };
        Ext.applyIf(def, conf);
        HT.JsonStore.superclass.constructor.call(this, def);
    }
});
/**
 * 根据普通的配置，生成表格所需要的配置
 * 
 * @param {}
 *            conf
 * @return {}
 */
HT.initGridConfig = function(conf) {
    if (!conf.store) {
        conf.store = new HT.JsonStore({
            url : conf.url,
            fields : conf.fields,
            pruneModifiedRecords:conf.pruneModifiedRecords,
            baseParams : conf.baseParams
        });
        if (conf.isautoLoad==false||null == conf.url) {
           
        }else{
        	 conf.store.load();
            if(typeof(conf.notmask)=='undefined'||conf.notmask==false){
	         this.loadMask1 = new Ext.LoadMask(Ext.getBody(), {
			 msg  : '正在加载数据中······,请稍候······',
			 store:conf.store,
			 removeMask : true// 完成后移除
			});
			this.loadMask1 .show(); //显示
			}
        }
    }
    else{
    	// conf.store.addListener('load',function(){this.loadMarsk1 .hide()},this);
    }
	
    // --start grid cell 内容提示展示
    for (var i = 0; i < conf.columns.length; i++) {
        if (!conf.columns[i].renderer) {
            conf.columns[i].renderer = function(data, metadata, record,
                    rowIndex, columnIndex, store) {
                metadata.attr = ' ext:qtip="' + data + '"';
                return data;
            }
        }
    }
    // --end
    
    conf.sm = new Ext.grid.CheckboxSelectionModel({
        singleSelect : conf.singleSelect ? conf.singleSelect : false,
        hidden:conf.isAllReadOnly
    });
   
    if (conf.columns) {
        if(conf.hiddenCm){
            conf.columns.unshift(new Ext.grid.RowNumberer({width :35}));
        }
        else {
            if(conf.hiddenCmTwo){
                
            }else{
                conf.columns.unshift(new Ext.grid.RowNumberer({width :35}));   
                conf.columns.unshift(conf.sm);
            }
        }
        
    } else {
        conf.columns = [conf.sm, new Ext.grid.RowNumberer({width :35})];
    }

    if (!conf.tbar && conf.isShowTbar != false) {
        conf.tbar = new Ext.Toolbar();
    }

    if (conf.addTool) {
        conf.tbar.add(new Ext.Button({
            text : '添加记录',
            iconCls : 'btn-add',
            scope : this,
            handler : function() {
                var recordType = conf.store.recordType;
                conf.store.add(new recordType());
            }
        }));
    }

    this.cm = new Ext.grid.ColumnModel({
        columns : this.columns,
        defaults : {
            sortable : true,
            menuDisabled : false,
            width : 100
        }
    });
    if(typeof(conf.plugins) == "undefined" ||conf.plugins == null || conf.plugins == ""){
        conf.plugins = [];
    }
    // 加上rowActions
    if (conf.rowActions) {
        var rowActionCol = conf.columns[conf.columns.length - 1];
        conf.plugins.push(rowActionCol);
    }
    // 导出
    if (conf.exportable) {
        conf.plugins.push(HT.ux.plugins.Export({
            store : conf.store
        }));
    }
    // 打印
    if (conf.printable) {
        conf.plugins.push(HT.ux.plugins.Print);
    }
    var def = {
        shim : true,
        trackMouseOver : true,
        disableSelection : false,
       // loadMask : new Ext.LoadMask(Ext.getBody(), { msg: "系统正在处理数据，请稍候..." }),
        loadMask : false,
        stripeRows : true,
        viewConfig : {
            forceFit : true,
            enableRowBody : false,
            showPreview : false
        },
        bbar : (conf.showPaging == null || conf.showPaging)
                ? new HT.PagingBar({
                    store : conf.store
                })
                : null
    };
    Ext.apply(def, conf);

    return def;
}

/**
 * 
 * 
 * 表格管理控件，包括高级查询，数据导出，分页，排序等
 * 
 * @class HT.GridPanel
 * @extends Ext.grid.GridPanel
 */
HT.GridPanel = Ext.extend(Ext.grid.GridPanel, {
    constructor : function(conf) {
        var def = HT.initGridConfig(conf);
        HT.GridPanel.superclass.constructor.call(this, def);
    }
});

/**
 * 
 * 
 * 表格编辑管理控件，包括高级查询，数据导出，分页，排序等
 * 
 * @class HT.GridPanel
 * @extends Ext.grid.GridPanel
 */
HT.EditorGridPanel = Ext.extend(Ext.grid.EditorGridPanel, {
    constructor : function(conf) {
        var def = HT.initGridConfig(conf);
        HT.GridPanel.superclass.constructor.call(this, def);
    }
});

Ext.reg("htgrid", HT.GridPanel);
Ext.reg("hteditorgrid", HT.EditorGridPanel);

HT.FormPanel = Ext.extend(Ext.form.FormPanel, {
    constructor : function(conf) {
        var def = {
            layout : 'form',
            bodyStyle : 'padding:5px',
            defaults : {
                anchor : '96%,96%'
            },
            defaultType : 'textfield',
            border : false
        };
        Ext.apply(def, conf);
        HT.FormPanel.superclass.constructor.call(this, def);
    }
});

/*
 * 为Form表单设置加载数据,使用方式如下： this.formPanel.loadData({ url:__ctxPath +
 * '/system/getAppRole.do?roleId=' + this.roleId, preName:'AppRole', root:'data'
 * });
 */
Ext.override(Ext.Panel, {
    loadData : function(conf) {
        if (!conf.root) {
            conf.root = 'data';
        }
        var ct = this;
        // 遍历该表单下所有的子项控件，并且为它赋值
        var setByName = function(container, data) {
            var items = container.items;
            if (items != null && items != undefined && items.getCount) {
                for (var i = 0; i < items.getCount(); i++) {
                    var comp = items.get(i);
                    if (comp.items) {
                        setByName(comp, data);
                        continue;
                    }
                    // 判断组件的类型，并且根据组件的名称进行json数据的自动匹配
                    var xtype = comp.getXType();
                    try {
                        if (xtype == 'textfield' || xtype == 'textarea'
                                || xtype == 'radio' || xtype == 'checkbox'
                                || xtype == 'datefield' || xtype == 'combo'
                                || xtype == 'hidden'|| xtype=='smdiccombo'|| xtype=='dicIndepCombo'
                                || xtype == 'datetimefield' || xtype=='mordiccombo' || xtype=='csdiccombo'
                                || xtype == 'htmleditor'
                                || xtype == 'displayfield'
                                || xtype == 'diccombo' || xtype == 'fckeditor'
                                || xtype == 'numberfield' || xtype=='dicIndepCombo' || xtype=='dickeycombo'  || xtype=='moneyfield' || xtype=='zwcheckbox') {
                            var name = comp.getName();
                            if (name) {
                                if (conf.preName) {
                                    if (name.indexOf(conf.preName) != -1) {
                                        name = name
                                                .substring(conf.preName.length
                                                        + 1);
                                    }
                                }
                                var val = eval(conf.root + '.' + name);
                                if (val != null && val != undefined) {
                                    comp.setValue(val);
                                    comp.fireEvent('loadData',comp);//update by gao ,这样就可以回填值的时候触发事件了，哈哈
                                    comp.fireEvent('select',comp);
                                }
                                 if(xtype=='moneyfield'){
                                    var preName = comp.preName;
                                     var val;
                                    if(typeof(comp.preName)=="undefined" || preName== undefined || preName == null || preName == ""){
                                       val = eval(conf.root + '.' +name.substring(0,name.length-1));
                                       
                                    }else{
                                        val = eval(conf.root + '.' + preName+ '.'+name.substring(0,name.length-1));
                                    }
                                       comp.setValue(Ext.util.Format.number(val,'0,000.00'));
                                       comp.hiddenField.value=val;
                                    comp.fireEvent('loadData',comp);//
                                
                                  }
                                  if(xtype=='zwcheckbox'){
                                    var preName = comp.preName;
                                     var val = eval(conf.root + '.' + preName+ '.'+name);
                                     if(val==1){
                                        comp.setValue(true);
                                     }else if(val==0){
                                         comp.setValue(false);
                                     }
                                      
                                    comp.fireEvent('loadData',comp);//
                                
                                  }
                         
                            }
                        }
                    } catch (e) {
                        // alert(e);
                    }
                }
            }
        };
        if (!ct.loadMask) {
            ct.loadMask = new Ext.LoadMask(Ext.getBody());
            ct.loadMask.show();
        }
        var scope = conf.scope ? conf.scope : ct;
        var params = conf.params ? conf.params : {};
        Ext.Ajax.request({
            method : 'POST',
            url : conf.url,
            scope : scope,
            params : params,
            success : function(response, options) {
                var json = Ext.util.JSON.decode(response.responseText);
                var data = null;
                if (conf.root) {
                    data = eval('json.' + conf.root);
                } else {
                    data = json;
                }
                setByName(ct, data);
                if (ct.loadMask) {
                    ct.loadMask.hide();
                    ct.loadMask = null;
                }
                if (conf.success) {
                    conf.success.call(scope, response, options);
                }
            },// end of success
            failure : function(response, options) {
                if (ct.loadMask) {
                    ct.loadMask.hide();
                    ct.loadMask = null;
                }
                if (conf.failure) {
                    conf.failure.call(scope, response, options);
                }
            }
        });
    }/*,
    listeners:{
    	'activate':function(v){
    		if(this.gridPanel){
    			this.gridPanel.store.reload();
    		}
    	}
    }*/
});
/**
 * 修正表单的字段的长度限制的问题
 */
Ext.form.TextField.prototype.size = 20;
Ext.form.TextField.prototype.initValue = function() {
    if (this.value !== undefined) {
        this.setValue(this.value);
    } else if (this.el.dom.value.length > 0) {
        this.setValue(this.el.dom.value);
    }
    this.el.dom.size = this.size;
    if (!isNaN(this.maxLength) && (this.maxLength * 1) > 0
            && (this.maxLength != Number.MAX_VALUE)) {
        this.el.dom.maxLength = this.maxLength * 1;
    }
};

Ext.override(Ext.Container, {
    getCmpByName : function(name) {
        var getByName = function(container, name) {
            var items = container.items;
            if (items && items.getCount != undefined) {
                for (var i = 0; i < items.getCount(); i++) {
                    var comp = items.get(i);
                    if (name == comp.name
                            || (comp.getName && name == comp.getName())) {
                        return comp;
                        break;
                    }
                    var cp = getByName(comp, name);
                    if (cp != null)
                        return cp;
                }
            }
            return null;
        };
        return getByName(this, name);
    },
    onResize : function(adjWidth, adjHeight, rawWidth, rawHeight) {
        Ext.Container.superclass.onResize.apply(this, arguments);
        if ((this.rendered && this.layout && this.layout.monitorResize)
                && !this.suspendLayoutResize) {
            this.layout.onResize();
        }
    },

    canLayout : function() {
        var el = this.getVisibilityEl();
        return el && !el.isStyle("display", "none");
    },

    /**
     * Force this container's layout to be recalculated. A call to this function
     * is required after adding a new component to an already rendered
     * container, or possibly after changing sizing/position properties of child
     * components.
     * 
     * @param {Boolean}
     *            shallow (optional) True to only calc the layout of this
     *            component, and let child components auto calc layouts as
     *            required (defaults to false, which calls doLayout recursively
     *            for each subcontainer)
     * @param {Boolean}
     *            force (optional) True to force a layout to occur, even if the
     *            item is hidden.
     * @return {Ext.Container} this
     */
    doLayout : function(shallow, force) {
        var rendered = this.rendered, forceLayout = force || this.forceLayout, cs, i, len, c;

        if (!this.canLayout() || this.collapsed) {
            this.deferLayout = this.deferLayout || !shallow;
            if (!forceLayout) {
                return;
            }
            shallow = shallow && !this.deferLayout;
        } else {
            delete this.deferLayout;
        }

        cs = (shallow !== true && this.items) ? this.items.items : [];

        // Inhibit child Containers from relaying on resize. We plan to
        // explicitly call doLayout on them all!
        for (i = 0, len = cs.length; i < len; i++) {
            if ((c = cs[i]).layout) {
                c.suspendLayoutResize = true;
            }
        }

        // Tell the layout manager to ensure all child items are rendered, and
        // sized according to their rules.
        // Will not cause the child items to relayout.
        if (rendered && this.layout) {
            this.layout.layout();
        }

        // Lay out all child items
        for (i = 0; i < len; i++) {
            if ((c = cs[i]).doLayout) {
                c.doLayout(false, forceLayout);
            }
        }
        if (rendered) {
            this.onLayout(shallow, forceLayout);
        }
        // Initial layout completed
        this.hasLayout = true;
        delete this.forceLayout;

        // Re-enable child layouts relaying on resize.
        for (i = 0; i < len; i++) {
            if ((c = cs[i]).layout) {
                delete c.suspendLayoutResize;
            }
        }
    }
});

Ext.override(Ext.layout.ContainerLayout, {
    setContainer : function(ct) { // Don't use events!
        this.container = ct;
    }
});

Ext.override(Ext.BoxComponent, {
    setSize : function(w, h) {
        // support for standard size objects
        if (typeof w == 'object') {
            h = w.height, w = w.width;
        }
        if (Ext.isDefined(w) && Ext.isDefined(this.minWidth)
                && (w < this.minWidth)) {
            w = this.minWidth;
        }
        if (Ext.isDefined(h) && Ext.isDefined(this.minHeight)
                && (h < this.minHeight)) {
            h = this.minHeight;
        }
        if (Ext.isDefined(w) && Ext.isDefined(this.maxWidth)
                && (w > this.maxWidth)) {
            w = this.maxWidth;
        }
        if (Ext.isDefined(h) && Ext.isDefined(this.maxHeight)
                && (h > this.maxHeight)) {
            h = this.maxHeight;
        }
        // not rendered
        if (!this.boxReady) {
            this.width = w, this.height = h;
            return this;
        }

        // prevent recalcs when not needed
        if (this.cacheSizes !== false && this.lastSize
                && this.lastSize.width == w && this.lastSize.height == h) {
            return this;
        }
        this.lastSize = {
            width : w,
            height : h
        };
        var adj = this.adjustSize(w, h), aw = adj.width, ah = adj.height, rz;
        if (aw !== undefined || ah !== undefined) { // this code is nasty but
            // performs better with
            // floaters
            rz = this.getResizeEl();
            if (rz != null) {
                if (!this.deferHeight && aw !== undefined && ah !== undefined) {
                    rz.setSize(aw, ah);
                } else if (!this.deferHeight && ah !== undefined) {
                    rz.setHeight(ah);
                } else if (aw !== undefined) {
                    rz.setWidth(aw);
                }
            }
            this.onResize(aw, ah, w, h);
        }
        return this;
    },

    onResize : function(adjWidth, adjHeight, rawWidth, rawHeight) {
        this
                .fireEvent('resize', this, adjWidth, adjHeight, rawWidth,
                        rawHeight);
    }
});

Ext.override(Ext.Panel, {
    onResize : Ext.Panel.prototype.onResize
            .createSequence(Ext.Container.prototype.onResize)
});

Ext.override(Ext.Viewport, {
    fireResize : function(w, h) {
        this.onResize(w, h, w, h);
    }
});

// Ext.override(Ext.form.ComboBox,{
// setValue : function(v){
// var text = v;
// if(this.valueField){
// var r = this.findRecord(this.valueField, v);
// if(r){
// text = r.data[this.displayField];
// }else if(Ext.isDefined(this.valueNotFoundText)){
// text = this.valueNotFoundText;
// }
// }
// this.lastSelectionText = text;
// if(this.hiddenField){
// this.hiddenField.value = Ext.value(v, '');
// }
// Ext.form.ComboBox.superclass.setValue.call(this, text);
// this.value = v;
// return this;
// }
// });

HT.ComboBox = Ext.extend(Ext.form.ComboBox, {
    constructor : function(conf) {
        Ext.apply(this, conf);
        HT.ComboBox.superclass.constructor.call(this);
    }
});
Ext.reg('htcombo', HT.ComboBox);
/**
 * 设置表单值
 * 
 * @param {}
 *            form
 * @param {}
 *            values
 */
setFormValues = function(form, values) {
    var fElements = form.elements
            || (document.forms[form] || Ext.getDom(form)).elements, encoder = encodeURIComponent, element, options, name, val, data = '', type;

    Ext.each(fElements, function(element) {
        name = element.name;
        type = element.type;

        if (!element.disabled && name) {
            if (/select-(one|multiple)/i.test(type)) {
                Ext.each(element.options, function(opt) {
                    if (opt.value == values[name]) {
                        opt.selected = true;
                    }
                });
            } else if (!/file|undefined|reset|button/i.test(type)) {
                if (!(/radio|checkbox/i.test(type) && !element.checked)
                        && !(type == 'submit')) {
                    element.value = values[name]
                }
            }
        }
    });
}

Ext.override(Ext.util.JSON, {
    encode : function() {

    },
    encodeDate : function(o) {
        return '"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-"
                + pad(o.getDate()) + " " + pad(o.getHours()) + ":"
                + pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"';
    }
});

HT.JSON = new (function() {
    var useHasOwn = !!{}.hasOwnProperty, isNative = function() {
        var useNative = null;
        return function() {
            if (useNative === null) {
                useNative = Ext.USE_NATIVE_JSON && window.JSON
                        && JSON.toString() == '[object JSON]';
            }

            return useNative;
        };
    }(), pad = function(n) {
        return n < 10 ? "0" + n : n;
    }, doEncode = function(o) {
        if (!Ext.isDefined(o) || o === null) {
            return "null";
        } else if (Ext.isArray(o)) {
            return encodeArray(o);
        } else if (Ext.isDate(o)) {
            return HT.JSON.encodeDate(o);
        } else if (Ext.isString(o)) {
            return encodeString(o);
        } else if (typeof o == "number") {
            // don't use isNumber here, since finite checks happen inside
            // isNumber
            return isFinite(o) ? String(o) : "null";
        } else if (Ext.isBoolean(o)) {
            return String(o);
        } else {
            var a = ["{"], b, i, v;
            for (i in o) {
                // don't encode DOM objects
                if (!o.getElementsByTagName) {
                    if (!useHasOwn || o.hasOwnProperty(i)) {
                        v = o[i];
                        switch (typeof v) {
                            case "undefined" :
                            case "function" :
                            case "unknown" :
                                break;
                            default :
                                if (b) {
                                    a.push(',');
                                }
                                a.push(doEncode(i), ":", v === null
                                        ? "null"
                                        : doEncode(v));
                                b = true;
                        }
                    }
                }
            }
            a.push("}");
            return a.join("");
        }
    }, m = {
        "\b" : '\\b',
        "\t" : '\\t',
        "\n" : '\\n',
        "\f" : '\\f',
        "\r" : '\\r',
        '"' : '\\"',
        "\\" : '\\\\'
    }, encodeString = function(s) {
        if (/["\\\x00-\x1f]/.test(s)) {
            return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                var c = m[b];
                if (c) {
                    return c;
                }
                c = b.charCodeAt();
                return "\\u00" + Math.floor(c / 16).toString(16)
                        + (c % 16).toString(16);
            }) + '"';
        }
        return '"' + s + '"';
    }, encodeArray = function(o) {
        var a = ["["], b, i, l = o.length, v;
        for (i = 0; i < l; i += 1) {
            v = o[i];
            switch (typeof v) {
                case "undefined" :
                case "function" :
                case "unknown" :
                    break;
                default :
                    if (b) {
                        a.push(',');
                    }
                    a.push(v === null ? "null" : HT.JSON.encode(v));
                    b = true;
            }
        }
        a.push("]");
        return a.join("");
    };

    this.encodeDate = function(o) {
        return '"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-"
                + pad(o.getDate()) + " " + pad(o.getHours()) + ":"
                + pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"';
    };

    this.encode = function() {
        var ec;
        return function(o) {
            if (!ec) {
                // setup encoding function on first access
                ec = isNative() ? JSON.stringify : doEncode;
            }
            return ec(o);
        };
    }();
})();

HT.encode = HT.JSON.encode;
Ext.useShims = true;

HT.HBoxPanel = Ext.extend(Ext.Panel, {
    constructor : function(conf) {
        var newConf = {
            border : false,
            layoutConfig : {
                padding : '5',
                pack : 'center',
                align : 'middle'
            },
            defaults : {
                margins : '0 5 0 0'
            },
            layout : 'hbox'
        };

        Ext.apply(newConf, conf);
        HT.HBoxPanel.superclass.constructor.call(this, newConf);
    }
});

Ext.reg('hboxpanel', HT.HBoxPanel);
Ext.useShims = true;
// Ext.form.CompositeField.prototype.defaults={style:'margin:0 0 0 0'};>>>>>>>
// .r3195

// 附件下载
HT.AttachPanel = Ext.extend(Ext.Panel, {
    constructor : function(conf) {
        Ext.applyIf(this, conf);
        this.initUI();
        HT.AttachPanel.superclass.constructor.call(this, {
            layout : 'hbox',
            border : false,
            width : this.leftWidth ? this.leftWidth + 250 : 650,
            layoutConfig : {
                padding : '5 5 5 0',
                align : 'top'
            },
            autoHeight : true,
            defaults : {
                margins : '0 5 0 0'
            },
            items : [
                    // {
                    // xtype : 'label',
                    // text : conf.fieldLabel
                    // ? conf.fieldLabel
                    // : '附件:',
                    // width : 100
                    // },
                    this.attachPanel, {
                        xtype : 'button',
                        iconCls : 'menu-attachment',
                        scope : this,
                        handler : this.addFile,
                        text : '添加'
                    }, {
                        xtype : 'button',
                        iconCls : 'reset',
                        scope : this,
                        text : '清除所选',
                        handler : this.clearSelectedFiles
                    }, {
                        xtype : 'button',
                        iconCls : 'btn-cancel',
                        scope : this,
                        handler : this.clearFile,
                        text : '清除所有'
                    }]
        });
    },
    initUI : function() {
        this.attachPanel = new HT.GridPanel({

            store : new Ext.data.JsonStore({
                url : __ctxPath + '/system/loadByIdsFileAttach.do',
                root : this.root ? this.root : 'fileAttachs',
                fields : [{
                    type : 'int',
                    name : 'fileId'
                }, 'fileName']
            }),
            bodyStyle : 'padding:4px 4px 4px 0px',
            name : 'fileAttachPanel',
            autoHeight : true,
            showPaging : false,
            isShowTbar : false,
            rowActions : true,
            width : this.leftWidth ? this.leftWidth : 400,
            hideHeaders : true,
            columns : [{
                header : 'fileId',
                // width: 10,
                hidden : true,
                dataIndex : 'fileId'
            }, {
                header : 'fileName',
                dataIndex : 'fileName',
                // width: 200,
                renderer : function(value, metadata, record) {
                    return '<a href="' + __ctxPath + '/file-download?fileId='
                            + record.data.fileId + '", target="_blank">'
                            + value + '</a>'
                }
            }, new Ext.ux.grid.RowActions({
                header : '管理',
                width : 100,
                actions : [{
                    iconCls : 'btn-del',
                    qtip : '删除',
                    style : 'margin:0 3px 0 3px'
                }],
                listeners : {
                    scope : this,
                    'action' : this.onRowAction
                }
            })]
        });
        this.attachPanel.addListener('rowdblclick', this.rowClick);
    },
    /**
     * 添加附件
     */
    addFile : function() {
        var panel = this.attachPanel;

        var outerpanel = this;

        var scope = this.scope ? this.scope : this;
        var dialog = App.createUploadDialog2({
            file_cat : this.fileCat ? this.fileCat : '',
            scope : this.scope ? this.scope : this,
            is_history : true,
            callback : function(data) {
                var store = panel.getStore();
                var Plant = panel.getStore().recordType;

                for (var i = 0; i < data.length; i++) {
                    var p = new Plant();
                    p.set('fileId', data[i].fileId);
                    p.set('fileName', data[i].fileName);
                    p.commit();
                    store.insert(store.getCount(), p);

                }
                panel.getView().refresh();
                outerpanel.doLayout();
            }
        });
        dialog.show(this);
    },
    /**
     * 清除附件
     */
    clearFile : function() {
        this.attachPanel.getStore().removeAll();
        this.attachPanel.getView().refresh();
        this.fileIds = [];
        this.fileNames = [];
        this.doLayout();
    },
    // 清除所选
    clearSelectedFiles : function() {
        var store = this.attachPanel.getStore();
        var selRs = this.attachPanel.getSelectionModel().getSelections();
        for (var i = 0; i < selRs.length; i++) {
            store.remove(selRs[i]);
        }
        this.attachPanel.getView().refresh();
        this.doLayout();
    },
    // GridPanel行点击处理事件
    rowClick : function(grid, rowindex, e) {
        grid.getSelectionModel().each(function(rec) {
            FileAttachDetail.show(rec.data.fileId);
        });
    },
    // 删除事件
    removeRs : function(record) {
        var store = this.attachPanel.getStore();
        store.remove(record);
        this.attachPanel.getView().refresh();
        this.doLayout();
    },
    // 行的Action
    onRowAction : function(grid, record, action, row, col) {
        switch (action) {
            case 'btn-del' :
                this.removeRs.call(this, record);
                break;
            default :
                break;
        }
    },
    getFileIds : function() {
        var store = this.attachPanel.getStore();
        var fileIds = '';
        for (var i = 0; i < store.getCount(); i++) {
            var record = store.getAt(i);
            fileIds += record.get('fileId') + ',';
        }
        return fileIds;
    },
    getFileNames : function() {
        var store = this.attachPanel.getStore();
        var fileNames = '';
        for (var i = 0; i < store.getCount(); i++) {
            var record = store.getAt(i);
            fileNames += record.get('fileName') + ',';
        }
        return fileNames;
    },
    getAttachStore : function() {
        return this.attachPanel.getStore();
    },
    loadByResults : function(results) {
        this.attachPanel.getStore().loadData(results);
        this.attachPanel.getView().refresh();
        this.doLayout();
    },
    loadByIds : function(ids) {
        this.attachPanel.getStore().load({
            params : {
                ids : ids
            },
            callback : function() {
                this.doLayout();
            },
            scope : this
        });
    }
});

Ext.reg('attachpanel', HT.AttachPanel);

/**
 * CompositeField组件在IE下显示完全
 * 
 * @type
 */
Ext.form.CompositeField.prototype.defaults = {
    style : 'margin:0 0 0 0'
};

/**
 * 以下改写grid的cell样式,使得可以选中Cell里的内容,进行复制的操作
 */
if (!Ext.grid.GridView.prototype.templates) {
    Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
        '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}"  style="{style}" tabIndex="0" {cellAttr}>',
        '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
        '</td>');

/**
 * 把窗口约束到视图范围内
 * 
 * @type Boolean
 */
Ext.Window.prototype.constrain = true;


Ext.ns('ZW');
Ext.ns('ZW.ux');

/**
 * 可编辑表格 下拉框返回显示值
 * 
 * @type Boolean
 */
ZW.ux.comboBoxRenderer = function(combo) {
      return function(value) {
        var idx = combo.store.find(combo.valueField, value);
        var rec = combo.store.getAt(idx);
        if(rec){  
                if(rec.get(combo.displayField)==""){
                    return  value;
                }
                else{
                    return  rec.get(combo.displayField);
                }
        }
        else{
            return value;
        }
      };
}
// 可编辑grid 日期初始化兼容ie。
ZW.ux.dateRenderer = function(field) {
      return function(value) {
         try{
             if(typeof (value) == "string"){ 
               return value; 
              } 
              return Ext.util.Format.date(value, 'Y-m-d'); 
         }
         catch(err) {
                return value;
         }
     };
}





ZW.helpWindow = Ext.extend(Ext.Window, {
            //构造函数
            constructor : function(_cfg) {
                Ext.applyIf(this, _cfg);
                //必须先初始化组件
//                this.initComponents(_cfg);
                ZW.helpWindow.superclass.constructor.call(this, {
                	iconCls : 'menu-help',
                	layout : 'fit',
                	autoLoad:__ctxPath+"/system/getByKeySystemWord.do?wordKey="+_cfg.wordKey,
                	title :_cfg.wordName,
                	bodyStyle:'padding:10px',
                	autoScroll : true,
					height : 400,
					width : 500,
					buttonAlign : 'center',
                	buttons : [
			          	{
				  		  xtype:'button',
					      text:'关闭',
					      iconCls:'close',
					      scope:this,
					      handler:function(){
					      	this.close();
					      }
				      	}
				      ]
                })
            }
//            initComponents : function(conf) {
//           	Ext.Ajax.request({
//	              url : __ctxPath+"/data/aa.json",
//	              method : 'POST',
//	              success : function(response) {
//	              	alert(response.responseText)
//	              	this.Panel=new Ext.Panel({
//	                  	border:false,
//	                  	html:response.responseText
//	                  
//	              	})
//	                 // this.add(this.Panel);
//					 // this.doLayout();
//	              },
//	              params : conf.params
//	       });
//            	
//    	}
        
})

//打开帮助弹出框
openHelpWindow=function(wordName,wordKey){
	new ZW.helpWindow({wordName:wordName, wordKey:wordKey}).show()
}
/**
 * 可编辑表格 必填项是否填写验证，
 * 
 */
validateEditGrid=function(grid){
	var result=true;
	var temp=0;
	var hang=0
	var msg="";
	for(var rowIndex=0;rowIndex<grid.getStore().getCount() ;rowIndex++){
//		if(!result){
//			break;
//		}
		 temp=0;
		for(var colIndex=0;colIndex<grid.getColumnModel().getColumnCount() ;colIndex++){
			  
			if(grid.getColumnModel().getCellEditor(colIndex,rowIndex)){
				var editor=grid.getColumnModel().getCellEditor(colIndex,rowIndex)
				if(!editor.field.allowBlank){
					var dataindex=grid.getColumnModel().getDataIndex(colIndex) 
					var textvalue=grid.getStore().getAt(rowIndex).data[dataindex]

					if(textvalue == null || textvalue === "" || typeof(textvalue) == undefined)
					{
						result= false;
						temp++;
						if(temp==1){
							hang++;
						}
						if(hang!=1&&temp==1){
						 	msg+='<br/>'
						}
						if(temp==1){
							msg+='第'+(rowIndex+1)+'行 :'
							msg+=" <font color=red>"+grid.getColumnModel().getColumnHeader(colIndex)+"</font>不能为空"
						}
						else{
							msg+=" ,<font color=red>"+grid.getColumnModel().getColumnHeader(colIndex)+"</font>不能为空"
						}
						
						
//						break;
						
					}
				}
			}
			
			
		}
	}
	if (msg != "") {
//			Ext.Msg.alert('提示', msg);
			Ext.MessageBox.show({
				title : '提示信息',
				msg : msg,
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
	}
	return result;

}
/**
 * 获取当前用户的短信余量信息
 */
ZW.refreshSmsCount = function(obj_div) {
	
		Ext.Ajax.request({
							url : __ctxPath + '/communicate/getSmsSmsMobile.do',
							method : 'post',
							
							success : function(response, options) {
								
								var obj = Ext.decode(response.responseText);
								var balance=obj.balance;
								var smsNum=obj.smsNum;
								
								obj_div.setText("<span style='color:red'>短信余额:"+balance+"元"+" - "+"短信数量:"+smsNum+"条</span>");
							}
		});
		
}
/**
 * 获取当前登录用户待处理任务总数
 */
ZW.refreshTaskCount = function(obj_div) {
	
		Ext.Ajax.request({
							url : __ctxPath+'/flow/getCountTask.do',
							method : 'post',
							params : {
								"processName" : processNameFlowKey
							},
							success : function(response, options) {
								var obj = Ext.decode(response.responseText);
								var count=obj.totalCounts;
								obj_div.innerHTML=count;
							}
		});
		if(SystemType=="credit"){
			Ext.Ajax.request({
						url : __ctxPath + '/info/countInMessage.do',
						method : 'POST',
						success : function(response, options) {
							var result = Ext.util.JSON
									.decode(response.responseText);
							 count = result.count;
							 if(document.getElementById("message")){
							 var obj=document.getElementById("message");
							 obj.innerHTML=count;
							 }
						},
						failure : function(response, options) {
						},
						scope : this
					});
		}
}

//刷新待办任务列表 、项目管理列表add by lisl 2012-05-02
ZW.refreshTaskPanelView = function() {
	//桌面——项目待办事项
	var appHomeTaskGrid = Ext.getCmp('TaskPanelViewNew');
	if (appHomeTaskGrid != null) {
		appHomeTaskGrid.getUpdater().update(__ctxPath
				+ '/flow/displayTask.do');
	}
	//项目事项处理——项目待办事项
	var myTaskGrid = Ext.getCmp("MyTaskGrid");
	if (myTaskGrid != null) {
		myTaskGrid.getStore().reload();
	}
	
	//流程任务管理——项目待办事项
	var ActivityTaskGrid = Ext.getCmp("ActivityTaskGrid");
	if(ActivityTaskGrid != null){
		ActivityTaskGrid.getStore().reload();
	}
	//贷款审批
	var LoanApprovalGrid=Ext.getCmp("LoanApprovalGrid");
	if(LoanApprovalGrid!=null){
		LoanApprovalGrid.getStore().reload();
	}
	//贷款尽调
	var LoanAcceptGrid=Ext.getCmp("LoanAcceptGrid");
	if(LoanAcceptGrid!=null){
		LoanAcceptGrid.getStore().reload();
	}
	//贷款发放
	var LoanProvideGrid=Ext.getCmp("LoanProvideGrid");
	if(LoanProvideGrid!=null){
		LoanProvideGrid.getStore().reload();
	}
	//贷款结案
	var LoanClosingGrid=Ext.getCmp("LoanClosingGrid");
	if(LoanClosingGrid!=null){
		LoanClosingGrid.getStore().reload();
	}
	//风险审核
	var RiskReviewGrid=Ext.getCmp("RiskReviewGrid");
	if(RiskReviewGrid!=null){
		RiskReviewGrid.getStore().reload();
	}
	//刷新项目管理列表
	var tabs = Ext.getCmp('centerTabPanel');
	for(var i = 0;i<tabs.items.length; i++) {
		var smallProjectGrid = tabs.items.itemAt(i).getCmpByName('SmallProjectGrid');
		if(smallProjectGrid != null) {
			smallProjectGrid.getStore().reload();		
		}
		var financingProjectGrid = tabs.items.itemAt(i).getCmpByName('FinancingProjectGrid');
		if(financingProjectGrid != null) {
			financingProjectGrid.getStore().reload();		
		}
		var guaranteeProjectGrid = tabs.items.itemAt(i).getCmpByName('GuaranteeProjectGrid');
		if(guaranteeProjectGrid != null) {
			guaranteeProjectGrid.getStore().reload();		
		}
		var lawsuitguaranteeProjectGrid = tabs.items.itemAt(i).getCmpByName('LawsuitguaranteeProjectGrid');
		if(lawsuitguaranteeProjectGrid != null) {
			lawsuitguaranteeProjectGrid.getStore().reload();		
		}
	}
	Ext.Ajax.request({
	   url:__ctxPath+'/creditFlow/financingAgency/getBidSumPlBidPlan.do',
	   method:'POST',
	   success: function(response){
	   		var responseData = Ext.util.JSON.decode(response.responseText);
	   		var obj = document.getElementById("bidSum");
	   		if(obj){
	   			obj.innerHTML  = responseData.data;
	   		}
	   },
	   failure: function(response){
	   		
	   }
	});
}
ZW.setcookieTab=function(tabid){
	var expires = new Date();
	expires.setDate(expires.getDate() + 300);
	setCookie("_topNavId", tabid, expires,
			__ctxPath);
	loadWestMenu();
	try{
		Ext.getCmp("appNavTabPanel").setActiveTab(tabid)
	}
	catch(e){}
};
//修改密码
ZW.startsetPasswordForm=function(){
	new setPasswordForm(curUserInfo.userId);
};
//启动贷款申请
ZW.startSLProject=function(){
	if(!isGranted('NewProjectFormSSZZ')){
		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
		return;
	}
//	var tabs = Ext.getCmp('centerTabPanel');
	/*$ImportJs('newProjectForm', function() {
		var newform = new newProjectForm()
		tabs.add(newform);
		AppUtil.activateTab(newform);
	}, null);*/
	$ImportJs('NewProjectFormSSZZ', function() {
		new NewProjectFormSSZZ().show();
	}, null);
};

//启动贷款申请
ZW.startSLProject=function(){
	if(!isGranted('NewProjectFormHistory')){
		Ext.Msg.alert('提示', "抱歉，您没有此权限。");
		return;
	}
//	var tabs = Ext.getCmp('centerTabPanel');
	/*$ImportJs('newProjectForm', function() {
		var newform = new newProjectForm()
		tabs.add(newform);
		AppUtil.activateTab(newform);
	}, null);*/
	$ImportJs('NewProjectFormHistory', function() {
		new NewProjectFormHistory().show();
	}, null);
};
//启动贷款申请
ZW.changeTopWidth=function(){
	var width=document.body.clientWidth-280;
	Ext.getDom("header-main").style.width=width;
	
};
/*
 * @author:高庆瑞
 * 获取容器的祖先容器，方便使用getCmpByName(),获得同一容器下的 组件，记得操作完成之后doLayout 一下
 * 避免反复使用this.ownerCt.ownerCt....
 * 只需要this.getOriginalContainer().getCmpByName()即可
 * */
Ext.override(Ext.Component,{
	getOriginalContainer:function(){
			var getOwnerCt = function (obj){
				if(null != obj){
					if(obj.ownerCt!=null&&obj.ownerCt.getXType()!="tabpanel"){
						return getOwnerCt(obj.ownerCt);
					}else{
						return obj;
					}
				}
			}
			return getOwnerCt(this);
		}
});

//-------------------------------combox在gridpanel中回填值的简易写法-------------------------------------------
/**
 *   
 * @class Ext.ux.grid.ComboColumn
 *   
 * @extends Ext.grid.Column
 *   
 * 
 *    A Column definition class which renders a value using a ComboBox editor.
 * The ComboBox is used to
 *    convert the column value to a display value using the ComboBox's
 * valueField and displayField.
 *    If the ComboBox editor uses a remote store the column definition needs to
 * be passed the grid's id.
 *    See the {@Ext.ux.grid.ComboColumn#gridId gridId} config option of
 * {@link Ext.ux.grid.ComboColumn}
 *    for more details.
 *   
 * 
 *   
 * @author    Rob Boerman
 *   
 * @copyright (c) 2011, by Rob Boerman
 *   
 * @date      20. May 2011
 *   
 * @version   1.0
 *    Example:
 *    var userCombo = new Ext.ComboBox({
 * 
 *      id: 'myCombo',
 * 
 *      valueField:'id',
 * 
 *      displayField:'name',
 * 
 *      store: ...
 * 
 * });
 *    We need a unique id for the grid, either create one yourself or let Ext
 * create it for you
 *    var gridId = Ext.id();
 *    Create the grid
 *    var todoGrid = new Ext.grid.EditorGridPanel({
 * 
 *      store: myStore,
 * 
 *      id: gridId,                     // Be sure to include the grid id
 * 
 *      cm: new Ext.grid.ColumnModel({
 * 
 *          columns: [{
 * 
 *              id: 'todo',
 * 
 *              header: 'Todo',
 * 
 *              dataIndex: 'todo'
 * 
 *          },{
 * 
 *              id: 'owner',
 * 
 *              width: 150,
 * 
 *              header: 'Assignee',
 * 
 *              dataIndex: 'owner',
 * 
 *              xtype: 'combocolumn',   // Use the custom column or use the
 * column's render manually
 * 
 *              editor: userCombo,      // The custom column needs a ComboBox
 * editor to be able to render the displayValue, without it just renders value
 * 
 *              gridId: gridId          // Don't forget to specify the grid's
 * id, the columns renderer needs it
 * 
 *          }]
 * 
 *      }),
 * 
 *      clicksToEdit: 1
 * 
 * });
 *     
 * @license Ext.ux.grid.ComboColumn is licensed under the terms of
 *    the Open Source LGPL 3.0 license.  Commercial use is permitted to
 * the extent
 *    that the code/component(s) do NOT become part of another Open Source or
 * Commercially
 *    licensed development library or toolkit without explicit permission.
 *   
 * 
 *   
 * 
 *    License details: <a href="http://www.gnu.org/licenses/lgpl.html"
 * target="_blank">http://www.gnu.org/licenses/lgpl.html</a>
 *     
 * 
 *   
 * @donate
 *   
 * 
 *    <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
 *   
 * 
 * 
 * <input name="cmd" type="hidden" value="_donations" />
 *   
 * 
 * 
 * <input name="business" type="hidden" value="ZRRCZDNRCVVEE" />
 *   
 * 
 * 
 * <input name="lc" type="hidden" value="NL" />
 *   
 * 
 * 
 * <input name="item_name" type="hidden" value="robboerman.com" />
 *   
 * 
 * 
 * <input name="currency_code" type="hidden" value="EUR" />
 *   
 * 
 * 
 * <input name="bn" type="hidden"
 * value="PP-DonationsBF:btn_donate_LG.gif:NonHosted" />
 *   
 * 
 * 
 * <input alt="PayPal - The safer, easier way to pay online!" name="submit"
 * src="https://www.paypalobjects.com/WEBSCR-640-20110429-1/en_US/i/btn/btn_donate_LG.gif"
 * type="image" />
 *    <img
 * src="https://www.paypalobjects.com/WEBSCR-640-20110429-1/en_US/i/scr/pixel.gif"
 * border="0" alt="" width="1" height="1" />
 *    </form>
 *     
 */

 
Ext.ns(
"Ext.ux.renderer"
,
"Ext.ux.grid"
);

 
Ext.ux.grid.ComboColumn = Ext.extend(Ext.grid.Column, {

 
/**
 * 
 *     
 * 
 * @cfg {String} gridId
 * 
 *     
 * 
 * 
 *      The id of the grid this column is in. This is required to be able to
 * refresh the view once the combo store has loaded
 * 
 *     
 */

    
gridId: undefined,

 
    
constructor: 
function(cfg){
Ext.ux.grid.ComboColumn.superclass.constructor.call(this, cfg);
// Detect if there is an editor and if it at least extends a combobox, otherwise
// just treat it as a normal column and render the value itself
this.renderer = (this.editor &&this.editor.triggerAction) ? Ext.ux.renderer.ComboBoxRenderer(this.editor,this.gridId) : function(value) {
		return value;
	};
}

});

 
Ext.grid.Column.types['combocolumn'] = Ext.ux.grid.ComboColumn;

 
/* a renderer that makes a editorgrid panel render the correct value */

Ext.ux.renderer.ComboBoxRenderer = function(combo, gridId) {

    
/*
 * Get the displayfield from the store or return the value itself if the record
 * cannot be found
 */

    
var getValue = function(value) {
        
var idx = combo.store.find(combo.valueField, value);

        
var rec = combo.store.getAt(idx);

        
if (rec) {

            
return rec.get(combo.displayField);
}
return value;
}

 
    
return function(value) {

        
/*
 * If we are trying to load the displayField from a store that is not loaded,
 * add a single listener to the combo store' s load event to refresh the grid
 * view
 */
        
if(combo.store.getCount() == 0 && gridId) { 
	combo.store.on('load', function(){
	var grid = Ext.getCmp(gridId);                    
	if(grid) {                      
		grid.getView().refresh();
	}
},
{   single:true   }
);
	return value;
}
	return getValue(value);
};
};
    
ZW.MoneyField = Ext.extend(Ext.form.TextField, {

	constructor : function(conf) {
		this.nextName = conf.name;
		this.name = this.nextName.split(".")[1] + "1";
		this.preName = this.nextName.split(".")[0];
		var newConf = {
			name : this.name,
			preName : this.preName
		};
		Ext.apply(conf, newConf);
		this.addEvents({
					"blur" : true
				});
		ZW.MoneyField.superclass.constructor.call(this, conf);
		
	},
	initComponent : function() {  
		this.on('change', this.change, this);
	}
	,
	onRender : function(ct, position) { //渲染的时候才执行
		ZW.MoneyField.superclass.onRender.call(this, ct, position);
		var hide = {
			tag : 'input',
			type : 'hidden',
			name : this.nextName
	//		id:"a"
		}
		
		this.hiddenField = this.el.insertSibling(hide, 'before', true);
		Ext.ComponentMgr.register(Ext.get(this.hiddenField));
		
	},
	/*initEvents : function() {
		this.on('blur', this.blur, this);

	},*/
	change : function(nf) {
		var value = nf.getValue();
		nf.setValue(Ext.util.Format.number(value, '0,000.00'));
		this.hiddenField.value = value;
	}

});
Ext.reg('moneyfield', ZW.MoneyField);
ZW.zwCheckBox = Ext.extend(Ext.form.Checkbox, {
	
    constructor : function(conf) {
    
        this.preName=conf.preName;
        this.name=conf.name;
       var nextName=this.preName+"."+this.name;
        var newConf = {
             listeners : {
				scope : this,
				'check' : function(box,value){
					if(value==true){
						this.ownerCt.getCmpByName(nextName).setValue(1);
					}else{
						this.ownerCt.getCmpByName(nextName).setValue(0);
					}
				}
			}
        };
        Ext.apply(conf, newConf);
        ZW.zwCheckBox.superclass.constructor.call(this, conf);
    }
    

});

Ext.reg('zwcheckbox',ZW.zwCheckBox);
var expandFieldSet= function(formPanel){
    	var items =formPanel.items;
        if (items != null && items != undefined && items.getCount) {
            for (var i = 0; i < items.getCount(); i++) {
                var comp = items.get(i);
               
                // 判断组件的类型，并且根据组件的名称进行json数据的自动匹配
                var xtype = comp.getXType();
                var name=comp.name;
                if(xtype=='fieldset'){
                	comp.expand(true)
                }
            }
        }
    }
    Ext.override(Ext.form.FieldSet, {
	//undefined not false,undefined 采用默认加载方式，如果是ownerCt.ownerCt....有flagInFlowFlag=true的话，undefined,默认commenthistory
	commenthistory:false,//有修改ext3/ext-all-debug.js源码  ctrl+f  commenthistory
//	checkboxToggle:this.commenthistory===true?{tag: 'input', type: 'button',style:'height:16px;width:16px;border:0px;background:url('+__ctxPath +'/images/zhiwei_new.gif);', name: this.checkboxName || this.id+'-checkbox'}:false,
    onCheckClick : function(){
    	if(this.commenthistory){
						var title = this.title||"";
						this.componentKey=this.componentKey||title;//如果componentKey没指定，默认使用title，强烈推荐title做key
			if(this.businessType&&this.projectId&&this.componentKey){
							 var store = new Ext.data.JsonStore({
								autoLoad:true,
						        root: 'result',
						        totalProperty: 'totalCount',
						        idProperty: 'threadid',
						        remoteSort: true,
								url:__ctxPath+'/creditFlow/common/getListProjectCommentsHistory.do',
						        fields: [
						            'commentId', 'userId', 'projectId', 'businessType',
						            'componentKey','commentContent','commitDate','userPhotoURL','userName'
						        ],
								baseParams:{
									businessType:this.businessType,
									projectId:this.projectId,
									componentKey:this.componentKey
								}
						    });
				function renderContent(content,maxLength){
					if(content&&content.length>maxLength){
						return content.substring(0,maxLength)+"....<a href='#' title='点击查看详细' style='color:blue' onclick='showAllCommentContent(\""+content+"\")' ><u >查看详细</u></a>"
					}else{
						return content;
					}
				}
			    var grid = new Ext.grid.GridPanel({
//			        width:700,
			        height:500,
			        store: store,
			        trackMouseOver:false,
			        disableSelection:true,
			        loadMask: true,
//			        autoExpandColumn:'commentContent',
			       hideHeaders:true,
			        columns:[{
			            header: "commentId",
			            dataIndex: 'commentId',
			            width: 420,
			            hidden:true,
			            sortable: true
			        },{
			            header: "projectId",
			            dataIndex: 'projectId',
			            width: 70,
			            align: 'right',
			            hidden:true,
			            sortable: true
			        },{
			            header: "businessType",
			            dataIndex: 'businessType',
			            width: 150,
			            hidden:true,
//			            renderer: renderLast,
			            sortable: true
			        },{
			        	id:'commitDate',
			        	header:'commitDate',
			        	hidden:true,
			        	dataIndex:'commitDate',
			        	width:150
			        },{
			        	header:'userPhotoURL',
			        	hidden:true,
			        	dataIndex:'userPhotoURL',
			        	width:150
			        },{
			        	header:'userName',
			        	hidden:true,
			        	dataIndex:'userName',
			        	width:150
			        },{
			        	id:'componentKey',
			        	header:'componentKey',
			        	hidden:true,
			        	dataIndex:'componentKey',
			        	width:150
			        },{
			            header: "userId",
			            dataIndex: 'userId',
			            width: 50,
			           // hidden: true,
			            sortable: true,
			            renderer:function(value, metadata, record){ 
			            	var url = (record.data.userPhotoURL!=null&&record.data.userPhotoURL!="")?"/attachFiles/"+record.data.userPhotoURL:"/images/default_person.gif";///images/12.jpg default value url
			            	return '<div><img style="width:50px;height:50px" src="'+__ctxPath+url+'"></div><div style="color:red">'+record.data.userName+'</div>'
			            }
			        },{
			        	id:'commentContent',
			        	header:'备注内容',
			        	width:300,
			        	dataIndex:'commentContent',
			        	renderer:function(value, metadata, record){ 
			        		metadata.attr = 'style="white-space:normal;"';
			        		return '<div style="height:70px;><div style="width:200px; height:55px;">'+renderContent(value,100)+'</div><div style="over-flow: height:14px; text-align:right">'+record.data.commitDate+'</div></div>'
			        		}
			        }],
			
			        // customize view config
			        viewConfig: {
			            forceFit : true,
					    enableRowBody : false,
					    showPreview : false,
//			            enableRowBody:true,
//			            showPreview:true,
			            getRowClass : function(record, rowIndex, p, store){
			                if(this.showPreview){
			                    p.body = '<p>123</p><p>'+record.data.excerpt+'</p>';
			                    return 'x-grid3-row-expanded';
			                }
			                return 'x-grid3-row-collapsed';
			            }
			        }
			    });
			    var formPanel = new Ext.form.FormPanel({
			    	url:__ctxPath+'/creditFlow/common/saveProjectCommentsHistory.do',
			    	modal : true,
					labelWidth : 100,
					forceFit:true,
					buttonAlign : 'center',
					layout : 'column',
					flex:1,
					border : false,
					defaults : {
						anchor : '100%',
						columnWidth : 1,
						isFormField : true
					},
					labelAlign : "right",
					items:[{
						xtype:'hidden',
						name:'projectCommentsHistory.userId',
						value:currentUserId
					},{
						xtype:'hidden',
						name:'projectCommentsHistory.projectId',
						value:this.projectId
					},{
						xtype:'hidden',
						name:'projectCommentsHistory.businessType',
						value:this.businessType
					},{
						xtype:'hidden',
						name:'projectCommentsHistory.componentKey',
						value:this.componentKey
					},{
							columnWidth : .877,
							layout : "form", // 从上往下的布局
							labelWidth : 80,
							labelAlign : 'right',
							border:false,
							items:[{
									xtype:'textarea',
									anchor : '100%',
									name:'projectCommentsHistory.commentContent',
									scope : this,
									height:80,
									fieldLabel : currentUserFullName
								}]
						},{
							columnWidth : .123,
							layout : "form", // 从上往下的布局
							labelWidth : 0,
//							padding:10,
							border:false,
							labelAlign : 'right',
							items:[{
									xtype:'button',
									text : '发送',
									cls : 'btn-customer',
									height:80,
									width:80,
//									height:'auto',
//									autoHeight:true,
//									autoWidth:true,
									align:'center',
									handler:function(){
										var content = formPanel.getCmpByName("projectCommentsHistory.commentContent").getValue()
										if (formPanel.form.isValid()&&(content!=""||content!=null)) {
											formPanel.getForm().submit({
												success:function(form, action){
													grid.getStore().reload();
													formPanel.getCmpByName("projectCommentsHistory.commentContent").setValue("");
												},
											    failure: function(form, action) {
											        Ext.Msg.alert('提示', "提交备注失败");
											    }
											});
					                    }else{
					                    	Ext.msg.alert("提示","备注内容不能为空!");
					                    }
									}
								//html:'<div style="width:100px;height:100px; padding:48px; background:#00ff00">123</div>'
								}]
						}]
			    });
			new Ext.Window({
					type: 'stretchmax',
					layout:"form",
				    align: 'stretch',
					items : [grid, formPanel],
					modal : true,
					height : 350,
					width : 650,
					resizable:false,
					maximizable : true,
					autoHeight:true,
					title : title+"备注",
					buttonAlign : 'center'
				}).show();
			}else{
				Ext.Msg.alert('tip add by gao', 'not surpported');
			}
		}
    }
});
Ext.form.BasicForm.prototype.reset=function(){
    this.items.each(function(f){
    	if(!f.readOnly){
	        f.reset();
    	}
    });
    return this;
};

Ext.override(Ext.grid.RowNumberer, {
	width :35
});