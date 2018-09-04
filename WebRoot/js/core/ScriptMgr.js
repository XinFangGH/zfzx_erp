/**
 * 用于动态加载js
  *  sample is here
  *	  ScriptMgr.load({
  *	  scripts: ['/js/other-prerequisite.js', '/js/other.js'],
  *	  callback: function() {
  *	    var other = new OtherObject();
  *	    alert(other); //just loaded
  *	  }
  *	}); 
  */
ScriptLoader = function() {
	this.timeout = 10;
	this.scripts = [];
	this.disableCaching = true;//false
	this.loadMask = null;
};

ScriptLoader.prototype = {
	showMask : function() {
		if (!this.loadMask) {
			this.loadMask = new Ext.LoadMask(Ext.getBody());
			this.loadMask.show();
		}
	},

	hideMask : function() {
		if (this.loadMask) {
			this.loadMask.hide();
			this.loadMask = null;
		}
	},

	processSuccess : function(response) {
		if (typeof response.argument.callback == 'function') {
			response.argument.callback.call(response.argument.scope,
			response.responseText,response.argument.url,response.argument.index);
		}
	},

	processFailure : function(response) {
		this.hideMask();
		Ext.MessageBox.show({
					title : '应用程序出错',
					msg : 'Js脚本库加载出错，服务器可能停止，请联系管理员。文件路径：' + response.argument.url,
					closable : false,
					icon : Ext.MessageBox.ERROR,
					minWidth : 200
				});
		setTimeout(function() {Ext.MessageBox.hide();}, 3);
	},

	load : function(url, callback) {
		
		var cfg, callerScope, index;
		if (typeof url == 'object') { // must be config object
			cfg = url;
			url = cfg.url;	
			index=cfg.index;
			callback = callback || cfg.callback;
			callerScope = cfg.scope;
			if (typeof cfg.timeout != 'undefined') {
				this.timeout = cfg.timeout;
			}
			if (typeof cfg.disableCaching != 'undefined') {
				this.disableCaching = cfg.disableCaching;
			}
		}
		if (this.scripts[url]) {
			if (typeof callback == 'function') {
				callback.call(callerScope || window);
			}
			return null;
		}
		this.showMask();
		Ext.Ajax.request({
					url : url,
					success : this.processSuccess,
					failure : this.processFailure,
					scope : this,
					timeout : (this.timeout * 1000),
					disableCaching : this.disableCaching,
					argument : {
						'url' : url,
						'scope' : callerScope || window,
						'callback' : callback,
						'options' : cfg,
						'index':index
					}
				});
	}
};


ScriptLoaderMgr=function() {
	//缓存加载内容
	this.mdCache=[];
	this.loader = new ScriptLoader();
	var loader=this.loader;
	
	this.load = function(o) {
		this.loader.scope=o.scope;
		if (!Ext.isArray(o.scripts)) {
			o.scripts = [o.scripts];
		}
		//记数器
		o.lfiles=0;
		this.mdCache.length=0;
		var mdCache=this.mdCache;
		for(var i=0;i<o.scripts.length;i++){
			o.url = o.scripts[i];
			o.index=i;
			this.loader.load(o, function(rs,url,idx) {
				o.scope = this;
				mdCache[idx]={
					content:rs
				};
				o.lfiles++;
				if(o.lfiles>=o.scripts.length){
					//是否执行加载的js
					for(var j=0;j<mdCache.length;j++){
						try{
							window.execScript ? window.execScript(mdCache[j].content) : window.eval(mdCache[j].content);
						}catch(ex){
							
						}
					}
					loader.hideMask();
					if(o.callback!=null){
						o.callback.call(this);
					}
				}
			});
		}
	};
};

ScriptMgr = new ScriptLoaderMgr();
