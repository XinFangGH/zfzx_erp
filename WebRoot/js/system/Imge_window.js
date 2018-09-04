var imgss;
var index;
function imageMove(direction, el) {
	el.move(direction, 50, true);
}
				
function restore(el) {
					var size = el.osize;
					
					//自定义回调函数
					function center(el,callback){
						el.center();
						callback(el);
					}
					el.fadeOut({callback:function(){
						el.setSize(size.width, size.height, {callback:function(){
							center(el,function(ee){//调用回调
								ee.fadeIn();
							});
						}});
					}
					});
				}
function nextImg(el, direction) {
	/*var imgss = ['publicmodel/uploads/css/imagesn/Chrysanthemum.jpg',
			'publicmodel/uploads/css/imagesn/Hydrangeas.jpg',
			'publicmodel/uploads/css/imagesn/Jellyfish.jpg',
			'publicmodel/uploads/css/imagesn/Penguins.jpg'];*/
	var index = parseInt(el.dom.name);
	// alert(index+":"+direction+"--"+imgss.length);
	if (direction == 'left') {
		if (index == '0') {
			el.dom.src = imgss[imgss.length - 1];
			el.dom.name = imgss.length - 1;
		} else {
			el.dom.src = imgss[index - 1];
			el.dom.name = index - 1;
		}

	} else {
		if (index == (imgss.length - 1)) {
			el.dom.src = imgss[0];
			el.dom.name = 0;
		} else {
			el.dom.src = imgss[index + 1];
			el.dom.name = index + 1;
		}
	}
	//restore(el);
}	



function zoom(el, type, offset) {
	var width = el.getWidth();
	var height = el.getHeight();
	var nwidth = type ? (width * offset) : (width / offset);
	var nheight = type ? (height * offset) : (height / offset);
	var left = type ? -((nwidth - width) / 2) : ((width - nwidth) / 2);
	var top = type ? -((nheight - height) / 2) : ((height - nheight) / 2);
	el.animate({
				height : {
					to : nheight,
					from : height
				},
				width : {
					to : nwidth,
					from : width
				},
				left : {
					by : left
				},
				top : {
					by : top
				}
			}, null, null, 'backBoth', 'motion');
}

function changeImage(el){
	var type = false;
	
	
	if(event.wheelDelta>0){
		type = true;
	}else{
		type = false;
	}
	zoom(el,type,1.1);
}


my_image_window = Ext.extend(Ext.Window,{
    
	constructor:function(_cfg){
		
		
		/*imgss = ['publicmodel/uploads/css/imagesn/Chrysanthemum.jpg',
			'publicmodel/uploads/css/imagesn/Hydrangeas.jpg',
			'publicmodel/uploads/css/imagesn/Jellyfish.jpg',
			'publicmodel/uploads/css/imagesn/Penguins.jpg','publicmodel/uploads/css/imagesn/1.jpg'];*/
		Ext.applyIf(this,_cfg);
		
		this.initUI();
		my_image_window.superclass.constructor.call(this,{
			id: 'image-window',
			title : '图片浏览',
			width : 750,
			height : 500,
			maximizable :true,
			resizable : false,
			closeAction :'close',
			layout:'border',
			pagex:100,
			pagey:100,
			items : [{
				xtype : 'panel',
				region : 'center',
				layout : 'fit',
				bodyStyle : 'background-color:#E5E3DF;',
				frame : false,
				border : false,
				html : '<div id="mapPic" style="margin:auto; padding:0px auto;">'
						+'<div id="leftPre" style="display:none;blackground:#ffooff; width:100px;z-index:1002; position:absolute;left:0px">' +
								'<img style="margin-top:200px;margin-bottom:200px;float:left; z-index:1002" src="publicmodel/uploads/css/imagesn/left-1.png" /> ' +
								'</div>'
						+'<div id="rightPre" style="display:none; width:100px; z-index:1002; position:absolute;right:0px" >' +
								'<img style="margin-top:200px;margin-bottom:200px;margin-right:0px;float:right; z-index:1002" src="publicmodel/uploads/css/imagesn/right_1.png" /> ' +
								'</div>'
						+ '<img id="view-image" style="width:100%;hieght:100%;"  name="" src="" border="0"  > </div>'
			}],
			listeners : {
				'show' : function(c) {
					this.pageInit();
				}
			}
		});
	},
	initUI:function(){
		
		imgss = this.imgs;
		index = this.inde;
		//img.dom.src=imgss[i];
	},
	pageInit : function() {

		var btime;
		var etime;
		
		// alert(new Date().getTime());
		var image = Ext.get('view-image');
		image.dom.src=imgss[index];
		image.dom.name=index;
		if (image != null) {
			/*image.center();
			image.osize = {
				width:image.getWidth(),
				height:image.getHeight()
			};*/
			Ext.get('view-image').on({
				'mousemove' : {
					fn : function() {
						var x = event.offsetX;
						if (x <= 150) {
							//this.setStyle({cursor:'pointer'});
							Ext.get('leftPre').setStyle({display:'block'});
						} else if (x >= 600) {
							Ext.get('rightPre').setStyle({display:'block'});
						}else{
							Ext.get('leftPre').setStyle({display:'none'});
							Ext.get('rightPre').setStyle({display:'none'});
						}
					},
					
					scope : image
				},
				'mousewheel':{
					
						fn:function(){
							image.center();
							image.osize = {
								width:image.getWidth(),
								height:image.getHeight()
							};
							changeImage(image);
						},	
						scope : image
					}
			});
			Ext.get('leftPre').on({
				'click':function(){
					nextImg(image, 'left');
				}
				
			});
			Ext.get('rightPre').on({
				'click':function(){
					nextImg(image, 'right');
				}
				
			});
			/*Ext.get('rightPre').on({
				'mouseover':{
						fn:function(){
							Ext.get('rightPre').setStyle({display:'block'});
						}
				},
				'mouseout':{
						fn:function(){
							Ext.get('rightPre').setStyle({display:'none'});
						}
				},
				'click':{
						fn:function(){
							nextImg(image, 'right');
						}
				}
				
			});*/
			new Ext.dd.DD(image, 'pic');

			// image.center();//图片居中

			// 获得原始尺寸
			//image.osize = {
			//	width : image.getWidth(),
			//	height : image.getHeight()
			//};
			/*Ext.get('up').on('click', function() {
						imageMove('up', image);
					}); // 向上移动
			Ext.get('down').on('click', function() {
						imageMove('down', image);
					}); // 向下移动
			 Ext.get('left').on('click',function(){imageMove('left',image);});
			// //左移
			 Ext.get('right').on('click',function(){imageMove('right',image);});
			// //右移动
*/			/*Ext.get('left').on('click', function() {
						nextImg(image, 'left');
					});
			Ext.get('right').on('click', function() {
						nextImg(image, 'right');
					});*/
			/*Ext.get('in').on('click', function() {
						zoom(image, true, 1.5);
					}); //放大
			Ext.get('out').on('click', function() {
						zoom(image, false, 1.5);
					}); //缩小
			Ext.get('zoom').on('click', function() {
						restore(image);
					}); //还原
*/		}

	}/*,
	imageMove:function(direction, el){
		el.move(direction, 50, true);
	},
	nextImg : function(el, direction) {
		var imgss = ['publicmodel/uploads/css/imagesn/Chrysanthemum.jpg',
				'publicmodel/uploads/css/imagesn/Hydrangeas.jpg',
				'publicmodel/uploads/css/imagesn/Jellyfish.jpg',
				'publicmodel/uploads/css/imagesn/Penguins.jpg'];
		var index = parseInt(el.dom.name);
		// alert(index+":"+direction+"--"+imgss.length);
		if (direction == 'left') {
			if (index == '0') {
				el.dom.src = imgss[imgss.length - 1];
				el.dom.name = imgss.length - 1;
			} else {
				el.dom.src = imgss[index - 1];
				el.dom.name = index - 1;
			}

		} else {
			if (index == (imgss.length - 1)) {
				el.dom.src = imgss[0];
				el.dom.name = 0;
			} else {
				el.dom.src = imgss[index + 1];
				el.dom.name = index + 1;
			}
		}
	},
	zoom : function(el, type, offset) {
		var width = el.getWidth();
		var height = el.getHeight();
		var nwidth = type ? (width * offset) : (width / offset);
		var nheight = type ? (height * offset) : (height / offset);
		var left = type ? -((nwidth - width) / 2) : ((width - nwidth) / 2);
		var top = type ? -((nheight - height) / 2) : ((height - nheight) / 2);
		el.animate({
					height : {
						to : nheight,
						from : height
					},
					width : {
						to : nwidth,
						from : width
					},
					left : {
						by : left
					},
					top : {
						by : top
					}
				}, null, null, 'backBoth', 'motion');
	},
	restore : function(el) {
		var size = el.osize;

		// 自定义回调函数
		function center(el, callback) {
			el.center();
			callback(el);
		}
		el.fadeOut({
					callback : function() {
						el.setSize(size.width, size.height, {
									callback : function() {
										center(el, function(ee) {// 调用回调
													ee.fadeIn();
												});
									}
								});
					}
				});
	}*/
})
/*
my_image_window = new Ext.Window({
					id: 'image-window',
					title : '图片浏览',
					width : 750,
					height : 500,
					resizable : false,
					closeAction :'hide',
					layout:'border',
					pagex:100,
					pagey:100,
					items:[{
						xtype: 'panel',
						region: 'center',
						layout:'fit',
						bodyStyle : 'background-color:#E5E3DF;',
						frame:false,
						border:false,
						html :'<div id="mapPic"><div class="nav">'
							+'<div class="up" id="up"></div><div class="right" id="right"></div>'
							+'<div class="down" id="down"></div><div class="left" id="left"></div>'
							+'<div class="zoom" id="zoom"></div><div class="in" id="in"></div>'
							+'<div class="out" id="out"></div></div>'
							+'<img id="view-image" name="0" src="publicmodel/uploads/css/imagesn/girl.jpg" border="0" style="cursor: url(publicmodel/uploads/css/imagesn/openhand_8_8.cur), default;" > </div>'
					}],
					buttons: [{
						text: '取消',
						handler: function() {
							my_image_window.hide();
						}
					}],
					listeners: {
						'show': function(c) {
									pageInit();
								}
					}
				//	renderTo:'imgs'
				});


				*//**
				 * 初始化
				 *//*
				function pageInit(){
					var btime;
					var etime;
					
					//alert(new Date().getTime());
					var image = Ext.get('view-image');
					if(image!=null){
						Ext.get('view-image').on({
							'mousedown':{fn:function(){btime =new Date().getTime(); this.setStyle('cursor','url(publicmodel/uploads/css/imagesn/closedhand_8_8.cur),default;');},scope:image},
							'mouseup':{fn:function(){
								etime = new Date().getTime(); 
								if((etime-btime)>250){
									this.setStyle('cursor','url(publicmodel/uploads/css/imagesn/openhand_8_8.cur),move;');
								}else{
									var x = event.offsetX;
									if(x<=150){
										nextImg(this,'left');
									}else if(x>=600){
										nextImg(this,'right');
									}
								}
								
								},scope:image},
							'dblclick':{fn:function(){zoom(image,true,1.2);}},
							'mousemove':{fn:function(){
								var x = event.offsetX;
								if(x<=150){
									this.setStyle('cursor','url(publicmodel/uploads/css/imagesn/left.gif),default;');
								}else if(x>=600){
									this.setStyle('cursor','url(publicmodel/uploads/css/imagesn/right.gif),move;');
								}},scope:image}
								'click':{fn:function(){
									var x = event.offsetX;
									if(x<=150){
										nextImg(this,'left');
									}else if(x>=600){
										nextImg(this,'right');
									}
								},scope:image}
								//alert(event.offsetX);},scope:image}
						});
						new Ext.dd.DD(image, 'pic');
						
						//image.center();//图片居中
						
						//获得原始尺寸
						image.osize = {
							width:image.getWidth(),
							height:image.getHeight()
						};
					//alert("current name"+image.dom.name);
						Ext.get('up').on('click',function(){imageMove('up',image);}); 		//向上移动
						Ext.get('down').on('click',function(){imageMove('down',image);});	//向下移动
						//Ext.get('left').on('click',function(){imageMove('left',image);});	//左移
						//Ext.get('right').on('click',function(){imageMove('right',image);});	//右移动
						Ext.get('left').on('click',function(){nextImg(image,'left');});
						Ext.get('right').on('click',function(){nextImg(image,'right');});
						Ext.get('in').on('click',function(){zoom(image,true,1.5);});		//放大
						Ext.get('out').on('click',function(){zoom(image,false,1.5);});		//缩小
						Ext.get('zoom').on('click',function(){restore(image);});			//还原
					}
				};


				*//**
				 * 图片移动
				 *//*
				function imageMove(direction, el) {
					el.move(direction, 50, true);
				}
				
				function nextImg(el,direction){
					var imgss = ['publicmodel/uploads/css/imagesn/Chrysanthemum.jpg','publicmodel/uploads/css/imagesn/Hydrangeas.jpg','publicmodel/uploads/css/imagesn/Jellyfish.jpg','publicmodel/uploads/css/imagesn/Penguins.jpg'];
					var index = parseInt(el.dom.name);
					//alert(index+":"+direction+"--"+imgss.length);
					if(direction=='left'){
						if(index=='0'){
							el.dom.src=imgss[imgss.length-1];
							el.dom.name=imgss.length-1;
						}else{
							el.dom.src=imgss[index-1];
							el.dom.name=index-1;
						}
							
						
					}else{
						if(index==(imgss.length-1)){
							el.dom.src=imgss[0];
							el.dom.name=0;
						}else{
							el.dom.src=imgss[index+1];
							el.dom.name=index+1;
						}
					}
				}	

				*//**
				 * 
				 * @param el 图片对象
				 * @param type true放大,false缩小
				 * @param offset 量
				 *//*
				function zoom(el,type,offset){
					var width = el.getWidth();
					var height = el.getHeight();
					var nwidth = type ? (width * offset) : (width / offset);
					var nheight = type ? (height * offset) : (height / offset);
					var left = type ? -((nwidth - width) / 2):((width - nwidth) / 2);
					var top =  type ? -((nheight - height) / 2):((height - nheight) / 2); 
					el.animate(
						{
					        height: {to: nheight, from: height},
					        width: {to: nwidth, from: width},
					        left: {by:left},
					        top: {by:top}
				        },
				        null,      
					    null,     
					    'backBoth',
					    'motion'
					);
				}


				*//**
				 * 图片还原
				 *//*
				function restore(el) {
					var size = el.osize;
					
					//自定义回调函数
					function center(el,callback){
						el.center();
						callback(el);
					}
					el.fadeOut({callback:function(){
						el.setSize(size.width, size.height, {callback:function(){
							center(el,function(ee){//调用回调
								ee.fadeIn();
							});
						}});
					}
					});
				}
*/		//	my_image_window.show();	